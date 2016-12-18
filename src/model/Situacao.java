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
public enum Situacao {

    NORMAL(1), EMPRESTADO(2), RESERVADO(3), RESERVADOEMPRESTADO(4);

    private final Integer value;
    private String estado;

    Situacao(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static Situacao getSituacao(int value) {
        switch (value) {
            case 2:
                return EMPRESTADO;
            case 3:
                return RESERVADO;
            case 4:
                return RESERVADOEMPRESTADO;
        }
        return NORMAL;
    }
    
}
