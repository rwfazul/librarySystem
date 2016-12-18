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
public class Reserva extends Registro implements Serializable {
    
    private Integer codigo;
    private Livro exemplar;
    private Usuario user;
    private Calendar retirada;

    public Reserva() {
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.codigo);
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
        final Reserva other = (Reserva) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
    
}
