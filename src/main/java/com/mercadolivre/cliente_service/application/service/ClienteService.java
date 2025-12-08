package com.mercadolivre.cliente_service.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mercadolivre.cliente_service.application.api.ClienteDetalhadoResponse;
import com.mercadolivre.cliente_service.application.api.ClienteListResponse;
import com.mercadolivre.cliente_service.application.api.ClienteRequest;
import com.mercadolivre.cliente_service.application.api.ClienteResponse;

@Service
public interface ClienteService {

	ClienteResponse criaCliente(ClienteRequest request);
	List<ClienteListResponse> getAllClientes();
	ClienteDetalhadoResponse buscaClientePorId(UUID idCliente);
	void deletaCliente(UUID idCliente);

}
