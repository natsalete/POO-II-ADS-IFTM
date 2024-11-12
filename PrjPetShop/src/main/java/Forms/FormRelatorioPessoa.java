/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Forms;

import BO.PessoaBO;
import Objetos.Pessoa;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 *
 * @author natsa
 */
public class FormRelatorioPessoa extends javax.swing.JFrame {
    
    private MaskFormatter mftDataInicio, mftDataFim;
    PessoaBO pBO = new PessoaBO();
    
    private void pesquisar(){
        List<Pessoa> lstPessoas;
        
        String nome = txtConsNome.getText().trim();
        String dataInicio = txtDataInicial.getText().trim();
        String dataFim = txtDataFim.getText().trim();
        
        if((!nome.isEmpty()) && (dataInicio.length() != 4) && (dataFim.length() != 4)){
            lstPessoas = pBO.getPessoas(nome);
            preencherTabela(lstPessoas);
        }else if((!nome.isEmpty()) && (dataInicio.length() != 4) && (dataFim.length() != 4)){
            lstPessoas = pBO.getPessoas(nome, dataInicio, dataFim);
            preencherTabela(lstPessoas);
        }else if((dataInicio.length() != 4) && (dataFim.length() != 4)){
            lstPessoas = pBO.getPessoas(dataInicio, dataFim);
            preencherTabela(lstPessoas);
        }else {
            lstPessoas = pBO.getPessoas();
            preencherTabela(lstPessoas);
        }
    
    }   
    
    private void preencherTabela(List<Pessoa> lstPessoas){
        DefaultTableModel tabelaPessoas = (DefaultTableModel)tabPessoas.getModel();
        tabPessoas.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabPessoas.getColumnModel().getColumn(0).setPreferredWidth(180);
        tabPessoas.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabelaPessoas.setNumRows(0);
        
        lstPessoas.forEach(itemPessoa ->{
            tabelaPessoas.addRow(new Object[]{itemPessoa.getIdpessoa(), itemPessoa.getNome(), itemPessoa.getData()});
        });
    }
    
    
    public FormRelatorioPessoa() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtConsNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        try{
            mftDataInicio = new MaskFormatter("##/##/####"); 
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Não foi possivel usar a mascara!!");
        }
        txtDataInicial = new JFormattedTextField(mftDataInicio);
        jLabel5 = new javax.swing.JLabel();
        try{
            mftDataFim = new MaskFormatter("##/##/####"); 
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Não foi possivel usar a mascara!!");
        }
        txtDataFim = new JFormattedTextField(mftDataFim);
        btnConsultar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabPessoas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RELATÓRIO DE PESSOAS");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("RELATÓRIO DE PESSOAS");

        jLabel2.setText("Pesquisar pessoas");

        jLabel3.setText("Nome:");

        txtConsNome.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtConsNomeCaretUpdate(evt);
            }
        });

        jLabel4.setText("Data de Nasc:");

        txtDataInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataInicialActionPerformed(evt);
            }
        });

        jLabel5.setText("até");

        txtDataFim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataFimActionPerformed(evt);
            }
        });

        btnConsultar.setText("Consulta");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        tabPessoas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Código", "Nome", "Data de Nascimento"
            }
        ));
        tabPessoas.setShowHorizontalLines(true);
        tabPessoas.setShowVerticalLines(true);
        jScrollPane1.setViewportView(tabPessoas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(47, 47, 47)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtConsNome, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(btnConsultar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtConsNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnConsultar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDataInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataInicialActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        pesquisar();
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void txtConsNomeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtConsNomeCaretUpdate
        pesquisar();
    }//GEN-LAST:event_txtConsNomeCaretUpdate

    private void txtDataFimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataFimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataFimActionPerformed

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
            java.util.logging.Logger.getLogger(FormRelatorioPessoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormRelatorioPessoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormRelatorioPessoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormRelatorioPessoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormRelatorioPessoa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabPessoas;
    private javax.swing.JTextField txtConsNome;
    private javax.swing.JTextField txtDataFim;
    private javax.swing.JTextField txtDataInicial;
    // End of variables declaration//GEN-END:variables
}