package com.mercadolivre.cliente_service.application.api;

import java.time.LocalDate;
import java.util.UUID;

import com.mercadolivre.cliente_service.domain.Cliente;

import lombok.Value;

@Value
public class ClienteDetalhadoResponse {

    private UUID idCliente;
    private String nomeCompleto;
    private String cpf;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
    private EnderecoResponse endereco;

    public ClienteDetalhadoResponse(Cliente cliente) {
        this.idCliente = cliente.getIdCliente();
        this.nomeCompleto = cliente.getNomeCompleto();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.email = cliente.getEmail();
        this.dataNascimento = cliente.getDataNascimento();

        // conversão do value object Endereco -> Response
        this.endereco = new EnderecoResponse(cliente.getEndereco());
    }
}
