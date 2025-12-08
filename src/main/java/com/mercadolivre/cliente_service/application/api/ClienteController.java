package com.mercadolivre.cliente_service.application.api;

import java.util.List;
import java.util.UUID;

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
	public List<ClienteListResponse> getAllClientes() {
		log.info("[inicia] ClienteController - getAllClientes");
		List<ClienteListResponse> clientes = clienteService.getAllClientes();
		log.info("[finaliza] ClienteController - getAllClientes");
		return clientes;
	}

	@Override
	public ClienteDetalhadoResponse getClientePorId(final UUID idCliente) {
		log.info("[Inicia] ClienteController - getClientePorId | idCliente={}", idCliente);
		ClienteDetalhadoResponse clienteDetalhado = clienteService.buscaClientePorId(idCliente);
		log.info("[Finaliza] ClienteController - getClientePorId | idCliente={}", idCliente);
		return clienteDetalhado;
	}


}
