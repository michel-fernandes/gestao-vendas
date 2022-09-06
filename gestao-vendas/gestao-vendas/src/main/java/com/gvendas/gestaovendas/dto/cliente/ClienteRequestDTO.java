package com.gvendas.gestaovendas.dto.cliente;

import javax.validation.Valid;

import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.Endereco;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Cliente requisição DTO")
public class ClienteRequestDTO {

    @ApiModelProperty(value = "Nome")
    private String nome;

    @ApiModelProperty(value = "Telefone")
    private String telefone;

    @ApiModelProperty(value = "Ativo")
    private boolean ativo;

    @ApiModelProperty(value = "Endereço")
    private EnderencoRequestDTO enderencoRequestDTO;

    public Cliente converterParaEntidadeCliente(@Valid ClienteRequestDTO clienteRequestDTO) {
        Endereco endereco = new Endereco(enderencoRequestDTO.getLogradouro(), enderencoRequestDTO.getNumero(),
                enderencoRequestDTO.getComplemento(), enderencoRequestDTO.getBairro(), enderencoRequestDTO.getCep(),
                enderencoRequestDTO.getCidade(), enderencoRequestDTO.getEstado());
        return new Cliente(nome, telefone, ativo, endereco);
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

    public EnderencoRequestDTO getEnderencoRequestDTO() {
        return enderencoRequestDTO;
    }

    public void setEnderencoRequestDTO(EnderencoRequestDTO enderencoRequestDTO) {
        this.enderencoRequestDTO = enderencoRequestDTO;
    }

}
