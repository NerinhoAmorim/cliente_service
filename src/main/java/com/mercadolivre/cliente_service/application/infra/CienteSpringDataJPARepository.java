package com.mercadolivre.cliente_service.application.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercadolivre.cliente_service.domain.Cliente;

public interface CienteSpringDataJPARepository extends JpaRepository<Cliente, UUID> {

}
