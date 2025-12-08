package com.mercadolivre.cliente_service.application.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercadolivre.cliente_service.application.infra.entity.ClienteEntity;

public interface CienteSpringDataJPARepository extends JpaRepository<ClienteEntity, UUID> {

}
