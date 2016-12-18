package model.dao;

import banco.Registros;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import model.Professor;

/**
 *
 * @author rhau
 */
public class ProfessorDAO extends Registros<Professor> { 
        
    public ProfessorDAO() {
        setSqlInsercao("INSERT INTO professores (nome, matricula, multa, livros, login)"
                + "VALUES (?, ?, ?, ?, ?)");
        setSqlAlteracao("UPDATE professores SET nome = ?, matricula = ?, "
                + "multa = ?, livros = ?, login = ? WHERE codigo = ?");
        setSqlExclusao("DELETE FROM professores WHERE codigo = ?");
        setSqlBuscaChavePrimaria("SELECT * FROM professores WHERE codigo = ?");
        setSqlBuscaContida("SELECT * FROM professores WHERE nome LIKE ?");
        setSqlBuscaTodos("SELECT * FROM professores");
    }

    @Override
    protected void preencherInsercao(PreparedStatement ps, Professor p) throws SQLException {
        ps.setString(1, p.getNome());
        ps.setInt(2, p.getMatricula());
        ps.setInt(3, p.getValorMulta());
        ps.setInt(4, p.getNrLivros());
        ps.setString(5, p.getLogin());
    }

    @Override
    protected void preencherAlteracao(PreparedStatement ps, Professor p) throws SQLException {
        ps.setString(1, p.getNome());
        ps.setInt(2, p.getMatricula());
        ps.setInt(3, p.getValorMulta());
        ps.setInt(4, p.getNrLivros());
        ps.setString(5, p.getLogin());
        ps.setInt(6, p.getCodigo());
    }

    @Override
    protected void preencherExclusao(PreparedStatement ps, Professor p) throws SQLException {
        ps.setInt(1, p.getCodigo());
    }

    @Override
    protected void preencherBusca(PreparedStatement ps, Professor p) throws SQLException {
        ps.setString(1, "%" + p.getNome() + "%");
    }

    @Override
    protected Professor preencher(ResultSet rs) throws SQLException {
        Professor p = new Professor();
        p.setCodigo(rs.getInt("codigo"));
        p.setNome(rs.getString("nome"));
        p.setMatricula(rs.getInt("matricula"));
        p.setValorMulta(rs.getInt("multa"));
        p.setNrLivros(rs.getInt("livros"));
        p.setLogin(rs.getString("login"));
        return p;
    }

    @Override
    protected Collection<Professor> preencherColecao(ResultSet rs) throws SQLException {
        Collection<Professor> professores = new ArrayList<>();
        while (rs.next())
            professores.add(preencher(rs));
        return professores;
    }
    
    public String getSqlBuscaMatricula() {
        return "SELECT * FROM professores WHERE matricula = ?";
    }  
    
    public String getSqlBuscaLogin() {
        return "SELECT * FROM professores WHERE login =  ?";
    }
    
}
