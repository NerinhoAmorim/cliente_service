package com.mercadolivre.cliente_service.application.infra;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.mercadolivre.cliente_service.application.infra.entity.ClienteEntity;
import com.mercadolivre.cliente_service.application.repository.ClienteRepository;
import com.mercadolivre.cliente_service.domain.Cliente;
import com.mercadolivre.cliente_service.handler.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ClienteInfraRepository implements ClienteRepository {

	private final CienteSpringDataJPARepository clienteSpringDataJPARepository;

	@Override
	public Cliente save(Cliente cliente) {
		log.info("[Inicia] ClienteInfraRepository - save");
		ClienteEntity entity = new ClienteEntity(cliente);
		ClienteEntity salvo = clienteSpringDataJPARepository.save(entity);
		log.info("[Finaliza] ClienteInfraRepository - save");
		return salvo.toDomain();
	}

	@Override
	public Page<Cliente> getAllClientes(Pageable pageable) {
	    log.info("[Inicia] ClienteInfraRepository - getAllClientes | pageable={}", pageable);
	    Page<ClienteEntity> pageEntity =
	            clienteSpringDataJPARepository.findAll(pageable);
	    Page<Cliente> pageDomain =
	            pageEntity.map(ClienteEntity::toDomain);
	    log.info("[Finaliza] ClienteInfraRepository - getAllClientes | total={}", 
	             pageDomain.getTotalElements());
	    return pageDomain;
	}


	@Override
	public Cliente buscaClientePorId(UUID idCliente) {
		log.info("[Inicia] buscaClientePorId | id={}", idCliente);
		ClienteEntity entity = clienteSpringDataJPARepository.findById(idCliente)
				.orElseThrow(() -> ApiException.build(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
		return entity.toDomain();
	}

	@Override
	public void deletaCliente(UUID idCliente) {
		log.info("[Inicia] ClienteInfraRepository - deletaCliente | idCliente={}", idCliente);
		clienteSpringDataJPARepository.deleteById(idCliente);
		log.info("[Finaliza] ClienteInfraRepository - deletaCliente | deletaCliente={}", idCliente);
	}

}
