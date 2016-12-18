/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import model.Livro;
import model.Situacao;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author rhau
 */
public class ConverterSituacao extends Converter {


    @Override
    public Object convertForward(Object value) {
        Situacao s = (Situacao) value;
        switch (s.getValue()) {
            case 1: return "Normal";
            case 2: return "Emprestado";
            case 3: return "Reservado"; 
        }
        return "Emprestado e Reservado";
    }

    @Override
    public Object convertReverse(Object value) {
         String str = (String) value;
         Livro l = new Livro();
         if (str.equals("Normal"))
            l.setSituacao(Situacao.NORMAL);
         else if (str.equals("Emprestado"))
            l.setSituacao(Situacao.EMPRESTADO);
         else if (str.equals("Reservado"))
            l.setSituacao(Situacao.RESERVADO);
         else
             l.setSituacao(Situacao.RESERVADOEMPRESTADO);
         return l.getSituacao();     
    }
            
}
