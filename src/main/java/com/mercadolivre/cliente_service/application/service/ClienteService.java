package com.mercadolivre.cliente_service.application.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.mercadolivre.cliente_service.application.api.ClienteAlteracaoRequest;
import com.mercadolivre.cliente_service.application.api.ClienteDetalhadoResponse;
import com.mercadolivre.cliente_service.application.api.ClienteListResponse;
import com.mercadolivre.cliente_service.application.api.ClienteRequest;
import com.mercadolivre.cliente_service.application.api.ClienteResponse;
import com.mercadolivre.cliente_service.application.api.PageResponse;


public interface ClienteService {

	ClienteResponse criaCliente(ClienteRequest request);
	ClienteDetalhadoResponse buscaClientePorId(UUID idCliente);
	void deletaCliente(UUID idCliente);
	void atualizaParcial(UUID idCliente, ClienteAlteracaoRequest request);
	PageResponse<ClienteListResponse> getAllClientes(
			String nome,
			String email,
			String cpf, 
			String telefone,
			Pageable pageable
			);

}
