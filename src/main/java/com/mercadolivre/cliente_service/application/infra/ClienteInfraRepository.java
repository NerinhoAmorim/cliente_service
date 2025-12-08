package com.mercadolivre.cliente_service.application.infra;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

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
		clienteSpringDataJPARepository.save(cliente);
		log.info("[Finaliza] ClienteInfraRepository - save");
		return cliente;

	}

	@Override
	public List<Cliente> getAllClientes() {
		log.info("[Inicia] ClienteInfraRepository - getAllClientes");
		List<Cliente> todosClientes = clienteSpringDataJPARepository.findAll();
		log.info("[Finaliza] ClienteInfraRepository - getAllClientes");
		return todosClientes;
	}

	@Override
	public Cliente buscaClientePorId(UUID idCliente) {
	    log.debug("[Inicia] buscaClientePorId | id={}", idCliente);

	    return clienteSpringDataJPARepository.findById(idCliente)
	            .orElseThrow(() -> {
	                log.warn("Cliente não encontrado | id={}", idCliente);
	                return ApiException.build(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
	            });
	}

}
