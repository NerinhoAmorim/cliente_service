package com.mercadolivre.cliente_service.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import com.mercadolivre.cliente_service.application.api.ClienteRequest;

import jakarta.persistence.Column;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

	@Id
	@GeneratedValue
	@Column(name = "id_cliente", columnDefinition = "uuid")
	private UUID idCliente;

	@Column(nullable = false, length = 150)
	private String nomeCompleto;

	@Column(nullable = false, unique = true, length = 11)
	private String cpf;

	@Column(nullable = false, length = 150)
	private String email;

	private LocalDate dataNascimento;

	@Column(length = 20)
	private String telefone;

	private Endereco endereco;

	public Cliente(ClienteRequest request) {
		this.nomeCompleto = request.getNomeCompleto();
		this.email = request.getEmail();
		this.cpf = request.getCpf();
		this.endereco = new Endereco(request.getEndereco());
	}

}
