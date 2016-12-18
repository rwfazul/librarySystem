package views.professor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import model.Emprestimo;
import model.Livro;
import model.Professor;
import model.Reserva;
import model.Situacao;
import model.dao.EmprestimoProfessorDAO;
import model.dao.LivroDAO;
import model.dao.ProfessorDAO;
import model.dao.ReservaAlunoDAO;
import model.dao.ReservaProfessorDAO;
import util.ConverterData;
import util.VerificaString;
import static util.OperacoesData.dataSistema;
import views.aluno.EmprestimoAlunoView;
import static util.OperacoesData.dataMaisDias;

/**
 *
 * @author rhau
 */
public class EmprestimoProfessorView extends javax.swing.JDialog {

    EmprestimoProfessorDAO edao = new EmprestimoProfessorDAO();
    LivroDAO ldao = new LivroDAO();
    ProfessorDAO pdao = new ProfessorDAO();

    public void atualizaTabela() throws SQLException {
        listEmprestimos.clear();
        listEmprestimos.addAll(edao.buscarTodos());
        int linha = listEmprestimos.size() - 1;
        if (linha >= 0) {
            tblEmprestimos.setRowSelectionInterval(linha, linha);
            tblEmprestimos.scrollRectToVisible(
                    tblEmprestimos.getCellRect(linha, linha, true));
            barra_navegacao(true);
        } else {
            limpa_campos();
            limpa_informacoes();
            trataEdicao(false);
            barra_navegacao(false);
        }
    }
        
    public void atualizaLivros() throws SQLException {
        listLivros.clear();
        List<Livro> filtro = ldao.buscarTodos();
        for (Livro l : filtro) {
            if (l.getSituacao() == Situacao.NORMAL) {
                listLivros.add(l);
            }
        }
    }

    public void atualizaProfessores() throws SQLException {
        listProfessores.clear();
        listProfessores.addAll(pdao.buscarTodos());
    }

    public void trataEdicao(boolean editando) {
        // combo box
        cbxLivro.setEnabled(editando);
        cbxUser.setEnabled(editando);
        // botoes ativados se estivar editando 
        btnCancelar.setEnabled(editando);
        btnSalvar.setEnabled(editando);
        // botoes desativados se estivar editando 
        btnRenovar.setEnabled(!editando);
        btnDevolver.setEnabled(!editando);
        btnEfetuar.setEnabled(!editando);
        btnFechar.setEnabled(!editando);
        btnPrimeiro.setEnabled(!editando);
        btnProximo.setEnabled(!editando);
        btnAnterior.setEnabled(!editando);
        btnUltimo.setEnabled(!editando);
        btnMostraTodos.setEnabled(!editando);
        btIsbn.setEnabled(!editando);
        btMatricula.setEnabled(!editando);
        btNome.setEnabled(!editando);
        btTitulo.setEnabled(!editando);
        // ativiado
        txtRetirada.setEnabled(editando);
        txtEntrega.setEnabled(editando);
        // desativado
        txtBuscaIsbn.setEditable(!editando);
        txtBuscaMatricula.setEditable(!editando);
        txtBuscaNome.setEditable(!editando);
        txtBuscaTitulo.setEditable(!editando);
        txtBuscaIsbn.setEnabled(!editando);
        txtBuscaMatricula.setEnabled(!editando);
        txtBuscaNome.setEnabled(!editando);
        txtBuscaTitulo.setEnabled(!editando);
        txtInfoEntrega.setEnabled(!editando);
        txtInfoRetirada.setEnabled(!editando);
        txtInfoLivro.setEnabled(!editando);
        txtInfoProfessor.setEnabled(!editando);
        // tabela
        tblEmprestimos.setEnabled(!editando);
    }

    public boolean validaCampos() {
        if (!(cbxLivro.getSelectedIndex() >= 0)) {
            JOptionPane.showMessageDialog(null, "Informe o livro");
            cbxLivro.requestFocus();
            return false;
        }

        if (!(cbxUser.getSelectedIndex() >= 0)) {
            JOptionPane.showMessageDialog(null, "Informe o professor");
            cbxUser.requestFocus();
            return false;
        }

        return true;
    }

    public boolean txtVazio(JTextField txt, String msg) {
        if (txt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe " + msg + " do livro");
            txt.requestFocus();
            return true;
        }

        return false;
    }

    public void limpa_campos() {
        txtRetirada.setText(dataSistema());
        txtEntrega.setText(dataMaisDias(new Professor().getMaxDiasPorLivro()));
    }

    public void limpa_informacoes() {
        txtInfoIsbn.setText("");
        txtInfoLivro.setText("");
        txtInfoMatricula.setText("");
        txtInfoProfessor.setText("");
        txtInfoRetirada.setText("");
        txtInfoEntrega.setText("");
    }

    public void limpa_buscas() {
        txtBuscaIsbn.setText("");
        txtBuscaMatricula.setText("");
        txtBuscaNome.setText("");
        txtBuscaTitulo.setText("");
    }

    public void barra_navegacao(Boolean b) {
        btnPrimeiro.setEnabled(b);
        btnAnterior.setEnabled(b);
        btnProximo.setEnabled(b);
        btnUltimo.setEnabled(b);

    }
    
    public Emprestimo corrigeDatas(Emprestimo e) {
        ConverterData conversor = new ConverterData();
        e.setRetirada((Calendar) conversor.convertReverse(txtRetirada.getText()));
        e.setEntrega((Calendar) conversor.convertReverse(txtEntrega.getText()));
        return e;
    }
    
    public void mostraTodos() {
        try {
            atualizaTabela();
            if (listEmprestimos.size() - 1 >= 0) {
                tblEmprestimos.setRowSelectionInterval(0, 0);
                tblEmprestimos.scrollRectToVisible(tblEmprestimos.getCellRect(0, 0, true));
                barra_navegacao(true);
            } else {
                JOptionPane.showConfirmDialog(null, "Tabela de empréstimos vazia.",
                "Nenhum empréstimo", JOptionPane.DEFAULT_OPTION);
                limpa_informacoes();
                barra_navegacao(false);
            }
            atualizaLivros();
            limpa_buscas();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoProfessorView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void liberaLivroReserva(Livro l) {
        ReservaAlunoDAO rAdao = new ReservaAlunoDAO();
        ReservaProfessorDAO rPdao = new ReservaProfessorDAO();
        List<Reserva> reservaAluno =
                rAdao.buscarQualquer(rAdao.getSqlBuscaLivro(), l.getCodigo());
        List<Reserva> reservaProfessor = 
                rPdao.buscarQualquer(rPdao.getSqlBuscaLivro(), l.getCodigo());

        ConverterData conversor = new ConverterData();
        boolean flag = false;
        
        for (Reserva r : reservaProfessor) {
            r.setRetirada((Calendar) conversor.convertReverse(dataSistema()));
            rPdao.alterar(r);
            flag = true;
        }
        
        if (!flag) {
            for (Reserva r : reservaAluno) {
                if (r.getExemplar().getNome().equals(l.getNome())) {
                    r.setRetirada((Calendar) conversor.convertReverse(dataSistema()));
                    rAdao.alterar(r);
                }
            }
        }
    }
        
    //Construtor
    public EmprestimoProfessorView(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        trataEdicao(false);
        atualizaTabela();
        if (listEmprestimos.size() - 1 >= 0) {
            tblEmprestimos.setRowSelectionInterval(0, 0);
            tblEmprestimos.scrollRectToVisible(tblEmprestimos.getCellRect(0, 0, true));
        }
        atualizaLivros();
        atualizaProfessores();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        listLivros = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Livro>())
        ;
        converterData = new util.ConverterData();
        listEmprestimos = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Emprestimo>())
        ;
        listProfessores = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Professor>())
        ;
        painelAuxiliar = new javax.swing.JPanel();
        btnPrimeiro = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnProximo = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        lblEmprestimosTitulo = new javax.swing.JLabel();
        abas = new javax.swing.JTabbedPane();
        abaEmprestimos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmprestimos = new javax.swing.JTable();
        abaNovo = new javax.swing.JPanel();
        painelNovo = new javax.swing.JPanel();
        btnEfetuar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        lblRetirada = new javax.swing.JLabel();
        lblEntrega = new javax.swing.JLabel();
        lblProfessor = new javax.swing.JLabel();
        lblLivro = new javax.swing.JLabel();
        cbxLivro = new javax.swing.JComboBox<>();
        javax.swing.text.MaskFormatter maskData2 = null;
        try {
            maskData2 = new MaskFormatter("##/##/####");
            maskData2.setPlaceholder("_");
        } catch (Exception e) {
        }

        txtEntrega = new javax.swing.JFormattedTextField(maskData2);
        javax.swing.text.MaskFormatter maskData = null;
        try {
            maskData = new MaskFormatter("##/##/####");
            maskData.setPlaceholder("_");
        } catch (Exception e) {
        }

        txtRetirada = new javax.swing.JFormattedTextField(maskData);
        lblDisponivel = new javax.swing.JLabel();
        lblDias = new javax.swing.JLabel();
        cbxUser = new javax.swing.JComboBox<>();
        abaManutencao = new javax.swing.JPanel();
        pnlAcoes = new javax.swing.JPanel();
        btnRenovar = new javax.swing.JButton();
        btnDevolver = new javax.swing.JButton();
        lbRetirada = new javax.swing.JLabel();
        lbEntrega = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskDataA = null;
        try {
            maskDataA = new MaskFormatter("##/##/####");
            maskDataA.setPlaceholder("_");
        } catch (Exception e) {
        }

        txtInfoRetirada = new javax.swing.JFormattedTextField(maskDataA);
        javax.swing.text.MaskFormatter maskDataB = null;
        try {
            maskDataB = new MaskFormatter("##/##/####");
            maskDataB.setPlaceholder("_");
        } catch (Exception e) {
        }

        txtInfoEntrega = new javax.swing.JFormattedTextField(maskDataB);
        pbInfoLivro = new javax.swing.JPanel();
        txtInfoIsbn = new javax.swing.JTextField();
        lblIsbn = new javax.swing.JLabel();
        txtInfoLivro = new javax.swing.JTextField();
        lbLivro = new javax.swing.JLabel();
        pblInfoProfessor = new javax.swing.JPanel();
        txtInfoProfessor = new javax.swing.JTextField();
        txtInfoMatricula = new javax.swing.JTextField();
        lblMatricula = new javax.swing.JLabel();
        lbProfessor = new javax.swing.JLabel();
        abaBuscas = new javax.swing.JPanel();
        pnlBuscaLivro = new javax.swing.JPanel();
        lbIsbn = new javax.swing.JLabel();
        lbTitulo = new javax.swing.JLabel();
        btIsbn = new javax.swing.JButton();
        btTitulo = new javax.swing.JButton();
        txtBuscaIsbn = new javax.swing.JTextField();
        txtBuscaTitulo = new javax.swing.JTextField();
        pnlBuscaProfessor = new javax.swing.JPanel();
        lbNome = new javax.swing.JLabel();
        lbMatricula = new javax.swing.JLabel();
        btNome = new javax.swing.JButton();
        btMatricula = new javax.swing.JButton();
        txtBuscaMatricula = new javax.swing.JTextField();
        txtBuscaNome = new javax.swing.JTextField();
        lblInfo = new javax.swing.JLabel();
        btnMostraTodos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento de empréstimos");
        setResizable(false);

        painelAuxiliar.setBorder(javax.swing.BorderFactory.createTitledBorder("Ferramentas auxiliares"));
        painelAuxiliar.setLayout(new java.awt.GridLayout(1, 0));

        btnPrimeiro.setText("Primeiro");
        btnPrimeiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeiroActionPerformed(evt);
            }
        });
        painelAuxiliar.add(btnPrimeiro);

        btnAnterior.setText("Anterior");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        painelAuxiliar.add(btnAnterior);

        btnProximo.setText("Próximo");
        btnProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProximoActionPerformed(evt);
            }
        });
        painelAuxiliar.add(btnProximo);

        btnUltimo.setText("Último");
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });
        painelAuxiliar.add(btnUltimo);

        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });
        painelAuxiliar.add(btnFechar);

        lblEmprestimosTitulo.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblEmprestimosTitulo.setForeground(new java.awt.Color(75, 192, 183));
        lblEmprestimosTitulo.setText("Gerenciamento de Empréstimos - Professores");

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listEmprestimos, tblEmprestimos);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${exemplar}"));
        columnBinding.setColumnName("Livro");
        columnBinding.setColumnClass(model.Livro.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${user}"));
        columnBinding.setColumnName("Professor");
        columnBinding.setColumnClass(model.Usuario.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${retiradaFormatado}"));
        columnBinding.setColumnName("Retirada");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${entregaFormatado}"));
        columnBinding.setColumnName("Entrega");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(tblEmprestimos);
        if (tblEmprestimos.getColumnModel().getColumnCount() > 0) {
            tblEmprestimos.getColumnModel().getColumn(2).setMinWidth(90);
            tblEmprestimos.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblEmprestimos.getColumnModel().getColumn(2).setMaxWidth(90);
            tblEmprestimos.getColumnModel().getColumn(3).setMinWidth(90);
            tblEmprestimos.getColumnModel().getColumn(3).setPreferredWidth(90);
            tblEmprestimos.getColumnModel().getColumn(3).setMaxWidth(90);
        }

        javax.swing.GroupLayout abaEmprestimosLayout = new javax.swing.GroupLayout(abaEmprestimos);
        abaEmprestimos.setLayout(abaEmprestimosLayout);
        abaEmprestimosLayout.setHorizontalGroup(
            abaEmprestimosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
        );
        abaEmprestimosLayout.setVerticalGroup(
            abaEmprestimosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
        );

        abas.addTab("Listagem dos empréstimos", abaEmprestimos);

        painelNovo.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        painelNovo.setLayout(new java.awt.GridLayout(1, 0));

        btnEfetuar.setText("Efetuar Empréstimo");
        btnEfetuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEfetuarActionPerformed(evt);
            }
        });
        painelNovo.add(btnEfetuar);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        painelNovo.add(btnCancelar);

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        painelNovo.add(btnSalvar);

        lblRetirada.setText("Retirada");

        lblEntrega.setText("Entrega");

        lblProfessor.setText("Professor");

        lblLivro.setText("Livro");

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listLivros, cbxLivro);
        bindingGroup.addBinding(jComboBoxBinding);

        txtEntrega.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblEmprestimos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.entrega}"), txtEntrega, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(converterData);
        bindingGroup.addBinding(binding);

        txtRetirada.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblEmprestimos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.retirada}"), txtRetirada, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(converterData);
        bindingGroup.addBinding(binding);

        lblDisponivel.setText("(Apenas disponíveis)");

        lblDias.setText("(15 dias)");

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listProfessores, cbxUser);
        bindingGroup.addBinding(jComboBoxBinding);

        javax.swing.GroupLayout abaNovoLayout = new javax.swing.GroupLayout(abaNovo);
        abaNovo.setLayout(abaNovoLayout);
        abaNovoLayout.setHorizontalGroup(
            abaNovoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaNovoLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(painelNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(abaNovoLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(abaNovoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLivro)
                    .addComponent(lblProfessor)
                    .addComponent(lblRetirada))
                .addGap(18, 18, 18)
                .addGroup(abaNovoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaNovoLayout.createSequentialGroup()
                        .addComponent(txtRetirada, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(lblEntrega)
                        .addGap(18, 18, 18)
                        .addComponent(txtEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDias))
                    .addGroup(abaNovoLayout.createSequentialGroup()
                        .addGroup(abaNovoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbxLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxUser, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDisponivel)))
                .addGap(0, 29, Short.MAX_VALUE))
        );
        abaNovoLayout.setVerticalGroup(
            abaNovoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaNovoLayout.createSequentialGroup()
                .addGroup(abaNovoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abaNovoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(abaNovoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblDisponivel)
                            .addComponent(cbxLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLivro)))
                    .addGroup(abaNovoLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(painelNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)))
                .addGap(18, 18, 18)
                .addGroup(abaNovoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProfessor))
                .addGap(74, 74, 74)
                .addGroup(abaNovoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRetirada)
                    .addComponent(txtRetirada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEntrega)
                    .addComponent(lblDias))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        abas.addTab("Novo", abaNovo);

        pnlAcoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        pnlAcoes.setLayout(new java.awt.GridLayout(1, 0));

        btnRenovar.setText("Renovar Empréstimo");
        btnRenovar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRenovarActionPerformed(evt);
            }
        });
        pnlAcoes.add(btnRenovar);

        btnDevolver.setText("Devolver Livro");
        btnDevolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDevolverActionPerformed(evt);
            }
        });
        pnlAcoes.add(btnDevolver);

        lbRetirada.setText("Data Retirada");

        lbEntrega.setText("Data Entrega");

        txtInfoRetirada.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblEmprestimos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.retirada}"), txtInfoRetirada, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(converterData);
        bindingGroup.addBinding(binding);

        txtInfoEntrega.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblEmprestimos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.entrega}"), txtInfoEntrega, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(converterData);
        bindingGroup.addBinding(binding);

        pbInfoLivro.setBorder(javax.swing.BorderFactory.createTitledBorder("Livro"));
        pbInfoLivro.setToolTipText("");

        txtInfoIsbn.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblEmprestimos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.exemplar.isbn}"), txtInfoIsbn, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblIsbn.setText("ISBN");

        txtInfoLivro.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblEmprestimos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.user.nome}"), txtInfoLivro, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lbLivro.setText("Título");

        javax.swing.GroupLayout pbInfoLivroLayout = new javax.swing.GroupLayout(pbInfoLivro);
        pbInfoLivro.setLayout(pbInfoLivroLayout);
        pbInfoLivroLayout.setHorizontalGroup(
            pbInfoLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pbInfoLivroLayout.createSequentialGroup()
                .addGroup(pbInfoLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pbInfoLivroLayout.createSequentialGroup()
                        .addComponent(lblIsbn)
                        .addGap(8, 8, 8))
                    .addComponent(lbLivro, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(47, 47, 47)
                .addGroup(pbInfoLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pbInfoLivroLayout.createSequentialGroup()
                        .addComponent(txtInfoIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtInfoLivro))
                .addContainerGap())
        );
        pbInfoLivroLayout.setVerticalGroup(
            pbInfoLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pbInfoLivroLayout.createSequentialGroup()
                .addGroup(pbInfoLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtInfoIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIsbn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pbInfoLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtInfoLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbLivro))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pblInfoProfessor.setBorder(javax.swing.BorderFactory.createTitledBorder("Professor"));
        pblInfoProfessor.setToolTipText("");

        txtInfoProfessor.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblEmprestimos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.exemplar.nome}"), txtInfoProfessor, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtInfoMatricula.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblEmprestimos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.user.matricula}"), txtInfoMatricula, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblMatricula.setText("Matícula");

        lbProfessor.setText("Professor");

        javax.swing.GroupLayout pblInfoProfessorLayout = new javax.swing.GroupLayout(pblInfoProfessor);
        pblInfoProfessor.setLayout(pblInfoProfessorLayout);
        pblInfoProfessorLayout.setHorizontalGroup(
            pblInfoProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblInfoProfessorLayout.createSequentialGroup()
                .addGroup(pblInfoProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMatricula)
                    .addComponent(lbProfessor))
                .addGap(18, 18, 18)
                .addGroup(pblInfoProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtInfoProfessor)
                    .addGroup(pblInfoProfessorLayout.createSequentialGroup()
                        .addComponent(txtInfoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pblInfoProfessorLayout.setVerticalGroup(
            pblInfoProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pblInfoProfessorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pblInfoProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMatricula)
                    .addComponent(txtInfoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pblInfoProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProfessor)
                    .addComponent(txtInfoProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout abaManutencaoLayout = new javax.swing.GroupLayout(abaManutencao);
        abaManutencao.setLayout(abaManutencaoLayout);
        abaManutencaoLayout.setHorizontalGroup(
            abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaManutencaoLayout.createSequentialGroup()
                .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaManutencaoLayout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(pnlAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(abaManutencaoLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(abaManutencaoLayout.createSequentialGroup()
                                .addComponent(lbRetirada)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtInfoRetirada, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(lbEntrega)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtInfoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pbInfoLivro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pblInfoProfessor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        abaManutencaoLayout.setVerticalGroup(
            abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abaManutencaoLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(pbInfoLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pblInfoProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbEntrega)
                    .addComponent(txtInfoRetirada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbRetirada)
                    .addComponent(txtInfoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pnlAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        abas.addTab("Manutenção", abaManutencao);

        pnlBuscaLivro.setBorder(javax.swing.BorderFactory.createTitledBorder("Busca por livro"));

        lbIsbn.setText("ISBN");

        lbTitulo.setText("Título");

        btIsbn.setText("Buscar IBSN");
        btIsbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIsbnActionPerformed(evt);
            }
        });

        btTitulo.setText("Buscar Título");
        btTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTituloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBuscaLivroLayout = new javax.swing.GroupLayout(pnlBuscaLivro);
        pnlBuscaLivro.setLayout(pnlBuscaLivroLayout);
        pnlBuscaLivroLayout.setHorizontalGroup(
            pnlBuscaLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscaLivroLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlBuscaLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTitulo)
                    .addComponent(lbIsbn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(pnlBuscaLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscaTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscaIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlBuscaLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(btIsbn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlBuscaLivroLayout.setVerticalGroup(
            pnlBuscaLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscaLivroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscaLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbIsbn)
                    .addComponent(btIsbn)
                    .addComponent(txtBuscaIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlBuscaLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTitulo)
                    .addComponent(btTitulo)
                    .addComponent(txtBuscaTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        pnlBuscaProfessor.setBorder(javax.swing.BorderFactory.createTitledBorder("Busca por professor"));

        lbNome.setText("Nome");

        lbMatricula.setText("Matrícula");

        btNome.setText("Buscar Nome");
        btNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNomeActionPerformed(evt);
            }
        });

        btMatricula.setText("Buscar Matrícula");
        btMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMatriculaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBuscaProfessorLayout = new javax.swing.GroupLayout(pnlBuscaProfessor);
        pnlBuscaProfessor.setLayout(pnlBuscaProfessorLayout);
        pnlBuscaProfessorLayout.setHorizontalGroup(
            pnlBuscaProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBuscaProfessorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscaProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMatricula)
                    .addComponent(lbNome))
                .addGap(18, 18, 18)
                .addGroup(pnlBuscaProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscaNome)
                    .addGroup(pnlBuscaProfessorLayout.createSequentialGroup()
                        .addComponent(txtBuscaMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 181, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(pnlBuscaProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btMatricula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlBuscaProfessorLayout.setVerticalGroup(
            pnlBuscaProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscaProfessorLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnlBuscaProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMatricula)
                    .addComponent(btMatricula)
                    .addComponent(txtBuscaMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlBuscaProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNome)
                    .addComponent(btNome)
                    .addComponent(txtBuscaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblInfo.setText("* Os resultados das buscas são exibidos na tabela principal (listagem dos empréstimos)");

        javax.swing.GroupLayout abaBuscasLayout = new javax.swing.GroupLayout(abaBuscas);
        abaBuscas.setLayout(abaBuscasLayout);
        abaBuscasLayout.setHorizontalGroup(
            abaBuscasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaBuscasLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(abaBuscasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlBuscaLivro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBuscaProfessor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        abaBuscasLayout.setVerticalGroup(
            abaBuscasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abaBuscasLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(pnlBuscaLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(pnlBuscaProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblInfo)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        abas.addTab("Buscas", abaBuscas);

        btnMostraTodos.setText("Mostrar todos empréstimos");
        btnMostraTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostraTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(abas)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(painelAuxiliar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblEmprestimosTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(111, 111, 111))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMostraTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEmprestimosTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMostraTodos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelAuxiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bindingGroup.bind();

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeiroActionPerformed
        tblEmprestimos.setRowSelectionInterval(0, 0);
        tblEmprestimos.scrollRectToVisible(tblEmprestimos.getCellRect(0, 0, true));
    }//GEN-LAST:event_btnPrimeiroActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        int linha = tblEmprestimos.getSelectedRow();
        if ((linha + 1) < tblEmprestimos.getRowCount()) {
            linha++;
        }
        tblEmprestimos.setRowSelectionInterval(linha, linha);
        tblEmprestimos.scrollRectToVisible(tblEmprestimos.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        int linha = tblEmprestimos.getSelectedRow();
        if ((linha - 1) >= 0) {
            linha--;
        }
        tblEmprestimos.setRowSelectionInterval(linha, linha);
        tblEmprestimos.scrollRectToVisible(tblEmprestimos.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        int linha = tblEmprestimos.getRowCount() - 1;
        tblEmprestimos.setRowSelectionInterval(linha, linha);
        tblEmprestimos.scrollRectToVisible(tblEmprestimos.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnMostraTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostraTodosActionPerformed
        mostraTodos();
    }//GEN-LAST:event_btnMostraTodosActionPerformed

    private void btnDevolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDevolverActionPerformed
        int linhaSelecionada = tblEmprestimos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            if (JOptionPane.showOptionDialog(null, "Você confirma a devolução do livro?",
                    "Confirmar devolução", JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"Sim", "Não"}, "Sim") == 0) {
                Emprestimo e = listEmprestimos.get(linhaSelecionada);
                Livro l = e.getExemplar();
                if (l.getSituacao().equals(Situacao.RESERVADOEMPRESTADO)) {
                    l.setSituacao(Situacao.RESERVADO);
                } else {
                    l.setSituacao(Situacao.NORMAL);
                }
                
                liberaLivroReserva(l);
                ldao.alterar(l);
                e.getUser().setNrLivros(e.getUser().getNrLivros() - 1);
                pdao.alterar((Professor) e.getUser());
                edao.excluir(e);

                try {
                    atualizaTabela();
                    if (listEmprestimos.size() - 1 >= 0) {
                        tblEmprestimos.setRowSelectionInterval(0, 0);
                        tblEmprestimos.scrollRectToVisible(tblEmprestimos.getCellRect(0, 0, true));
                    } else {
                        limpa_informacoes();
                    }
                    atualizaLivros();
                } catch (SQLException ex) {
                    Logger.getLogger(EmprestimoProfessorView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showConfirmDialog(null, "Nenhum empréstimo selecionado.",
                    "Lista de empréstimos vazia", JOptionPane.DEFAULT_OPTION);
        }
    }//GEN-LAST:event_btnDevolverActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (validaCampos()) {
            int linhaSelecionada = tblEmprestimos.getSelectedRow();
            Emprestimo e = listEmprestimos.get(linhaSelecionada);
            e.setUser((Professor) cbxUser.getSelectedItem());
            if (e.getUser().getValorMulta() > 0) {
                JOptionPane.showConfirmDialog(null, "O professor selecionado tem multa de R$"
                        + e.getUser().getValorMulta() + ".\nAção necessária: "
                        + "Quitar a multa.",
                        "Empréstimo recusado", JOptionPane.DEFAULT_OPTION);
                trataEdicao(false);
                try {
                    atualizaTabela();
                } catch (SQLException ex) {
                    Logger.getLogger(EmprestimoProfessorView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
            if (e.getUser().getNrLivros() >= e.getUser().getMaxLivrosRetirados()) {
                JOptionPane.showConfirmDialog(null, "O professor selecionado já possui "
                        + e.getUser().getNrLivros() + " livros emprestados.\n"
                        + "Ação necessária: Devolver algum livro.",
                        "Empréstimo recusado", JOptionPane.DEFAULT_OPTION);
                trataEdicao(false);
                try {
                    atualizaTabela();
                } catch (SQLException ex) {
                    Logger.getLogger(EmprestimoProfessorView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }

            if (e.getCodigo() == null) {
                e.getUser().setNrLivros(e.getUser().getNrLivros() + 1);
                pdao.alterar((Professor) e.getUser());
                e.setExemplar((Livro) cbxLivro.getSelectedItem());
                e.getExemplar().setSituacao(Situacao.EMPRESTADO);
                ldao.alterar(e.getExemplar());
                edao.inserir(e);
            }
            trataEdicao(false);
            try {
                atualizaTabela();
                atualizaLivros();
            } catch (SQLException ex) {
                Logger.getLogger(EmprestimoProfessorView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        trataEdicao(false);
        try {
            atualizaTabela();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoProfessorView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRenovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenovarActionPerformed
        if (tblEmprestimos.getSelectedRow() >= 0) {
            if (JOptionPane.showOptionDialog(null, "Você confirma a renovação do livro?",
                    "Confirmar renovação", JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"Sim", "Não"}, "Sim") == 0) {
                int linhaSelecionada = tblEmprestimos.getSelectedRow();
                Emprestimo e = listEmprestimos.get(linhaSelecionada);
                if (e.getExemplar().getSituacao().equals(Situacao.RESERVADOEMPRESTADO)) {
                    JOptionPane.showConfirmDialog(null, "O livro selecionado já está reservado!",
                            "Livro não disponível", JOptionPane.DEFAULT_OPTION);
                    trataEdicao(false);
                } else if (e.getUser().getValorMulta() > 0) {
                    JOptionPane.showConfirmDialog(null, 
                            "O professor selecionado tem multa de R$"
                            + e.getUser().getValorMulta() 
                            + ".\nAção necessária: Quitar a multa.",
                            "Empréstimo recusado", JOptionPane.DEFAULT_OPTION);
                    trataEdicao(false);
                } else {
                    ConverterData conversor = new ConverterData();
                    // renova
                    e.setEntrega((Calendar) conversor.convertReverse(
                            dataMaisDias((Calendar) conversor.convertReverse(txtEntrega.getText()), new Professor().getMaxDiasPorLivro())));
                    edao.alterar(e);
                    try {
                        atualizaTabela();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmprestimoProfessorView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            JOptionPane.showConfirmDialog(null, "Algum empréstimo deve ser selecionado ser renovado.",
                    "Lista de empréstimos vazia", JOptionPane.DEFAULT_OPTION);
        }
    }//GEN-LAST:event_btnRenovarActionPerformed

    private void btnEfetuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEfetuarActionPerformed
        if (cbxLivro.getItemCount() == 0) {
            JOptionPane.showConfirmDialog(null, "Não há livros disponíveis para o empréstimo.",
                    "Lista de livros vazia", JOptionPane.DEFAULT_OPTION);
            trataEdicao(false);
            return;
        }

        if (cbxUser.getItemCount() == 0) {
            JOptionPane.showConfirmDialog(null, "Não professores.",
                    "Lista de professores vazia", JOptionPane.DEFAULT_OPTION);
            trataEdicao(false);
            return;
        }

        txtRetirada.setText(dataSistema());
        txtEntrega.setText(dataMaisDias(new Professor().getMaxDiasPorLivro()));
        listEmprestimos.add(corrigeDatas(new Emprestimo()));
        int linhas = listEmprestimos.size() - 1;
        tblEmprestimos.setRowSelectionInterval(linhas, linhas);
        trataEdicao(true);
        limpa_campos();
        cbxLivro.requestFocus();
    }//GEN-LAST:event_btnEfetuarActionPerformed

    private void btIsbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIsbnActionPerformed
        String busca = txtBuscaIsbn.getText();

        if (busca.length() > 0) {
            boolean ehNumero = VerificaString.ehNumero(busca);

            if (ehNumero) {
                listEmprestimos.clear();
                List<Livro> l2 = ldao.buscarQualquer(
                    ldao.getSqlBuscaIsbn(), Integer.parseInt(busca));  // isbn
                l2.stream().forEach((livros) -> {
                    listEmprestimos.addAll(edao.buscarQualquer(edao.getSqlBuscaLivro(), livros.getCodigo()));
                });
                int linhas = listEmprestimos.size() - 1;
                if (linhas >= 0) {
                    tblEmprestimos.setRowSelectionInterval(0, 0);
                    tblEmprestimos.scrollRectToVisible(tblEmprestimos.getCellRect(0, 0, true));
                    barra_navegacao(true);
                } else {
                    JOptionPane.showConfirmDialog(null, "Nenhuma correspondência encontrada.",
                    "Busca sem resultado", JOptionPane.DEFAULT_OPTION);
                    mostraTodos();
                }
            } else {
                JOptionPane.showConfirmDialog(null, "Apenas números.",
                    "Busca inválida", JOptionPane.DEFAULT_OPTION);
            }
        } else {
            try {
                atualizaTabela();
            } catch (SQLException ex) {
                Logger.getLogger(EmprestimoAlunoView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btIsbnActionPerformed

    private void btTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTituloActionPerformed
        String busca = txtBuscaTitulo.getText();

        if (busca.length() > 0) {
            listEmprestimos.clear();

            Livro l = new Livro();
            l.setNome(busca);
            List<Livro> l1 = ldao.buscar(l); // parte do titulo
            l1.stream().forEach((livros) -> {
                listEmprestimos.addAll(edao.buscarQualquer(edao.getSqlBuscaLivro(), livros.getCodigo()));
            });

            int linhas = listEmprestimos.size() - 1;
            if (linhas >= 0) {
                tblEmprestimos.setRowSelectionInterval(0, 0);
                tblEmprestimos.scrollRectToVisible(tblEmprestimos.getCellRect(0, 0, true));
                barra_navegacao(true);
            } else {
                JOptionPane.showConfirmDialog(null, "Nenhuma correspondência encontrada.",
                "Busca sem resultado", JOptionPane.DEFAULT_OPTION);
                mostraTodos();
            }
        } else {
            try {
                atualizaTabela();
            } catch (SQLException ex) {
                Logger.getLogger(EmprestimoAlunoView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btTituloActionPerformed

    private void btNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNomeActionPerformed
        String busca = txtBuscaNome.getText();

        if (busca.length() > 0) {
            listEmprestimos.clear();

            Professor a = new Professor();
            a.setNome(busca);
            List<Professor> l2 = pdao.buscar(a); // parte do nome
            l2.stream().forEach((professores) -> {
                listEmprestimos.addAll(edao.buscarQualquer(edao.getSqlBuscaProfessor(), professores.getCodigo()));
            });

            int linhas = listEmprestimos.size() - 1;
            if (linhas >= 0) {
                tblEmprestimos.setRowSelectionInterval(0, 0);
                tblEmprestimos.scrollRectToVisible(tblEmprestimos.getCellRect(0, 0, true));
                barra_navegacao(true);
            } else {
                JOptionPane.showConfirmDialog(null, "Nenhuma correspondência encontrada.",
                "Busca sem resultado", JOptionPane.DEFAULT_OPTION);
                mostraTodos();
            }
        } else {
            try {
                atualizaTabela();
            } catch (SQLException ex) {
                Logger.getLogger(EmprestimoAlunoView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btNomeActionPerformed

    private void btMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMatriculaActionPerformed
        String busca = txtBuscaMatricula.getText();

        if (busca.length() > 0) {
            boolean ehNumero = VerificaString.ehNumero(busca);

            if (ehNumero) {
                listEmprestimos.clear();
                List<Professor> l1 = pdao.buscarQualquer(
                    pdao.getSqlBuscaMatricula(), Integer.parseInt(busca)); // matricula
                l1.stream().forEach((professores) -> {
                    listEmprestimos.addAll(edao.buscarQualquer(edao.getSqlBuscaProfessor(), professores.getCodigo()));
                });
                int linhas = listEmprestimos.size() - 1;
                if (linhas >= 0) {
                    tblEmprestimos.setRowSelectionInterval(0, 0);
                    tblEmprestimos.scrollRectToVisible(tblEmprestimos.getCellRect(0, 0, true));
                    barra_navegacao(true);
                } else {
                    JOptionPane.showConfirmDialog(null, "Nenhuma correspondência encontrada.",
                    "Busca sem resultado", JOptionPane.DEFAULT_OPTION);
                    mostraTodos();
                }
            } else {
                JOptionPane.showConfirmDialog(null, "Apenas números.",
                    "Busca inválida", JOptionPane.DEFAULT_OPTION);
            }
        } else {
            try {
                atualizaTabela();
            } catch (SQLException ex) {
                Logger.getLogger(EmprestimoAlunoView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btMatriculaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmprestimoProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmprestimoProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmprestimoProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmprestimoProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EmprestimoProfessorView dialog = null;
                try {
                    dialog = new EmprestimoProfessorView(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    Logger.getLogger(EmprestimoProfessorView.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel abaBuscas;
    private javax.swing.JPanel abaEmprestimos;
    private javax.swing.JPanel abaManutencao;
    private javax.swing.JPanel abaNovo;
    private javax.swing.JTabbedPane abas;
    private javax.swing.JButton btIsbn;
    private javax.swing.JButton btMatricula;
    private javax.swing.JButton btNome;
    private javax.swing.JButton btTitulo;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDevolver;
    private javax.swing.JButton btnEfetuar;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnMostraTodos;
    private javax.swing.JButton btnPrimeiro;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnRenovar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JComboBox<String> cbxLivro;
    private javax.swing.JComboBox<String> cbxUser;
    private util.ConverterData converterData;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbEntrega;
    private javax.swing.JLabel lbIsbn;
    private javax.swing.JLabel lbLivro;
    private javax.swing.JLabel lbMatricula;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbProfessor;
    private javax.swing.JLabel lbRetirada;
    private javax.swing.JLabel lbTitulo;
    private javax.swing.JLabel lblDias;
    private javax.swing.JLabel lblDisponivel;
    private javax.swing.JLabel lblEmprestimosTitulo;
    private javax.swing.JLabel lblEntrega;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblIsbn;
    private javax.swing.JLabel lblLivro;
    private javax.swing.JLabel lblMatricula;
    private javax.swing.JLabel lblProfessor;
    private javax.swing.JLabel lblRetirada;
    private java.util.List<Emprestimo> listEmprestimos;
    private java.util.List<Livro> listLivros;
    private java.util.List<Professor> listProfessores;
    private javax.swing.JPanel painelAuxiliar;
    private javax.swing.JPanel painelNovo;
    private javax.swing.JPanel pbInfoLivro;
    private javax.swing.JPanel pblInfoProfessor;
    private javax.swing.JPanel pnlAcoes;
    private javax.swing.JPanel pnlBuscaLivro;
    private javax.swing.JPanel pnlBuscaProfessor;
    private javax.swing.JTable tblEmprestimos;
    private javax.swing.JTextField txtBuscaIsbn;
    private javax.swing.JTextField txtBuscaMatricula;
    private javax.swing.JTextField txtBuscaNome;
    private javax.swing.JTextField txtBuscaTitulo;
    private javax.swing.JFormattedTextField txtEntrega;
    private javax.swing.JFormattedTextField txtInfoEntrega;
    private javax.swing.JTextField txtInfoIsbn;
    private javax.swing.JTextField txtInfoLivro;
    private javax.swing.JTextField txtInfoMatricula;
    private javax.swing.JTextField txtInfoProfessor;
    private javax.swing.JFormattedTextField txtInfoRetirada;
    private javax.swing.JFormattedTextField txtRetirada;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
