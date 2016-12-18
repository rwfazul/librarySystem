package model.dao;

import banco.Registros;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import model.Livro;
import model.Situacao;

/**
 *
 * @author rhau
 */
public class LivroDAO extends Registros<Livro> {

    public LivroDAO() {
        setSqlInsercao("INSERT INTO livros (isbn, nome, editora, edicao, ano, autor, situacao)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)");
        setSqlAlteracao("UPDATE livros SET isbn = ?, nome = ?, editora = ?, "
                + "edicao = ?, ano = ?, autor = ?, situacao = ? WHERE codigo = ?");
        setSqlExclusao("DELETE FROM livros WHERE codigo = ?");
        setSqlBuscaChavePrimaria("SELECT * FROM livros WHERE codigo = ?");
        setSqlBuscaContida("SELECT * FROM livros WHERE nome LIKE ?");
        setSqlBuscaTodos("SELECT * FROM livros");
    }

    @Override
    protected void preencherInsercao(PreparedStatement ps, Livro l) throws SQLException {
        ps.setInt(1, l.getIsbn());
        ps.setString(2, l.getNome());
        ps.setString(3, l.getEditora());
        ps.setString(4, l.getEdicao());
        ps.setInt(5, l.getAno());
        ps.setString(6, l.getAutor());
        ps.setInt(7, l.getSituacao().getValue());
    }

    @Override
    protected void preencherAlteracao(PreparedStatement ps, Livro l) throws SQLException {
        ps.setInt(1, l.getIsbn());
        ps.setString(2, l.getNome());
        ps.setString(3, l.getEditora());
        ps.setString(4, l.getEdicao());
        ps.setInt(5, l.getAno());
        ps.setString(6, l.getAutor());
        ps.setInt(7, l.getSituacao().getValue());
        ps.setInt(8, l.getCodigo());
    }

    @Override
    protected void preencherExclusao(PreparedStatement ps, Livro l) throws SQLException {
        ps.setInt(1, l.getCodigo());
    }

    @Override
    protected void preencherBusca(PreparedStatement ps, Livro l) throws SQLException {
        ps.setString(1, "%" + l.getNome() + "%");
    }

    @Override
    protected Livro preencher(ResultSet rs) throws SQLException {
        Livro l = new Livro();
        l.setCodigo(rs.getInt("codigo"));
        l.setIsbn(rs.getInt("isbn"));
        l.setNome(rs.getString("nome"));
        l.setEditora(rs.getString("editora"));
        l.setEdicao(rs.getString("edicao"));
        l.setAno(rs.getInt("ano"));
        l.setAutor(rs.getString("autor"));
        l.setSituacao(Situacao.getSituacao(rs.getInt("situacao")));
        return l;
    }

    @Override
    protected Collection<Livro> preencherColecao(ResultSet rs) throws SQLException {
        Collection<Livro> livros = new ArrayList<>();
        while (rs.next()) 
            livros.add(preencher(rs));
        return livros;
    }

    public String getSqlBuscaIsbn() {
        return "SELECT * FROM livros WHERE isbn = ?";
    }

    public String getSqlBuscaEditora() {
        return "SELECT * FROM livros WHERE editora = ?";
    }
    
    public String getSqlBuscaDisponivel() {
        return "SELECT * FROM livros WHERE situacao = 1";
    }
   
}
