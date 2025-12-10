package com.mercadolivre.cliente_service.application.api;

import java.util.List;

import org.springframework.data.domain.Page;

public record ClienteFiltroPageResponse(
        List<ClienteFiltroResponse> clientes,
        int paginaAtual,
        int totalPaginas,
        long totalElementos
) {
    public static ClienteFiltroPageResponse from(Page<ClienteFiltroResponse> page) {
        return new ClienteFiltroPageResponse(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}
