package com.sd.prj_mvc_view;

import com.sd.prj_mvc_controller.OrcamentoController;
import com.sd.prj_mvc_model.Orcamento;
import com.sd.prj_mvc_observer.OrcamentoObserver;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class OrcamentoView extends JFrame implements OrcamentoObserver {
    private OrcamentoController controller;
    
    private JTextField descricaoField;
    private JTextField valorField;
    private JTextField dataInicioField;
    private JTextField dataFimField;
    private JComboBox<String> statusCombo;
    private JTable orcamentoTable;
    private DefaultTableModel tableModel;
    private SimpleDateFormat dateFormat;
    private Long orcamentoEmEdicaoId;
    
    @Override
    public void onOrcamentoChanged(Orcamento orcamento) {
        loadOrcamentos();
    }
    
    public OrcamentoView() {
        controller = new OrcamentoController();
        controller.adicionarObserver(this);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        initComponents();
        loadOrcamentos();
    }
    
    private void initComponents() {
        setTitle("Gestão de Orçamentos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("Descrição:"));
        descricaoField = new JTextField();
        formPanel.add(descricaoField);
        
        formPanel.add(new JLabel("Valor Total:"));
        valorField = new JTextField();
        formPanel.add(valorField);
        
        formPanel.add(new JLabel("Data Início (dd/mm/aaaa):"));
        dataInicioField = new JTextField();
        formPanel.add(dataInicioField);
        
        formPanel.add(new JLabel("Data Fim (dd/mm/aaaa):"));
        dataFimField = new JTextField();
        formPanel.add(dataFimField);
        
        formPanel.add(new JLabel("Status:"));
        String[] statusOptions = {"Em Andamento", "Concluído", "Cancelado"};
        statusCombo = new JComboBox<>(statusOptions);
        formPanel.add(statusCombo);
        
        // Botão de salvar
        JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> salvarOrcamento());
        savePanel.add(saveButton);
        
        // Painel superior combinando formulário e botão salvar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(savePanel, BorderLayout.SOUTH);
        
        // Tabela
        String[] columns = {"ID", "Descrição", "Valor Total", "Data Início", "Data Fim", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        orcamentoTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orcamentoTable);
        
        // Painel de botões da tabela
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");
        JButton clearButton = new JButton("Limpar");
        
        editButton.addActionListener(e -> editarOrcamento());
        deleteButton.addActionListener(e -> excluirOrcamento());
        clearButton.addActionListener(e -> limparCampos());
        
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        // Adiciona componentes ao frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadOrcamentos() {
        tableModel.setRowCount(0);
        List<Orcamento> orcamentos = controller.listarOrcamentos();
        for (Orcamento orcamento : orcamentos) {
            tableModel.addRow(new Object[]{
                orcamento.getId(),
                orcamento.getDescricao(),
                orcamento.getValorTotal(),
                dateFormat.format(orcamento.getDataInicio()),
                dateFormat.format(orcamento.getDataFim()),
                orcamento.getStatus()
            });
        }
    }
    
    private void salvarOrcamento() {
        try {
            String descricao = descricaoField.getText().trim();
            String valorStr = valorField.getText().trim();
            String dataInicioStr = dataInicioField.getText().trim();
            String dataFimStr = dataFimField.getText().trim();
            String status = (String) statusCombo.getSelectedItem();
            
            // Validações
            if (descricao.isEmpty() || valorStr.isEmpty() || dataInicioStr.isEmpty() || dataFimStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos");
                return;
            }
            
            BigDecimal valor;
            try {
                valor = new BigDecimal(valorStr.replace(",", "."));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido");
                return;
            }
            
            Date dataInicio = dateFormat.parse(dataInicioStr);
            Date dataFim = dateFormat.parse(dataFimStr);
            
            if (dataFim.before(dataInicio)) {
                JOptionPane.showMessageDialog(this, "A data fim não pode ser anterior à data início");
                return;
            }
            
            Orcamento orcamento = new Orcamento();
            if (orcamentoEmEdicaoId != null) {
                orcamento.setId(orcamentoEmEdicaoId);  // Define o ID se estiver editando
            }
            orcamento.setDescricao(descricao);
            orcamento.setValorTotal(valor);
            orcamento.setDataInicio(dataInicio);
            orcamento.setDataFim(dataFim);
            orcamento.setStatus(status);

            controller.salvarOrcamento(orcamento);
            orcamentoEmEdicaoId = null;  // Limpa o ID após salvar
            loadOrcamentos();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Orçamento salvo com sucesso!");

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/mm/aaaa");
        }
    }
    
    private void editarOrcamento() {
        int selectedRow = orcamentoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um orçamento para editar");
            return;
        }

        orcamentoEmEdicaoId = (Long) tableModel.getValueAt(selectedRow, 0);  // Salva o ID
        descricaoField.setText((String) tableModel.getValueAt(selectedRow, 1));
        valorField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
        dataInicioField.setText((String) tableModel.getValueAt(selectedRow, 3));
        dataFimField.setText((String) tableModel.getValueAt(selectedRow, 4));
        statusCombo.setSelectedItem(tableModel.getValueAt(selectedRow, 5));
    }
    
    private void excluirOrcamento() {
        int selectedRow = orcamentoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um orçamento para excluir");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir este orçamento?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            controller.excluirOrcamento(id);
            loadOrcamentos();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Orçamento excluído com sucesso!");
        }
    }
    
    private void limparCampos() {
        orcamentoEmEdicaoId = null;
        descricaoField.setText("");
        valorField.setText("");
        dataInicioField.setText("");
        dataFimField.setText("");
        statusCombo.setSelectedIndex(0);
        orcamentoTable.clearSelection();
    }
    
}
