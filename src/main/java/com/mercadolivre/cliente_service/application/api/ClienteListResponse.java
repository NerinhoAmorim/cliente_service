package com.mercadolivre.cliente_service.application.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mercadolivre.cliente_service.domain.Cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClienteListResponse {
	private UUID idCliente;
	private String nomeCompleto;
	private String cpf;
	private String telefone;
	private String email;

	public ClienteListResponse(Cliente cliente) {
		this.idCliente = cliente.getIdCliente();
		this.nomeCompleto = cliente.getNomeCompleto();
		this.cpf = cliente.getCpf();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
	}

	public static List<ClienteListResponse> converte(List<Cliente> clientes) {
		return clientes.stream()
				.map(ClienteListResponse ::new)
				.collect(Collectors.toList());
	}
	public static ClienteListResponse fromDomain(Cliente cliente) {
	    return new ClienteListResponse(cliente);
	}


}
