package com.gvendas.gestaovendas.entidades;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "cliente")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="codigo")
    private long codigo;
    
    @Column(name="nome")
    private String nome;

    @Column(name="telefone")
    private String telefone;

    @Column(name="ativo")
    private boolean ativo;

    @Embedded
    private Endereco endereco;
   
    public Cliente() {
    }

    
    public Cliente(long codigo, String nome, String telefone, boolean ativo, Endereco endereco) {
        this.codigo = codigo;
        this.nome = nome;
        this.telefone = telefone;
        this.ativo = ativo;
        this.endereco = endereco;
    }

    public Cliente(String nome, String telefone, boolean ativo, Endereco endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.ativo = ativo;
        this.endereco = endereco;
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (ativo ? 1231 : 1237);
        result = prime * result + (int) (codigo ^ (codigo >>> 32));
        result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cliente other = (Cliente) obj;
        if (ativo != other.ativo)
            return false;
        if (codigo != other.codigo)
            return false;
        if (endereco == null) {
            if (other.endereco != null)
                return false;
        } else if (!endereco.equals(other.endereco))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (telefone == null) {
            if (other.telefone != null)
                return false;
        } else if (!telefone.equals(other.telefone))
            return false;
        return true;
    }
    
}
