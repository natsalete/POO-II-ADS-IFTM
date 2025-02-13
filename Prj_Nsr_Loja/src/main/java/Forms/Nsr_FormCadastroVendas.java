package Forms;

import BO.Nsr_ClienteBO;
import BO.Nsr_ProdutoBO;
import BO.Nsr_VendaBO;
import BO.Nsr_Venda_ProdutoBO;
import Objetos.Nsr_Cliente;
import Objetos.Nsr_Produto;
import Objetos.Nsr_Venda;
import Objetos.Nsr_Venda_Produto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author natsa
 */
public class Nsr_FormCadastroVendas extends javax.swing.JFrame {

    Nsr_Venda_ProdutoBO nsr_vpBo = new Nsr_Venda_ProdutoBO();
    Nsr_VendaBO nsr_vBO = new Nsr_VendaBO();
    Nsr_ProdutoBO nsr_pBO = new Nsr_ProdutoBO();
    private List<Nsr_Venda_Produto> nsr_lstVendaProdutos;
    private List<Nsr_Cliente> nsr_lstClientes;
    private List<Nsr_Venda> nsr_lstVendas;
    private List<Nsr_Produto> nsr_lstProdutos;

    private void Nsr_preencherCampos(Nsr_Venda_Produto nsr_venda_produto) {

        nsr_txtIdVenda.setText(String.valueOf(nsr_venda_produto.getNsr_Venda().getNsr_idVenda()));
        nsr_lstVendas = new ArrayList<>();
        nsr_lstVendas.add(nsr_venda_produto.getNsr_Venda());
        nsr_cmbClientes.addItem(nsr_venda_produto.getNsr_Venda().getNsr_cliente().getNsr_nome() + " | " + nsr_venda_produto.getNsr_Venda().getNsr_cliente().getNsr_idCliente());
        nsr_lstProdutos = new ArrayList<>();
        nsr_lstProdutos.add(nsr_venda_produto.getNsr_produto());
        nsr_cmbProdutos.addItem(nsr_venda_produto.getNsr_produto().getNsr_idProduto() + " - " + nsr_venda_produto.getNsr_produto().getNsr_nome() + " | " + nsr_venda_produto.getNsr_produto().getNsr_modelo() + " | " + nsr_venda_produto.getNsr_produto().getNsr_valor());
        nsr_txtQuantidade.setValue(String.valueOf(nsr_venda_produto.getNsr_quantidade()));
        nsr_txtDesconto.setText(String.valueOf(nsr_venda_produto.getNsr_desconto_item()));
        nsr_btnSalvar.setEnabled(false);
    }

    private void Nsr_preencherTabela(List<Nsr_Venda_Produto> nsr_lstVendaProdutos) {
        DefaultTableModel nsr_tabelaVendaProdutos = (DefaultTableModel) tabProdutos.getModel();
        tabProdutos.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabProdutos.getColumnModel().getColumn(1).setPreferredWidth(60);
        tabProdutos.getColumnModel().getColumn(2).setPreferredWidth(60);
        tabProdutos.getColumnModel().getColumn(3).setPreferredWidth(60);
        tabProdutos.getColumnModel().getColumn(4).setPreferredWidth(60);
        nsr_tabelaVendaProdutos.setNumRows(0);

        Nsr_calcularItem();

        nsr_lstVendaProdutos.forEach(nsr_itemProdutoVenda -> {
            // Adiciona as linhas na tabela com as informações do produto, quantidade, valor, desconto e total
            nsr_tabelaVendaProdutos.addRow(new Object[]{
                nsr_itemProdutoVenda.getNsr_produto().getNsr_nome(),
                nsr_itemProdutoVenda.getNsr_quantidade(),
                nsr_itemProdutoVenda.getNsr_produto().getNsr_valor(),
                nsr_itemProdutoVenda.getNsr_desconto_item(),
                nsr_itemProdutoVenda.getNsr_total_item()
            });
        });

        Nsr_calcularTotalVenda();
    }

    private void Nsr_calcularItem() {
        double nsr_totalItem;
        for (int i = 0; i < nsr_lstVendaProdutos.size(); i++) {
            Nsr_Venda_Produto item = nsr_lstVendaProdutos.get(i);
            nsr_totalItem = (item.getNsr_produto().getNsr_valor() * item.getNsr_quantidade()) - item.getNsr_desconto_item();
            item.setNsr_total_item(nsr_totalItem);
        }
    }

    private void Nsr_calcularTotalVenda() {
        double nsr_totalVenda = 0;
        for (int i = 0; i < nsr_lstVendaProdutos.size(); i++) {
            nsr_totalVenda += nsr_lstVendaProdutos.get(i).getNsr_total_item();
        }
        nsr_lblTotalItem.setText(String.format("R$ %.2f", nsr_totalVenda));
    }

    private void Nsr_preencherComboCliente() {
        String nome = nsr_txtNomeCliente.getText();
        if (!nome.isEmpty()) {
            nsr_lstClientes = nsr_vBO.getNsr_Vendas(nome);
            nsr_cmbClientes.removeAllItems();

            nsr_lstClientes.forEach(nsr_itemCliente -> {
                nsr_cmbClientes.addItem(nsr_itemCliente.getNsr_nome() + " | " + nsr_itemCliente.getNsr_idCliente());
            });
        } else {
            nsr_cmbClientes.removeAllItems();
        }
    }

    private void Nsr_preencherComboProduto() {
        String nome = nsr_txtNomeProduto.getText();
        if (!nome.isEmpty()) {
            nsr_lstProdutos = nsr_pBO.getNsr_Produtos(nome);
            nsr_cmbProdutos.removeAllItems();
            nsr_lstProdutos.forEach(nsr_itemProduto -> {
                nsr_cmbProdutos.addItem(nsr_itemProduto.getNsr_idProduto() + " - " + nsr_itemProduto.getNsr_nome() + " | " + nsr_itemProduto.getNsr_modelo() + " | " + nsr_itemProduto.getNsr_valor());
            });
        } else {
            nsr_cmbProdutos.removeAllItems();
        }
    }

    private void Nsr_novo() {
        nsr_txtIdVenda.setText("");
        nsr_txtNomeCliente.setText("");
        nsr_cmbClientes.removeAllItems();
        nsr_txtNomeProduto.setText("");
        nsr_cmbProdutos.removeAllItems();
        nsr_lstVendaProdutos = new ArrayList<>();
        nsr_txtQuantidade.setValue(0);
        nsr_txtDesconto.setText("");

        DefaultTableModel model = (DefaultTableModel) tabProdutos.getModel();
        model.setRowCount(0);

        nsr_lblTotalItem.setText("");

        nsr_btnSalvar.setEnabled(true);
    }

    public Nsr_FormCadastroVendas() {
        initComponents();
        nsr_lstVendaProdutos = new ArrayList<>();
        nsr_lstClientes = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        nsr_txtIdVenda = new javax.swing.JTextField();
        nsr_txtDesconto = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        nsr_cmbProdutos = new javax.swing.JComboBox<>();
        nsr_txtNomeCliente = new javax.swing.JTextField();
        nsr_txtNomeProduto = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        nsr_cmbClientes = new javax.swing.JComboBox<>();
        nsr_txtQuantidade = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabProdutos = new javax.swing.JTable();
        nsr_btnAdicionarProduto = new javax.swing.JButton();
        nsr_lblTotalItem = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        nsr_btnSalvar = new javax.swing.JButton();
        nsr_btnSair = new javax.swing.JButton();
        nsr_btnNovo1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CADASTRO DE VENDAS");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados para cadastro"));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setText("Código Venda:");

        jLabel10.setText("Total da Venda:");

        jLabel8.setText("Produtos:");

        jLabel11.setText("Quantidade:");

        nsr_txtIdVenda.setEditable(false);

        jLabel27.setText("Nome Cliente:");

        jLabel26.setText("Clientes:");

        nsr_txtNomeCliente.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNomeClienteCaretUpdate(evt);
            }
        });

        nsr_txtNomeProduto.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                nsr_txtNomeProdutoCaretUpdate(evt);
            }
        });

        jLabel28.setText("Nome Produto:");

        jLabel12.setText("Desconto:");

        tabProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Produto", "Quantidade", "Valor", "Desconto", "Total"
            }
        ));
        tabProdutos.setShowGrid(true);
        jScrollPane1.setViewportView(tabProdutos);

        nsr_btnAdicionarProduto.setText("Adicionar Produto");
        nsr_btnAdicionarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsr_btnAdicionarProdutoActionPerformed(evt);
            }
        });

        nsr_lblTotalItem.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel27)
                                .addComponent(jLabel11)
                                .addComponent(jLabel8)
                                .addComponent(jLabel4)
                                .addComponent(jLabel26)
                                .addComponent(jLabel12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(nsr_txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nsr_txtIdVenda)
                                    .addComponent(nsr_cmbProdutos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nsr_txtNomeCliente)
                                    .addComponent(nsr_cmbClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nsr_txtDesconto)
                                    .addComponent(nsr_txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(nsr_btnAdicionarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nsr_lblTotalItem, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(nsr_txtIdVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nsr_txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(nsr_cmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nsr_txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(nsr_cmbProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(nsr_txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nsr_txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nsr_btnAdicionarProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(nsr_lblTotalItem))
                .addGap(105, 105, 105))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        nsr_btnSalvar.setText("Salvar");
        nsr_btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsr_btnSalvarActionPerformed(evt);
            }
        });

        nsr_btnSair.setText("Sair");
        nsr_btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsr_btnSairActionPerformed(evt);
            }
        });

        nsr_btnNovo1.setText("Novo");
        nsr_btnNovo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsr_btnNovo1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(nsr_btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(nsr_btnNovo1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(nsr_btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(194, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nsr_btnSalvar)
                    .addComponent(nsr_btnSair)
                    .addComponent(nsr_btnNovo1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nsr_btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsr_btnSalvarActionPerformed
        try {
            if (nsr_lstVendaProdutos == null || nsr_lstVendaProdutos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Adicione pelo menos um produto à venda.");
                return;
            }

            Nsr_Venda novaVenda = new Nsr_Venda();
            novaVenda.setNsr_cliente(nsr_lstVendaProdutos.get(0).getNsr_Venda().getNsr_cliente());

            double totalVenda = 0;
            for (Nsr_Venda_Produto item : nsr_lstVendaProdutos) {
                totalVenda += item.getNsr_total_item();
                item.setNsr_Venda(novaVenda);
            }
            novaVenda.setNsr_total_venda(totalVenda);

            List<Nsr_Venda_Produto> savedItems = nsr_vpBo.Nsr_salvar(nsr_lstVendaProdutos);

            if (!savedItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Venda salva com sucesso!");
                Nsr_novo(); // Reset the form
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a venda.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            e.printStackTrace();
        }

    }//GEN-LAST:event_nsr_btnSalvarActionPerformed

    private void nsr_btnAdicionarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsr_btnAdicionarProdutoActionPerformed

        try {
            if (nsr_lstClientes == null || nsr_lstClientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado. Por favor, pesquise um cliente primeiro.");
                return;
            }

            if (nsr_cmbClientes.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um cliente.");
                return;
            }

            if (nsr_cmbProdutos.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um produto.");
                return;
            }

            Nsr_Venda_Produto nsr_venda_produto = new Nsr_Venda_Produto();

            Nsr_Cliente clienteSelecionado = nsr_lstClientes.get(nsr_cmbClientes.getSelectedIndex());
            Nsr_Produto produtoSelecionado = nsr_lstProdutos.get(nsr_cmbProdutos.getSelectedIndex());

            double quantidade = ((Number) nsr_txtQuantidade.getValue()).doubleValue();
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero.");
                return;
            }
            double desconto = Double.parseDouble(nsr_txtDesconto.getText());

            Nsr_Venda novaVenda = new Nsr_Venda();

            novaVenda.setNsr_cliente(clienteSelecionado);

            nsr_venda_produto.setNsr_Venda(novaVenda);

            nsr_venda_produto.setNsr_Venda(novaVenda);
            nsr_venda_produto.setNsr_produto(produtoSelecionado);
            nsr_venda_produto.setNsr_quantidade(quantidade);
            nsr_venda_produto.setNsr_desconto_item(desconto);

            nsr_lstVendaProdutos.add(nsr_venda_produto);

            Nsr_preencherTabela(nsr_lstVendaProdutos);

            nsr_txtNomeProduto.setText("");
            nsr_txtQuantidade.setValue(0);
            nsr_txtDesconto.setText("");

            // Recalcula o total da venda
            Nsr_calcularTotalVenda();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar o produto: " + e.getMessage());
            e.printStackTrace();
        }

    }//GEN-LAST:event_nsr_btnAdicionarProdutoActionPerformed

    private void nsr_btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsr_btnSairActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_nsr_btnSairActionPerformed

    private void txtNomeClienteCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNomeClienteCaretUpdate
        // TODO add your handling code here:
        Nsr_preencherComboCliente();
    }//GEN-LAST:event_txtNomeClienteCaretUpdate

    private void nsr_txtNomeProdutoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_nsr_txtNomeProdutoCaretUpdate
        // TODO add your handling code here:
        Nsr_preencherComboProduto();
    }//GEN-LAST:event_nsr_txtNomeProdutoCaretUpdate

    private void nsr_btnNovo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsr_btnNovo1ActionPerformed
        // TODO add your handling code here:
        Nsr_novo();
    }//GEN-LAST:event_nsr_btnNovo1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Nsr_FormCadastroVendas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton nsr_btnAdicionarProduto;
    private javax.swing.JButton nsr_btnNovo1;
    private javax.swing.JButton nsr_btnSair;
    private javax.swing.JButton nsr_btnSalvar;
    private javax.swing.JComboBox<String> nsr_cmbClientes;
    private javax.swing.JComboBox<String> nsr_cmbProdutos;
    private javax.swing.JLabel nsr_lblTotalItem;
    private javax.swing.JTextField nsr_txtDesconto;
    private javax.swing.JTextField nsr_txtIdVenda;
    private javax.swing.JTextField nsr_txtNomeCliente;
    private javax.swing.JTextField nsr_txtNomeProduto;
    private javax.swing.JSpinner nsr_txtQuantidade;
    private javax.swing.JTable tabProdutos;
    // End of variables declaration//GEN-END:variables
}
