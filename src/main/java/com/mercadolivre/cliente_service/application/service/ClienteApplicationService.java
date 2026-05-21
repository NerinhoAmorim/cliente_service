	package com.mercadolivre.cliente_service.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mercadolivre.cliente_service.application.api.ClienteAlteracaoRequest;
import com.mercadolivre.cliente_service.application.api.ClienteDetalhadoResponse;
import com.mercadolivre.cliente_service.application.api.ClienteFiltroPageResponse;
import com.mercadolivre.cliente_service.application.api.ClienteFiltroResponse;
import com.mercadolivre.cliente_service.application.api.ClienteRequest;
import com.mercadolivre.cliente_service.application.api.ClienteResponse;
import com.mercadolivre.cliente_service.application.infra.specs.ClienteSpecification;
import com.mercadolivre.cliente_service.application.repository.ClienteRepository;
import com.mercadolivre.cliente_service.domain.Cliente;
import com.mercadolivre.cliente_service.domain.Endereco;
import com.mercadolivre.cliente_service.handler.ApiException;

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

	    try {
            if (request.getCpf() == null || request.getCpf().isBlank()) {
                throw ApiException.build(HttpStatus.BAD_REQUEST, "CPF não pode ser vazio.");
            }
            if (request.getEmail() == null || request.getEmail().isBlank()) {
                throw ApiException.build(HttpStatus.BAD_REQUEST, "E-mail não pode ser vazio.");
            }

            if (clienteRepository.existsByCpf(request.getCpf())) {
                throw ApiException.build(HttpStatus.UNPROCESSABLE_CONTENT,
                        "Já existe um cliente cadastrado com este CPF.");
            }

	    Cliente cliente = new Cliente(request);
	    Cliente clienteSalvo = clienteRepository.save(cliente);
	    log.info("[finish] ClienteApplicationService - criaCliente | id={}", clienteSalvo.getIdCliente());
	    return new ClienteResponse(clienteSalvo);
	    } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw ApiException.build(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao criar cliente.", e);
        }
	}



	@Override
	public ClienteDetalhadoResponse buscaClientePorId(final UUID idCliente) {
		log.info("[Inicia] ClienteApplicationService - buscaClientePorId | idCliente={}", idCliente);
		try {
            Cliente cliente = clienteRepository.findById(idCliente)
                    .orElseThrow(() -> ApiException.build(
                            HttpStatus.NOT_FOUND,
                            "Cliente não encontrado."
                    ));
            log.info("[Finaliza] ClienteApplicationService - buscaClientePorId | idCliente={}", idCliente);
		return new ClienteDetalhadoResponse(cliente);
		} catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw ApiException.build(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao deletar cliente.", e);
        }
	}

	@Override
	public void deletaCliente(UUID idCliente) {
		log.info("[Inicia] ClienteApplicationService - deletaCliente | idCliente={}", idCliente);
		try {

            if (!clienteRepository.existsById(idCliente)) {
                throw ApiException.build(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
            }

            clienteRepository.deleteById(idCliente);
		log.info("[Finaliza] ClienteApplicationService - deletaCliente | idCliente={}", idCliente);
		} catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw ApiException.build(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao deletar cliente.", e);
        }
	}

	@Override
	public void atualizaParcial(UUID idCliente, ClienteAlteracaoRequest request) {
	    log.info("[start] ClienteApplicationService - atualizaParcial | id={}", idCliente);
	    try {
            Cliente cliente = clienteRepository.findById(idCliente)
                    .orElseThrow(() -> ApiException.build(
                            HttpStatus.NOT_FOUND,
                            "Cliente não encontrado."
                    ));

            if (request.getNomeCompleto() != null && !request.getNomeCompleto().isBlank()) {
                cliente.setNomeCompleto(request.getNomeCompleto());
            }

            if (request.getEmail() != null && !request.getEmail().isBlank()) {
                cliente.setEmail(request.getEmail());
            }

            if (request.getTelefone() != null && !request.getTelefone().isBlank()) {
                cliente.setTelefone(request.getTelefone());
            }

            if (request.getEndereco() != null) {
                Endereco enderecoAtual = cliente.getEndereco();
                String rua = request.getEndereco().getRua() != null ? request.getEndereco().getRua() : enderecoAtual.getRua();
                String numero = request.getEndereco().getNumero() != null ? request.getEndereco().getNumero() : enderecoAtual.getNumero();
                String bairro = request.getEndereco().getBairro() != null ? request.getEndereco().getBairro() : enderecoAtual.getBairro();
                String cidade = request.getEndereco().getCidade() != null ? request.getEndereco().getCidade() : enderecoAtual.getCidade();
                String estado = request.getEndereco().getEstado() != null ? request.getEndereco().getEstado() : enderecoAtual.getEstado();
                String cep = request.getEndereco().getCep() != null ? request.getEndereco().getCep() : enderecoAtual.getCep();
                cliente.setEndereco(new Endereco(rua, numero, bairro, cidade, estado, cep));
            }

            clienteRepository.save(cliente);

            log.info("[finish] ClienteApplicationService - atualizaParcial | id={}", idCliente);

        } catch (IllegalArgumentException e) {
            throw ApiException.build(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw ApiException.build(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar cliente.", e);
        }
	    
	}



	@Override
	public ClienteFiltroPageResponse getAllClientes(
	        String nome,
	        String email,
	        String cpf,
	        String telefone,
	        Pageable pageable
	) {
	    log.info("[start] ClienteApplicationService - getAllClientes");
	    try {
            Page<Cliente> page = clienteRepository.findAll(
                    ClienteSpecification.filtrar(
                            nome,
                            email,
                            cpf,
                            telefone
                    ),
                    pageable
            );

            Page<ClienteFiltroResponse> mapped = page.map(ClienteFiltroResponse::from);

            log.info("[finish] ClienteApplicationService - getAllClientes");

            return ClienteFiltroPageResponse.from(mapped);

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw ApiException.build(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao buscar clientes com filtros.", e);
        }
    }
}
