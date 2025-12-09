package com.mercadolivre.cliente_service.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mercadolivre.cliente_service.application.api.ClienteAlteracaoRequest;
import com.mercadolivre.cliente_service.application.api.ClienteDetalhadoResponse;
import com.mercadolivre.cliente_service.application.api.ClienteListResponse;
import com.mercadolivre.cliente_service.application.api.ClienteRequest;
import com.mercadolivre.cliente_service.application.api.ClienteResponse;


public interface ClienteService {

	ClienteResponse criaCliente(ClienteRequest request);
	Page<ClienteListResponse> getAllClientes(Pageable pageable);
	ClienteDetalhadoResponse buscaClientePorId(UUID idCliente);
	void deletaCliente(UUID idCliente);
	void atualizaParcial(UUID idCliente, ClienteAlteracaoRequest request);

}
