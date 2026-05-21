package com.mercadolivre.cliente_service.application.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteAlteracaoRequest {
    @Size(min = 3, max = 120, message = "{cliente.nome.tamanho}")
	private String nomeCompleto;
    @Email(message = "{cliente.email.invalido}")
    private String email;
    @Pattern(regexp = "^[0-9]{10,11}$", message = "{cliente.telefone.invalido}")
    private String telefone;
    @Valid
    private EnderecoAlteracaoRequest endereco;
}
