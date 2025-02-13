package com.sd.prj_mvc_view;

import com.sd.prj_mvc_controller.FornecedoresController;
import com.sd.prj_mvc_model.Fornecedores;
import com.sd.prj_mvc_observer.FornecedoresObserver;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class FornecedoresView extends JFrame implements FornecedoresObserver {
    private FornecedoresController controller;
    private JTextField nomeField, telefoneField, emailField;
    private JTable fornecedorTable;
    private DefaultTableModel tableModel;
    
    public FornecedoresView() {
        controller = new FornecedoresController();
        controller.adicionarObserver(this);
        initComponents();
        loadFornecedores();
    }
    
    public void onFornecedorChanged(Fornecedores fornecedor) {
        // Atualiza a view quando um fornecedor é modificado
        loadFornecedores();
    }
    
    private void initComponents() {
        setTitle("Gestão de Fornecedores");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField(20);
        formPanel.add(nomeField);
        
        formPanel.add(new JLabel("Telefone:"));
        telefoneField = new JTextField(15);
        formPanel.add(telefoneField);
        
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        formPanel.add(emailField);
        
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> salvarFornecedor());
        formPanel.add(saveButton);
        
        // Tabela
        String[] columns = {"ID", "Nome", "Telefone", "Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        fornecedorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(fornecedorTable);
        
        // Painel de botões da tabela
        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");
        
        editButton.addActionListener(e -> editarFornecedor());
        deleteButton.addActionListener(e -> excluirFornecedor());
        
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        // Adiciona componentes ao frame
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadFornecedores() {
        tableModel.setRowCount(0);
        List<Fornecedores> fornecedores = controller.listarFornecedores();
        for (Fornecedores fornecedor : fornecedores) {
            tableModel.addRow(new Object[]{
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getTelefone(),
                fornecedor.getEmail()
            });
        }
    }
    
    private void salvarFornecedor() {
        String nome = nomeField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String email = emailField.getText().trim();
        
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o nome do fornecedor");
            return;
        }
        
        Fornecedores fornecedor = new Fornecedores();
        fornecedor.setNome(nome);
        fornecedor.setTelefone(telefone);
        fornecedor.setEmail(email);
        
        controller.salvarFornecedor(fornecedor);
        loadFornecedores();
        nomeField.setText("");
        telefoneField.setText("");
        emailField.setText("");
    }
    
    private void editarFornecedor() {
        int selectedRow = fornecedorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um fornecedor para editar");
            return;
        }
        
        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        String nome = (String) tableModel.getValueAt(selectedRow, 1);
        String telefone = (String) tableModel.getValueAt(selectedRow, 2);
        String email = (String) tableModel.getValueAt(selectedRow, 3);
        
        String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome do fornecedor:", nome);
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            Fornecedores fornecedor = new Fornecedores();
            fornecedor.setId(id);
            fornecedor.setNome(novoNome);
            fornecedor.setTelefone(telefone);
            fornecedor.setEmail(email);
            
            controller.salvarFornecedor(fornecedor);
            loadFornecedores();
        }
    }
    
    private void excluirFornecedor() {
        int selectedRow = fornecedorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um fornecedor para excluir");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir este fornecedor?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            controller.excluirFornecedor(id);
            loadFornecedores();
        }
    }
}

