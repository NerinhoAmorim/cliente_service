package com.mercadolivre.cliente_service.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mercadolivre.cliente_service.application.api.ClienteListResponse;
import com.mercadolivre.cliente_service.application.api.ClienteRequest;
import com.mercadolivre.cliente_service.application.api.ClienteResponse;

@Service
public interface ClienteService {

	ClienteResponse criaCliente(ClienteRequest request);
	List<ClienteListResponse> getAllClientes();

}
