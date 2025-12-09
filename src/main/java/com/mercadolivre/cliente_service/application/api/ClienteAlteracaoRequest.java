package com.mercadolivre.cliente_service.application.api;

import lombok.Data;

@Data
public class ClienteAlteracaoRequest {
	private String nomeCompleto;
    private String email;
    private String telefone;

}
