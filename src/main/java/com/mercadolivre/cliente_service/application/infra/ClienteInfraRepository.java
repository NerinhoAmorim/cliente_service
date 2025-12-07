package com.mercadolivre.cliente_service.application.infra;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mercadolivre.cliente_service.application.repository.ClienteRepository;
import com.mercadolivre.cliente_service.domain.Cliente;

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
}
