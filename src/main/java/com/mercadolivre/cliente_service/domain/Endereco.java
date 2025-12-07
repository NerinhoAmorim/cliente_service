package com.mercadolivre.cliente_service.domain;

import com.mercadolivre.cliente_service.application.api.EnderecoRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private Integer numero;

    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
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
}
