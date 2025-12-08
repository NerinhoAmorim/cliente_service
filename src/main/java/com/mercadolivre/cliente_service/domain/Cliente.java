package com.mercadolivre.cliente_service.domain;

import java.time.LocalDate;
import java.util.UUID;

import com.mercadolivre.cliente_service.application.api.ClienteRequest;
import lombok.Getter;

@Getter
public class Cliente {

    private UUID idCliente;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private LocalDate dataNascimento;
    private String telefone;
    private Endereco endereco;

    // Construtor usado quando vem do banco via ClienteEntity
    public Cliente(
            UUID idCliente,
            String nomeCompleto,
            String cpf,
            String email,
            LocalDate dataNascimento,
            String telefone,
            Endereco endereco
    ) {
        this.idCliente = idCliente;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    // Construtor usado no cadastro (Request → Domain)
    public Cliente(ClienteRequest request) {
        this.nomeCompleto = request.getNomeCompleto();
        this.cpf = request.getCpf();
        this.email = request.getEmail();
        this.dataNascimento = LocalDate.parse(request.getDataNascimento());
        this.telefone = request.getTelefone();
        this.endereco = new Endereco(request.getEndereco());
    }
}
