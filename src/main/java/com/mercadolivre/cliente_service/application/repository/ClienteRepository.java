package com.mercadolivre.cliente_service.application.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercadolivre.cliente_service.domain.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

}
