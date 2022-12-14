package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.dto.cliente.ClienteRequestDTO;
import com.gvendas.gestaovendas.dto.cliente.ClienteResponseDTO;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.servico.ClienteServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Clientes")
@RestController
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private ClienteServico clienteServico;

    @ApiOperation(value = "Listar", nickname = "listarTodoslientes")
    @GetMapping
    public List<ClienteResponseDTO> listarTodas() {
        return clienteServico.listarTodos().stream()
                .map(cliente -> ClienteResponseDTO.converterParaClienteResponseDTO(cliente))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Listar por código", nickname = "buscarClientesPorCodigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<ClienteResponseDTO> buscarPorCodigo(@PathVariable(name = "codigo") Long codigo) {
        Optional<Cliente> cliente = clienteServico.buscarPorCodigo(codigo);
        return cliente.isPresent()
                ? ResponseEntity.ok(ClienteResponseDTO.converterParaClienteResponseDTO(cliente.get()))
                : ResponseEntity.notFound().build();
    }

        // @valid irá aplicar as constrints bean validatio da class produto @NotNull e
    // @Length
    @ApiOperation(value = "Salvar", nickname = "salvarCliente")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvar(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        Cliente clienteSalvo = clienteServico.salvar(clienteRequestDTO.converterParaEntidadeCliente());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClienteResponseDTO.converterParaClienteResponseDTO(clienteSalvo));
    }

    @ApiOperation(value = "Atualizar", nickname = "atualizarCliente")
    @PutMapping("/{codigo}")
    public ResponseEntity<ClienteResponseDTO> salvar(@PathVariable Long codigo, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        Cliente clienteAtualizado = clienteServico.atualizar(codigo, clienteRequestDTO.converterParaEntidadeCliente(codigo));
        return ResponseEntity.ok(ClienteResponseDTO.converterParaClienteResponseDTO(clienteAtualizado));
    }

    @ApiOperation(value = "Deletar", nickname = "deletarCliente")
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long codigo){
        clienteServico.deletar(codigo);
    }
    
}
