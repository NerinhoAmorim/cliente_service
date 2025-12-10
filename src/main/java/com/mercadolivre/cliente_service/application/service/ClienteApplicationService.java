	package com.mercadolivre.cliente_service.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mercadolivre.cliente_service.application.api.ClienteAlteracaoRequest;
import com.mercadolivre.cliente_service.application.api.ClienteDetalhadoResponse;
import com.mercadolivre.cliente_service.application.api.ClienteFiltroPageResponse;
import com.mercadolivre.cliente_service.application.api.ClienteFiltroResponse;
import com.mercadolivre.cliente_service.application.api.ClienteRequest;
import com.mercadolivre.cliente_service.application.api.ClienteResponse;
import com.mercadolivre.cliente_service.application.repository.ClienteRepository;
import com.mercadolivre.cliente_service.domain.Cliente;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2

public class ClienteApplicationService implements ClienteService {

	private final ClienteRepository clienteRepository;

	@Override
	public ClienteResponse criaCliente(ClienteRequest request) {
	    log.info("[start] ClienteApplicationService - criaCliente");

	    if (request.getCpf() == null || request.getCpf().isBlank()) {
	        throw new IllegalArgumentException("CPF não pode ser vazio");
	    }
	    if (request.getEmail() == null || request.getEmail().isBlank()) {
	        throw new IllegalArgumentException("E-mail não pode ser vazio");
	    }

	    Cliente cliente = new Cliente(request);
	    Cliente clienteSalvo = clienteRepository.save(cliente);

	    log.info("[finish] ClienteApplicationService - criaCliente | id={}", clienteSalvo.getIdCliente());
	    return new ClienteResponse(clienteSalvo);
	}



	@Override
	public ClienteDetalhadoResponse buscaClientePorId(final UUID idCliente) {
		log.info("[Inicia] ClienteApplicationService - buscaClientePorId | idCliente={}", idCliente);
		Cliente cliente = clienteRepository.buscaClientePorId(idCliente);
		log.info("[Finaliza] ClienteApplicationService - buscaClientePorId | idCliente={}", idCliente);
		return new ClienteDetalhadoResponse(cliente);
	}

	@Override
	public void deletaCliente(UUID idCliente) {
		log.info("[Inicia] ClienteApplicationService - deletaCliente | idCliente={}", idCliente);
		clienteRepository.buscaClientePorId(idCliente);
		clienteRepository.deletaCliente(idCliente);
		log.info("[Finaliza] ClienteApplicationService - deletaCliente | idCliente={}", idCliente);
	}

	@Override
	public void atualizaParcial(UUID idCliente, ClienteAlteracaoRequest request) {
	    log.info("[start] ClienteApplicationService - atualizaParcial | id={}", idCliente);
	    Cliente cliente = clienteRepository.buscaClientePorId(idCliente);
	    if (request.getNomeCompleto() != null && !request.getNomeCompleto().isBlank()) {
	        cliente.setNomeCompleto(request.getNomeCompleto());
	    }
	    if (request.getEmail() != null && !request.getEmail().isBlank()) {
	        cliente.setEmail(request.getEmail());
	    }
	    if (request.getTelefone() != null && !request.getTelefone().isBlank()) {
	        cliente.setTelefone(request.getTelefone());
	    }
	    clienteRepository.save(cliente);
	    log.info("[finish] ClienteApplicationService - atualizaParcial | id={}", idCliente);
	}



	@Override
	public ClienteFiltroPageResponse getAllClientes(
	        String nome,
	        String email,
	        String cpf,
	        String telefone,
	        Pageable pageable
	) {
	    Page<ClienteFiltroResponse> page = clienteRepository.findByFiltros(nome, email, cpf, telefone, pageable);
	    return ClienteFiltroPageResponse.from(page);
	}
	
	





}
