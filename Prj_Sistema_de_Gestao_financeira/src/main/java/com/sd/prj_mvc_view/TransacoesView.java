package com.sd.prj_mvc_view;

import com.sd.prj_mvc_controller.TransacoesController;
import com.sd.prj_mvc_model.Transacoes;
import com.sd.prj_mvc_observer.TransacoesObserver;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TransacoesView extends JFrame implements TransacoesObserver {
    private TransacoesController controller;
    private JTextField descricaoField;
    private JTextField valorField;
    private JTextField dataField;
    private JComboBox<String> orcamentoComboBox;
    private JComboBox<String> tipoComboBox;
    private JTable transacaoTable;
    private DefaultTableModel tableModel;
    private DateTimeFormatter dateTimeFormatter;

    public TransacoesView() {
        controller = new TransacoesController();
        controller.adicionarObserver(this);
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        initComponents();
        loadTransacoes();
        loadOrcamentos();
    }
    
    @Override
    public void onTransacaoChanged(Transacoes transacao) {
        loadTransacoes();
    }

    private void initComponents() {
        setTitle("Gestão de Transações");
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

        formPanel.add(new JLabel("Tipo:"));
        tipoComboBox = new JComboBox<>(new String[] {"Entrada", "Saída"});
        formPanel.add(tipoComboBox);

        formPanel.add(new JLabel("Orçamento:"));
        orcamentoComboBox = new JComboBox<>();
        formPanel.add(orcamentoComboBox);

        formPanel.add(new JLabel("Data:"));
        dataField = new JTextField(10);  
        formPanel.add(dataField);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> salvarTransacao());
        formPanel.add(saveButton);

        // Tabela
        String[] columns = {"ID", "Descrição", "Valor", "Tipo", "Data", "Orçamento"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transacaoTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transacaoTable);

        // Painel de botões da tabela
        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");

        editButton.addActionListener(e -> editarTransacao());
        deleteButton.addActionListener(e -> excluirTransacao());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Adiciona componentes ao frame
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadTransacoes() {
        tableModel.setRowCount(0);
        List<Transacoes> transacoes = controller.listarTransacoes();
        for (Transacoes transacao : transacoes) {
            String tipoCapitalizado = transacao.getTipo().substring(0, 1).toUpperCase() + 
                                    transacao.getTipo().substring(1);
            tableModel.addRow(new Object[]{
                transacao.getId(),
                transacao.getDescricao(),
                transacao.getValor(),
                tipoCapitalizado, 
                transacao.getData().format(dateTimeFormatter),
                transacao.getOrcamentoId()
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

    private void salvarTransacao() {
        String descricao = descricaoField.getText().trim();
        String valorTexto = valorField.getText().trim();
        String tipo = ((String) tipoComboBox.getSelectedItem()).toLowerCase();
        String orcamento = (String) orcamentoComboBox.getSelectedItem();
        String dataTexto = dataField.getText().trim();

        if (descricao.isEmpty() || valorTexto.isEmpty() || tipo == null || orcamento == null || dataTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            return;
        }

        try {
            BigDecimal valor = new BigDecimal(valorTexto.replace(",", "."));
            Transacoes transacao = new Transacoes();
            transacao.setDescricao(descricao);
            transacao.setValor(valor);
            transacao.setTipo(tipo);

            // Tratamento da data simplificado, igual ao DespesasView
            LocalDate data = LocalDate.parse(dataTexto, dateTimeFormatter);
            transacao.setData(data);

            transacao.setOrcamentoId(controller.obterOrcamentoId(orcamento));

            if (!controller.salvarTransacao(transacao)) {
                JOptionPane.showMessageDialog(this, "O valor da transação ultrapassa o orçamento disponível.");
            } else {
                loadTransacoes();
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

    private void editarTransacao() {
        int selectedRow = transacaoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma transação para editar.");
            return;
        }

        try {
            // Obter valores da linha selecionada
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            String descricao = (String) tableModel.getValueAt(selectedRow, 1);
            BigDecimal valorAtual = (BigDecimal) tableModel.getValueAt(selectedRow, 2);
            String tipo = ((String) tableModel.getValueAt(selectedRow, 3)).toLowerCase(); // Converter para minúsculo
            String dataAtual = (String) tableModel.getValueAt(selectedRow, 4);
            Long orcamentoId = (Long) tableModel.getValueAt(selectedRow, 5);

            // Solicitar novos valores
            String novaDescricao = JOptionPane.showInputDialog(this, "Digite a nova descrição:", descricao);
            String novoValorTexto = JOptionPane.showInputDialog(this, "Digite o novo valor:", valorAtual.toString());

            if (novaDescricao != null && !novaDescricao.trim().isEmpty() && novoValorTexto != null) {
                try {
                    // Criar nova transação com os valores atualizados
                    Transacoes transacao = new Transacoes();
                    transacao.setId(id);
                    transacao.setDescricao(novaDescricao);

                    // Converter o valor para BigDecimal, tratando tanto vírgula quanto ponto
                    BigDecimal novoValor = new BigDecimal(novoValorTexto.replace(",", "."));
                    transacao.setValor(novoValor);

                    // Manter a data atual
                    LocalDate data = LocalDate.parse(dataAtual, dateTimeFormatter);
                    transacao.setData(data);

                    // Manter tipo e orçamento
                    transacao.setTipo(tipo); // Já está em minúsculo
                    transacao.setOrcamentoId(orcamentoId);

                    if (!controller.salvarTransacao(transacao)) {
                        JOptionPane.showMessageDialog(this, "O valor da transação ultrapassa o orçamento disponível.");
                    } else {
                        loadTransacoes();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "O valor deve ser um número válido.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao editar transação: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar dados da transação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void excluirTransacao() {
        int selectedRow = transacaoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma transação para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir esta transação?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            controller.excluirTransacao(id);
            loadTransacoes();
        }
    }
}


