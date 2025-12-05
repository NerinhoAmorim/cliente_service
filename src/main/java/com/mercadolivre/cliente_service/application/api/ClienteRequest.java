package com.mercadolivre.cliente_service.application.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ClienteRequest {
	
	@NotBlank
    private String nomeCompleto;

    @NotBlank
    private String cpf;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String celular;

    @NotNull
    private String dataNascimento;

    @NotNull
    private EnderecoRequest endereco;

}
