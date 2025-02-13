package com.sd.prj_mvc_view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    
    public MainView() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Sistema de Gestão Financeira");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Criando o menu
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Cadastros
        JMenu menuCadastros = new JMenu("Cadastros");
        JMenuItem menuCategoria = new JMenuItem("Categorias");
        JMenuItem menuOrcamento = new JMenuItem("Orçamentos");
        JMenuItem menuDespesa = new JMenuItem("Despesas");
        JMenuItem menuTransacoes = new JMenuItem("Transaçoes");
        JMenuItem menuFornecedor = new JMenuItem("Fornecedores");
        JMenuItem menuPagamento = new JMenuItem("Pagamentos");
        JMenuItem menuItemOrcamento = new JMenuItem("Item Orçamento");
        JMenuItem menuUsuario = new JMenuItem("Usuario");
        
        
        menuCategoria.addActionListener(e -> abrirTelaCategoria());
        menuOrcamento.addActionListener(e -> abrirTelaOrcamento());
        menuDespesa.addActionListener(e -> abrirTelaDespesa());
        menuTransacoes.addActionListener(e -> abrirTelaTransacoes());
        menuFornecedor.addActionListener(e -> abrirTelaFornecedor());
        menuPagamento.addActionListener(e -> abrirTelaPagamentos());
        menuItemOrcamento.addActionListener(e -> abrirTelaItemOrcamento());
        menuUsuario.addActionListener(e -> abrirTelaUsuario());
        
        menuCadastros.add(menuCategoria);
        menuCadastros.add(menuOrcamento);
        menuCadastros.add(menuDespesa);
        menuCadastros.add(menuTransacoes);
        menuCadastros.add(menuFornecedor);
        menuCadastros.add(menuPagamento);
        menuCadastros.add(menuItemOrcamento);
        menuCadastros.add(menuUsuario);
        
        menuBar.add(menuCadastros);
        setJMenuBar(menuBar);
        
        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // Adiciona uma imagem ou mensagem de boas-vindas
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema de Gestão Financeira", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void abrirTelaCategoria() {
        CategoriaView view = new CategoriaView();
        view.setVisible(true);
    }
    
    private void abrirTelaOrcamento() {
        OrcamentoView view = new OrcamentoView();
        view.setVisible(true);
    }
    
    private void abrirTelaDespesa() {
        DespesasView view = new DespesasView();
        view.setVisible(true);
    }
    
    private void abrirTelaTransacoes() {
        TransacoesView view = new TransacoesView();
        view.setVisible(true);
    }
    
    private void abrirTelaFornecedor() {
        FornecedoresView view = new FornecedoresView();
        view.setVisible(true);
    }
    
    private void abrirTelaPagamentos() {
        PagamentosView view = new PagamentosView();
        view.setVisible(true);
    }
    
    private void abrirTelaItemOrcamento() {
        ItemOrcamentoView view = new ItemOrcamentoView();
        view.setVisible(true);
    }
    
    private void abrirTelaUsuario() {
        UsuarioView view = new UsuarioView();
        view.setVisible(true);
    }
}
