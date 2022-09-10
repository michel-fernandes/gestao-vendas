package com.gvendas.gestaovendas.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gvendas.gestaovendas.entidades.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
        
    //fazer uma busca no Produto pelo atributo categoria - da objeto categoria (JPA têm que ser maiusculo) 
    // do qual eu entro na categoria e busco por codigo (JPA têm que ser maiusculo) 
    
    List<Produto> findByCategoriaCodigo(Long codigoCategoria);

    //prod é o nome do alias
    @Query("select new com.gvendas.gestaovendas.entidades.Produto("
            + " prod.codigo, prod.descricao, prod.quantidade, prod.precoCusto, prod.precoVenda, prod.observacao, prod.categoria)"
    + " from Produto prod"
    + " where prod.codigo = :codigo"
    + " and prod.categoria.codigo = :codigoCategoria")
    Optional<Produto> buscarPorCodigo(Long codigo, Long codigoCategoria);
    //Optional é retornado caso não encontre. Nesta imp. tb poderia ser usado findByCodigoCategoriaCodigo

//    Optional<Produto> findByDescricaoAndCategoriaCodigo(String descricao, long codigoCategoria);
//  alternativa por query do método acima
    @Query("select new com.gvendas.gestaovendas.entidades.Produto("
            + " prod.codigo, prod.descricao, prod.quantidade, prod.precoCusto, prod.precoVenda, prod.observacao, prod.categoria)"
            + " from Produto prod"
            + " where prod.descricao = :descricao"
            + " and prod.categoria.codigo = :codigoCategoria")
    Optional<Produto> buscarPorDescricaoAndCategoriaCodigo(String descricao, Long codigoCategoria);
}