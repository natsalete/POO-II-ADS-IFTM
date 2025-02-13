package com.sd.prj_mvc_view;

import com.sd.prj_mvc_controller.ItemOrcamentoController;
import com.sd.prj_mvc_model.ItemOrcamentos;
import com.sd.prj_mvc_observer.ItemOrcamentoObserver;
import java.math.BigDecimal;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ItemOrcamentoView extends JFrame implements ItemOrcamentoObserver {
    private ItemOrcamentoController controller;
    private JTextField descricaoField;
    private JTextField quantidadeField;
    private JTextField valorUnitarioField;
    private JComboBox<String> orcamentoComboBox;
    private JTable itemTable;
    private DefaultTableModel tableModel;

    public ItemOrcamentoView() {
        controller = new ItemOrcamentoController();
        controller.adicionarObserver(this);
        initComponents();
        loadItens();
        loadOrcamentos();
    }
    
    public void onItemOrcamentoChanged(ItemOrcamentos item) {
        loadItens();
    }

    private void initComponents() {
        setTitle("Gestão de Itens do Orçamento");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        formPanel.add(new JLabel("Descrição:"));
        descricaoField = new JTextField(15);
        formPanel.add(descricaoField);

        formPanel.add(new JLabel("Quantidade:"));
        quantidadeField = new JTextField(5);
        formPanel.add(quantidadeField);

        formPanel.add(new JLabel("Valor Unitário:"));
        valorUnitarioField = new JTextField(10);
        formPanel.add(valorUnitarioField);

        formPanel.add(new JLabel("Orçamento:"));
        orcamentoComboBox = new JComboBox<>();
        formPanel.add(orcamentoComboBox);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> salvarItem());
        formPanel.add(saveButton);

        // Tabela
        String[] columns = {"ID", "Descrição", "Quantidade", "Valor Unitário", "Valor Total", "Orçamento"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(itemTable);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");

        editButton.addActionListener(e -> editarItem());
        deleteButton.addActionListener(e -> excluirItem());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Adiciona componentes ao frame
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadItens() {
        tableModel.setRowCount(0);
        List<ItemOrcamentos> itens = controller.listarItens();
        for (ItemOrcamentos item : itens) {
            tableModel.addRow(new Object[]{
                item.getId(),
                item.getDescricao(),
                item.getQuantidade(),
                item.getValorUnitario(),
                item.getValorTotal(),
                item.getOrcamentoId()
            });
        }
    }

    private void loadOrcamentos() {
        List<String> orcamentos = controller.listarOrcamentos();
        orcamentoComboBox.removeAllItems();
        for (String orcamento : orcamentos) {
            orcamentoComboBox.addItem(orcamento);
        }
    }

    private void salvarItem() {
        try {
            String descricao = descricaoField.getText().trim();
            int quantidade = Integer.parseInt(quantidadeField.getText().trim());
            BigDecimal valorUnitario = new BigDecimal(valorUnitarioField.getText().trim().replace(",", "."));
            String orcamento = (String) orcamentoComboBox.getSelectedItem();

            if (descricao.isEmpty() || orcamento == null) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
                return;
            }

            ItemOrcamentos item = new ItemOrcamentos();
            item.setDescricao(descricao);
            item.setQuantidade(quantidade);
            item.setValorUnitario(valorUnitario);
            item.setOrcamentoId(controller.obterOrcamentoId(orcamento));

            controller.salvarItem(item);
            limparCampos();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar item: " + e.getMessage());
        }
    }

    private void editarItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item para editar.");
            return;
        }

        try {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            String descricao = (String) tableModel.getValueAt(selectedRow, 1);
            Integer quantidade = (Integer) tableModel.getValueAt(selectedRow, 2);
            BigDecimal valorUnitario = (BigDecimal) tableModel.getValueAt(selectedRow, 3);

            String novaDescricao = JOptionPane.showInputDialog(this, "Digite a nova descrição:", descricao);
            String novaQuantidadeStr = JOptionPane.showInputDialog(this, "Digite a nova quantidade:", quantidade);
            String novoValorStr = JOptionPane.showInputDialog(this, "Digite o novo valor unitário:", valorUnitario);

            if (novaDescricao != null && novaQuantidadeStr != null && novoValorStr != null) {
                ItemOrcamentos item = new ItemOrcamentos();
                item.setId(id);
                item.setDescricao(novaDescricao);
                item.setQuantidade(Integer.parseInt(novaQuantidadeStr));
                item.setValorUnitario(new BigDecimal(novoValorStr.replace(",", ".")));
                item.setOrcamentoId((Long) tableModel.getValueAt(selectedRow, 5));

                controller.salvarItem(item);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao editar item: " + e.getMessage());
        }
    }

    private void excluirItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este item?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            controller.excluirItem(id);
        }
    }

    private void limparCampos() {
        descricaoField.setText("");
        quantidadeField.setText("");
        valorUnitarioField.setText("");
    }
}

