	package com.mercadolivre.cliente_service.domain;

import com.mercadolivre.cliente_service.application.api.EnderecoRequest;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Endereco {

    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco(EnderecoRequest request) {
        this.rua = request.getRua();
        this.numero = request.getNumero();
        this.complemento = request.getComplemento();
        this.bairro = request.getBairro();
        this.cidade = request.getCidade();
        this.estado = request.getEstado();
        this.cep = request.getCep();
    }

    public Endereco(String rua, String numero, String complemento, String bairro, String cidade, String estado,
            String cep) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
}
