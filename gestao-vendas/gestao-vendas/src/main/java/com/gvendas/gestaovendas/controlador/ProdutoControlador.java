package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    
     @ApiOperation(value = "Listar", nickname = "listarTodos")
      
     @GetMapping
     public List<Produto> listarTodos() {
     return produtoServico.listarTodos();
     }
     
     
     @ApiOperation(value = "Listar por código do produto", nickname = "buscarPorCodigoProduto")
     @GetMapping("/{codigo}")
     public ResponseEntity<Optional<Produto>> buscarPorCodigo(@PathVariable(name =
     "codigo") Long codigo) {
     Optional<Produto> produto = produtoServico.buscarPorCodigo(codigo);
     return produto.isPresent() ? ResponseEntity.ok(produto) :
     ResponseEntity.notFound().build();
     }
     
    @ApiOperation(value = "Listar por código da Categoria", nickname = "listarTodasPorCategoria")
    @GetMapping("/categoria/{codigoCategoria}")
    public List<Produto> listarTodosPorCategoria(@PathVariable(name = "codigoCategoria") Long codigoCategoria) {
        return produtoServico.listarTodasPorCategoria(codigoCategoria);
    }

    @ApiOperation(value = "Listar por código da Categoria e por código do Produto", nickname = "buscarPorCodigoEPorCategoria")
    @GetMapping("/{codigo}/categoria/{codigoCategoria}")
    public ResponseEntity<Optional<Produto>> buscarPorCodigoECategoria(
            @PathVariable(name = "codigoCategoria") Long codigoCategoria, @PathVariable(name = "codigo") Long codigo) {
        Optional<Produto> produto = produtoServico.buscarPorCodigoEPorCategoria(codigo, codigoCategoria);
        return produto.isPresent() ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Salvar", nickname = "salvarProduto")
    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto){
        Produto produtoSalvo = produtoServico.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }
}