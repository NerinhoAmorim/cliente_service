package com.mercadolivre.cliente_service.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mercadolivre.cliente_service.application.api.ClienteAlteracaoRequest;
import com.mercadolivre.cliente_service.application.api.ClienteDetalhadoResponse;
import com.mercadolivre.cliente_service.application.api.ClienteListResponse;
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
		Cliente cliente = new Cliente(request);
		Cliente clienteSalvo = clienteRepository.save(cliente);
		log.info("[finish] ClienteApplicationService - criaCliente");
		return new ClienteResponse(clienteSalvo);
	}

	@Override
	public Page<ClienteListResponse> getAllClientes(Pageable pageable) {
	    log.info("[Inicia] ClienteApplicationService - getAllClientes | pageable={}", pageable);
	    Page<Cliente> pageDomain = clienteRepository.getAllClientes(pageable);
	    Page<ClienteListResponse> pageDto =
	            pageDomain.map(ClienteListResponse::fromDomain);
	    log.info("[Finaliza] ClienteApplicationService - getAllClientes| total={}", pageDto.getTotalElements());
	    return pageDto;
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
		log.info("[inicia] alteraCliente | id={}", idCliente);
		Cliente cliente = clienteRepository.buscaClientePorId(idCliente);
		if (request.getNomeCompleto() != null) {
			cliente.setNomeCompleto(request.getNomeCompleto());
		}
		if (request.getEmail() != null) {
			cliente.setEmail(request.getEmail());
		}
		if (request.getTelefone() != null) {
			cliente.setTelefone(request.getTelefone());
		}
		clienteRepository.save(cliente);
		log.info("[finaliza] alteraCliente | id={}", idCliente);
	}

}
