/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import views.aluno.AlunoView;
import views.aluno.EmprestimoAlunoView;
import views.aluno.MultaAlunoView;
import views.aluno.ReservaAlunoView;
import views.geral.LivroView;
import views.geral.MainWindowView;
import views.professor.EmprestimoProfessorView;
import views.professor.MultaProfessorView;
import views.professor.ProfessorView;
import views.professor.ReservaProfessorView;

/**
 *
 * @author rhau
 */
public class MainWindowController extends MainWindowView {

    public MainWindowController() {
        super();
        new MultasControl();
    }

    @Override
    protected void btnLivrosActionPerformed(java.awt.event.ActionEvent evt) {
        LivroView lv = null;
        try {
            lv = new LivroView(this, true);
            // args (apartir de qual frame eh criado, eh modal (nao acessa o de tras) ou nao)
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }
        // lv.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        lv.setTitle("Gerenciamento de livros");
        lv.setLocationRelativeTo(null);
        lv.setResizable(false);
        lv.setVisible(true);
    }

    @Override
    protected void maisSobreActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showConfirmDialog(null, "Sistema da biblioteca",
                "Sobre", JOptionPane.DEFAULT_OPTION);
    }
    
    @Override
    protected void btnSairActionPerformed(java.awt.event.ActionEvent evt) {                                        
       dispose();
    }                                       


    /* ALUNOS */
    @Override
    protected void btnAlunosActionPerformed(java.awt.event.ActionEvent evt) {
        AlunoView av = null;
        try {
            av = new AlunoView(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }
        av.setTitle("Gerenciamento de alunos");
        av.setLocationRelativeTo(null);
        av.setResizable(false);
        av.setVisible(true);
    }

    @Override
    protected void btnEmprestimosAlunosActionPerformed(java.awt.event.ActionEvent evt) {
        EmprestimoAlunoView ev = null;
        try {
            ev = new EmprestimoAlunoView(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }
        ev.setTitle("Gerenciamento de empréstimos alunos");
        ev.setLocationRelativeTo(null);
        ev.setResizable(false);
        ev.setVisible(true);
    }

    @Override
    protected void btnReservasAlunosActionPerformed(java.awt.event.ActionEvent evt) {
        ReservaAlunoView rv = null;
        try {
            rv = new ReservaAlunoView(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }
        rv.setTitle("Gerenciamento de reservas alunos");
        rv.setLocationRelativeTo(null);
        rv.setResizable(false);
        rv.setVisible(true);
    }

    @Override
    protected void btnMultasAlunoActionPerformed(java.awt.event.ActionEvent evt) {
        MultaAlunoView mv = null;
        try {
            mv = new MultaAlunoView(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }
        mv.setTitle("Gerenciamento de multas alunos");
        mv.setLocationRelativeTo(null);
        mv.setResizable(false);
        mv.setVisible(true);
    }

    /* FIM ALUNOS */

    /* PROFESSORES */
    @Override
    protected void btnProfessoresActionPerformed(java.awt.event.ActionEvent evt) {
        ProfessorView pv = null;
        try {
            pv = new ProfessorView(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }
        pv.setTitle("Gerenciamento de professores");
        pv.setLocationRelativeTo(null);
        pv.setResizable(false);
        pv.setVisible(true);
    }

    @Override
    protected void btnEmprestimosProfsActionPerformed(java.awt.event.ActionEvent evt) {
        EmprestimoProfessorView ev = null;
        try {
            ev = new EmprestimoProfessorView(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }
        ev.setTitle("Gerenciamento de empréstimos professores");
        ev.setLocationRelativeTo(null);
        ev.setResizable(false);
        ev.setVisible(true);
    }

    @Override
    protected void btnReservasProfsActionPerformed(java.awt.event.ActionEvent evt) {
        ReservaProfessorView rv = null;
        try {
            rv = new ReservaProfessorView(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }
        rv.setTitle("Gerenciamento de reservas professores");
        rv.setLocationRelativeTo(null);
        rv.setResizable(false);
        rv.setVisible(true);
    }

    @Override
    protected void btnMultasProfsActionPerformed(java.awt.event.ActionEvent evt) {
        MultaProfessorView mv = null;
        try {
            mv = new MultaProfessorView(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowView.class.getName()).log(Level.SEVERE, null, ex);
        }
        mv.setTitle("Gerenciamento de multas professores");
        mv.setLocationRelativeTo(null);
        mv.setResizable(false);
        mv.setVisible(true);
    }

    /* FIM PROFESSORES */
    
}
