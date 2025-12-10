package com.mercadolivre.cliente_service.application.api;

import com.mercadolivre.cliente_service.domain.Endereco;

import lombok.Value;

@Value
public class EnderecoResponse {
    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;

    public EnderecoResponse(Endereco endereco) {
        this.rua = endereco.getRua();
        this.numero = endereco.getNumero();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.cep = endereco.getCep();
    }
}
