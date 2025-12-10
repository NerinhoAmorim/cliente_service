package com.mercadolivre.cliente_service.application.api;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.cliente_service.application.service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/v1/cliente")
public class ClienteController implements ClienteAPI {

    private final ClienteService clienteService;

    @Override
    public ClienteResponse postcliente(@Valid @RequestBody ClienteRequest request) {
        log.info("[inicia] ClienteController - postCliente | request={}", request);
        ClienteResponse cliente = clienteService.criaCliente(request);
        log.info("[finaliza] ClienteController - postCliente");
        return cliente;
    }

    @Override
    public ClienteFiltroPageResponse getAllClientes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String telefone,
            Pageable pageable) {

        log.info(
            "[inicia] ClienteController - getAllClientes | nome={}, email={}, cpf={}, telefone={}, pageable={}",
            nome, email, cpf, telefone, pageable
        );

        ClienteFiltroPageResponse response =
                clienteService.getAllClientes(nome, email, cpf, telefone, pageable);

        log.info("[finaliza] ClienteController - getAllClientes");
        return response;
    }

    @Override
    public ClienteDetalhadoResponse getClientePorId(@PathVariable UUID idCliente) {
        log.info("[inicia] ClienteController - getClientePorId | idCliente={}", idCliente);
        ClienteDetalhadoResponse clienteDetalhado = clienteService.buscaClientePorId(idCliente);
        log.info("[finaliza] ClienteController - getClientePorId | idCliente={}", idCliente);
        return clienteDetalhado;
    }

    @Override
    public void deleteCliente(@PathVariable UUID idCliente) {
        log.info("[inicia] ClienteController - deleteCliente | idCliente={}", idCliente);
        clienteService.deletaCliente(idCliente);
        log.info("[finaliza] ClienteController - deleteCliente | idCliente={}", idCliente);
    }

    @Override
    public void alteraCliente(
            @PathVariable UUID idCliente,
            @Valid @RequestBody ClienteAlteracaoRequest request
    ) {
        log.info("[inicia] ClienteController - alteraCliente | idCliente={}, request={}", idCliente, request);
        clienteService.atualizaParcial(idCliente, request);
        log.info("[finaliza] ClienteController - alteraCliente | idCliente={}", idCliente);
    }
    
    

}
