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
public class Aluno extends Usuario{
    
    public Aluno() {
        setMaxDiasPorLivro(7);
        setMaxLivrosRetirados(3);
    }

}
