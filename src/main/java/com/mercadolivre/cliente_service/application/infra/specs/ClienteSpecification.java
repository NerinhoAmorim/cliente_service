package com.mercadolivre.cliente_service.application.infra.specs;

import org.springframework.data.jpa.domain.Specification;

import com.mercadolivre.cliente_service.application.infra.entity.ClienteEntity;

public class ClienteSpecification {

	public static Specification<ClienteEntity> nomeContains(String nome) {
		return (root, query, cb) -> nome == null || nome.isBlank() ? null
				: cb.like(cb.lower(root.get("nomeCompleto")), "%" + nome.toLowerCase() + "%");
	}

	public static Specification<ClienteEntity> emailContains(String email) {
		return (root, query, cb) -> email == null || email.isBlank() ? null
				: cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
	}

	public static Specification<ClienteEntity> cpfEquals(String cpf) {
		return (root, query, cb) -> cpf == null || cpf.isBlank() ? null : cb.equal(root.get("cpf"), cpf);
	}

	public static Specification<ClienteEntity> telefoneContains(String telefone) {
		return (root, query, cb) -> telefone == null || telefone.isBlank() ? null
				: cb.like(cb.lower(root.get("telefone")), "%" + telefone.toLowerCase() + "%");
	}

	public static Specification<ClienteEntity> build(String nome, String email, String cpf, String telefone) {

		return Specification.where(nomeContains(nome)).and(emailContains(email)).and(cpfEquals(cpf))
				.and(telefoneContains(telefone));
	}
}
