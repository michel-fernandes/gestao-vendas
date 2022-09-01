package com.gvendas.gestaovendas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gvendas.gestaovendas.entidades.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long>{
    
    //como não há esse tipo de consulta no JPARepository precisamos escrever a requisição
    //a requisição de banco é como se fosse -> from categoria cat where cat.nome = "string"
    Categoria findByNome(String nome);
}
