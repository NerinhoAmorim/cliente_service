package com.mercadolivre.cliente_service.application.repository;

import java.util.List;

import com.mercadolivre.cliente_service.domain.Cliente;

public interface ClienteRepository {

	Cliente save(Cliente cliente);
	List<Cliente> getAllClientes();


}
