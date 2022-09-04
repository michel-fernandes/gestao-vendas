package com.gvendas.gestaovendas.dto.categoria;

import com.gvendas.gestaovendas.entidades.Categoria;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Categoria retorno DTO")
public class CategoriaResponseDTO {
    
    @ApiModelProperty(value = "Código")
    private long codigo;

    @ApiModelProperty(value = "Nome")
    private String nome;
    
    
    public CategoriaResponseDTO(long codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public static CategoriaResponseDTO converterParaCategoriaDTO(Categoria categoria){
        return new CategoriaResponseDTO(categoria.getCodigo(), categoria.getNome());
    }

    public long getCodigo() {
        return codigo;
    }
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }    
}
