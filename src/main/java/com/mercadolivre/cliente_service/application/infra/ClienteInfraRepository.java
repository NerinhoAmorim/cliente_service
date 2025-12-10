package com.mercadolivre.cliente_service.application.infra;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.mercadolivre.cliente_service.application.api.ClienteFiltroResponse;
import com.mercadolivre.cliente_service.application.infra.entity.ClienteEntity;
import com.mercadolivre.cliente_service.application.infra.specs.ClienteSpecification;
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
		log.info("[Inicial] ClienteInfraRepository - save");

		ClienteEntity entity = cliente.getIdCliente() != null
				? clienteSpringDataJPARepository.findById(cliente.getIdCliente()).map(e -> {
					e.updateFromDomain(cliente);
					return e;
				}).orElse(new ClienteEntity(cliente))
				: new ClienteEntity(cliente);
		ClienteEntity saved = clienteSpringDataJPARepository.save(entity);
		return saved.toDomain();
	}
	
	@Override
	public Page<ClienteFiltroResponse> findByFiltros(
			String nome,
			String email,
			String cpf,
			String telefone,
			Pageable pageable) {
		
		Specification<ClienteEntity> spec =
				ClienteSpecification.build(nome, email, cpf, telefone);
		Page<ClienteEntity> page = clienteSpringDataJPARepository.findAll(spec, pageable);
		return page.map(entity -> new ClienteFiltroResponse(
		        entity.getIdCliente(),
		        entity.getNomeCompleto(),
		        entity.getCpf(),
		        entity.getEmail(),
		        entity.getTelefone(),
		        entity.getEndereco().getCidade(),
		        entity.getEndereco().getEstado()
		));

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
