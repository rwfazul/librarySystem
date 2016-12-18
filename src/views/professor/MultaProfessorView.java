/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.professor;

import views.geral.LivroView;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Multa;
import model.Professor;
import model.dao.ProfessorDAO;
import control.MultasControl;
import util.VerificaString;

/**
 *
 * @author rhau
 */
public class MultaProfessorView extends javax.swing.JDialog {

    ProfessorDAO pdao = new ProfessorDAO();

    public void atualizaTabela() throws SQLException {
        List<Professor> professores = pdao.buscarTodos();
        listMultas.clear();

        professores.stream().filter((p) -> (p.getValorMulta() > 0)).map((p) -> {
            Multa m = new Multa(p, p.getValorMulta());
            return m;
        }).forEach((m) -> {
            listMultas.add(m);
        });

        int linha = listMultas.size() - 1;
        if (linha >= 0) {
            tblMultas.setRowSelectionInterval(linha, linha);
            tblMultas.scrollRectToVisible(
                    tblMultas.getCellRect(linha, linha, true));
        } else {
            limpa_campos();
            JOptionPane.showConfirmDialog(null, "Não há multas.",
                "Lista de multas vazia", JOptionPane.DEFAULT_OPTION);
        }
        txtBusca.setText("");
    }

    public void limpa_campos() {
        txtMatricula.setText("");
        txtAluno.setText("");
        txtMulta.setText("");
        txtBusca.setEditable(false);
        txtMatricula.setEnabled(false);
        txtAluno.setEnabled(false);
        txtMulta.setEnabled(false);
        txtBusca.setEnabled(false);
        btnPagar.setEnabled(false);
        btnPrimeiro.setEnabled(false);
        btnAnterior.setEnabled(false);
        btnProximo.setEnabled(false);
        btnUltimo.setEnabled(false);
        btnBusca.setEnabled(false);
    }

    //Construtor
    public MultaProfessorView(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        new MultasControl();
        initComponents();
        atualizaTabela();
        if (listMultas.size() - 1 >= 0) {
            tblMultas.setRowSelectionInterval(0, 0);
            tblMultas.scrollRectToVisible(tblMultas.getCellRect(0, 0, true));
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

        listMultas = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Multa>())
        ;
        painelAuxiliar = new javax.swing.JPanel();
        btnPrimeiro = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnProximo = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        lblMultasTitulo = new javax.swing.JLabel();
        btnBusca = new javax.swing.JButton();
        txtBusca = new javax.swing.JTextField();
        abas = new javax.swing.JTabbedPane();
        abaMultas = new javax.swing.JPanel();
        spainelTbl = new javax.swing.JScrollPane();
        tblMultas = new javax.swing.JTable();
        abaDados = new javax.swing.JPanel();
        txtMulta = new javax.swing.JTextField();
        lblMulta = new javax.swing.JLabel();
        txtAluno = new javax.swing.JTextField();
        lblProfessor = new javax.swing.JLabel();
        lblRs = new javax.swing.JLabel();
        btnPagar = new javax.swing.JButton();
        lblMatricula = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento de multas");
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

        lblMultasTitulo.setFont(new java.awt.Font("Century Schoolbook L", 1, 18)); // NOI18N
        lblMultasTitulo.setForeground(new java.awt.Color(75, 192, 183));
        lblMultasTitulo.setText("Gerenciamento de multas - Professores");

        btnBusca.setText("Buscar");
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
            }
        });

        abaMultas.setLayout(new java.awt.BorderLayout());

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listMultas, tblMultas);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${user.matricula}"));
        columnBinding.setColumnName("Matrícula");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${user.nome}"));
        columnBinding.setColumnName("Professor");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${user.valorMulta}"));
        columnBinding.setColumnName("Valor(R$)");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        spainelTbl.setViewportView(tblMultas);
        if (tblMultas.getColumnModel().getColumnCount() > 0) {
            tblMultas.getColumnModel().getColumn(0).setMinWidth(130);
            tblMultas.getColumnModel().getColumn(0).setPreferredWidth(130);
            tblMultas.getColumnModel().getColumn(0).setMaxWidth(130);
            tblMultas.getColumnModel().getColumn(2).setMinWidth(90);
            tblMultas.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblMultas.getColumnModel().getColumn(2).setMaxWidth(90);
        }

        abaMultas.add(spainelTbl, java.awt.BorderLayout.PAGE_START);

        abas.addTab("Listagem das multas", abaMultas);

        txtMulta.setEditable(false);
        txtMulta.setToolTipText("");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblMultas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.valor}"), txtMulta, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblMulta.setText("Valor multa");

        txtAluno.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblMultas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.user.nome}"), txtAluno, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblProfessor.setText("Professor");

        lblRs.setText("R$");

        btnPagar.setText("Pagar");
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });

        lblMatricula.setText("Matrícula");

        txtMatricula.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblMultas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.user.matricula}"), txtMatricula, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout abaDadosLayout = new javax.swing.GroupLayout(abaDados);
        abaDados.setLayout(abaDadosLayout);
        abaDadosLayout.setHorizontalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProfessor)
                            .addComponent(lblMatricula))
                        .addGap(46, 46, 46))
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addComponent(lblMulta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(lblRs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMulta, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 108, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abaDadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        abaDadosLayout.setVerticalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMatricula))
                .addGap(29, 29, 29)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtAluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProfessor))
                .addGap(30, 30, 30)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtMulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRs)
                    .addComponent(lblMulta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPagar)
                .addGap(66, 66, 66))
        );

        abas.addTab("Dados", abaDados);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(painelAuxiliar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMultasTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(abas)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMultasTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBusca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelAuxiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        bindingGroup.bind();

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeiroActionPerformed
        tblMultas.setRowSelectionInterval(0, 0);
        tblMultas.scrollRectToVisible(tblMultas.getCellRect(0, 0, true));
    }//GEN-LAST:event_btnPrimeiroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        int linha = tblMultas.getSelectedRow();
        if ((linha - 1) >= 0) {
            linha--;
        }
        tblMultas.setRowSelectionInterval(linha, linha);
        tblMultas.scrollRectToVisible(tblMultas.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        int linha = tblMultas.getSelectedRow();
        if ((linha + 1) < tblMultas.getRowCount()) {
            linha++;
        }
        tblMultas.setRowSelectionInterval(linha, linha);
        tblMultas.scrollRectToVisible(tblMultas.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        int linha = tblMultas.getRowCount() - 1;
        tblMultas.setRowSelectionInterval(linha, linha);
        tblMultas.scrollRectToVisible(tblMultas.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
        String busca = txtBusca.getText();

        if (busca.length() > 0) {
            boolean ehNumero = VerificaString.ehNumero(busca);
            List<Multa> temp = new ArrayList(listMultas);

            listMultas.clear();
            if (ehNumero) {
                for (Multa m : temp) { // matricula
                    if (m.getUser().getMatricula().equals(Integer.parseInt(busca))) {
                        listMultas.add(m);
                    }
                }
            } else { // parte nome
                temp.stream().filter((m) -> (m.getUser().getNome().contains(busca))).forEach((m) -> {
                    listMultas.add(m);
                });
            }
            int linhas = listMultas.size() - 1;
            if (linhas >= 0) {
                tblMultas.setRowSelectionInterval(0, 0);
                tblMultas.scrollRectToVisible(tblMultas.getCellRect(0, 0, true));
            } else {
                try {
                    atualizaTabela();
                } catch (SQLException ex) {
                    Logger.getLogger(MultaProfessorView.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showConfirmDialog(null, "Nenhuma correspondência encontrada.",
                        "Busca impossível", JOptionPane.DEFAULT_OPTION);
            }
        } else {
            try {
                atualizaTabela();
            } catch (SQLException ex) {
                Logger.getLogger(LivroView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnBuscaActionPerformed

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        int linhaSelecionada = tblMultas.getSelectedRow();
        if (linhaSelecionada >= 0) {
            if (JOptionPane.showOptionDialog(null, "Você confirma o pagamento da multa?",
                    "Confirmar pagamento", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"Sim", "Não"}, "Sim") == 0) {
                Multa m = listMultas.get(linhaSelecionada);
                m.getUser().setValorMulta(0);
                pdao.alterar((Professor) m.getUser());
                try {
                    atualizaTabela();
                    if (listMultas.size() - 1 >= 0) {
                        tblMultas.setRowSelectionInterval(0, 0);
                        tblMultas.scrollRectToVisible(tblMultas.getCellRect(0, 0, true));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LivroView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showConfirmDialog(null, "Não há multas.",
                    "Lista de multas vazia", JOptionPane.DEFAULT_OPTION);
        }
    }//GEN-LAST:event_btnPagarActionPerformed

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
            java.util.logging.Logger.getLogger(MultaProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MultaProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MultaProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MultaProfessorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MultaProfessorView dialog = null;
                try {
                    dialog = new MultaProfessorView(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    Logger.getLogger(MultaProfessorView.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JPanel abaMultas;
    private javax.swing.JTabbedPane abas;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnPrimeiro;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JLabel lblMatricula;
    private javax.swing.JLabel lblMulta;
    private javax.swing.JLabel lblMultasTitulo;
    private javax.swing.JLabel lblProfessor;
    private javax.swing.JLabel lblRs;
    private java.util.List<Multa> listMultas;
    private javax.swing.JPanel painelAuxiliar;
    private javax.swing.JScrollPane spainelTbl;
    private javax.swing.JTable tblMultas;
    private javax.swing.JTextField txtAluno;
    private javax.swing.JTextField txtBusca;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtMulta;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}