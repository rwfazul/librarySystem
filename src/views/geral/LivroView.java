package views.geral;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.dao.LivroDAO;
import model.Livro;
import model.Situacao;
import util.VerificaString;
/**
 *
 * @author rhau
 */
public class LivroView extends javax.swing.JDialog {

    LivroDAO ldao = new LivroDAO();

    public void atualizaTabela() throws SQLException {
        listLivros.clear();
        listLivros.addAll(ldao.buscarTodos());
        int linha = listLivros.size() - 1;
        if (linha >= 0) {
            tblLivros.setRowSelectionInterval(linha, linha);
            tblLivros.scrollRectToVisible(
                    tblLivros.getCellRect(linha, linha, true));
            barra_navegacao(true);
        }
        else {
            limpa_campos();  
            barra_navegacao(false);
        }
        txtBusca.setText("");
    }

    public void trataEdicao(boolean editando) {
        // botoes ativados se estivar editando 
        btnCancelar.setEnabled(editando);
        btnSalvar.setEnabled(editando);
        // botoes desativados se estivar editando 
        btnEditar.setEnabled(!editando);
        btnExcluir.setEnabled(!editando);
        btnNovo.setEnabled(!editando);
        btnFechar.setEnabled(!editando);
        btnPrimeiro.setEnabled(!editando);
        btnProximo.setEnabled(!editando);
        btnAnterior.setEnabled(!editando);
        btnUltimo.setEnabled(!editando);
        btnBusca.setEnabled(!editando);
        // campos ativados se estivar editando 
        txtTitulo.setEditable(editando);
        txtIsbn.setEditable(editando);
        txtAutor.setEditable(editando);
        txtEdicao.setEditable(editando);
        txtEditora.setEditable(editando);
        txtAno.setEditable(editando);
        // desativado
        txtBusca.setEditable(!editando);
        // tabela
        tblLivros.setEnabled(!editando);
    }
    
    public boolean validaCampos() {
        if (txtVazio(txtTitulo, "o nome"))
            return false;
        
        if (txtVazio(txtIsbn, "o ISBN"))
            return false;
        
        if (!VerificaString.ehNumero(txtIsbn.getText())) {
            JOptionPane.showMessageDialog(null, "O ISBN deve conter apenas números");
            txtIsbn.requestFocus();
            return false;
        }
        
        if (txtVazio(txtAutor, "o(s) autor(es)"))
            return false;
        
        if (txtVazio(txtEditora, "a editora"))
            return false;
        
        if (txtVazio(txtAno, "o ano"))
            return false;
      
        if (txtVazio(txtEdicao, "a edição"))
            return false;
 
        if (!VerificaString.ehNumero(txtAno.getText())) {
            JOptionPane.showMessageDialog(null, "O ano deve conter apenas números");
            txtAno.requestFocus();
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
       txtIsbn.setText("");
       txtTitulo.setText("");
       txtAutor.setText("");
       txtCodigo.setText("");
       txtEdicao.setText("");
       txtEditora.setText("");
       txtAno.setText("");
    }   
    
    public void barra_navegacao(Boolean b) {
        btnPrimeiro.setEnabled(b);
        btnAnterior.setEnabled(b);
        btnProximo.setEnabled(b);
        btnUltimo.setEnabled(b);
    }      
    
    //Construtor
    public LivroView(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        trataEdicao(false);
        atualizaTabela();
        if (listLivros.size() - 1 >= 0) {
            tblLivros.setRowSelectionInterval(0, 0);
            tblLivros.scrollRectToVisible(tblLivros.getCellRect(0, 0, true));
        }
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
        converterSituacao = new util.ConverterSituacao();
        painelAuxiliar = new javax.swing.JPanel();
        btnPrimeiro = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnProximo = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        lblLivrosTitulo = new javax.swing.JLabel();
        abas = new javax.swing.JTabbedPane();
        abaLivros = new javax.swing.JPanel();
        spainelTbl = new javax.swing.JScrollPane();
        tblLivros = new javax.swing.JTable();
        abaDados = new javax.swing.JPanel();
        painelOpcoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        txtAutor = new javax.swing.JTextField();
        lblAutor = new javax.swing.JLabel();
        lblAno = new javax.swing.JLabel();
        txtAno = new javax.swing.JTextField();
        txtEdicao = new javax.swing.JTextField();
        lblEdicao = new javax.swing.JLabel();
        txtEditora = new javax.swing.JTextField();
        lblEditora = new javax.swing.JLabel();
        txtIsbn = new javax.swing.JTextField();
        lblIsbn = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        lblAutomatico = new javax.swing.JLabel();
        txtSituacao = new javax.swing.JTextField();
        lblSituacao = new javax.swing.JLabel();
        btnBusca = new javax.swing.JButton();
        txtBusca = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento de livros");
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

        lblLivrosTitulo.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblLivrosTitulo.setForeground(new java.awt.Color(75, 192, 183));
        lblLivrosTitulo.setText("Gerenciamento de Livros");

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listLivros, tblLivros);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${isbn}"));
        columnBinding.setColumnName("ISBN");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nome}"));
        columnBinding.setColumnName("Nome");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${autor}"));
        columnBinding.setColumnName("Autores");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${editora}"));
        columnBinding.setColumnName("Editora");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${ano}"));
        columnBinding.setColumnName("Ano");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${edicao}"));
        columnBinding.setColumnName("Edição");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        spainelTbl.setViewportView(tblLivros);
        if (tblLivros.getColumnModel().getColumnCount() > 0) {
            tblLivros.getColumnModel().getColumn(0).setMinWidth(90);
            tblLivros.getColumnModel().getColumn(0).setPreferredWidth(90);
            tblLivros.getColumnModel().getColumn(0).setMaxWidth(90);
            tblLivros.getColumnModel().getColumn(3).setMinWidth(100);
            tblLivros.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblLivros.getColumnModel().getColumn(3).setMaxWidth(100);
            tblLivros.getColumnModel().getColumn(4).setMinWidth(60);
            tblLivros.getColumnModel().getColumn(4).setPreferredWidth(60);
            tblLivros.getColumnModel().getColumn(4).setMaxWidth(60);
            tblLivros.getColumnModel().getColumn(5).setMinWidth(70);
            tblLivros.getColumnModel().getColumn(5).setPreferredWidth(70);
            tblLivros.getColumnModel().getColumn(5).setMaxWidth(70);
        }

        javax.swing.GroupLayout abaLivrosLayout = new javax.swing.GroupLayout(abaLivros);
        abaLivros.setLayout(abaLivrosLayout);
        abaLivrosLayout.setHorizontalGroup(
            abaLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaLivrosLayout.createSequentialGroup()
                .addComponent(spainelTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        abaLivrosLayout.setVerticalGroup(
            abaLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaLivrosLayout.createSequentialGroup()
                .addComponent(spainelTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        abas.addTab("Listagem dos livros", abaLivros);

        painelOpcoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        painelOpcoes.setLayout(new java.awt.GridLayout(1, 0));

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        painelOpcoes.add(btnNovo);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        painelOpcoes.add(btnEditar);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        painelOpcoes.add(btnCancelar);

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        painelOpcoes.add(btnSalvar);

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        painelOpcoes.add(btnExcluir);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblLivros, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.autor}"), txtAutor, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblAutor.setText("Autores");

        lblAno.setText("Ano");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblLivros, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.ano}"), txtAno, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblLivros, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.edicao}"), txtEdicao, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblEdicao.setText("Edição");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblLivros, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.editora}"), txtEditora, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblEditora.setText("Editora");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblLivros, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.isbn}"), txtIsbn, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblIsbn.setText("ISBN");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblLivros, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.nome}"), txtTitulo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloActionPerformed(evt);
            }
        });

        lblTitulo.setText("Título");

        txtCodigo.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblLivros, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.codigo}"), txtCodigo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        lblCodigo.setText("Código");

        lblAutomatico.setText("(Automático)");

        txtSituacao.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblLivros, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.situacao}"), txtSituacao, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(converterSituacao);
        bindingGroup.addBinding(binding);

        lblSituacao.setText("Situação atual");

        javax.swing.GroupLayout abaDadosLayout = new javax.swing.GroupLayout(abaDados);
        abaDados.setLayout(abaDadosLayout);
        abaDadosLayout.setHorizontalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelOpcoes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAno)
                    .addComponent(lblEditora)
                    .addComponent(lblAutor)
                    .addComponent(lblIsbn)
                    .addComponent(lblTitulo)
                    .addComponent(lblCodigo))
                .addGap(32, 32, 32)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(abaDadosLayout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAutomatico))
                            .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                            .addComponent(txtAutor))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abaDadosLayout.createSequentialGroup()
                        .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(abaDadosLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblSituacao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(abaDadosLayout.createSequentialGroup()
                                .addComponent(txtEditora, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                                .addComponent(lblEdicao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEdicao, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(120, 120, 120))))
        );
        abaDadosLayout.setVerticalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addComponent(painelOpcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodigo)
                    .addComponent(lblAutomatico))
                .addGap(18, 18, 18)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIsbn)
                    .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAutor))
                .addGap(22, 22, 22)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtEdicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEdicao)
                    .addComponent(txtEditora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditora))
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAno))
                        .addContainerGap(61, Short.MAX_VALUE))
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSituacao))
                        .addGap(17, 17, 17))))
        );

        abas.addTab("Dados", abaDados);

        btnBusca.setText("Buscar");
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
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
                        .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(132, 132, 132))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLivrosTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(181, 181, 181))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLivrosTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBusca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(painelAuxiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        bindingGroup.bind();

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        Livro l = new Livro();
        l.setSituacao(Situacao.NORMAL);
        listLivros.add(l);
        int linhas = listLivros.size() - 1;
        tblLivros.setRowSelectionInterval(linhas, linhas);
        trataEdicao(true);
        txtTitulo.requestFocus();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (validaCampos()) {
            int linhaSelecionada = tblLivros.getSelectedRow();
            Livro l = listLivros.get(linhaSelecionada);
            if (l.getCodigo() != null) {
                ldao.alterar(l);
            } else {
                ldao.inserir(l);
            }
            trataEdicao(false);
            try {
                atualizaTabela();
            } catch (SQLException ex) {
                Logger.getLogger(LivroView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (tblLivros.getSelectedRow() >= 0) {
            trataEdicao(true);
            txtTitulo.requestFocus();
        } else {
            JOptionPane.showConfirmDialog(null, "Algum livro deve ser selecionado para edição.",
            "Lista de livros vazia", JOptionPane.DEFAULT_OPTION);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        trataEdicao(false);
        try {
            atualizaTabela();
        } catch (SQLException ex) {
            Logger.getLogger(LivroView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int linhaSelecionada = tblLivros.getSelectedRow();
        if (linhaSelecionada >= 0) {
            if (JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o livro?",
                        "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new String[]{"Sim", "Não"}, "Sim") == 0) {
                Livro l = listLivros.get(linhaSelecionada);
                if (l.getSituacao().equals(Situacao.NORMAL)) {
                    ldao.excluir(l);
                    try {
                        atualizaTabela();
                        if (listLivros.size() - 1 >= 0) {
                            tblLivros.setRowSelectionInterval(0, 0);
                            tblLivros.scrollRectToVisible(tblLivros.getCellRect(0, 0, true));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(LivroView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "O livro selecionado está " 
                    + txtSituacao.getText() + ".\nAção necessária para remoção: Cancelar reserva/empréstimo.",
                    "Remoção impossível - Pendência", JOptionPane.DEFAULT_OPTION);             
                }
           }
        } else {
            JOptionPane.showConfirmDialog(null, "Não há livros para exclusão.",
                    "Lista de livros vazia", JOptionPane.DEFAULT_OPTION);
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeiroActionPerformed
        tblLivros.setRowSelectionInterval(0, 0);
        tblLivros.scrollRectToVisible(tblLivros.getCellRect(0, 0, true));
    }//GEN-LAST:event_btnPrimeiroActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        int linha = tblLivros.getSelectedRow();
        if ((linha + 1) < tblLivros.getRowCount()) {
            linha++;
        }
        tblLivros.setRowSelectionInterval(linha, linha);
        tblLivros.scrollRectToVisible(tblLivros.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        int linha = tblLivros.getSelectedRow();
        if ((linha - 1) >= 0) {
            linha--;
        }
        tblLivros.setRowSelectionInterval(linha, linha);
        tblLivros.scrollRectToVisible(tblLivros.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        int linha = tblLivros.getRowCount() - 1;
        tblLivros.setRowSelectionInterval(linha, linha);
        tblLivros.scrollRectToVisible(tblLivros.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
        String busca = txtBusca.getText();

        if (busca.length() > 0) {
            boolean ehNumero = VerificaString.ehNumero(busca);

            listLivros.clear();
            if (ehNumero) {
                listLivros.addAll(ldao.buscarQualquer
                    (ldao.getSqlBuscaIsbn(), Integer.parseInt(busca))); // isbn
            } else {
                Livro l = new Livro();
                l.setNome(busca);
                listLivros.addAll(ldao.buscar(l)); // parte do nome
                listLivros.addAll(ldao.buscarQualquer(ldao.getSqlBuscaEditora(), busca)); // editora
            }
            int linhas = listLivros.size() - 1;
            if (linhas >= 0) {
                tblLivros.setRowSelectionInterval(0, 0);
                tblLivros.scrollRectToVisible(tblLivros.getCellRect(0, 0, true));
            } else {
                JOptionPane.showConfirmDialog(null, "Nenhuma correspondência encontrada.",
                "Busca sem resultado", JOptionPane.DEFAULT_OPTION);
                try {
                    atualizaTabela();
                } catch (SQLException ex) {
                    Logger.getLogger(LivroView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        } else {
            try {
                atualizaTabela();
            } catch (SQLException ex) {
                Logger.getLogger(LivroView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnBuscaActionPerformed

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
            java.util.logging.Logger.getLogger(LivroView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LivroView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LivroView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LivroView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LivroView dialog = null;
                try {
                    dialog = new LivroView(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    Logger.getLogger(LivroView.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JPanel abaDados;
    private javax.swing.JPanel abaLivros;
    private javax.swing.JTabbedPane abas;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPrimeiro;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnUltimo;
    private util.ConverterSituacao converterSituacao;
    private javax.swing.JLabel lblAno;
    private javax.swing.JLabel lblAutomatico;
    private javax.swing.JLabel lblAutor;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblEdicao;
    private javax.swing.JLabel lblEditora;
    private javax.swing.JLabel lblIsbn;
    private javax.swing.JLabel lblLivrosTitulo;
    private javax.swing.JLabel lblSituacao;
    private javax.swing.JLabel lblTitulo;
    private java.util.List<Livro> listLivros;
    private javax.swing.JPanel painelAuxiliar;
    private javax.swing.JPanel painelOpcoes;
    private javax.swing.JScrollPane spainelTbl;
    private javax.swing.JTable tblLivros;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtBusca;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEdicao;
    private javax.swing.JTextField txtEditora;
    private javax.swing.JTextField txtIsbn;
    private javax.swing.JTextField txtSituacao;
    private javax.swing.JTextField txtTitulo;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}