package trabalhofinal;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteController {
    private ClienteDAO clienteDAO;
    private ClienteTableModel clienteTableModel;
    
    public ClienteController(ClienteDAO clienteDAO, ClienteTableModel clienteTableModel){
        this.clienteDAO = clienteDAO;
        this.clienteTableModel = clienteTableModel;
    }
    
    public void adicionarCliente(Cliente cliente) throws SQLException{
        clienteDAO.adicionarCliente(cliente);
        atualizarListaClientes();
    }
    
    public List<Cliente> buscarClientes(String filtro){
        List<Cliente> clientes = clienteDAO.buscarClientes(filtro);
        clienteTableModel.atualizarLista(clientes);
        return clientes;
    }
    
    public void excluirCliente(int idCliente) throws SQLException{
        clienteDAO.excluirCliente(idCliente);
        atualizarListaClientes();
    }
    
    public void atualizarListaClientes() throws SQLException{
        List<Cliente> clientes = clienteDAO.listarClientes();
        clienteTableModel.atualizarLista(clientes);
    }

    public static List<Cliente> listarTodos() {
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            return clienteDAO.listarTodos();
        } catch (Exception e) {
            // Log de erro (opcional) e tratamento
            System.err.println("Erro ao listar clientes: " + e.getMessage());
            return new ArrayList<>(); // Retorna uma lista vazia em caso de erro
        }
    }

    public static void atualizarCliente(Cliente cliente) {
        try {
            // Chama o DAO para atualizar o cliente no banco de dados
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.atualizar(cliente);
        } catch (SQLException e) {
            e.printStackTrace();
            // Aqui você pode mostrar uma mensagem de erro caso algo dê errado
            JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
