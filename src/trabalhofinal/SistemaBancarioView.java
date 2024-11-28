package trabalhofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SistemaBancarioView extends JFrame {
    private JTable tabelaClientes;
    private ClienteTableModel modeloTabela;
    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltro;
    
    public SistemaBancarioView() {
        setTitle("Gerenciamento de Clientes");
        setSize(900, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        modeloTabela = new ClienteTableModel(new ArrayList<>());
        tabelaClientes = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        add(scrollPane, BorderLayout.CENTER);

        txtBuscar = new JTextField(20);
        cmbFiltro = new JComboBox<>(new String[]{"Nome", "Sobrenome", "RG", "CPF"});
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarClientes());

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Buscar por:"));
        painelBusca.add(cmbFiltro);
        painelBusca.add(txtBuscar);
        painelBusca.add(btnBuscar);
        add(painelBusca, BorderLayout.NORTH);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        btnAdicionar.addActionListener(e -> {
            try {
                adicionarCliente();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnEditar.addActionListener(e -> editarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarDadosTabela();

    }

    public void buscarClientes(){
        String filtro = (String) cmbFiltro.getSelectedItem();
        String texto = txtBuscar.getText();

        if(texto.isEmpty()){
            JOptionPane.showMessageDialog(this, "Digite um texto para busca", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try{
            ClienteDAO clienteDAO = new ClienteDAO();
            modeloTabela = new ClienteTableModel(new ArrayList<>());
            ClienteController clienteController = new ClienteController(clienteDAO, modeloTabela);
            List<Cliente> resultados = clienteController.buscarClientes(txtBuscar.getText());
            modeloTabela.atualizarLista(resultados);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Erro ao buscar cliente" + e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void adicionarCliente() throws SQLException {
        // Campos para entrada de dados
        JTextField nomeField = new JTextField();
        JTextField sobrenomeField = new JTextField();
        JTextField rgField = new JTextField();
        JTextField cpfField = new JTextField();
        JTextField enderecoField = new JTextField();

        // Painel para organizar os campos
        JPanel painel = new JPanel(new GridLayout(5, 2));
        painel.add(new JLabel("Nome:"));
        painel.add(nomeField);
        painel.add(new JLabel("Sobrenome:"));
        painel.add(sobrenomeField);
        painel.add(new JLabel("RG:"));
        painel.add(rgField);
        painel.add(new JLabel("CPF:"));
        painel.add(cpfField);
        painel.add(new JLabel("Endereço:"));
        painel.add(enderecoField);

        // Exibe o diálogo para entrada de dados
        int resultado = JOptionPane.showConfirmDialog(
                this,
                painel,
                "Adicionar Cliente",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Se o usuário clicar em "OK", tenta salvar o cliente
        if (resultado == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String sobrenome = sobrenomeField.getText();
            String rg = rgField.getText();
            String cpf = cpfField.getText();
            String endereco = enderecoField.getText();

            // Verifica se todos os campos foram preenchidos
            if (nome.isEmpty() || sobrenome.isEmpty() || rg.isEmpty() || cpf.isEmpty() || endereco.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente cliente = new Cliente(0, nome, sobrenome, rg, cpf, endereco);
            try {
                ClienteDAO clienteDAO = new ClienteDAO();
                modeloTabela = new ClienteTableModel(new ArrayList<>());
                ClienteController clienteController = new ClienteController(clienteDAO, modeloTabela);
                clienteController.adicionarCliente(cliente);
                carregarDadosTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            modeloTabela.atualizarLista(ClienteController.listarTodos());
            modeloTabela.fireTableDataChanged();
        }
    }

    private void editarCliente(){
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = modeloTabela.getCliente(linhaSelecionada);
        JTextField nomeField = new JTextField(cliente.getNome());
        JTextField sobrenomeField = new JTextField(cliente.getSobrenome());
        JTextField rgField = new JTextField(cliente.getRg());
        JTextField cpfField = new JTextField(cliente.getCpf());
        JTextField enderecoField = new JTextField(cliente.getEndereco());

        JPanel painel = new JPanel(new GridLayout(5, 2));
        painel.add(new JLabel("Nome:"));
        painel.add(nomeField);
        painel.add(new JLabel("Sobrenome:"));
        painel.add(sobrenomeField);
        painel.add(new JLabel("RG:"));
        painel.add(rgField);
        painel.add(new JLabel("CPF:"));
        painel.add(cpfField);
        painel.add(new JLabel("Endereço:"));
        painel.add(enderecoField);

        int resultado = JOptionPane.showConfirmDialog(this, painel, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            cliente.setNome(nomeField.getText());
            cliente.setSobrenome(sobrenomeField.getText());
            cliente.setRg(rgField.getText());
            cliente.setCpf(cpfField.getText());
            cliente.setEndereco(String.valueOf(enderecoField));

            try {
                ClienteDAO clienteDAO = new ClienteDAO();
                modeloTabela = new ClienteTableModel(new ArrayList<>());
                ClienteController clienteController = new ClienteController(clienteDAO, modeloTabela);
                clienteController.atualizarCliente(cliente);
                carregarDadosTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao editar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void excluirCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = modeloTabela.getCliente(linhaSelecionada);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o cliente e suas contas?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                ClienteDAO clienteDAO = new ClienteDAO();
                modeloTabela = new ClienteTableModel(new ArrayList<>());
                ClienteController clienteController = new ClienteController(clienteDAO, modeloTabela);
                clienteController.excluirCliente(cliente.getId());
                carregarDadosTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarDadosTabela() {
        try {
            List<Cliente> clientes = ClienteController.listarTodos();
            modeloTabela.atualizarLista(clientes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SistemaBancarioView().setVisible(true);
            }
        });
    }
}
