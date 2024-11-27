package trabalhofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

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

        btnAdicionar.addActionListener(e -> adicionarCliente());
        btnEditar.addActionListener(e -> editarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());
    }

    public void buscarClientes(){
        String filtro = (String) cmbFiltro.getSelectedItem();
        String texto = txtBuscar.getText();

        List resultados = ClienteController.buscarClientes(filtro, texto);
        modeloTabela.atualizarLista((java.util.List<Cliente>) resultados);
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

            // Cria um novo cliente e o adiciona ao banco de dados
            Cliente cliente = new Cliente(0, nome, sobrenome, rg, cpf, endereco); // ID será gerado pelo banco
            ClienteController.adicionarCliente(cliente);

            // Atualiza a tabela com os novos dados
            modeloTabela.atualizarLista(ClienteController.listarTodos());
        }
    }

    private void editarCliente(){
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada != -1) {
            Cliente cliente = modeloTabela.getCliente(linhaSelecionada);
            Cliente clienteAtualizado = ClienteForm.mostrarFormulario(cliente);
            if (clienteAtualizado != null) {
                ClienteController.atualizarCliente(clienteAtualizado);
                modeloTabela.atualizarLista(ClienteController.listarTodos());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.");
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
