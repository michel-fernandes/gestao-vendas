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

import com.gvendas.gestaovendas.dto.produto.ProdutoRequestDTO;
import com.gvendas.gestaovendas.dto.produto.ProdutoResponseDTO;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.servico.ProdutoServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produto")
@RestController
@RequestMapping("/produto")

public class ProdutoControlador {

    @Autowired
    private ProdutoServico produtoServico;

    @ApiOperation(value = "Listar", nickname = "listarTodosProduto")
    @GetMapping
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoServico.listarTodos().stream()
                .map(produto -> ProdutoResponseDTO.converterParaResponseDTO(produto)).collect(Collectors.toList());
    }

    @ApiOperation(value = "Listar por código do produto", nickname = "buscarPorCodigoProduto")
    @GetMapping("/{codigo}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorCodigo(@PathVariable(name = "codigo") Long codigo) {
        Optional<Produto> produto = produtoServico.buscarPorCodigo(codigo);
        return produto.isPresent() ? ResponseEntity.ok(ProdutoResponseDTO.converterParaResponseDTO(produto.get()))
                : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Listar por código da Categoria", nickname = "listarTodosProdutoPorCategoria")
    @GetMapping("/categoria/{codigoCategoria}")
    public List<ProdutoResponseDTO> listarTodosPorCategoria(
            @PathVariable(name = "codigoCategoria") Long codigoCategoria) {
        return produtoServico.listarTodasPorCategoria(codigoCategoria).stream()
                .map(produto -> ProdutoResponseDTO.converterParaResponseDTO(produto)).collect(Collectors.toList());
    }

    @ApiOperation(value = "Listar por código da Categoria e por código do Produto", nickname = "buscarProdutoPorCodigoEPorCategoria")
    @GetMapping("/{codigo}/categoria/{codigoCategoria}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorCodigoECategoria(
            @PathVariable(name = "codigoCategoria") Long codigoCategoria, @PathVariable(name = "codigo") Long codigo) {
        Optional<Produto> produto = produtoServico.buscarPorCodigoEPorCategoria(codigo, codigoCategoria);
        return produto.isPresent() ? ResponseEntity.ok(ProdutoResponseDTO.converterParaResponseDTO(produto.get()))
                : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Salvar", nickname = "salvarProduto")
    @PostMapping
    // @valid irá aplicar as constraints bean validator da class produto @NotNull e @Length
    public ResponseEntity<ProdutoResponseDTO> salvar(@Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        Produto produtoSalvo = produtoServico.salvar(produtoDTO.converterParaEntidade(produtoDTO.getCodigoCategoria()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoResponseDTO.converterParaResponseDTO(produtoSalvo));
    }

    @ApiOperation(value = "Atualizar", nickname = "atualizarProduto")
    @PutMapping("/{codigo}")
    // @valid irá aplicar as constraints bean validator da class produto @NotNull e @Length
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable(name = "codigo") Long codigo,
            @Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        Produto produtoAtualizado = produtoServico.atualizar(codigo, produtoDTO.converterParaEntidade(codigo, produtoDTO.getCodigoCategoria()));
        return ResponseEntity.ok(ProdutoResponseDTO.converterParaResponseDTO(produtoAtualizado));
    }

    @ApiOperation(value = "Deletar", nickname = "deletarProduto")
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable(name = "codigo") Long codigo) {
        produtoServico.deletar(codigo);
    }

}
