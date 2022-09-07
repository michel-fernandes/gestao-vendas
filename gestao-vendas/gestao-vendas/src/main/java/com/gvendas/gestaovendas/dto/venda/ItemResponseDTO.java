package com.gvendas.gestaovendas.dto.venda;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Itens da venda retorno DTO")
public class ItemResponseDTO {
    
    @ApiModelProperty(value = "Código")
    private Long codigo;

    @ApiModelProperty(value = "Quantidade")
    private Integer quantidade;

    @ApiModelProperty(value = "Preço vendido")
    private BigDecimal procoVendido;

    @ApiModelProperty(value = "Código produto")
    private Long codigoProduto;

    @ApiModelProperty(value = "Descrição do produto")
    private String produtoDescricao;

    public ItemResponseDTO(Long codigo, Integer quantidade, BigDecimal procoVendido, Long codigoProduto,
            String produtoDescricao) {
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.procoVendido = procoVendido;
        this.codigoProduto = codigoProduto;
        this.produtoDescricao = produtoDescricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getProcoVendido() {
        return procoVendido;
    }

    public void setProcoVendido(BigDecimal procoVendido) {
        this.procoVendido = procoVendido;
    }

    public Long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getProdutoDescricao() {
        return produtoDescricao;
    }

    public void setProdutoDescricao(String produtoDescricao) {
        this.produtoDescricao = produtoDescricao;
    }

}
