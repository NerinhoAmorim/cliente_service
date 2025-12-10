package com.mercadolivre.cliente_service.application.api;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.Valid;

@RequestMapping("/v1/cliente")
public interface ClienteAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ClienteResponse postcliente(@Valid @RequestBody ClienteRequest request);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ClienteFiltroPageResponse getAllClientes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String telefone,
            Pageable pageable
    );

    @GetMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    ClienteDetalhadoResponse getClientePorId(@PathVariable UUID idCliente);

    @DeleteMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCliente(@PathVariable UUID idCliente);

    @PatchMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void alteraCliente(
            @PathVariable UUID idCliente,
            @Valid @RequestBody ClienteAlteracaoRequest request
    );
}
