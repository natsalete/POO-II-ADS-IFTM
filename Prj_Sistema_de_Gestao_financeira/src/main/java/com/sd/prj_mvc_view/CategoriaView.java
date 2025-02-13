package com.sd.prj_mvc_view;


import com.sd.prj_mvc_controller.CategoriaController;
import com.sd.prj_mvc_model.Categoria;
import com.sd.prj_mvc_observer.CategoriaObserver;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class CategoriaView extends JFrame implements CategoriaObserver{
    private CategoriaController controller;
    private JTextField nomeField;
    private JTable categoriaTable;
    private DefaultTableModel tableModel;
    
    public CategoriaView() {
        controller = new CategoriaController();
        controller.adicionarObserver(this);
        initComponents();
        loadCategorias();
    }
    
    public void onCategoriaChanged(Categoria categoria) {
        // Atualiza a view quando uma categoria é modificada
        loadCategorias();
    }
    
    
    private void initComponents() {
        setTitle("Gestão de Categorias");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField(20);
        formPanel.add(nomeField);
        
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> salvarCategoria());
        formPanel.add(saveButton);
        
        // Tabela
        String[] columns = {"ID", "Nome"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        categoriaTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(categoriaTable);
        
        // Painel de botões da tabela
        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");
        
        editButton.addActionListener(e -> editarCategoria());
        deleteButton.addActionListener(e -> excluirCategoria());
        
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        // Adiciona componentes ao frame
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadCategorias() {
        tableModel.setRowCount(0);
        List<Categoria> categorias = controller.listarCategorias();
        for (Categoria categoria : categorias) {
            tableModel.addRow(new Object[]{
                categoria.getId(),
                categoria.getNome()
            });
        }
    }
    
    private void salvarCategoria() {
        String nome = nomeField.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o nome da categoria");
            return;
        }
        
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        
        controller.salvarCategoria(categoria);
        loadCategorias();
        nomeField.setText("");
    }
    
    private void editarCategoria() {
        int selectedRow = categoriaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma categoria para editar");
            return;
        }
        
        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        String nome = (String) tableModel.getValueAt(selectedRow, 1);
        
        String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome da categoria:", nome);
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            Categoria categoria = new Categoria();
            categoria.setId(id);
            categoria.setNome(novoNome);
            
            controller.salvarCategoria(categoria);
            loadCategorias();
        }
    }
    
    private void excluirCategoria() {
        int selectedRow = categoriaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma categoria para excluir");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir esta categoria?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            controller.excluirCategoria(id);
            loadCategorias();
        }
    }
}
