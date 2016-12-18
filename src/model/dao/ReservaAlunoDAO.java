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
import model.Livro;
import model.Reserva;
import model.Usuario;

/**
 *
 * @author rhau
 */
public class ReservaAlunoDAO extends Registros<Reserva> {
    
    LivroDAO ldao = new LivroDAO();
    AlunoDAO adao = new AlunoDAO();

    public ReservaAlunoDAO() {
        setSqlInsercao("INSERT INTO reservas_aluno (retirada, id_livro, id_aluno)"
                + "VALUES (?, ?, ?)");
        setSqlAlteracao("UPDATE reservas_aluno SET retirada = ?,"
                + "id_livro = ?, id_aluno = ? WHERE codigo = ?");
        setSqlExclusao("DELETE FROM reservas_aluno WHERE codigo = ?");
        setSqlBuscaTodos("SELECT * FROM reservas_aluno");
        setSqlBuscaChavePrimaria("SELECT FROM reservas_aluno WHERE codigo = ?");

    }

    @Override
    protected void preencherInsercao(PreparedStatement ps, Reserva r) throws SQLException {
        ps.setDate(1, new java.sql.Date(r.getRetirada().getTimeInMillis()));
        ps.setInt(2, r.getExemplar().getCodigo());
        ps.setInt(3, r.getUser().getCodigo());
    }

    @Override
    protected void preencherAlteracao(PreparedStatement ps, Reserva r) throws SQLException {
        ps.setDate(1, new java.sql.Date(r.getRetirada().getTimeInMillis()));
        ps.setInt(2, r.getExemplar().getCodigo());
        ps.setInt(3, r.getUser().getCodigo());
        ps.setInt(4, r.getCodigo());
    }

    @Override
    protected void preencherExclusao(PreparedStatement ps, Reserva r) throws SQLException {
        ps.setInt(1, r.getCodigo());
    }

    @Override
    protected void preencherBusca(PreparedStatement ps, Reserva r) throws SQLException {
        ps.setInt(1, r.getCodigo());
    }

    @Override
    protected Reserva preencher(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setCodigo(rs.getInt("codigo"));
        java.sql.Date dt = rs.getDate("retirada");
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        r.setRetirada(c);
        r.setExemplar((Livro) ldao.buscar(rs.getInt("id_livro")));
        r.setUser((Usuario) adao.buscar(rs.getInt("id_aluno")));
        return r;
    }

    @Override
    protected Collection<Reserva> preencherColecao(ResultSet rs) throws SQLException {
        Collection<Reserva> reservas = new ArrayList<>();
        while (rs.next()) 
            reservas.add(preencher(rs));
        return reservas;
    }
    
    public String getSqlBuscaAluno() {
        return "SELECT * from reservas_aluno WHERE id_aluno = ?";
    }
    
    public String getSqlBuscaLivro() {
        return "SELECT * from reservas_aluno WHERE id_livro = ?";
    }
    
}
