package com.mercadolivre.cliente_service.application.api;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class ClienteRequest {
	@NotBlank(message = "{cliente.nome.obrigatorio}")
	@Size(min = 3, max = 100, message = "{cliente.nome.tamanho}")
	private String nomeCompleto;
	@NotBlank(message = "{cliente.cpf.obrigatorio}")
	@Pattern(regexp = "^[0-9]{11}$", message = "{cliente.cpf.invalido}")
	private String cpf;
	@NotBlank(message = "{cliente.email.obrigatorio}")
	@Email(message = "{cliente.email.invalido}")
	private String email;
	@NotBlank(message = "{cliente.telefone.obrigatorio}")
	@Pattern(regexp = "^[0-9]{10,11}$", message = "{cliente.telefone.invalido}")
	private String telefone;
	@NotNull(message = "{cliente.dataNascimento.obrigatoria}")
	@Past(message = "{cliente.dataNascimento.passado}")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataNascimento;
	private EnderecoRequest endereco;
}
