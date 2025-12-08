package com.mercadolivre.cliente_service.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mercadolivre.cliente_service.application.api.ClienteDetalhadoResponse;
import com.mercadolivre.cliente_service.application.api.ClienteListResponse;
import com.mercadolivre.cliente_service.application.api.ClienteRequest;
import com.mercadolivre.cliente_service.application.api.ClienteResponse;
import com.mercadolivre.cliente_service.application.repository.ClienteRepository;
import com.mercadolivre.cliente_service.domain.Cliente;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2

public class ClienteApplicationService implements ClienteService {

	private final ClienteRepository clienteRepository;

	@Override
	public ClienteResponse criaCliente(ClienteRequest request) {
		log.info("[start] ClienteApplicationService - criaCliente");
		Cliente cliente = new Cliente(request);
		clienteRepository.save(cliente);
		log.info("[finish] ClienteApplicationService - criaCliente");
		return new ClienteResponse(cliente);
	}

	@Override
	public List<ClienteListResponse> getAllClientes() {
		log.info("[inicia] ClienteApplicationService - getAllClientes");
		List<Cliente> clientes = clienteRepository.getAllClientes();
		log.info("[finaliza] ClienteApplicationService - getAllClientes");
		return ClienteListResponse.converte(clientes);
	}

	@Override
	public ClienteDetalhadoResponse buscaClientePorId(final UUID idCliente) {
		log.info("[Inicia] ClienteApplicationService - buscaClientePorId | idCliente={}", idCliente);
		Cliente cliente = clienteRepository.buscaClientePorId(idCliente);
		log.info("[Finaliza] ClienteApplicationService - buscaClientePorId | idCliente={}", idCliente);
		return new ClienteDetalhadoResponse(cliente);
	}

}
