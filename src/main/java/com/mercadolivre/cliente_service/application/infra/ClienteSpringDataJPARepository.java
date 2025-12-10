package com.mercadolivre.cliente_service.application.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mercadolivre.cliente_service.application.infra.entity.ClienteEntity;

public interface ClienteSpringDataJPARepository
extends JpaRepository<ClienteEntity, UUID>, JpaSpecificationExecutor<ClienteEntity> {
}

