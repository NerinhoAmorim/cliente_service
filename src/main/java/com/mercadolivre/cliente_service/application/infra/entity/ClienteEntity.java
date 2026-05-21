package com.mercadolivre.cliente_service.application.infra.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.mercadolivre.cliente_service.domain.Cliente;
import com.mercadolivre.cliente_service.domain.Endereco;

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
	private Endereco endereco;

	// Construtor de entidade via domínio
	public ClienteEntity(Cliente cliente) {
		this.idCliente = cliente.getIdCliente();
		this.nomeCompleto = cliente.getNomeCompleto();
		this.cpf = cliente.getCpf();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
		this.dataNascimento = cliente.getDataNascimento();
		this.endereco = cliente.getEndereco();
	}

	// Converte entidade -> domínio
	public Cliente toDomain() {
		return new Cliente(this.idCliente, this.nomeCompleto, this.cpf, this.email, this.dataNascimento, this.telefone,
				this.endereco);
	}

	public void updateFromDomain(Cliente cliente) {
		this.nomeCompleto = cliente.getNomeCompleto();
		this.cpf = cliente.getCpf();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
		this.dataNascimento = cliente.getDataNascimento();
		this.endereco = cliente.getEndereco();
	}

	public Cliente toCliente() {
		return Cliente.builder()
				.idCliente(this.idCliente)
				.nomeCompleto(this.nomeCompleto)
				.email(this.email).cpf(this.cpf)
				.telefone(this.telefone).build();
	}

	public static ClienteEntity fromCliente(Cliente cliente) {
		return ClienteEntity.builder()
				.idCliente(cliente.getIdCliente())
				.nomeCompleto(cliente.getNomeCompleto())
				.email(cliente.getEmail())
				.cpf(cliente.getCpf())
				.telefone(cliente.getTelefone())
				.build();
	}

}
