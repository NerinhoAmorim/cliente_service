package com.mercadolivre.cliente_service.application.infra;

import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

	private final ClienteSpringDataJPARepository clienteSpringDataJPARepository;

	@Override
	public Cliente save(Cliente cliente) {
		log.info("[Inicia] ClienteInfraRepository - save");
		ClienteEntity entity = cliente.getIdCliente() != null
				? clienteSpringDataJPARepository.findById(cliente.getIdCliente()).map(e -> {
					e.updateFromDomain(cliente);
					return e;
				}).orElse(new ClienteEntity(cliente))
				: new ClienteEntity(cliente);
		ClienteEntity saved = clienteSpringDataJPARepository.save(entity);
		log.info("[Finaliza] ClienteInfraRepository - save");
		return saved.toDomain();
	}

	@Override
	public boolean existsByCpf(String cpf) {
		log.info("[Inicia] ClienteInfraRepository - existsByCpf");
		try {
			return clienteSpringDataJPARepository.existsByCpf(cpf);
		} catch (Exception e) {
			throw ApiException.build(HttpStatus.INTERNAL_SERVER_ERROR,
					"Erro ao verificar existência de cliente por CPF", e);
		} finally {
			log.info("[Finaliza] ClienteInfraRepository - existsByCpf");
		}
	}

	@Override
	public Optional<Cliente> findById(UUID idCliente) {
		log.info("[Inicia] ClienteInfraRepository - findById");
		try {
			return clienteSpringDataJPARepository.findById(idCliente).map(ClienteEntity::toDomain);
		} catch (Exception e) {
			throw ApiException.build(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar cliente por ID", e);
		} finally {
			log.info("[Finaliza] ClienteInfraRepository - findById");
		}
	}

	@Override
	public boolean existsById(UUID idCliente) {
		log.info("[Inicia] ClienteInfraRepository - existsById");
		try {
			return clienteSpringDataJPARepository.existsById(idCliente);
		} catch (Exception e) {
			throw ApiException.build(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao verificar existência de cliente por ID",
					e);
		} finally {
			log.info("[Finaliza] ClienteInfraRepository - existsById");
		}
	}

	@Override
	public void deleteById(UUID idCliente) {
		log.info("[Inicia] ClienteInfraRepository - deleteById");
		try {
			clienteSpringDataJPARepository.deleteById(idCliente);
		} catch (EmptyResultDataAccessException e) {
			throw ApiException.build(HttpStatus.NOT_FOUND, "Cliente não encontrado para exclusão", e);
		} catch (Exception e) {
			throw ApiException.build(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar cliente por ID", e);
		} finally {
			log.info("[Finaliza] ClienteInfraRepository - deleteById");
		}
	}

	@Override
	public Page<Cliente> findAll(Specification<ClienteEntity> filtro, Pageable pageable) {
		log.info("[Inicia] ClienteInfraRepository - findAll");
		try {
			Page<ClienteEntity> page = clienteSpringDataJPARepository.findAll(filtro, pageable);
			return page.map(ClienteEntity::toDomain);
		} catch (Exception e) {
			throw ApiException.build(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar clientes com filtros", e);
		} finally {
			log.info("[Finaliza] ClienteInfraRepository - findAll");
		}
	}
}
