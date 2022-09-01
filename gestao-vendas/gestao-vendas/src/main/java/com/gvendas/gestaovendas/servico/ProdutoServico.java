package com.gvendas.gestaovendas.servico;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.gvendas.gestaovendas.repositorio.ProdutoRepositorio;
import com.gvendas.gestaovendas.entidades.Produto;

import java.util.List;
import java.util.Optional;;

@Service
public class ProdutoServico {
    
    //instanciar em tempo de execução a classe CategoriaRepositorio para conexão com o banco de dados
    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    
    public List<Produto> listarTodas() {
        return produtoRepositorio.findAll();
    }
    
    public Optional<Produto> buscarPorCodigo(Long codigo) {
        return produtoRepositorio.findById(codigo);
    }
}
