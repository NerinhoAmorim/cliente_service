package com.mercadolivre.cliente_service.application.api;

public record ClienteFiltroRequest(
        String nome,
        String cpf,
        String email,
        String cidade,
        String estado
) {}
