package com.mercadolivre.cliente_service.application.api;

import java.util.UUID;

import com.mercadolivre.cliente_service.domain.Cliente;

import lombok.Value;

@Value
public class ClienteResponse {

	private UUID idCliente;

	public ClienteResponse(Cliente cliente) {
		this.idCliente = cliente.getIdCliente();
	}

}