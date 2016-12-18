/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import banco.Registro;
import java.beans.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author rhau
 */
public class Emprestimo extends Registro implements Serializable {
    
    private Integer codigo;
    private Livro exemplar;
    private Usuario user;
    private Calendar retirada;
    private Calendar entrega;

    public Emprestimo() {
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    

    public Livro getExemplar() {
        return exemplar;
    }

    public void setExemplar(Livro exemplar) {
        this.exemplar = exemplar;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Calendar getRetirada() {
        return retirada;
    }

    public void setRetirada(Calendar retirada) {
        this.retirada = retirada;
    }
    
    @Transient
    public String getRetiradaFormatado(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(retirada.getTime());                
    }

    public Calendar getEntrega() {
        return entrega;
    }

    public void setEntrega(Calendar entrega) {
        this.entrega = entrega;
    }
    
    @Transient
    public String getEntregaFormatado(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(entrega.getTime());                
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.codigo);
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
        final Emprestimo other = (Emprestimo) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
    
}
