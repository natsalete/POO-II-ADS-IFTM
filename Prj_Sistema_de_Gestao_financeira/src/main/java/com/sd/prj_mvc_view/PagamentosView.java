package com.sd.prj_mvc_view;

import com.sd.prj_mvc_controller.PagamentoController;
import com.sd.prj_mvc_model.Pagamentos;
import com.sd.prj_mvc_observer.PagamentosObserver;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PagamentosView extends JFrame implements PagamentosObserver {
    private PagamentoController controller;
    private JTextField descricaoField;
    private JTextField valorField;
    private JTextField dataField;
    private JComboBox<String> fornecedorComboBox;
    private JTable pagamentosTable;
    private DefaultTableModel tableModel;
    private DateTimeFormatter dateTimeFormatter;

    public PagamentosView() {
        controller = new PagamentoController();
        controller.adicionarObserver(this);
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        initComponents();
        loadPagamentos();
        loadFornecedores();
    }
    
    @Override
    public void onPagamentosChanged(Pagamentos pagamento) {
        loadPagamentos();
    }

    private void initComponents() {
        setTitle("Gestão de Pagamentos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("Descrição:"));
        descricaoField = new JTextField(15);
        formPanel.add(descricaoField);

        formPanel.add(new JLabel("Valor:"));
        valorField = new JTextField(10);
        formPanel.add(valorField);

        formPanel.add(new JLabel("Fornecedor:"));
        fornecedorComboBox = new JComboBox<>();
        formPanel.add(fornecedorComboBox);

        formPanel.add(new JLabel("Data:"));
        dataField = new JTextField(10);  
        formPanel.add(dataField);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> salvarPagamento());
        formPanel.add(saveButton);

        // Tabela
        String[] columns = {"ID", "Descrição", "Valor", "Data", "Fornecedor"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        pagamentosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(pagamentosTable);

        // Painel de botões da tabela
        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");

        editButton.addActionListener(e -> editarPagamento());
        deleteButton.addActionListener(e -> excluirPagamento());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Adiciona componentes ao frame
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadPagamentos() {
        tableModel.setRowCount(0);
        List<Pagamentos> pagamentos = controller.listarPagamentos();
        for (Pagamentos pagamento : pagamentos) {
            tableModel.addRow(new Object[]{
                pagamento.getId(),
                pagamento.getDescricao(),
                pagamento.getValor(),
                pagamento.getData().format(dateTimeFormatter),
                pagamento.getFornecedor()
            });
        }
    }

    private void loadFornecedores() {
        List<String> fornecedores = controller.listarFornecedores();
        fornecedorComboBox.removeAllItems();
        for (String fornecedor : fornecedores) {
            fornecedorComboBox.addItem(fornecedor);
        }
    }
    
    private Long extractFornecedorId(String fornecedorString) {
        if (fornecedorString == null || fornecedorString.isEmpty()) {
            return null;
        }
        try {
            return Long.valueOf(fornecedorString.split(" - ")[0]);
        } catch (Exception e) {
            return null;
        }
    }

    private void salvarPagamento() {
        String descricao = descricaoField.getText().trim();
        String valorTexto = valorField.getText().trim();
        String fornecedor = (String) fornecedorComboBox.getSelectedItem();
        String dataTexto = dataField.getText().trim();

        if (descricao.isEmpty() || valorTexto.isEmpty() || fornecedor == null || dataTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            return;
        }

        try {
            BigDecimal valor = new BigDecimal(valorTexto.replace(",", "."));
            Pagamentos pagamento = new Pagamentos();
            pagamento.setDescricao(descricao);
            pagamento.setValor(valor);

            // Tratamento da data simplificado, igual ao DespesasView
            LocalDate data = LocalDate.parse(dataTexto, dateTimeFormatter);
            pagamento.setData(data);

            pagamento.setFornecedor(extractFornecedorId(fornecedor));

            if (!controller.salvarPagamento(pagamento)) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar pagamento.");
            } else {
                loadPagamentos();
                descricaoField.setText("");
                valorField.setText("");
                dataField.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O valor deve ser um número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy");
            e.printStackTrace(); // Adicione temporariamente para debug
        }
    }

    private void editarPagamento() {
        int selectedRow = pagamentosTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um pagamento para editar.");
            return;
        }

        try {
            // Obter valores da linha selecionada
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            String descricao = (String) tableModel.getValueAt(selectedRow, 1);
            BigDecimal valorAtual = (BigDecimal) tableModel.getValueAt(selectedRow, 2);
            String dataAtual = (String) tableModel.getValueAt(selectedRow, 3);
            Long fornecedorId = (Long) tableModel.getValueAt(selectedRow, 4);

            // Solicitar novos valores
            String novaDescricao = JOptionPane.showInputDialog(this, "Digite a nova descrição:", descricao);
            String novoValorTexto = JOptionPane.showInputDialog(this, "Digite o novo valor:", valorAtual.toString());

            if (novaDescricao != null && !novaDescricao.trim().isEmpty() && novoValorTexto != null) {
                try {
                    // Criar novo pagamento com os valores atualizados
                    Pagamentos pagamento = new Pagamentos();
                    pagamento.setId(id);
                    pagamento.setDescricao(novaDescricao);

                    // Converter o valor para BigDecimal, tratando tanto vírgula quanto ponto
                    BigDecimal novoValor = new BigDecimal(novoValorTexto.replace(",", "."));
                    pagamento.setValor(novoValor);

                    // Manter a data atual
                    LocalDate data = LocalDate.parse(dataAtual, dateTimeFormatter);
                    pagamento.setData(data);

                    // Manter fornecedor
                    pagamento.setFornecedor(fornecedorId);

                    if (!controller.salvarPagamento(pagamento)) {
                        JOptionPane.showMessageDialog(this, "Erro ao salvar pagamento.");
                    } else {
                        loadPagamentos();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "O valor deve ser um número válido.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao editar pagamento: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar dados do pagamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void excluirPagamento() {
        int selectedRow = pagamentosTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um pagamento para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este pagamento?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            controller.excluirPagamento(id);
            loadPagamentos();
        }
    }
}
