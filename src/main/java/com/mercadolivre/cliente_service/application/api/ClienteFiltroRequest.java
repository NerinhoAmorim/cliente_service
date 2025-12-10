package com.mercadolivre.cliente_service.application.api;

public record ClienteFiltroRequest(
        String nomeCompleto,
        String cpf,
        String email,
        String cidade,
        String estado,
        String telefone
) {

	}
