/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import banco.Registros;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import model.Emprestimo;
import model.Livro;
import model.Usuario;

/**
 *
 * @author rhau
 */
public class EmprestimoAlunoDAO extends Registros<Emprestimo> {

    LivroDAO ldao = new LivroDAO();
    AlunoDAO adao = new AlunoDAO();

    public EmprestimoAlunoDAO() {
        setSqlInsercao("INSERT INTO emprestimos_aluno (retirada, entrega, id_livro, id_aluno)"
                + "VALUES (?, ?, ?, ?)");
        setSqlAlteracao("UPDATE emprestimos_aluno SET retirada = ?, entrega = ?, "
                + "id_livro = ?, id_aluno = ? WHERE codigo = ?");
        setSqlExclusao("DELETE FROM emprestimos_aluno WHERE codigo = ?");
        setSqlBuscaTodos("SELECT * FROM emprestimos_aluno");
        setSqlBuscaChavePrimaria("SELECT FROM emprestimos_aluno WHERE codigo = ?");
    }

    @Override
    protected void preencherInsercao(PreparedStatement ps, Emprestimo e) throws SQLException {
        ps.setDate(1, new java.sql.Date(e.getRetirada().getTimeInMillis()));
        ps.setDate(2, new java.sql.Date(e.getEntrega().getTimeInMillis()));
        ps.setInt(3, e.getExemplar().getCodigo());
        ps.setInt(4, e.getUser().getCodigo());
    }

    @Override
    protected void preencherAlteracao(PreparedStatement ps, Emprestimo e) throws SQLException {
        ps.setDate(1, new java.sql.Date(e.getRetirada().getTimeInMillis()));
        ps.setDate(2, new java.sql.Date(e.getEntrega().getTimeInMillis()));
        ps.setInt(3, e.getExemplar().getCodigo());
        ps.setInt(4, e.getUser().getCodigo());
        ps.setInt(5, e.getCodigo());
    }

    @Override
    protected void preencherExclusao(PreparedStatement ps, Emprestimo e) throws SQLException {
        ps.setInt(1, e.getCodigo());
    }

    @Override
    protected void preencherBusca(PreparedStatement ps, Emprestimo e) throws SQLException {
        ps.setInt(1, e.getCodigo());
    }

    @Override
    protected Emprestimo preencher(ResultSet rs) throws SQLException {
        Emprestimo e = new Emprestimo();
        e.setCodigo(rs.getInt("codigo"));
        java.sql.Date dt = rs.getDate("retirada");
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        e.setRetirada(c);
        java.sql.Date dt2 = rs.getDate("entrega");
        Calendar c2 = Calendar.getInstance();
        c2.setTime(dt2);
        e.setEntrega(c2);
        e.setExemplar((Livro) ldao.buscar(rs.getInt("id_livro")));
        e.setUser((Usuario) adao.buscar(rs.getInt("id_aluno")));
        return e;
    }

    @Override
    protected Collection<Emprestimo> preencherColecao(ResultSet rs) throws SQLException {
        Collection<Emprestimo> emprestimos = new ArrayList<>();
        while (rs.next()) 
            emprestimos.add(preencher(rs));
        return emprestimos;
    }
    
    public String getSqlBuscaAluno() {
        return "SELECT * from emprestimos_aluno WHERE id_aluno = ?";
    }
    
    public String getSqlBuscaLivro() {
        return "SELECT * from emprestimos_aluno WHERE id_livro = ?";
    }
    
}
