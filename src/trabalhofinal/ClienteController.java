package trabalhofinal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteController {
    private ClienteDAO clienteDAO;
    private ClienteTableModel clienteTableModel;
    
    public ClienteController(ClienteTableModel clienteTableModel){
        this.clienteDAO = new ClienteDAO();
        this.clienteTableModel = clienteTableModel;
    }
    
    public void adicionarCliente(Cliente cliente) throws SQLException{
        clienteDAO.adicionarCliente(cliente);
        atualizarListaClientes();
    }
    
    public void buscarClientes(String filtro){
        List<Cliente> clientes = clienteDAO.buscarClientes(filtro);
        clienteTableModel.atualizarLista(clientes);
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

}
