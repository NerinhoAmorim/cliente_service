package com.mercadolivre.cliente_service.application.api;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnderecoAlteracaoRequest {

    @Size(min = 3, max = 120)
    private String rua;

    @Size(min = 1, max = 20)
    private String numero;

    @Size(min = 2, max = 80)
    private String bairro;

    @Size(min = 2, max = 80)
    private String cidade;

    @Size(min = 2, max = 2)
    private String estado;

    @Pattern(regexp = "^[0-9]{8}$", message = "CEP invalido. Use 8 digitos numericos.")
    private String cep;
}
