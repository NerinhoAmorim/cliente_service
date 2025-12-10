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
        this.dataNascimento = request.getDataNascimento();
        this.telefone = request.getTelefone();
        this.endereco = new Endereco(request.getEndereco());
    }

    public void setNomeCompleto(String nomeCompleto) {
    	if (nomeCompleto == null || nomeCompleto.isBlank()) {
    		throw new IllegalArgumentException("Nome completo não pode ser vazio.");
    	}
    	this.nomeCompleto = nomeCompleto.trim();
    }
    
    public void setEmail(String email) {
    	if (email == null || email.isBlank()) {
    		throw new IllegalArgumentException("Email não pode ser vazio.");
    	}
    	if (!email.contains("@")) {
    		throw new IllegalArgumentException("Email inválido.");
    	}
    	this.email = email.trim().toLowerCase();
    }

    public void setTelefone(String telefone) {
    	if (telefone == null || telefone.isBlank()) {
    		throw new IllegalArgumentException("Telefone não pode ser vazio.");
    	}
    	if (!telefone.matches("\\d{10,11}")) {
    		throw new IllegalArgumentException("Telefone inválido. Use apenas números com DDD.");
    	}
    	this.telefone = telefone.trim();
    }


}
