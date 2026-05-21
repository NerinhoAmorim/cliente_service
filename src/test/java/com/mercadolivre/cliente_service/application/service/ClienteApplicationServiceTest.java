package com.mercadolivre.cliente_service.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
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
import org.springframework.http.HttpStatus;

import com.mercadolivre.cliente_service.application.api.ClienteAlteracaoRequest;
import com.mercadolivre.cliente_service.application.api.ClienteFiltroPageResponse;
import com.mercadolivre.cliente_service.application.api.ClienteRequest;
import com.mercadolivre.cliente_service.application.api.ClienteResponse;
import com.mercadolivre.cliente_service.application.api.EnderecoRequest;
import com.mercadolivre.cliente_service.application.api.EnderecoAlteracaoRequest;
import com.mercadolivre.cliente_service.application.repository.ClienteRepository;
import com.mercadolivre.cliente_service.domain.Cliente;
import com.mercadolivre.cliente_service.domain.Endereco;
import com.mercadolivre.cliente_service.handler.ApiException;

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

    @Test
    void deveRetornarBadRequestQuandoPatchNaoTemCampos() {
        UUID idCliente = UUID.randomUUID();
        ClienteAlteracaoRequest request = new ClienteAlteracaoRequest();

        ApiException ex = assertThrows(ApiException.class,
                () -> clienteApplicationService.atualizaParcial(idCliente, request));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusException());
        assertEquals("Informe ao menos um campo para atualizacao.", ex.getMessage());
    }

    @Test
    void deveRetornarNotFoundQuandoPatchClienteNaoExiste() {
        UUID idCliente = UUID.randomUUID();
        ClienteAlteracaoRequest request = new ClienteAlteracaoRequest();
        request.setNomeCompleto("Nome Atualizado");

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());

        ApiException ex = assertThrows(ApiException.class,
                () -> clienteApplicationService.atualizaParcial(idCliente, request));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
        assertEquals("Cliente não encontrado.", ex.getMessage());
    }

    @Test
    void deveRetornarUnprocessableQuandoCpfJaExisteNoCadastro() {
        ClienteRequest request = new ClienteRequest(
                "Maria Silva",
                "12345678901",
                "maria@email.com",
                "11999998888",
                LocalDate.of(1990, 1, 1),
                new EnderecoRequest("Rua A", "100", null, "Centro", "Sao Paulo", "SP", "01001000")
        );

        when(clienteRepository.existsByCpf("12345678901")).thenReturn(true);

        ApiException ex = assertThrows(ApiException.class,
                () -> clienteApplicationService.criaCliente(request));

        assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, ex.getStatusException());
        assertEquals("Já existe um cliente cadastrado com este CPF.", ex.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void deveCriarClienteComSucesso() {
        UUID idCliente = UUID.randomUUID();
        ClienteRequest request = new ClienteRequest(
                "Joao Santos",
                "98765432100",
                "joao@email.com",
                "11911112222",
                LocalDate.of(1992, 2, 2),
                new EnderecoRequest("Rua B", "10", "Ap 12", "Centro", "Santos", "SP", "11000000")
        );

        Cliente salvo = new Cliente(
                idCliente,
                request.getNomeCompleto(),
                request.getCpf(),
                request.getEmail(),
                request.getDataNascimento(),
                request.getTelefone(),
                new Endereco("Rua B", "10", "Centro", "Santos", "SP", "11000000")
        );

        when(clienteRepository.existsByCpf("98765432100")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(salvo);

        ClienteResponse response = clienteApplicationService.criaCliente(request);

        assertEquals(idCliente, response.getIdCliente());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void deveRetornarBadRequestQuandoCpfNaoInformadoNoCadastro() {
        ClienteRequest request = new ClienteRequest(
                "Nome",
                "",
                "nome@email.com",
                "11911112222",
                LocalDate.of(1990, 1, 1),
                new EnderecoRequest("Rua A", "100", null, "Centro", "Sao Paulo", "SP", "01001000")
        );

        ApiException ex = assertThrows(ApiException.class,
                () -> clienteApplicationService.criaCliente(request));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusException());
        assertTrue(ex.getMessage().contains("CPF"));
    }
}
