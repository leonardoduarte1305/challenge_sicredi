package br.dev.leoduarte.sicredi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.leoduarte.sicredi.controller.dto.request.AssociadoDTOE;
import br.dev.leoduarte.sicredi.controller.dto.response.AssociadoDTOS;
import br.dev.leoduarte.sicredi.exception.EntidadeNaoEncontradaException;
import br.dev.leoduarte.sicredi.model.Associado;
import br.dev.leoduarte.sicredi.repository.AssociadoRepository;
import br.dev.leoduarte.sicredi.utils.AuxilioParaTestes;

@ExtendWith(MockitoExtension.class)
public class AssociadoServiceTest {

	private AssociadoRepository repository;
	private AssociadoService service;
	private UriComponentsBuilder uriBuilder;

	@BeforeEach
	void setUp() {
		repository = mock(AssociadoRepository.class);
		service = new AssociadoService(repository);
		uriBuilder = UriComponentsBuilder.newInstance();
	}

	@Test
	public void deveRetornarOkSeSalvarAssociado() {
		Associado salvo = new AuxilioParaTestes().novoAssociadoComId(1L, "Teste");

		when(repository.save(any(Associado.class))).thenReturn(salvo);

		ResponseEntity<AssociadoDTOS> retorno = service.criarNovo(new AssociadoDTOE(""), uriBuilder);

		Assertions.assertEquals(retorno.getBody().getId(), 1);
		Assertions.assertEquals(retorno.getBody().getNome(), "Teste");
		Assertions.assertEquals(retorno.getStatusCodeValue(), 201);
	}

	@Test
	public void deveRetornarOkSeGerarCaminhoCorreto() {
		Associado salvo = new AuxilioParaTestes().novoAssociadoComId(2L, "Teste");

		when(repository.save(any(Associado.class))).thenReturn(salvo);

		List<String> esperado = List.of("/associados/2");
		ResponseEntity<AssociadoDTOS> retorno = service.criarNovo(new AssociadoDTOE(""), uriBuilder);
		Assertions.assertEquals(esperado.get(0), retorno.getHeaders().get("Location").get(0));

	}

	@Test
	public void deveRetornarOkSeEncontrarEntidade() {
		Associado salvo = new AuxilioParaTestes().novoAssociadoComId(3L, "Entidade");

		when(repository.findById(anyLong())).thenReturn(Optional.of(salvo));

		ResponseEntity<AssociadoDTOS> retorno = service.pesquisarPorId(4L);

		Assertions.assertEquals(retorno.getBody().getId(), 3);
		Assertions.assertEquals(retorno.getBody().getNome(), "Entidade");
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
}
