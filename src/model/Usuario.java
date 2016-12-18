/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import banco.Registro;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author rhau
 */
public abstract class Usuario extends Registro implements Serializable {
    
    private Integer codigo;
    private String nome;
    private String login;
    private Integer matricula;
    private Integer valorMulta;
    private Integer nrLivros;
    private Integer maxLivrosRetirados;
    private Integer maxDiasPorLivro;    
    
    public Usuario() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(Integer valorMulta) {
        this.valorMulta = valorMulta;
    }

    public Integer getNrLivros() {
        return nrLivros;
    }

    public void setNrLivros(Integer nrLivros) {
        this.nrLivros = nrLivros;
    }

    public Integer getMaxLivrosRetirados() {
        return maxLivrosRetirados;
    }

    public void setMaxLivrosRetirados(Integer maxLivrosRetirados) {
        this.maxLivrosRetirados = maxLivrosRetirados;
    }

    public Integer getMaxDiasPorLivro() {
        return maxDiasPorLivro;
    }

    public void setMaxDiasPorLivro(Integer maxDiasPorLivro) {
        this.maxDiasPorLivro = maxDiasPorLivro;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome();
    }
    
}
