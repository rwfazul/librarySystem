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
import model.dao.EmprestimoAlunoDAO;
import model.dao.EmprestimoProfessorDAO;
import model.dao.LivroDAO;
import model.dao.ProfessorDAO;
import model.dao.ReservaProfessorDAO;
import util.ConverterData;
import static util.OperacoesData.dataSistema;
import util.VerificaString;

/**
 *
 * @author rhau
 */
public class ReservaProfessorView extends javax.swing.JDialog {

    ReservaProfessorDAO rdao = new ReservaProfessorDAO();
    LivroDAO ldao = new LivroDAO();
    ProfessorDAO pdao = new ProfessorDAO();

    public void atualizaTabela() throws SQLException {
        listReservas.clear();
        listReservas.addAll(rdao.buscarTodos());
        int linha = listReservas.size() - 1;
        if (linha >= 0) {
            tblReservas.setRowSelectionInterval(linha, linha);
            tblReservas.scrollRectToVisible(
                    tblReservas.getCellRect(linha, linha, true));
            barra_navegacao(true);
        } else {
            limpa_informacoes();
            trataEdicao(false);
            barra_navegacao(false);
        }
    }

    public void atualizaLivros() throws SQLException {
        listLivros.clear();
        List<Livro> filtro = ldao.buscarTodos();
        for (Livro l : filtro) {
            if (l.getSituacao() == Situacao.NORMAL || l.getSituacao() == Situacao.EMPRESTADO) {
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
        btnCancelarReserva.setEnabled(!editando);
        btnEfetuar.setEnabled(!editando);
        btnFechar.setEnabled(!editando);
        btnPrimeiro.setEnabled(!editando);
        btnProximo.setEnabled(!editando);
        btnAnterior.setEnabled(!editando);
        btnUltimo.setEnabled(!editando);
        btnMostratodos.setEnabled(!editando);
        btIsbn.setEnabled(!editando);
        btMatricula.setEnabled(!editando);
        btNome.setEnabled(!editando);
        btTitulo.setEnabled(!editando);
        // ativida
        txtRetirada.setEnabled(editando);
        // desativado
        txtBuscaIsbn.setEditable(!editando);
        txtBuscaMatricula.setEditable(!editando);
        txtBuscaNome.setEditable(!editando);
        txtBuscaTitulo.setEditable(!editando);
        txtBuscaIsbn.setEnabled(!editando);
        txtBuscaMatricula.setEnabled(!editando);
        txtBuscaNome.setEnabled(!editando);
        txtBuscaTitulo.setEnabled(!editando);
        txtInfoRetirada.setEnabled(!editando);
        txtInfoLivro.setEnabled(!editando);
        txtInfoProfessor.setEnabled(!editando);
        txtInfoMatricula.setEnabled(!editando);
        txtInfoIsbn.setEnabled(!editando);
        // tabela
        tblReservas.setEnabled(!editando);
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

    public void limpa_informacoes() {
        txtInfoIsbn.setText("");
        txtInfoLivro.setText("");
        txtInfoMatricula.setText("");
        txtInfoProfessor.setText("");
        txtInfoRetirada.setText("");
    }

    public void verificaDataEntrega() {
        if (cbxLivro.getSelectedIndex() >= 0) {
            boolean flag = false;
            Livro l = (Livro) cbxLivro.getSelectedItem();
            if (l.getSituacao() != Situacao.NORMAL) {
                EmprestimoProfessorDAO ePdao = new EmprestimoProfessorDAO();
                List<Emprestimo> emprestimosProfessor = ePdao.buscarQualquer(ePdao.getSqlBuscaLivro(), l.getCodigo());
                for (Emprestimo e : emprestimosProfessor) {
                    txtRetirada.setText(e.getEntregaFormatado());
                    break;
                }
                if (!flag) {
                    EmprestimoAlunoDAO eAdao = new EmprestimoAlunoDAO();
                    List<Emprestimo> emprestimosAluno = eAdao.buscarQualquer(eAdao.getSqlBuscaLivro(), l.getCodigo());
                    for (Emprestimo e : emprestimosAluno) {
                        txtRetirada.setText(e.getEntregaFormatado());
                        break;
                    }
                }
            }
            else
                txtRetirada.setText(dataSistema());
        }
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

    public Reserva corrigeDatas(Reserva r) {
        ConverterData conversor = new ConverterData();
        r.setRetirada((Calendar) conversor.convertReverse(txtRetirada.getText()));
        return r;
    }
    
    public void mostraTodos() {
        try {
            atualizaTabela();
            if (listReservas.size() - 1 >= 0) {
                tblReservas.setRowSelectionInterval(0, 0);
                tblReservas.scrollRectToVisible(tblReservas.getCellRect(0, 0, true));
                barra_navegacao(true);
            } else {
                JOptionPane.showConfirmDialog(null, "Tabela de reservas vazia.",
                "Nenhum reserva", JOptionPane.DEFAULT_OPTION);
                limpa_informacoes();
                barra_navegacao(false);
            }
            atualizaLivros();
            limpa_buscas();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoProfessorView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    //Construtor
    public ReservaProfessorView(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        trataEdicao(false);
        atualizaTabela();
        if (listReservas.size() - 1 >= 0) {
            tblReservas.setRowSelectionInterval(0, 0);
            tblReservas.scrollRectToVisible(tblReservas.getCellRect(0, 0, true));
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
        listProfessores = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Professor>())
        ;
        listReservas = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Reserva>())
        ;
        painelAuxiliar = new javax.swing.JPanel();
        btnPrimeiro = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnProximo = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        lblReservasTitulo = new javax.swing.JLabel();
        abas = new javax.swing.JTabbedPane();
        abaReservas = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();
        abaDados = new javax.swing.JPanel();
        painelNovo = new javax.swing.JPanel();
        btnEfetuar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        lblRetirada = new javax.swing.JLabel();
        lblProfessor = new javax.swing.JLabel();
        lblLivro = new javax.swing.JLabel();
        cbxLivro = new javax.swing.JComboBox<>();
        javax.swing.text.MaskFormatter maskData = null;
        try {
            maskData = new MaskFormatter("##/##/####");
            maskData.setPlaceholder("_");
        } catch (Exception e) {
        }

        txtRetirada = new javax.swing.JFormattedTextField(maskData);
        lblDisponivel = new javax.swing.JLabel();
        cbxUser = new javax.swing.JComboBox<>();
        abaManutencao = new javax.swing.JPanel();
        pnlAcoes = new javax.swing.JPanel();
        btnCancelarReserva = new javax.swing.JButton();
        lbLivro = new javax.swing.JLabel();
        lbProfessor = new javax.swing.JLabel();
        lbRetirada = new javax.swing.JLabel();
        txtInfoLivro = new javax.swing.JTextField();
        txtInfoProfessor = new javax.swing.JTextField();
        javax.swing.text.MaskFormatter maskDataA = null;
        try {
            maskDataA = new MaskFormatter("##/##/####");
            maskDataA.setPlaceholder("_");
        } catch (Exception e) {
        }

        txtInfoRetirada = new javax.swing.JFormattedTextField(maskDataA);
        txtInfoIsbn = new javax.swing.JTextField();
        lblIsbn = new javax.swing.JLabel();
        txtInfoMatricula = new javax.swing.JTextField();
        lblMatricula = new javax.swing.JLabel();
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
        btnMostratodos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento de reservas");
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

        lblReservasTitulo.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblReservasTitulo.setForeground(new java.awt.Color(75, 192, 183));
        lblReservasTitulo.setText("Gerenciamento de Reservas - Professores");

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listReservas, tblReservas);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${exemplar}"));
        columnBinding.setColumnName("Livro");
        columnBinding.setColumnClass(model.Livro.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${user}"));
        columnBinding.setColumnName("Professor");
        columnBinding.setColumnClass(model.Usuario.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${retiradaFormatado}"));
        columnBinding.setColumnName("Data para retirada");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(tblReservas);
        if (tblReservas.getColumnModel().getColumnCount() > 0) {
            tblReservas.getColumnModel().getColumn(2).setMinWidth(140);
            tblReservas.getColumnModel().getColumn(2).setPreferredWidth(140);
            tblReservas.getColumnModel().getColumn(2).setMaxWidth(140);
        }

        javax.swing.GroupLayout abaReservasLayout = new javax.swing.GroupLayout(abaReservas);
        abaReservas.setLayout(abaReservasLayout);
        abaReservasLayout.setHorizontalGroup(
            abaReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
        );
        abaReservasLayout.setVerticalGroup(
            abaReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaReservasLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        abas.addTab("Listagem das reservas", abaReservas);

        painelNovo.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        painelNovo.setLayout(new java.awt.GridLayout(1, 0));

        btnEfetuar.setText("Efetuar Reserva");
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

        lblRetirada.setText("Retirada possível apartir de");

        lblProfessor.setText("Professor");

        lblLivro.setText("Livro");

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listLivros, cbxLivro);
        bindingGroup.addBinding(jComboBoxBinding);

        cbxLivro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxLivroActionPerformed(evt);
            }
        });

        txtRetirada.setEditable(false);

        lblDisponivel.setText("(Apenas livros disponíveis e emprestados. Não são visíveis livros já reservados).");

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listProfessores, cbxUser);
        bindingGroup.addBinding(jComboBoxBinding);

        javax.swing.GroupLayout abaDadosLayout = new javax.swing.GroupLayout(abaDados);
        abaDados.setLayout(abaDadosLayout);
        abaDadosLayout.setHorizontalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(abaDadosLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(lblLivro)
                                .addGap(47, 47, 47))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abaDadosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblProfessor)
                                .addGap(18, 18, 18)))
                        .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxUser, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(painelNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(lblRetirada)
                        .addGap(29, 29, 29)
                        .addComponent(txtRetirada, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(lblDisponivel, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        abaDadosLayout.setVerticalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(painelNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblDisponivel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLivro)
                    .addComponent(cbxLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProfessor)
                    .addComponent(cbxUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRetirada)
                    .addComponent(txtRetirada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        abas.addTab("Novo", abaDados);

        pnlAcoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        pnlAcoes.setLayout(new java.awt.GridLayout(1, 0));

        btnCancelarReserva.setText("Cancelar Reserva");
        btnCancelarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarReservaActionPerformed(evt);
            }
        });
        pnlAcoes.add(btnCancelarReserva);

        lbLivro.setText("Livro");

        lbProfessor.setText("Professor");

        lbRetirada.setText("Data Retirada a partir de");

        txtInfoLivro.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReservas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.exemplar.nome}"), txtInfoLivro, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtInfoProfessor.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReservas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.user.nome}"), txtInfoProfessor, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtInfoRetirada.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReservas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.retirada}"), txtInfoRetirada, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(converterData);
        bindingGroup.addBinding(binding);

        txtInfoIsbn.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReservas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.exemplar.isbn}"), txtInfoIsbn, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblIsbn.setText("ISBN");

        txtInfoMatricula.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReservas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.user.matricula}"), txtInfoMatricula, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblMatricula.setText("Matrícula");

        javax.swing.GroupLayout abaManutencaoLayout = new javax.swing.GroupLayout(abaManutencao);
        abaManutencao.setLayout(abaManutencaoLayout);
        abaManutencaoLayout.setHorizontalGroup(
            abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaManutencaoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaManutencaoLayout.createSequentialGroup()
                        .addComponent(lbRetirada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInfoRetirada, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addComponent(pnlAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(abaManutencaoLayout.createSequentialGroup()
                        .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(abaManutencaoLayout.createSequentialGroup()
                                .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbLivro)
                                    .addComponent(lblIsbn)
                                    .addComponent(lblMatricula))
                                .addGap(21, 21, 21))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abaManutencaoLayout.createSequentialGroup()
                                .addComponent(lbProfessor)
                                .addGap(18, 18, 18)))
                        .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtInfoIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtInfoLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtInfoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtInfoProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        abaManutencaoLayout.setVerticalGroup(
            abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abaManutencaoLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtInfoIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIsbn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtInfoLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbLivro))
                .addGap(38, 38, 38)
                .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtInfoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMatricula))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtInfoProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbProfessor))
                .addGap(53, 53, 53)
                .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(abaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbRetirada)
                        .addComponent(txtInfoRetirada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
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
                .addGroup(pnlBuscaProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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

        lblInfo.setText("* Os resultados das buscas são exibidos na tabela principal (listagem das reservas)");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        abas.addTab("Buscas", abaBuscas);

        btnMostratodos.setText("Mostrar todas reservas");
        btnMostratodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostratodosActionPerformed(evt);
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
                        .addComponent(btnMostratodos, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblReservasTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblReservasTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMostratodos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        tblReservas.setRowSelectionInterval(0, 0);
        tblReservas.scrollRectToVisible(tblReservas.getCellRect(0, 0, true));
    }//GEN-LAST:event_btnPrimeiroActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        int linha = tblReservas.getSelectedRow();
        if ((linha + 1) < tblReservas.getRowCount()) {
            linha++;
        }
        tblReservas.setRowSelectionInterval(linha, linha);
        tblReservas.scrollRectToVisible(tblReservas.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        int linha = tblReservas.getSelectedRow();
        if ((linha - 1) >= 0) {
            linha--;
        }
        tblReservas.setRowSelectionInterval(linha, linha);
        tblReservas.scrollRectToVisible(tblReservas.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        int linha = tblReservas.getRowCount() - 1;
        tblReservas.setRowSelectionInterval(linha, linha);
        tblReservas.scrollRectToVisible(tblReservas.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnMostratodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostratodosActionPerformed
        mostraTodos();
    }//GEN-LAST:event_btnMostratodosActionPerformed

    private void btnCancelarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarReservaActionPerformed
        int linhaSelecionada = tblReservas.getSelectedRow();
        if (linhaSelecionada >= 0) {
            if (JOptionPane.showOptionDialog(null, "Você deseja mesmo cancelar e reserva?",
                    "Cancelar reserva", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"Sim", "Não"}, "Sim") == 0) {
                Reserva r = listReservas.get(linhaSelecionada);
                Livro l = r.getExemplar();
                if (l.getSituacao() == Situacao.RESERVADOEMPRESTADO) {
                    l.setSituacao(Situacao.EMPRESTADO);
                } else {
                    l.setSituacao(Situacao.NORMAL);
                }
                ldao.alterar(l);
                rdao.excluir(r);
                try {
                    atualizaTabela();
                    atualizaLivros();
                    if (listReservas.size() - 1 >= 0) {
                        tblReservas.setRowSelectionInterval(0, 0);
                        tblReservas.scrollRectToVisible(tblReservas.getCellRect(0, 0, true));
                    } else {
                        limpa_informacoes();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ReservaProfessorView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showConfirmDialog(null, "Nenhuma reserva selecionada.",
                    "Lista de reservas vazia", JOptionPane.DEFAULT_OPTION);
        }
    }//GEN-LAST:event_btnCancelarReservaActionPerformed

    private void btIsbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIsbnActionPerformed
        String busca = txtBuscaIsbn.getText();

        if (busca.length() > 0) {
            boolean ehNumero = VerificaString.ehNumero(busca);

            if (ehNumero) {
                listReservas.clear();
                List<Livro> l2 = ldao.buscarQualquer(
                        ldao.getSqlBuscaIsbn(), Integer.parseInt(busca));  // isbn
                l2.stream().forEach((livros) -> {
                    listReservas.addAll(rdao.buscarQualquer(rdao.getSqlBuscaLivro(), livros.getCodigo()));
                });
                int linhas = listReservas.size() - 1;
                if (linhas >= 0) {
                    tblReservas.setRowSelectionInterval(0, 0);
                    tblReservas.scrollRectToVisible(tblReservas.getCellRect(0, 0, true));
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
                Logger.getLogger(ReservaProfessorView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btIsbnActionPerformed

    private void btTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTituloActionPerformed
        String busca = txtBuscaTitulo.getText();

        if (busca.length() > 0) {
            listReservas.clear();

            Livro l = new Livro();
            l.setNome(busca);
            List<Livro> l1 = ldao.buscar(l); // parte do titulo
            l1.stream().forEach((livros) -> {
                listReservas.addAll(rdao.buscarQualquer(rdao.getSqlBuscaLivro(), livros.getCodigo()));
            });

            int linhas = listReservas.size() - 1;
            if (linhas >= 0) {
                tblReservas.setRowSelectionInterval(0, 0);
                tblReservas.scrollRectToVisible(tblReservas.getCellRect(0, 0, true));
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
                Logger.getLogger(ReservaProfessorView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btTituloActionPerformed

    private void btMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMatriculaActionPerformed
        String busca = txtBuscaMatricula.getText();

        if (busca.length() > 0) {
            boolean ehNumero = VerificaString.ehNumero(busca);

            if (ehNumero) {
                listReservas.clear();
                List<Professor> l1 = pdao.buscarQualquer(pdao.getSqlBuscaMatricula(), 
                        Integer.parseInt(busca)); // matricula
                l1.stream().forEach((professores) -> {
                    listReservas.addAll(rdao.buscarQualquer(rdao.getSqlBuscaProfessor(), 
                        professores.getCodigo()));
                });
                int linhas = listReservas.size() - 1;
                if (linhas >= 0) {
                    tblReservas.setRowSelectionInterval(0, 0);
                    tblReservas.scrollRectToVisible(tblReservas.getCellRect(0, 0, true));
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
                Logger.getLogger(ReservaProfessorView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btMatriculaActionPerformed

    private void btNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNomeActionPerformed
        String busca = txtBuscaNome.getText();

        if (busca.length() > 0) {
            listReservas.clear();

            Professor a = new Professor();
            a.setNome(busca);
            List<Professor> l2 = pdao.buscar(a); // parte do nome
            l2.stream().forEach((professores) -> {
                listReservas.addAll(
                        rdao.buscarQualquer(rdao.getSqlBuscaProfessor(), professores.getCodigo()));
            });

            int linhas = listReservas.size() - 1;
            if (linhas >= 0) {
                tblReservas.setRowSelectionInterval(0, 0);
                tblReservas.scrollRectToVisible(tblReservas.getCellRect(0, 0, true));
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
                Logger.getLogger(ReservaProfessorView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btNomeActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (validaCampos()) {
            int linhaSelecionada = tblReservas.getSelectedRow();
            Reserva r = listReservas.get(linhaSelecionada);
            r = corrigeDatas(r);
            r.setUser((Professor) cbxUser.getSelectedItem());

            if (r.getUser().getValorMulta() > 0) {
                JOptionPane.showConfirmDialog(null, 
                        "O professor selecionado tem multa de R$"
                        + r.getUser().getValorMulta()
                        + ".\nAção necessária:  Quitar a multa.",
                        "Reserva recusada", JOptionPane.DEFAULT_OPTION);
                trataEdicao(false);
                try {
                    atualizaTabela();
                } catch (SQLException ex) {
                    Logger.getLogger(
                            ReservaProfessorView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }

            if (r.getCodigo() == null) {
                r.setExemplar((Livro) cbxLivro.getSelectedItem());
                if (r.getExemplar().getSituacao() == Situacao.EMPRESTADO) {
                    r.getExemplar().setSituacao(Situacao.RESERVADOEMPRESTADO);
                } else {
                    r.getExemplar().setSituacao(Situacao.RESERVADO);
                }
                ldao.alterar(r.getExemplar());
                rdao.inserir(r);
            }
            trataEdicao(false);
            try {
                atualizaTabela();
                atualizaLivros();
            } catch (SQLException ex) {
                Logger.getLogger(ReservaProfessorView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        trataEdicao(false);
        try {
            atualizaTabela();
        } catch (SQLException ex) {
            Logger.getLogger(ReservaProfessorView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEfetuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEfetuarActionPerformed
        if (cbxLivro.getItemCount() == 0) {
            JOptionPane.showConfirmDialog(null, 
                    "Não há livros disponíveis para a reserva.",
                    "Lista de livros vazia", JOptionPane.DEFAULT_OPTION);
            trataEdicao(false);
            return;
        }

        if (cbxUser.getItemCount() == 0) {
            JOptionPane.showConfirmDialog(null, "Não há professores.",
                    "Lista de professores vazia", JOptionPane.DEFAULT_OPTION);
            trataEdicao(false);
            return;
        }
        txtRetirada.setText(dataSistema());
        listReservas.add(corrigeDatas(new Reserva()));
        int linhas = listReservas.size() - 1;
        tblReservas.setRowSelectionInterval(linhas, linhas);
        trataEdicao(true);
        cbxLivro.requestFocus();
        verificaDataEntrega();
    }//GEN-LAST:event_btnEfetuarActionPerformed

    private void cbxLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxLivroActionPerformed
        verificaDataEntrega();
    }//GEN-LAST:event_cbxLivroActionPerformed

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
            java.util.logging.Logger.getLogger(ReservaProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReservaProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReservaProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReservaProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                ReservaProfessorView dialog = null;
                try {
                    dialog = new ReservaProfessorView(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    Logger.getLogger(ReservaProfessorView.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JPanel abaDados;
    private javax.swing.JPanel abaManutencao;
    private javax.swing.JPanel abaReservas;
    private javax.swing.JTabbedPane abas;
    private javax.swing.JButton btIsbn;
    private javax.swing.JButton btMatricula;
    private javax.swing.JButton btNome;
    private javax.swing.JButton btTitulo;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarReserva;
    private javax.swing.JButton btnEfetuar;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnMostratodos;
    private javax.swing.JButton btnPrimeiro;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JComboBox<String> cbxLivro;
    private javax.swing.JComboBox<String> cbxUser;
    private util.ConverterData converterData;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbIsbn;
    private javax.swing.JLabel lbLivro;
    private javax.swing.JLabel lbMatricula;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbProfessor;
    private javax.swing.JLabel lbRetirada;
    private javax.swing.JLabel lbTitulo;
    private javax.swing.JLabel lblDisponivel;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblIsbn;
    private javax.swing.JLabel lblLivro;
    private javax.swing.JLabel lblMatricula;
    private javax.swing.JLabel lblProfessor;
    private javax.swing.JLabel lblReservasTitulo;
    private javax.swing.JLabel lblRetirada;
    private java.util.List<Livro> listLivros;
    private java.util.List<Professor> listProfessores;
    private java.util.List<Reserva> listReservas;
    private javax.swing.JPanel painelAuxiliar;
    private javax.swing.JPanel painelNovo;
    private javax.swing.JPanel pnlAcoes;
    private javax.swing.JPanel pnlBuscaLivro;
    private javax.swing.JPanel pnlBuscaProfessor;
    private javax.swing.JTable tblReservas;
    private javax.swing.JTextField txtBuscaIsbn;
    private javax.swing.JTextField txtBuscaMatricula;
    private javax.swing.JTextField txtBuscaNome;
    private javax.swing.JTextField txtBuscaTitulo;
    private javax.swing.JTextField txtInfoIsbn;
    private javax.swing.JTextField txtInfoLivro;
    private javax.swing.JTextField txtInfoMatricula;
    private javax.swing.JTextField txtInfoProfessor;
    private javax.swing.JFormattedTextField txtInfoRetirada;
    private javax.swing.JFormattedTextField txtRetirada;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
