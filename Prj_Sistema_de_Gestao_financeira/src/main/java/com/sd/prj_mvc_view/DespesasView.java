package com.sd.prj_mvc_view;

import com.sd.prj_mvc_controller.DespesasController;
import com.sd.prj_mvc_model.Despesas;
import com.sd.prj_mvc_observer.DespesasObserver;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class DespesasView extends JFrame implements DespesasObserver {
    private DespesasController controller;
    private JTextField descricaoField;
    private JTextField valorField;
    private JTextField dataField;
    private JComboBox<String> categoriaComboBox;
    private JComboBox<String> orcamentoComboBox;
    private JTable despesaTable;
    private DefaultTableModel tableModel;
    private DateTimeFormatter dateTimeFormatter;

    public DespesasView() {
        controller = new DespesasController();
        controller.adicionarObserver(this);
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        initComponents();
        loadDespesas();
        loadCategorias();
        loadOrcamentos();
    }

    @Override
    public void onDespesaChanged(Despesas despesa) {
        loadDespesas();
    }

    private void initComponents() {
        setTitle("Gestão de Despesas");
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

        formPanel.add(new JLabel("Categoria:"));
        categoriaComboBox = new JComboBox<>();
        formPanel.add(categoriaComboBox);

        formPanel.add(new JLabel("Orçamento:"));
        orcamentoComboBox = new JComboBox<>();
        formPanel.add(orcamentoComboBox);

        formPanel.add(new JLabel("Data:"));
        dataField = new JTextField(10);  
        formPanel.add(dataField);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> salvarDespesa());
        formPanel.add(saveButton);

        // Tabela
        String[] columns = {"ID", "Descrição", "Valor", "Data", "Categoria", "Orçamento"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        despesaTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(despesaTable);

        // Painel de botões da tabela
        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");

        editButton.addActionListener(e -> editarDespesa());
        deleteButton.addActionListener(e -> excluirDespesa());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Adiciona componentes ao frame
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadDespesas() {
        tableModel.setRowCount(0);
        List<Despesas> despesas = controller.listarDespesas();
        for (Despesas despesa : despesas) {
            tableModel.addRow(new Object[]{
                despesa.getId(),
                despesa.getDescricao(),
                despesa.getValor(),
                despesa.getData().format(dateTimeFormatter), // Use DateTimeFormatter instead of DateFormat
                despesa.getCategoriaId(),
                despesa.getOrcamentoId()
            });
        }
    }

    private void loadCategorias() {
        List<String> categorias = controller.listarCategorias();
        categoriaComboBox.removeAllItems();
        for (String categoria : categorias) {
            categoriaComboBox.addItem(categoria);
        }
    }

    private void loadOrcamentos() {
        List<String> orcamentos = controller.listarOrcamentos();
        orcamentoComboBox.removeAllItems();
        for (String orcamento : orcamentos) {
            orcamentoComboBox.addItem(orcamento);
        }
    }

    private void salvarDespesa() {
        String descricao = descricaoField.getText().trim();
        String valorTexto = valorField.getText().trim();
        String categoria = (String) categoriaComboBox.getSelectedItem();
        String orcamento = (String) orcamentoComboBox.getSelectedItem();
        String dataTexto = dataField.getText().trim();

        if (descricao.isEmpty() || valorTexto.isEmpty() || categoria == null || orcamento == null || dataTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            return;
        }

        try {
            BigDecimal valor = new BigDecimal(valorTexto.replace(",", "."));
            Despesas despesa = new Despesas();
            despesa.setDescricao(descricao);
            despesa.setValor(valor);

            // Parse the date string directly to LocalDate
            LocalDate data = LocalDate.parse(dataTexto, dateTimeFormatter);
            despesa.setData(data);
            
            despesa.setCategoriaId(controller.obterCategoriaId(categoria));
            despesa.setOrcamentoId(controller.obterOrcamentoId(orcamento));

            if (!controller.salvarDespesa(despesa)) {
                JOptionPane.showMessageDialog(this, "O valor da despesa ultrapassa o orçamento disponível.");
            } else {
                loadDespesas();
                descricaoField.setText("");
                valorField.setText("");
                dataField.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O valor deve ser um número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy");
        }
    }
   
    private void editarDespesa() {
        int selectedRow = despesaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma despesa para editar.");
            return;
        }

        try {
            // Obter valores da linha selecionada
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            String descricao = (String) tableModel.getValueAt(selectedRow, 1);
            BigDecimal valorAtual = (BigDecimal) tableModel.getValueAt(selectedRow, 2);
            String dataAtual = (String) tableModel.getValueAt(selectedRow, 3);
            Long categoriaId = (Long) tableModel.getValueAt(selectedRow, 4);
            Long orcamentoId = (Long) tableModel.getValueAt(selectedRow, 5);

            // Solicitar novos valores
            String novaDescricao = JOptionPane.showInputDialog(this, "Digite a nova descrição:", descricao);
            String novoValorTexto = JOptionPane.showInputDialog(this, "Digite o novo valor:", valorAtual.toString());

            if (novaDescricao != null && !novaDescricao.trim().isEmpty() && novoValorTexto != null) {
                try {
                    // Criar nova despesa com os valores atualizados
                    Despesas despesa = new Despesas();
                    despesa.setId(id);
                    despesa.setDescricao(novaDescricao);
                    
                    // Converter o valor para BigDecimal, tratando tanto vírgula quanto ponto
                    BigDecimal novoValor = new BigDecimal(novoValorTexto.replace(",", "."));
                    despesa.setValor(novoValor);
                    
                    // Manter a data atual
                    LocalDate data = LocalDate.parse(dataAtual, dateTimeFormatter);
                    despesa.setData(data);
                    
                    // Manter categoria e orçamento
                    despesa.setCategoriaId(categoriaId);
                    despesa.setOrcamentoId(orcamentoId);

                    if (!controller.salvarDespesa(despesa)) {
                        JOptionPane.showMessageDialog(this, "O valor da despesa ultrapassa o orçamento disponível.");
                    } else {
                        loadDespesas();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "O valor deve ser um número válido.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao editar despesa: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar dados da despesa: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void excluirDespesa() {
        int selectedRow = despesaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma despesa para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir esta despesa?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            controller.excluirDespesa(id);
            loadDespesas();
        }
    }
}
