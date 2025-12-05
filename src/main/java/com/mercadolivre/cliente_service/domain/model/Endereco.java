package com.mercadolivre.cliente_service.domain.model;

import com.mercadolivre.cliente_service.application.api.EnderecoRequest;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco {

	private String rua;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;

	public Endereco(EnderecoRequest endereco) {
		this.rua = getRua();
		this.numero = getNumero();
		this.bairro = getBairro();
		this.cidade = getCidade();
		this.estado = getEstado();
		this.cep = getCep();
	}
}
