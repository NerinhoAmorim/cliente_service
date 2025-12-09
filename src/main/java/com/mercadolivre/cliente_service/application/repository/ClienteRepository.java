package com.mercadolivre.cliente_service.application.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mercadolivre.cliente_service.domain.Cliente;

public interface ClienteRepository {

    Cliente save(Cliente cliente);
    Page<Cliente> getAllClientes(Pageable pageable);
    Cliente buscaClientePorId(UUID idCliente);
    void deletaCliente(UUID idCliente);
}
