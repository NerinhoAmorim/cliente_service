package com.mercadolivre.cliente_service.application.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.mercadolivre.cliente_service.application.infra.entity.ClienteEntity;
import com.mercadolivre.cliente_service.domain.Cliente;

public interface ClienteRepository {

    Cliente save(Cliente cliente);
    boolean existsByCpf(String cpf);
    Optional<Cliente> findById(UUID idCliente);
    boolean existsById(UUID idCliente);
    void deleteById(UUID idCliente);
    Page<Cliente> findAll(Specification<ClienteEntity> filtro, Pageable pageable);
}
