/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Aluno;
import model.Emprestimo;
import model.Professor;
import model.dao.AlunoDAO;
import model.dao.EmprestimoAlunoDAO;
import model.dao.EmprestimoProfessorDAO;
import model.dao.ProfessorDAO;
import util.ConverterData;
import static util.OperacoesData.dataSistema;

/**
 *
 * @author rhau
 */
public class MultasControl {
    
    public MultasControl() {
        verificaMultas(new EmprestimoAlunoDAO());
        verificaMultas(new EmprestimoProfessorDAO());
    }
    
    /* MULTAS */
    public void verificaMultas(EmprestimoAlunoDAO edao) {
        List<Emprestimo> emprestimos = null;
        try {
            emprestimos = edao.buscarTodos();
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AlunoDAO adao = new AlunoDAO();
        List<Aluno> alunos = null;
        try {
            alunos = adao.buscarTodos();
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConverterData conversor = new ConverterData();
        Calendar dataAtual = (Calendar) conversor.convertReverse(dataSistema());
        for (Emprestimo e : emprestimos) // zera
            e.getUser().setValorMulta(0);
        for (Emprestimo e : emprestimos) {
            if (e.getEntrega().compareTo(dataAtual) < 0) { // data entrega antes data atual
                // acumula multas caso haja mais de uma
                e.getUser().setValorMulta(e.getUser().getValorMulta() + calculaMulta(e, dataAtual));
                adao.alterar((Aluno) e.getUser());
            }
        }
    }

    public void verificaMultas(EmprestimoProfessorDAO edao) {
        List<Emprestimo> emprestimos = null;
        try {
            emprestimos = edao.buscarTodos();
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ProfessorDAO pdao = new ProfessorDAO();
        List<Professor> professores = null;
        try {
            professores = pdao.buscarTodos();
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }       
        ConverterData conversor = new ConverterData();
        Calendar dataAtual = (Calendar) conversor.convertReverse(dataSistema());
        for (Emprestimo e : emprestimos)
            e.getUser().setValorMulta(0);
        for (Emprestimo e : emprestimos) {
            if (e.getEntrega().compareTo(dataAtual) < 0) { // data entrega antes data atual
                e.getUser().setValorMulta(e.getUser().getValorMulta() + calculaMulta(e, dataAtual));
                pdao.alterar((Professor) e.getUser());
            }
        }
    }

    /* Retorna o numero de dias entre a data de entrega do livro e a data atual (equivale a 1 real/dia) */
    public Integer calculaMulta(Emprestimo e, Calendar dataAtual) {
        Date dataInicial = (Date) e.getEntrega().getTime();
        Date dataFinal = (Date) dataAtual.getTime();
        long dt = (dataFinal.getTime() - dataInicial.getTime()) + 3600000; // soma para horario verao
        long dias = (dt / 86400000L);
        return (int) dias;
    }
    /* FIM MULTAS */
    
}
