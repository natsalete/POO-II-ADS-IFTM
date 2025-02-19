package Forms;

import BO.Nsr_Venda_ProdutoBO;
import Objetos.Nsr_Venda_Produto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author natsa
 */
public class Nsr_FormRelatVendas extends javax.swing.JFrame {

    private MaskFormatter mftDataInicio, mftDataFim;
    Nsr_Venda_ProdutoBO vpBO = new Nsr_Venda_ProdutoBO();
    private List<Nsr_Venda_Produto> nsr_lstVendaProdutos;

    private void Nsr_pesquisar() {
        List<Nsr_Venda_Produto> nsr_lstVendaProdutos;

        String dataInicio = nsr_txtDataInicial.getText().trim();
        String dataFim = nsr_txtDataFinal.getText().trim();

        if ((dataInicio.length() != 4) && (dataFim.length() != 4)) {
            nsr_lstVendaProdutos = vpBO.getNsr_Venda_Produtos(dataInicio, dataFim);
            preencherTabela(nsr_lstVendaProdutos);
            Nsr_calcularTotalVenda(nsr_lstVendaProdutos);
        }
    }

    private void Nsr_calcularTotalVenda(List<Nsr_Venda_Produto> nsr_lstVendaProdutos) {
        double nsr_totalVenda = 0;
        for (int i = 0; i < nsr_lstVendaProdutos.size(); i++) {
            nsr_totalVenda += nsr_lstVendaProdutos.get(i).getNsr_total_item();  // Soma o total de cada item
        }
        // Atualizando o total da venda no campo correspondente
        nsr_lblTotalPeriodo.setText(String.format("R$ %.2f", nsr_totalVenda));
    }

    private void preencherTabela(List<Nsr_Venda_Produto> nsr_lstVendaProdutos) {
        DefaultTableModel tabelaVendas = (DefaultTableModel) tabVendas.getModel();

        // Configuração das colunas
        tabelaVendas.setColumnIdentifiers(new String[]{
            "ID Venda", "Data Venda", "Cliente", "Quantidade",
            "Produto", "Marca", "Modelo", "Valor", "Desconto", "Total Item",});

        // Limpar tabela antes de preencher
        tabelaVendas.setRowCount(0);

        // Preencher dados na tabela
        nsr_lstVendaProdutos.forEach(nsr_itemVenda -> {
            tabelaVendas.addRow(new Object[]{
                nsr_itemVenda.getNsr_Venda().getNsr_idVenda(),
                nsr_itemVenda.getNsr_Venda().getNsr_data_venda() != null ? nsr_itemVenda.getNsr_Venda().getNsr_data_venda() : "",
                nsr_itemVenda.getNsr_Venda().getNsr_cliente() != null ? nsr_itemVenda.getNsr_Venda().getNsr_cliente().getNsr_nome() : "",
                nsr_itemVenda.getNsr_quantidade(),
                nsr_itemVenda.getNsr_produto() != null ? nsr_itemVenda.getNsr_produto().getNsr_nome() : "",
                nsr_itemVenda.getNsr_produto() != null ? nsr_itemVenda.getNsr_produto().getNsr_marca() : "",
                nsr_itemVenda.getNsr_produto() != null ? nsr_itemVenda.getNsr_produto().getNsr_modelo() : "",
                nsr_itemVenda.getNsr_produto() != null ? nsr_itemVenda.getNsr_produto().getNsr_valor() : 0,
                nsr_itemVenda.getNsr_desconto_item(),
                nsr_itemVenda.getNsr_total_item(),});
        });

        // Ajuste de largura das colunas
        tabVendas.getColumnModel().getColumn(0).setPreferredWidth(10);  // ID Venda
        tabVendas.getColumnModel().getColumn(1).setPreferredWidth(10);  // Data Venda
        tabVendas.getColumnModel().getColumn(2).setPreferredWidth(100); // Cliente
        tabVendas.getColumnModel().getColumn(3).setPreferredWidth(100); // Quantidade
        tabVendas.getColumnModel().getColumn(4).setPreferredWidth(100); // Produto
        tabVendas.getColumnModel().getColumn(5).setPreferredWidth(100); // Marca
        tabVendas.getColumnModel().getColumn(6).setPreferredWidth(100); // Modelo
        tabVendas.getColumnModel().getColumn(7).setPreferredWidth(100); // Valor
        tabVendas.getColumnModel().getColumn(8).setPreferredWidth(100); // Desconto
        tabVendas.getColumnModel().getColumn(9).setPreferredWidth(100); // Total Item
    }

    TableRowSorter<TableModel> sorter;

    public Nsr_FormRelatVendas() {
        initComponents();
        nsr_lstVendaProdutos = new ArrayList<>();
        // Instala o TableRowSorter.
        sorter = new TableRowSorter<TableModel>(tabVendas.getModel());
        tabVendas.setRowSorter(sorter);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabVendas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        try
        {
            mftDataInicio = new MaskFormatter("##/##/####");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Não foi possivel usar a mascara!" );
        }
        nsr_txtDataInicial = new JFormattedTextField(mftDataInicio);
        jLabel4 = new javax.swing.JLabel();
        try
        {
            mftDataFim = new MaskFormatter("##/##/####");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Não foi possivel usar a mascara!" );
        }
        nsr_txtDataFinal = new JFormattedTextField(mftDataFim);
        nsr_btnConsultar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        nsr_lblTotalPeriodo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RELATÓRIO DE VENDAS");

        tabVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "Data da Venda", "Cliente", "Quantidade", "Produto", "Marca", "Modelo", "Valor Item", "Desconto", "Total Item"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabVendas.setShowGrid(true);
        tabVendas.setShowHorizontalLines(true);
        tabVendas.setShowVerticalLines(true);
        jScrollPane1.setViewportView(tabVendas);
        if (tabVendas.getColumnModel().getColumnCount() > 0) {
            tabVendas.getColumnModel().getColumn(0).setResizable(false);
            tabVendas.getColumnModel().getColumn(1).setResizable(false);
            tabVendas.getColumnModel().getColumn(2).setResizable(false);
            tabVendas.getColumnModel().getColumn(3).setResizable(false);
            tabVendas.getColumnModel().getColumn(4).setResizable(false);
            tabVendas.getColumnModel().getColumn(5).setResizable(false);
            tabVendas.getColumnModel().getColumn(6).setResizable(false);
            tabVendas.getColumnModel().getColumn(7).setResizable(false);
            tabVendas.getColumnModel().getColumn(8).setResizable(false);
            tabVendas.getColumnModel().getColumn(9).setResizable(false);
            tabVendas.getColumnModel().getColumn(10).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("RELATÓRIO DE VENDAS");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar vendas"));

        jLabel3.setText("Período de:");

        jLabel4.setText("até");

        nsr_btnConsultar.setText("Consultar");
        nsr_btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsr_btnConsultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(490, 490, 490)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nsr_txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(nsr_txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(nsr_btnConsultar)))
                .addContainerGap(492, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nsr_txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(nsr_txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(nsr_btnConsultar)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel2.setText("Total Período:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nsr_lblTotalPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nsr_lblTotalPeriodo))
                .addGap(40, 40, 40))
        );

        jPanel1.getAccessibleContext().setAccessibleName("Filtrar por período");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void nsr_btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsr_btnConsultarActionPerformed
        // TODO add your handling code here:
        Nsr_pesquisar();
    }//GEN-LAST:event_nsr_btnConsultarActionPerformed

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
            java.util.logging.Logger.getLogger(Nsr_FormRelatVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Nsr_FormRelatVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Nsr_FormRelatVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Nsr_FormRelatVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Nsr_FormRelatVendas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton nsr_btnConsultar;
    private javax.swing.JLabel nsr_lblTotalPeriodo;
    private javax.swing.JTextField nsr_txtDataFinal;
    private javax.swing.JTextField nsr_txtDataInicial;
    private javax.swing.JTable tabVendas;
    // End of variables declaration//GEN-END:variables
}
