package com.mercadolivre.cliente_service.application.service;

import org.springframework.stereotype.Service;

import com.mercadolivre.cliente_service.application.api.ClienteRequest;
import com.mercadolivre.cliente_service.application.api.ClienteResponse;
import com.mercadolivre.cliente_service.application.repository.ClienteRepository;
import com.mercadolivre.cliente_service.domain.model.Cliente;

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

}
