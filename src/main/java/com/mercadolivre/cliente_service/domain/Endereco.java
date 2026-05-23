	package com.mercadolivre.cliente_service.domain;

import com.mercadolivre.cliente_service.application.api.EnderecoAlteracaoRequest;
import com.mercadolivre.cliente_service.application.api.EnderecoRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    public Endereco mergeCom(EnderecoAlteracaoRequest alteracao) {
        return new Endereco(
            alteracao.getRua() != null ? alteracao.getRua() : this.rua,
            alteracao.getNumero() != null ? alteracao.getNumero() : this.numero,
            alteracao.getComplemento() != null ? alteracao.getComplemento() : this.complemento,
            alteracao.getBairro() != null ? alteracao.getBairro() : this.bairro,
            alteracao.getCidade() != null ? alteracao.getCidade() : this.cidade,
            alteracao.getEstado() != null ? alteracao.getEstado() : this.estado,
            alteracao.getCep() != null ? alteracao.getCep() : this.cep
        );
    }
}
