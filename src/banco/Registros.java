package banco;

/**
 *
 * @author rhau
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import javax.swing.JOptionPane;

public abstract class Registros<T extends Registro> {

    private String sqlInsercao;
    private String sqlAlteracao;
    private String sqlExclusao;
    private String sqlBuscaChavePrimaria;
    private String sqlBuscaContida;
    private String sqlBuscaTodos;

    public String getSqlInsercao() {
        return sqlInsercao;
    }

    public void setSqlInsercao(String sqlInsercao) {
        this.sqlInsercao = sqlInsercao;
    }

    public String getSqlAlteracao() {
        return sqlAlteracao;
    }

    public void setSqlAlteracao(String sqlAlteracao) {
        this.sqlAlteracao = sqlAlteracao;
    }

    public String getSqlExclusao() {
        return sqlExclusao;
    }

    public void setSqlExclusao(String sqlExclusao) {
        this.sqlExclusao = sqlExclusao;
    }

    public String getSqlBuscaChavePrimaria() {
        return sqlBuscaChavePrimaria;
    }

    public void setSqlBuscaChavePrimaria(String sqlBuscaChavePrimaria) {
        this.sqlBuscaChavePrimaria = sqlBuscaChavePrimaria;
    }

    public String getSqlBuscaContida() {
        return sqlBuscaContida;
    }

    public void setSqlBuscaContida(String sqlBuscaContida) {
        this.sqlBuscaContida = sqlBuscaContida;
    }

    public String getSqlBuscaTodos() {
        return sqlBuscaTodos;
    }

    public void setSqlBuscaTodos(String sqlBuscaTodos) {
        this.sqlBuscaTodos = sqlBuscaTodos;
    }

    public Connection abrir() {
        Connection c = null;
        try {
            Class.forName(BD.JBDC_DRIVER);
        } catch (ClassNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "Drive " + BD.JBDC_DRIVER
                    + " não foi encontrado na memória.", "Erro com o banco de dados"
                    , JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
        try {
            c = DriverManager.getConnection(BD.URL_CONEXAO, BD.USUARIO, BD.SENHA);
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Não foi possível estabelecer conexão com o banco.",
                    "Erro com o banco de dados", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
        return c;
    }

    public void inserir(T t) {
        Connection c = abrir();
        try {
            PreparedStatement ps = c.prepareStatement(getSqlInsercao());
            preencherInsercao(ps, t);
            ps.execute();
            ps.close();
            c.close();
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Não foi possível realizar inserção no banco.",
                    "Erro com o banco de dados", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
    }

    public void alterar(T t) {
        Connection c = abrir();
        try {
            PreparedStatement ps = c.prepareStatement(getSqlAlteracao());
            preencherAlteracao(ps, t);
            ps.execute();
            ps.close();
            c.close();
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Não foi possível realizar alteração no banco.",
                    "Erro com o banco de dados", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
    }

    public void excluir(T t) {
        Connection c = abrir();
        try {
            PreparedStatement ps = c.prepareStatement(getSqlExclusao());
            preencherExclusao(ps, t);
            ps.execute();
            ps.close();
            c.close();
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Não foi possível realizar exclusão no banco.",
                    "Erro com o banco de dados", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
    }

    public List<T> buscar(T t) {
        Connection conexao = abrir();
        Collection<T> registros = null;
        try {
            PreparedStatement ps = conexao.prepareStatement(getSqlBuscaContida());
            preencherBusca(ps, t);
            ResultSet rs = ps.executeQuery();
            registros = preencherColecao(rs);
            rs.close();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Não foi possível realizar busca no banco.",
                    "Erro com o banco de dados", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
        return (List<T>) registros;
    }

    public T buscar(Integer id) {
        Connection c = abrir();
        T temp = null;
        try {
            PreparedStatement ps = c.prepareStatement(getSqlBuscaChavePrimaria());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                temp = preencher(rs);
            }
            rs.close();
            ps.close();
            c.close();
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Não foi possível realizar busca no banco.",
                    "Erro com o banco de dados", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
        return temp;
    }

    public T buscar(String buscaSql, Integer id) {
        Connection c = abrir();
        T temp = null;
        try {
            PreparedStatement ps = c.prepareStatement(buscaSql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                temp = preencher(rs);
            }
            rs.close();
            ps.close();
            c.close();
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Não foi possível realizar busca no banco.",
                    "Erro com o banco de dados", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
        return temp;
    }

    public List<T> buscarQualquer(String buscaSql, String busca) {
        Connection conexao = abrir();
        Collection<T> registros = null;
        try {
            PreparedStatement ps = conexao.prepareStatement(buscaSql);
            ps.setString(1, busca);
            ResultSet rs = ps.executeQuery();
            registros = preencherColecao(rs);
            rs.close();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Não foi possível realizar busca no banco.",
                    "Erro com o banco de dados", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
        return (List<T>) registros;
    }

    public List<T> buscarQualquer(String buscaSql, Integer busca) {
        Connection conexao = abrir();
        Collection<T> registros = null;
        try {
            PreparedStatement ps = conexao.prepareStatement(buscaSql);
            ps.setInt(1, busca);
            ResultSet rs = ps.executeQuery();
            registros = preencherColecao(rs);
            rs.close();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Não foi possível realizar busca no banco.",
                    "Erro com o banco de dados", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
        return (List<T>) registros;
    }

    public List<T> buscarTodos() throws SQLException {
        Connection c = abrir();
        Collection<T> registros = null;
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(getSqlBuscaTodos());
            registros = preencherColecao(rs);
            rs.close();
            c.close();
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Não foi possível realizar busca no banco.",
                    "Erro com o banco de dados", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
        return (List<T>) registros;
    }

    protected abstract void preencherInsercao(PreparedStatement ps, T t) throws SQLException;

    protected abstract void preencherAlteracao(PreparedStatement ps, T t) throws SQLException;

    protected abstract void preencherExclusao(PreparedStatement ps, T t) throws SQLException;

    protected abstract void preencherBusca(PreparedStatement ps, T t) throws SQLException;

    protected abstract T preencher(ResultSet rs) throws SQLException;

    protected abstract Collection<T> preencherColecao(ResultSet rs) throws SQLException;

}
