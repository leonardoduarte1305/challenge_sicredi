package br.dev.leoduarte.sicredi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import br.dev.leoduarte.sicredi.controller.dto.request.PautaDTOE;
import br.dev.leoduarte.sicredi.controller.dto.response.PautaDTOS;
import br.dev.leoduarte.sicredi.exception.EntidadeNaoEncontradaException;
import br.dev.leoduarte.sicredi.model.Associado;
import br.dev.leoduarte.sicredi.model.Pauta;
import br.dev.leoduarte.sicredi.repository.AssociadoRepository;
import br.dev.leoduarte.sicredi.repository.PautaRepository;
import br.dev.leoduarte.sicredi.utils.AuxilioParaTestes;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

	private PautaRepository pautaRepo;
	private AssociadoRepository assocRepo;
	private UriComponentsBuilder uriBuilder;
	private PautaService service;

	@BeforeEach
	void setUp() {
		pautaRepo = mock(PautaRepository.class);
		assocRepo = mock(AssociadoRepository.class);
		service = new PautaService(pautaRepo, assocRepo);
		uriBuilder = UriComponentsBuilder.newInstance();
	}

	@Test
	public void deveRetornarOkSeSalvarNovaPauta() {
		Pauta pauta = new AuxilioParaTestes().novaPauta(2L, "Pauta teste", LocalDateTime.now().plusMinutes(5));

		when(pautaRepo.save(any(Pauta.class))).thenReturn(pauta);

		ResponseEntity<PautaDTOS> retorno = service.criarNova(new PautaDTOE(), uriBuilder);

		Assertions.assertEquals(retorno.getBody().getId(), 2);
		Assertions.assertEquals(retorno.getBody().getNomeAssembleia(), "Pauta teste");
		Assertions.assertEquals(retorno.getStatusCodeValue(), 201);
	}

	@Test
	public void deveRetornarOkSeGerarCaminhoCorreto() {
		Pauta pauta = new AuxilioParaTestes().novaPauta(2L, "Pauta teste", LocalDateTime.now().plusMinutes(5));
		when(pautaRepo.save(any(Pauta.class))).thenReturn(pauta);

		List<String> esperado = List.of("/pautas/2");
		ResponseEntity<PautaDTOS> retorno = service.criarNova(new PautaDTOE(), uriBuilder);

		Assertions.assertEquals(esperado.get(0), retorno.getHeaders().get("Location").get(0));
	}

	@Test
	public void deveRetornarOkSeEncontrarEntidade() {
		Pauta pauta = new AuxilioParaTestes().novaPauta(2L, "Pauta", LocalDateTime.now().plusMinutes(5));
		when(pautaRepo.findById(anyLong())).thenReturn(Optional.of(pauta));

		ResponseEntity<PautaDTOS> retorno = service.pesquisarPorId(2L);

		Assertions.assertEquals(retorno.getBody().getId(), 2);
		Assertions.assertEquals(retorno.getBody().getNomeAssembleia(), "Pauta");
		Assertions.assertEquals(retorno.getStatusCodeValue(), 200);
	}

	@Test
	public void deveLancarExcecaoSeNaoEncontrarEntidade() {
		Exception e = assertThrows(EntidadeNaoEncontradaException.class, () -> {
			Optional.of(service.pesquisarPorId(null)).orElseThrow();
		});

		String esperada = "null";
		String recebida = e.getMessage();

		Assertions.assertTrue(recebida.endsWith(esperada));

	}

	@Test
	public void deveRetornarOkSeAdicionarAssociado() {
		Pauta obj = new AuxilioParaTestes().novaPauta(3L, "Teste", LocalDateTime.now().plusMinutes(5));
		when(pautaRepo.findById(3L)).thenReturn(Optional.of(obj));

		Associado associado = new AuxilioParaTestes().novoAssociadoComId(3L, "Associado");
		when(assocRepo.findById(3L)).thenReturn(Optional.of(associado));

		Pauta pautaComAssociado = new AuxilioParaTestes().novaPauta(3L, "Nova Pauta",
				LocalDateTime.now().plusMinutes(5));
		obj.adicionarAssociado(associado);
		when(pautaRepo.save(any(Pauta.class))).thenReturn(pautaComAssociado);

		ResponseEntity<PautaDTOS> retorno = service.adicionarAssociado(obj.getId(), associado.getId(), uriBuilder);
		Assertions.assertEquals(retorno.getStatusCodeValue(), 200);
	}

}
