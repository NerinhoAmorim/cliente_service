package com.mercadolivre.cliente_service.handler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorApiResponse {
	
    private final String message;
    private final String description;
}
