package trabalhofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SistemaBancarioView extends JFrame {
    private ClienteController controller;
    private ClienteTableModel tableModel;
    private JTable tabela;
    
    public SistemaBancarioView() {
        setTitle("Sistema Bancario - Clientes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new ClienteTableModel(null);
        controller = new ClienteController(tableModel);
        tabela = new JTable(tableModel);

        JButton btnAdcionar = new JButton("Adicionar Cliente");
        JButton btnBuscar = new JButton("Buscar Cliente");
        JTextField txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(200, 25));

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnAdcionar);
        painelBotoes.add(new JLabel("Buscar:"));
        painelBotoes.add(txtBuscar);
        painelBotoes.add(btnBuscar);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        btnAdcionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = JOptionPane.showInputDialog("Nome:");
                String sobrenome = JOptionPane.showInputDialog("Sobrenome:");
                String rg = JOptionPane.showInputDialog("RG:");
                String cpf = JOptionPane.showInputDialog("CPF:");
                String endereco = JOptionPane.showInputDialog("Endereço:");
                Cliente cliente = new Cliente(0, nome, sobrenome, rg, cpf, endereco);
                try {
                    controller.adicionarCliente(cliente);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filtro = txtBuscar.getText();
                controller.buscarClientes(filtro);
            }
        });
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
