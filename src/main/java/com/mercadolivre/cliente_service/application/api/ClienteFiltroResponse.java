package com.mercadolivre.cliente_service.application.api;

import java.util.UUID;

import com.mercadolivre.cliente_service.domain.Cliente;
public record ClienteFiltroResponse(
        UUID idCliente,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        String cidade,
        String estado
) {
    public static ClienteFiltroResponse fromDomain(Cliente cliente) {
        return new ClienteFiltroResponse(
                cliente.getIdCliente(),
                cliente.getNomeCompleto(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getEndereco().getCidade(),
                cliente.getEndereco().getEstado()
        );
    }
 // alias compacto para map(ClienteFiltroResponse::from)
    public static ClienteFiltroResponse from(Cliente cliente) {
        return fromDomain(cliente);
    }
    
    
}
