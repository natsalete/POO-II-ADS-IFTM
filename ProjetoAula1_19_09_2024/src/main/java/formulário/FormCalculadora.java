/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formulário;

/**
 *
 * @author natsa
 */
public class FormCalculadora extends javax.swing.JFrame {

    /**
     * Creates new form FormCalculadora
     */
    public FormCalculadora() {
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

        lblNumero1 = new javax.swing.JLabel();
        txtNumero1 = new javax.swing.JTextField();
        btnMostrarSoma = new javax.swing.JButton();
        lblResultado = new javax.swing.JLabel();
        lblNumero2 = new javax.swing.JLabel();
        txtNumero2 = new javax.swing.JTextField();
        btnMostarSubtração = new javax.swing.JButton();
        btnMostrarMultiplicacao = new javax.swing.JButton();
        btnMostrarDivisao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblNumero1.setText("Digite um numero:");

        txtNumero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumero1ActionPerformed(evt);
            }
        });

        btnMostrarSoma.setText("Mostrar +");
        btnMostrarSoma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarSomaActionPerformed(evt);
            }
        });

        lblNumero2.setText("Digite outro numero:");

        txtNumero2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumero2ActionPerformed(evt);
            }
        });

        btnMostarSubtração.setText("Mostrar -");
        btnMostarSubtração.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostarSubtraçãoActionPerformed(evt);
            }
        });

        btnMostrarMultiplicacao.setText("Mostrar *");
        btnMostrarMultiplicacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarMultiplicacaoActionPerformed(evt);
            }
        });

        btnMostrarDivisao.setText("Mostrar /");
        btnMostrarDivisao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarDivisaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnMostrarSoma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMostarSubtração)
                        .addGap(18, 18, 18)
                        .addComponent(btnMostrarMultiplicacao)
                        .addGap(18, 18, 18)
                        .addComponent(btnMostrarDivisao))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(lblNumero2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNumero2))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(lblNumero1)
                            .addGap(18, 18, 18)
                            .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumero1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumero2)
                    .addComponent(txtNumero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMostrarSoma)
                    .addComponent(btnMostarSubtração)
                    .addComponent(btnMostrarMultiplicacao)
                    .addComponent(btnMostrarDivisao))
                .addGap(18, 18, 18)
                .addComponent(lblResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumero1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumero1ActionPerformed

    private void btnMostrarSomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarSomaActionPerformed
        lblResultado.setText(String.valueOf((Float.parseFloat(txtNumero1.getText()) + Float.parseFloat(txtNumero1.getText()))));
    }//GEN-LAST:event_btnMostrarSomaActionPerformed

    private void txtNumero2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumero2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumero2ActionPerformed

    private void btnMostarSubtraçãoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostarSubtraçãoActionPerformed
        lblResultado.setText(String.valueOf(Float.parseFloat(txtNumero1.getText()) - Float.parseFloat(txtNumero1.getText())));
    }//GEN-LAST:event_btnMostarSubtraçãoActionPerformed

    private void btnMostrarMultiplicacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarMultiplicacaoActionPerformed
        lblResultado.setText(String.valueOf(Float.parseFloat(txtNumero1.getText()) * Float.parseFloat(txtNumero1.getText())));
    }//GEN-LAST:event_btnMostrarMultiplicacaoActionPerformed

    private void btnMostrarDivisaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarDivisaoActionPerformed
        lblResultado.setText(String.valueOf(Float.parseFloat(txtNumero1.getText()) / Float.parseFloat(txtNumero1.getText())));
    }//GEN-LAST:event_btnMostrarDivisaoActionPerformed

   
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
            java.util.logging.Logger.getLogger(FormCalculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormCalculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormCalculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormCalculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormCalculadora().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMostarSubtração;
    private javax.swing.JButton btnMostrarDivisao;
    private javax.swing.JButton btnMostrarMultiplicacao;
    private javax.swing.JButton btnMostrarSoma;
    private javax.swing.JLabel lblNumero1;
    private javax.swing.JLabel lblNumero2;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JTextField txtNumero1;
    private javax.swing.JTextField txtNumero2;
    // End of variables declaration//GEN-END:variables
}
