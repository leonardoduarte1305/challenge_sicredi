package br.dev.leoduarte.sicredi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.leoduarte.sicredi.controller.dto.response.Resultado;
import br.dev.leoduarte.sicredi.exception.AssociadoNaoCadastradoNaPautaException;
import br.dev.leoduarte.sicredi.exception.VotoInvalidoException;
import br.dev.leoduarte.sicredi.exception.VotoJaComputadoException;
import br.dev.leoduarte.sicredi.model.Associado;
import br.dev.leoduarte.sicredi.model.Pauta;
import br.dev.leoduarte.sicredi.model.VotoNaPauta;
import br.dev.leoduarte.sicredi.repository.AssociadoRepository;
import br.dev.leoduarte.sicredi.repository.PautaRepository;
import br.dev.leoduarte.sicredi.repository.VotoNaPautaRepository;
import br.dev.leoduarte.sicredi.utils.AuxilioParaTestes;

@ExtendWith(MockitoExtension.class)
public class VotoNaPautaServiceTest {

	private PautaService pautaService;
	private VotoNaPautaRepository votoNaPautaRepo;
	private VotoNaPautaService votoNaPautaService;
	private PautaRepository pautaRepo;
	private AssociadoRepository assocRepo;
	private UriComponentsBuilder uriBuilder;

	@BeforeEach
	void setUp() {
		votoNaPautaRepo = mock(VotoNaPautaRepository.class);
		pautaRepo = mock(PautaRepository.class);
		assocRepo = mock(AssociadoRepository.class);
		uriBuilder = UriComponentsBuilder.newInstance();
		pautaService = new PautaService(pautaRepo, assocRepo);
		votoNaPautaService = new VotoNaPautaService(pautaService, votoNaPautaRepo);
	}

	@Test
	void deveRetornarOkSeSalvarNovoVoto() {
		Pauta pauta = new AuxilioParaTestes().novaPauta(2L, "Pauta teste", LocalDateTime.now().plusMinutes(5));
		when(pautaRepo.findById(2L)).thenReturn(Optional.of(pauta));

		Associado associado = new AuxilioParaTestes().novoAssociadoComId(3L, "Associado");
		when(assocRepo.findById(3L)).thenReturn(Optional.of(associado));

		pauta.adicionarAssociado(associado);

		when(votoNaPautaRepo.save(any(VotoNaPauta.class))).thenReturn(new VotoNaPauta(pauta, associado, 0L));

		ResponseEntity<Object> retorno = votoNaPautaService.adicionarVotoDoAssociado(pauta.getId(), associado.getId(),
				0L, uriBuilder);

		String esperado = "Voto cadastrado";
		Assertions.assertTrue(retorno.getBody().toString().contains(esperado));
	}

	@Test
	void deveLancarExcecaoSeAssociadoNaoCadastradoTentarVotar() {
		Pauta pauta = new AuxilioParaTestes().novaPauta(2L, "Pauta teste", LocalDateTime.now().plusMinutes(5));
		when(pautaRepo.findById(2L)).thenReturn(Optional.of(pauta));

		Associado associado = new AuxilioParaTestes().novoAssociadoComId(3L, "Associado");
		when(assocRepo.findById(3L)).thenReturn(Optional.of(associado));

		Exception e = assertThrows(AssociadoNaoCadastradoNaPautaException.class, () -> {
			Optional.of(votoNaPautaService.adicionarVotoDoAssociado(pauta.getId(), associado.getId(), 1L, uriBuilder))
					.orElseThrow();
		});

		String esperada = "Este associado não pode votar nesta pauta";
		String recebida = e.getMessage();

		Assertions.assertTrue(recebida.startsWith(esperada));
	}

	@Test
	void deveLancarExcecaoSeTentarVotarNovamente() {
		// Pego uma pauta
		Pauta pauta = new AuxilioParaTestes().novaPauta(2L, "Pauta teste", LocalDateTime.now().plusMinutes(5));
		when(pautaRepo.findById(2L)).thenReturn(Optional.of(pauta));

		// Pego um Associado
		Associado associado = new AuxilioParaTestes().novoAssociadoComId(3L, "Associado");
		when(assocRepo.findById(3L)).thenReturn(Optional.of(associado));

		// Associo o associado a uma pauta
		pauta.adicionarAssociado(associado);

		// Cadastro o voto dele
		VotoNaPauta votado = new VotoNaPauta(pauta, associado, 0L);
		pauta.setVotos(List.of(votado));

		Exception e = assertThrows(VotoJaComputadoException.class, () -> {
			Optional.of(votoNaPautaService.adicionarVotoDoAssociado(pauta.getId(), associado.getId(), 0L, uriBuilder))
					.orElseThrow();
		});

		String esperada = "Associado já votou nesta pauta: ";
		String recebida = e.getMessage();

		Assertions.assertTrue(recebida.startsWith(esperada));
	}

	@Test
	void deveRetornarOkSeContabilizarCorretamenteAVotacaoComSim() {
		// Pego uma pauta
		Pauta pauta = new AuxilioParaTestes().novaPauta(2L, "Pauta teste", LocalDateTime.now().plusMinutes(5));
		when(pautaRepo.findById(2L)).thenReturn(Optional.of(pauta));

		// Pego um Associado
		Associado associado = new AuxilioParaTestes().novoAssociadoComId(3L, "Associado");

		// Associo o associado a uma pauta
		pauta.adicionarAssociado(associado);

		// Cadastro o voto dele
		VotoNaPauta votado = new VotoNaPauta(pauta, associado, 0L);
		pauta.setVotos(List.of(votado));

		int esperadoSIM = 1;
		int esperadoNAO = 0;

		ResponseEntity<Resultado> retorno = votoNaPautaService.contabilizarVotacao(pauta.getId());

		Assertions.assertEquals(esperadoSIM, retorno.getBody().getQtdVotosSim());
		Assertions.assertEquals(esperadoNAO, retorno.getBody().getQtdVotosNao());
	}

	@Test
	void deveRetornarOkSeContabilizarCorretamenteAVotacaoComNao() {
		// Pego uma pauta
		Pauta pauta = new AuxilioParaTestes().novaPauta(2L, "Pauta teste", LocalDateTime.now().plusMinutes(5));
		when(pautaRepo.findById(2L)).thenReturn(Optional.of(pauta));

		// Pego um Associado
		Associado associado = new AuxilioParaTestes().novoAssociadoComId(3L, "Associado");

		// Associo o associado a uma pauta
		pauta.adicionarAssociado(associado);

		// Cadastro o voto dele
		VotoNaPauta votado = new VotoNaPauta(pauta, associado, 1L);
		pauta.setVotos(List.of(votado));

		int esperadoSIM = 0;
		int esperadoNAO = 1;

		ResponseEntity<Resultado> retorno = votoNaPautaService.contabilizarVotacao(pauta.getId());

		Assertions.assertEquals(esperadoSIM, retorno.getBody().getQtdVotosSim());
		Assertions.assertEquals(esperadoNAO, retorno.getBody().getQtdVotosNao());
	}

	@Test
	void deveRetornarOkSeRetornarContagemDevotosIguaisAZero() {
		Pauta pauta = new AuxilioParaTestes().novaPauta(2L, "Pauta teste", LocalDateTime.now().plusMinutes(5));
		when(pautaRepo.findById(2L)).thenReturn(Optional.of(pauta));

		int esperadoSIM = 0;
		int esperadoNAO = 0;

		ResponseEntity<Resultado> retorno = votoNaPautaService.contabilizarVotacao(pauta.getId());

		Assertions.assertEquals(esperadoSIM, retorno.getBody().getQtdVotosSim());
		Assertions.assertEquals(esperadoNAO, retorno.getBody().getQtdVotosNao());
	}

	@Test
	void deveLancarExcecaoSeTentarInsetirVotoInvalido() {
		Associado associado = new AuxilioParaTestes().novoAssociadoComId(3L, "Associado");
		when(assocRepo.findById(3L)).thenReturn(Optional.of(associado));

		Pauta pauta = new AuxilioParaTestes().novaPauta(2L, "Pauta teste", LocalDateTime.now().plusMinutes(5));
		pauta.adicionarAssociado(associado);
		when(pautaRepo.findById(2L)).thenReturn(Optional.of(pauta));

		Exception e = assertThrows(VotoInvalidoException.class, () -> {
			Optional.of(votoNaPautaService.adicionarVotoDoAssociado(pauta.getId(), associado.getId(), 5L, uriBuilder))
					.orElseThrow();
		});

		String esperada = "Alternativa de voto inválida: ";
		String recebida = e.getMessage();

		Assertions.assertTrue(recebida.startsWith(esperada));
	}
}
