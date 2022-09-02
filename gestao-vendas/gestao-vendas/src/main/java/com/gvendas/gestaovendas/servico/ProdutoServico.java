package com.gvendas.gestaovendas.servico;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.gvendas.gestaovendas.repositorio.ProdutoRepositorio;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.excecao.RegraDeNegocioException;

import java.util.List;
import java.util.Optional;;

@Service
public class ProdutoServico {
    
    //instanciar em tempo de execução a classe CategoriaRepositorio para conexão com o banco de dados
    @Autowired
    private ProdutoRepositorio produtoRepositorio;
 
    @Autowired
    private CategoriaServico categoriaServico;

    public List<Produto> listarTodos() {
        return produtoRepositorio.findAll();
    }
  
    public Optional<Produto> buscarPorCodigo(Long codigo) {
        return produtoRepositorio.findById(codigo);
    }

    public List<Produto> listarTodasPorCategoria(Long codigoCategoria) {
        return produtoRepositorio.findByCategoriaCodigo(codigoCategoria);
    }
    
    public Optional<Produto> buscarPorCodigoEPorCategoria(Long codigo, Long codigoCategoria) {
        return produtoRepositorio.buscarPorCodigo(codigo, codigoCategoria);
    }

    public Produto salvar(Produto produto){
        validarCategoriaDoProdutoExiste(produto.getCategoria().getCodigo());
        validarProdutoDuplicado(produto);
        return produtoRepositorio.save(produto);
    }

    private void validarProdutoDuplicado(Produto produto){
        if(produtoRepositorio.findByDescricaoAndCategoriaCodigo(produto.getDescricao(), produto.getCategoria().getCodigo()).isPresent()){
            throw new RegraDeNegocioException(String.format("O produto %s já está cadastrado", produto.getDescricao().toUpperCase()));
        }
    }

    public void validarCategoriaDoProdutoExiste(Long codigoCategoria){
        if(codigoCategoria == null || codigoCategoria == 0){
            throw new RegraDeNegocioException("A categoria não pode ser nula");
        }
        if(categoriaServico.buscarPorCodigo(codigoCategoria).isEmpty()){
            throw new RegraDeNegocioException(String.format("A categoria de código %s informada não existe", codigoCategoria));
        }
    }
}
