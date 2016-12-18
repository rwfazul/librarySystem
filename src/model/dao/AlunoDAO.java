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
import java.util.Collection;
import model.Aluno;

/**
 *
 * @author rhau
 */
public class AlunoDAO extends Registros<Aluno> {
        
    public AlunoDAO() {
        setSqlInsercao("INSERT INTO alunos (nome, matricula, multa, livros, login)"
                + "VALUES (?, ?, ?, ?, ?)");
        setSqlAlteracao("UPDATE alunos SET nome = ?, matricula = ?, "
                + "multa = ?, livros = ?, login = ? WHERE codigo = ?");
        setSqlExclusao("DELETE FROM alunos WHERE codigo = ?");
        setSqlBuscaChavePrimaria("SELECT * FROM alunos WHERE codigo = ?");
        setSqlBuscaContida("SELECT * FROM alunos WHERE nome LIKE ?");
        setSqlBuscaTodos("SELECT * FROM alunos");
    }

    @Override
    protected void preencherInsercao(PreparedStatement ps, Aluno a) throws SQLException {
        ps.setString(1, a.getNome());
        ps.setInt(2, a.getMatricula());
        ps.setInt(3, a.getValorMulta());
        ps.setInt(4, a.getNrLivros());
        ps.setString(5, a.getLogin());
    }

    @Override
    protected void preencherAlteracao(PreparedStatement ps, Aluno a) throws SQLException {
        ps.setString(1, a.getNome());
        ps.setInt(2, a.getMatricula());
        ps.setInt(3, a.getValorMulta());
        ps.setInt(4, a.getNrLivros());
        ps.setString(5, a.getLogin());
        ps.setInt(6, a.getCodigo());
    }

    @Override
    protected void preencherExclusao(PreparedStatement ps, Aluno a) throws SQLException {
        ps.setInt(1, a.getCodigo());
    }

    @Override
    protected void preencherBusca(PreparedStatement ps, Aluno a) throws SQLException {
        ps.setString(1, "%" + a.getNome() + "%");
    }

    @Override
    protected Aluno preencher(ResultSet rs) throws SQLException {
        Aluno a = new Aluno();
        a.setCodigo(rs.getInt("codigo"));
        a.setNome(rs.getString("nome"));
        a.setMatricula(rs.getInt("matricula"));
        a.setValorMulta(rs.getInt("multa"));
        a.setNrLivros(rs.getInt("livros"));
        a.setLogin(rs.getString("login"));
        return a;
    }

    @Override
    protected Collection<Aluno> preencherColecao(ResultSet rs) throws SQLException {
        Collection<Aluno> alunos = new ArrayList<>();
        while (rs.next())
            alunos.add(preencher(rs));
        return alunos;
    }
    
    public String getSqlBuscaMatricula() {
        return "SELECT * FROM alunos WHERE matricula = ?";
    }   
    
    public String getSqlBuscaLogin() {
        return "SELECT * FROM alunos WHERE login = ?";
    }
    
}
