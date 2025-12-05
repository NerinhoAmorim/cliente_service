package com.mercadolivre.cliente_service.application.api;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class ClienteRequest {
	@NotBlank
	private String nomeCompleto;
	private String cpf;
	private String email;
	private String telefone;
	private String dataNascimento;
	private EnderecoRequest endereco;
}
