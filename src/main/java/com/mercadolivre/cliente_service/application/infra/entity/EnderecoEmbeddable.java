package com.mercadolivre.cliente_service.application.infra.entity;

import com.mercadolivre.cliente_service.domain.Endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EnderecoEmbeddable {

    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public static EnderecoEmbeddable fromDomain(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoEmbeddable(
            endereco.getRua(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getCep()
        );
    }

    public Endereco toDomain() {
        return new Endereco(rua, numero, complemento, bairro, cidade, estado, cep);
    }
}
