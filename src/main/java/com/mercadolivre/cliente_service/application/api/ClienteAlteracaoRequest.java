package com.mercadolivre.cliente_service.application.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteAlteracaoRequest {
    @Size(min = 3, max = 120, message = "{cliente.nome.tamanho}")
	private String nomeCompleto;
    @Email(message = "{cliente.email.invalido}")
    private String email;
    @Size(min = 10, max = 15, message = "{cliente.telefone.tamanho}")
    private String telefone;
    @Valid
    ClienteAlteracaoRequest endereco;
    {}
}