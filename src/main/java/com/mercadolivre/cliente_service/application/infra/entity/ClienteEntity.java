package com.mercadolivre.cliente_service.application.infra.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.mercadolivre.cliente_service.domain.Cliente;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "clientes")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteEntity {

	@Id
	@GeneratedValue
	@UuidGenerator(style = UuidGenerator.Style.AUTO)
	@Column(name = "id_cliente", updatable = false, nullable = false)
	private UUID idCliente;

	private String nomeCompleto;

	private String cpf;

	private String telefone;

	private String email;

	private LocalDate dataNascimento;

	@Embedded
	@AttributeOverride(name = "rua", column = @Column(name = "endereco_rua"))
	@AttributeOverride(name = "numero", column = @Column(name = "endereco_numero"))
	@AttributeOverride(name = "complemento", column = @Column(name = "endereco_complemento"))
	@AttributeOverride(name = "bairro", column = @Column(name = "endereco_bairro"))
	@AttributeOverride(name = "cidade", column = @Column(name = "endereco_cidade"))
	@AttributeOverride(name = "estado", column = @Column(name = "endereco_estado"))
	@AttributeOverride(name = "cep", column = @Column(name = "endereco_cep"))
	private EnderecoEmbeddable endereco;

	// Construtor de entidade via domínio
	public ClienteEntity(Cliente cliente) {
		this.idCliente = cliente.getIdCliente();
		this.nomeCompleto = cliente.getNomeCompleto();
		this.cpf = cliente.getCpf();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
		this.dataNascimento = cliente.getDataNascimento();
		this.endereco = EnderecoEmbeddable.fromDomain(cliente.getEndereco());
	}

	// Converte entidade -> domínio
	public Cliente toDomain() {
		return new Cliente(this.idCliente, this.nomeCompleto, this.cpf, this.email, this.dataNascimento, this.telefone,
				this.endereco.toDomain());
	}

	public void updateFromDomain(Cliente cliente) {
		this.nomeCompleto = cliente.getNomeCompleto();
		this.cpf = cliente.getCpf();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
		this.dataNascimento = cliente.getDataNascimento();
		this.endereco = EnderecoEmbeddable.fromDomain(cliente.getEndereco());
	}

}
