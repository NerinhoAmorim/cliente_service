package com.mercadolivre.cliente_service.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.mercadolivre.cliente_service.application.api.ClienteAlteracaoRequest;
import com.mercadolivre.cliente_service.application.api.ClienteFiltroPageResponse;
import com.mercadolivre.cliente_service.application.api.EnderecoAlteracaoRequest;
import com.mercadolivre.cliente_service.application.repository.ClienteRepository;
import com.mercadolivre.cliente_service.domain.Cliente;
import com.mercadolivre.cliente_service.domain.Endereco;

@ExtendWith(MockitoExtension.class)
class ClienteApplicationServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteApplicationService clienteApplicationService;

    @Test
    void deveAtualizarEnderecoParcialmenteNoPatch() {
        UUID idCliente = UUID.randomUUID();
        Cliente cliente = new Cliente(
                idCliente,
                "Maria Silva",
                "12345678901",
                "maria@email.com",
                LocalDate.of(1990, 1, 1),
                "11999998888",
                new Endereco("Rua A", "100", "Centro", "Sao Paulo", "SP", "01001000")
        );

        ClienteAlteracaoRequest request = new ClienteAlteracaoRequest();
        EnderecoAlteracaoRequest enderecoRequest = new EnderecoAlteracaoRequest();
        enderecoRequest.setCidade("Campinas");
        request.setEndereco(enderecoRequest);

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        clienteApplicationService.atualizaParcial(idCliente, request);

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository).save(captor.capture());
        Cliente salvo = captor.getValue();

        assertEquals("Rua A", salvo.getEndereco().getRua());
        assertEquals("Campinas", salvo.getEndereco().getCidade());
        assertEquals("SP", salvo.getEndereco().getEstado());
    }

    @Test
    void deveRetornarPaginaFiltradaDeClientes() {
        Pageable pageable = PageRequest.of(0, 10);
        Cliente cliente = new Cliente(
                UUID.randomUUID(),
                "Joao Silva",
                "12345678901",
                "joao@email.com",
                LocalDate.of(1988, 8, 8),
                "11911112222",
                new Endereco("Rua B", "10", "Centro", "Santos", "SP", "11000000")
        );
        Page<Cliente> page = new PageImpl<>(List.of(cliente), pageable, 1);

        when(clienteRepository.findAll(any(), eq(pageable))).thenReturn(page);

        ClienteFiltroPageResponse response = clienteApplicationService.getAllClientes(
                "Joao",
                null,
                null,
                null,
                pageable
        );

        assertEquals(1, response.totalElementos());
        assertEquals(1, response.clientes().size());
        assertEquals("Joao Silva", response.clientes().get(0).nomeCompleto());
    }
}
