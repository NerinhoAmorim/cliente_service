package com.mercadolivre.cliente_service.application.api;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.cliente_service.application.service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/v1/cliente")
public class ClienteController implements ClienteAPI {

	private final ClienteService clienteService;

	@Override
	public ClienteResponse postcliente(@Valid @RequestBody ClienteRequest request) {
		log.info("[inicia] ClienteController - postCliente");
		ClienteResponse cliente = clienteService.criaCliente(request);
		log.info("[finaliza] ClienteController - postCliente");
		return cliente;
	}

	@Override
	public PageResponse<ClienteListResponse> getAllClientes(Pageable pageable) {
	    var page = clienteService.getAllClientes(pageable);
	    return new PageResponse<>(page);
	}


	@Override
	public ClienteDetalhadoResponse getClientePorId(final UUID idCliente) {
		log.info("[Inicia] ClienteController - getClientePorId | idCliente={}", idCliente);
		ClienteDetalhadoResponse clienteDetalhado = clienteService.buscaClientePorId(idCliente);
		log.info("[Finaliza] ClienteController - getClientePorId | idCliente={}", idCliente);
		return clienteDetalhado;
	}

	@Override
	public void deleteCliente(UUID idCliente) {
		log.info("[inicia] ClienteController - deleteCliente | idCliente={}", idCliente);
		clienteService.deletaCliente(idCliente);
		log.info("[finaliza] ClienteController - deleteCliente | idCliente={}", idCliente);
	}

	@Override
	public void alteraCliente(UUID idCliente, ClienteAlteracaoRequest request) {
		log.info("[inicia] ClienteController - alteraCliente | id={}", idCliente);
		clienteService.atualizaParcial(idCliente, request);
		log.info("[finaliza] ClienteController - alteraCliente | id={}", idCliente);

	}

}
