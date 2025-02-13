package com.sd.prj_mvc_view;

import com.sd.prj_mvc_controller.UsuarioController;
import com.sd.prj_mvc_model.Usuario;
import com.sd.prj_mvc_observer.UsuarioObserver;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UsuarioView extends JFrame implements UsuarioObserver {
    private UsuarioController controller;
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JComboBox<String> papelComboBox;
    private JTable usuarioTable;
    private DefaultTableModel tableModel;

    public UsuarioView() {
        controller = new UsuarioController();
        controller.adicionarObserver(this);
        initComponents();
        loadUsuarios();
    }
    
    @Override
    public void onUsuarioChanged(Usuario usuario) {
        loadUsuarios();
    }

    private void initComponents() {
        setTitle("Gestão de Usuários");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        // Painel de formulário
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField(20);
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        formPanel.add(emailField);

        formPanel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField(15);
        formPanel.add(senhaField);

        formPanel.add(new JLabel("Papel:"));
        papelComboBox = new JComboBox<>(new String[]{"Usuário", "Administrador"});
        formPanel.add(papelComboBox);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> salvarUsuario());
        formPanel.add(saveButton);

        // Tabela
        String[] columns = {"ID", "Nome", "Email", "Papel"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        usuarioTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(usuarioTable);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");

        editButton.addActionListener(e -> editarUsuario());
        deleteButton.addActionListener(e -> excluirUsuario());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Adiciona componentes ao frame
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadUsuarios() {
        tableModel.setRowCount(0);
        List<Usuario> usuarios = controller.listarUsuarios();
        for (Usuario usuario : usuarios) {
            tableModel.addRow(new Object[]{
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPapel()
            });
        }
    }

    private void salvarUsuario() {
        try {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            String senha = new String(senhaField.getPassword());
            String papel = (String) papelComboBox.getSelectedItem();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setPapel(papel);

            controller.salvarUsuario(usuario);
            limparCampos();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar usuário: " + e.getMessage());
        }
    }

    private void editarUsuario() {
        int selectedRow = usuarioTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um usuário para editar.");
            return;
        }

        try {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            String nome = (String) tableModel.getValueAt(selectedRow, 1);
            String email = (String) tableModel.getValueAt(selectedRow, 2);
            String papel = (String) tableModel.getValueAt(selectedRow, 3);

            String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome:", nome);
            String novoEmail = JOptionPane.showInputDialog(this, "Digite o novo email:", email);
            String novaSenha = JOptionPane.showInputDialog(this, "Digite a nova senha (deixe em branco para manter a atual):");

            if (novoNome != null && novoEmail != null) {
                Usuario usuario = new Usuario();
                usuario.setId(id);
                usuario.setNome(novoNome);
                usuario.setEmail(novoEmail);
                usuario.setPapel(papel);
                
                if (novaSenha != null && !novaSenha.trim().isEmpty()) {
                    usuario.setSenha(novaSenha);
                } else {
                    // Mantém a senha atual
                    Usuario usuarioAtual = controller.buscarUsuario(id);
                    usuario.setSenha(usuarioAtual.getSenha());
                }

                controller.salvarUsuario(usuario);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao editar usuário: " + e.getMessage());
        }
    }

    private void excluirUsuario() {
        int selectedRow = usuarioTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um usuário para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este usuário?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            controller.excluirUsuario(id);
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        emailField.setText("");
        senhaField.setText("");
        papelComboBox.setSelectedIndex(0);
    }
}