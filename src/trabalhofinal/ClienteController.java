package trabalhofinal;

import java.sql.SQLException;
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
}
