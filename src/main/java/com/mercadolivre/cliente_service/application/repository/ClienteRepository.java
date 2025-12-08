package com.mercadolivre.cliente_service.application.repository;

import java.util.List;
import java.util.UUID;

import com.mercadolivre.cliente_service.domain.Cliente;

public interface ClienteRepository {

	Cliente save(Cliente cliente);
	List<Cliente> getAllClientes();
	Cliente buscaClientePorId(UUID idCliente);
	void deletaCliente(UUID idCliente);


}
