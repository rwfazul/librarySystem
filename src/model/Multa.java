/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author rhau
 */
public class Multa {

    private Usuario user;
    private Integer valor;

    public Multa() {
    }
    
    public Multa(Usuario user, Integer valor) {
        setUser(user);
        setValor(valor);
    }
    
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
    
}
