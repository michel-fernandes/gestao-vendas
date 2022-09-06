package com.gvendas.gestaovendas.dto.cliente;

import com.gvendas.gestaovendas.entidades.Cliente;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Cliente retorno DTO")
public class ClienteResponseDTO {

    @ApiModelProperty(value = "Código")
    private long codigo;

    @ApiModelProperty(value = "Nome")
    private String nome;

    @ApiModelProperty(value = "Telefone")
    private String telefone;

    @ApiModelProperty(value = "Ativo")
    private boolean ativo;

    private EnderencoResponseDTO enderencoResponseDTO;

    public ClienteResponseDTO(long codigo, String nome, String telefone, boolean ativo,
            EnderencoResponseDTO enderencoResponseDTO) {
        this.codigo = codigo;
        this.nome = nome;
        this.telefone = telefone;
        this.ativo = ativo;
        this.enderencoResponseDTO = enderencoResponseDTO;
    }

    public static ClienteResponseDTO converterParaClienteResponseDTO(Cliente cliente) {
        EnderencoResponseDTO enderencoResponseDTO = new EnderencoResponseDTO(cliente.getEndereco().getLogradouro(),
                cliente.getEndereco().getNumero(),
                cliente.getEndereco().getComplemento(), cliente.getEndereco().getBairro(),
                cliente.getEndereco().getCep(), cliente.getEndereco().getCidade(), cliente.getEndereco().getEstado());
        return new ClienteResponseDTO(cliente.getCodigo(), cliente.getNome(), cliente.getTelefone(), cliente.isAtivo(),
                enderencoResponseDTO);
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public EnderencoResponseDTO getEnderencoResponseDTO() {
        return enderencoResponseDTO;
    }

    public void setEnderencoResponseDTO(EnderencoResponseDTO enderencoResponseDTO) {
        this.enderencoResponseDTO = enderencoResponseDTO;
    }
}
