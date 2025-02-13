package Forms;

import BO.Nsr_ClienteBO;
import Objetos.Nsr_Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author natsa
 */
public class Nsr_FormCadastroCliente extends javax.swing.JFrame {

    private MaskFormatter FormatoDataNasc;
    private final Nsr_ClienteBO nsr_cBO;
    private List<Nsr_Cliente> nsr_lstClientes;
    private int nsr_idClinte;

    private void Nsr_preencherCombo() {
        Nsr_Cliente nsr_clienteCons = new Nsr_Cliente();
        String nsr_nome = nsr_txtConsNome.getText();
        if (!nsr_nome.isEmpty()) {
            nsr_lstClientes = nsr_cBO.getNsr_Clientes(nsr_nome);
            nsr_cmbClientes.removeAllItems();
            nsr_lstClientes.forEach(nsr_itemCliente -> {
                nsr_cmbClientes.addItem(nsr_itemCliente.getNsr_nome() + " | " + nsr_itemCliente.getNsr_idCliente());
            });
        } else {
            nsr_cmbClientes.removeAllItems();
        }
    }

    private void Nsr_preencherCombo(int nsr_idCliente) {
        Nsr_Cliente nsr_c = nsr_cBO.getNsr_Cliente(nsr_idCliente);
        nsr_cmbClientes.addItem(nsr_c.getNsr_nome() + " | " + nsr_c.getNsr_idCliente());
        Nsr_preencherCampos(nsr_c);

    }

    private void Nsr_preencherCampos(Nsr_Cliente nsr_cliente) {
        nsr_txtCodigo.setText(String.valueOf(nsr_cliente.getNsr_idCliente()));
        nsr_txtNome.setText(nsr_cliente.getNsr_nome());
        nsr_txtDataNasc.setText(nsr_cliente.getNsr_data_nascimento());
        nsr_txtCpf.setText(nsr_cliente.getNsr_cpf());
        nsr_btnSalvar.setEnabled(false);
    }

    private void Nsr_preencherCampos() {
        if (!nsr_lstClientes.isEmpty()) {
            int index = nsr_cmbClientes.getSelectedIndex();
            Nsr_Cliente nsr_cliente = nsr_lstClientes.get(index);
            Nsr_preencherCampos(nsr_cliente);
        }
    }

    private void Nsr_novo() {
        nsr_txtConsNome.setText("");
        nsr_lstClientes = new ArrayList<>();
        nsr_txtCodigo.setText("");
        nsr_txtNome.setText("");
        nsr_txtDataNasc.setText("");
        nsr_txtCpf.setText("");
        nsr_btnSalvar.setEnabled(true);
    }

    public Nsr_FormCadastroCliente() {
        initComponents();
        nsr_cBO = new Nsr_ClienteBO();
    }

    public Nsr_FormCadastroCliente(int nsr_idCliente) {
        initComponents();
        nsr_cBO = new Nsr_ClienteBO();
        Nsr_preencherCombo(nsr_idCliente);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        nsr_txtConsNome = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        nsr_cmbClientes = new javax.swing.JComboBox<>();
        nsr_btnConsultar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nsr_txtNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nsr_txtCodigo = new javax.swing.JTextField();

        try
        {
            FormatoDataNasc = new MaskFormatter("##/##/####");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Não foi possivel usar a mascara!" );
        }
        nsr_txtDataNasc = new JFormattedTextField(FormatoDataNasc);
        jLabel8 = new javax.swing.JLabel();
        nsr_txtCpf = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        nsr_btnSalvar = new javax.swing.JButton();
        nsr_btnEditar = new javax.swing.JButton();
        nsr_btnExcluir = new javax.swing.JButton();
        nsr_btnNovo = new javax.swing.JButton();
        nsr_btnSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados para consulta"));

        jLabel5.setText("Nome:");

        nsr_txtConsNome.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                nsr_txtConsNomeCaretUpdate(evt);
            }
        });

        jLabel6.setText("Cliente:");

        nsr_btnConsultar.setText("Consultar");
        nsr_btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsr_btnConsultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nsr_txtConsNome)
                    .addComponent(nsr_cmbClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nsr_btnConsultar)
                .addGap(145, 145, 145))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(nsr_txtConsNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(nsr_cmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(nsr_btnConsultar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CADASTRO DE CLIENTE");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados para cadastro"));

        jLabel3.setText("Nome:");

        jLabel4.setText("Código:");

        jLabel7.setText("Data de Nasc.:");

        nsr_txtCodigo.setEditable(false);

        jLabel8.setText("cpf:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(39, 39, 39)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nsr_txtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nsr_txtNome)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nsr_txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nsr_txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(nsr_txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nsr_txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(nsr_txtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(nsr_txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        nsr_btnSalvar.setText("Salvar");
        nsr_btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsr_btnSalvarActionPerformed(evt);
            }
        });

        nsr_btnEditar.setText("Editar");
        nsr_btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsr_btnEditarActionPerformed(evt);
            }
        });

        nsr_btnExcluir.setText("Excluir");
        nsr_btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsr_btnExcluirActionPerformed(evt);
            }
        });

        nsr_btnNovo.setText("Novo");
        nsr_btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsr_btnNovoActionPerformed(evt);
            }
        });

        nsr_btnSair.setText("Sair");
        nsr_btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsr_btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nsr_btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nsr_btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nsr_btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nsr_btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nsr_btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nsr_btnSalvar)
                    .addComponent(nsr_btnEditar)
                    .addComponent(nsr_btnExcluir)
                    .addComponent(nsr_btnNovo)
                    .addComponent(nsr_btnSair))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nsr_txtConsNomeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_nsr_txtConsNomeCaretUpdate

        Nsr_preencherCombo();
    }//GEN-LAST:event_nsr_txtConsNomeCaretUpdate

    private void nsr_btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsr_btnConsultarActionPerformed
        // TODO add your handling code here:
        Nsr_preencherCampos();
    }//GEN-LAST:event_nsr_btnConsultarActionPerformed

    private void nsr_btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsr_btnSalvarActionPerformed
        // TODO add your handling code here:
        Nsr_Cliente nsr_cliente = new Nsr_Cliente();
        nsr_cliente.setNsr_nome(nsr_txtNome.getText());
        nsr_cliente.setNsr_data_nascimento(nsr_txtDataNasc.getText());
        nsr_cliente.setNsr_cpf(nsr_txtCpf.getText());
        nsr_cBO.Nsr_salvar(nsr_cliente);
        int nsr_codigo = nsr_cliente.getNsr_idCliente();
        if (nsr_codigo != -1) {
            nsr_txtCodigo.setText(String.valueOf(nsr_cliente.getNsr_idCliente()));
            JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!!!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao salvar os dados!!!");
        }
    }//GEN-LAST:event_nsr_btnSalvarActionPerformed

    private void nsr_btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsr_btnEditarActionPerformed
        // TODO add your handling code here:
        Nsr_Cliente nsr_cliente = new Nsr_Cliente();
        nsr_cliente.setNsr_idCliente(Integer.parseInt(nsr_txtCodigo.getText()));
        nsr_cliente.setNsr_nome(nsr_txtNome.getText());
        nsr_cliente.setNsr_data_nascimento(nsr_txtDataNasc.getText());
        nsr_cliente.setNsr_cpf(nsr_txtCpf.getText());
        nsr_cBO.Nsr_editar(nsr_cliente);
    }//GEN-LAST:event_nsr_btnEditarActionPerformed

    private void nsr_btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsr_btnExcluirActionPerformed
        // TODO add your handling code here:
        Nsr_Cliente nsr_cliente = new Nsr_Cliente();
        nsr_cliente.setNsr_idCliente(Integer.parseInt(nsr_txtCodigo.getText()));
        int nsr_verif = nsr_cBO.Nsr_excluir(nsr_cliente);
        if (nsr_verif == 1) {
            Nsr_novo();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao excluir os dados!!!");
        }

    }//GEN-LAST:event_nsr_btnExcluirActionPerformed

    private void nsr_btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsr_btnNovoActionPerformed
        // TODO add your handling code here:
        Nsr_novo();
    }//GEN-LAST:event_nsr_btnNovoActionPerformed

    private void nsr_btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsr_btnSairActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_nsr_btnSairActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Nsr_FormCadastroCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton nsr_btnConsultar;
    private javax.swing.JButton nsr_btnEditar;
    private javax.swing.JButton nsr_btnExcluir;
    private javax.swing.JButton nsr_btnNovo;
    private javax.swing.JButton nsr_btnSair;
    private javax.swing.JButton nsr_btnSalvar;
    private javax.swing.JComboBox<String> nsr_cmbClientes;
    private javax.swing.JTextField nsr_txtCodigo;
    private javax.swing.JTextField nsr_txtConsNome;
    private javax.swing.JTextField nsr_txtCpf;
    private javax.swing.JTextField nsr_txtDataNasc;
    private javax.swing.JTextField nsr_txtNome;
    // End of variables declaration//GEN-END:variables
}
