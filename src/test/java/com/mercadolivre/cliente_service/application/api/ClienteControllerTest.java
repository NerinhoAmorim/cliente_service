package com.mercadolivre.cliente_service.application.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.mercadolivre.cliente_service.application.service.ClienteService;
import com.mercadolivre.cliente_service.domain.Cliente;
import com.mercadolivre.cliente_service.domain.Endereco;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void deveCriarClienteViaController() {
        ClienteRequest request = new ClienteRequest(
                "Maria Silva",
                "12345678901",
                "maria@email.com",
                "11999998888",
                LocalDate.of(1990, 1, 1),
                new EnderecoRequest("Rua A", "100", "Casa", "Centro", "Sao Paulo", "SP", "01001000")
        );

        UUID idCliente = UUID.randomUUID();
        Cliente cliente = new Cliente(
                idCliente,
                request.getNomeCompleto(),
                request.getCpf(),
                request.getEmail(),
                request.getDataNascimento(),
                request.getTelefone(),
                new Endereco("Rua A", "100", "Casa", "Centro", "Sao Paulo", "SP", "01001000")
        );

        when(clienteService.criaCliente(any(ClienteRequest.class))).thenReturn(new ClienteResponse(cliente));

        ClienteResponse response = clienteController.postCliente(request);

        assertEquals(idCliente, response.getIdCliente());
        verify(clienteService).criaCliente(request);
    }

    @Test
    void deveListarClientesViaController() {
        UUID idCliente = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);

        ClienteFiltroPageResponse pageResponse = new ClienteFiltroPageResponse(
                List.of(new ClienteFiltroResponse(
                        idCliente,
                        "Joao Silva",
                        "12345678901",
                        "joao@email.com",
                        "11911112222",
                        "Santos",
                        "SP"
                )),
                0,
                1,
                1
        );

        when(clienteService.getAllClientes(eq("Joao"), eq(null), eq(null), eq(null), eq(pageable)))
                .thenReturn(pageResponse);

        ClienteFiltroPageResponse response = clienteController.getAllClientes("Joao", null, null, null, pageable);

        assertEquals(1, response.totalElementos());
        assertEquals(idCliente, response.clientes().get(0).idCliente());
    }

    @Test
    void deveAlterarClienteViaController() {
        UUID idCliente = UUID.randomUUID();
        ClienteAlteracaoRequest request = new ClienteAlteracaoRequest();
        request.setTelefone("11988887777");

        doNothing().when(clienteService).atualizaParcial(idCliente, request);

        clienteController.alteraCliente(idCliente, request);

        verify(clienteService).atualizaParcial(idCliente, request);
    }
}
