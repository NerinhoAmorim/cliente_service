package com.mercadolivre.cliente_service.application.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

public class EnderecoRequest {
	
	@NotBlank
    private String rua;

    @NotNull
    private Integer numero;

    private String complemento;

    @NotBlank
    private String bairro;

    @NotBlank
    private String cidade;

    @NotBlank
    private String estado;

    @NotBlank
    private String cep;

}
