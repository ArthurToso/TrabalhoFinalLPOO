package trabalhofinal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public void adicionarCliente(Cliente cliente) throws SQLException{
        String sql = "insert into clientes (nome,sobrenome,rg,cpf,endereco) values (?,?,?,?,?)";
        
        try(Connection conn = ConexaoBanco.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getRg());
            stmt.setString(4, cliente.getCpf());
            stmt.setString(5, cliente.getEndereco());
            
            stmt.executeUpdate();            
        }catch(SQLException e){
            System.out.println("Erro" + e);
        }
    }
    public List<Cliente> listarClientes() throws SQLException{
        List<Cliente> clientes = new ArrayList<>();
        String sql = "select * from clientes";
        
        try(Connection conn = ConexaoBanco.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
        
            while(rs.next()){
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("rg"),
                        rs.getString("cpf"),
                        rs.getString("endereco")
                );
                clientes.add(cliente);
            }
        }catch(SQLException e){
            System.out.println("Erro" + e);
        }
            
        return clientes;
    }
    public List<Cliente> buscarClientes(String filtro){
        List<Cliente> clientes = new ArrayList<>();
        String sql = "select * from clientes where nome like ? or sobrenome like ?";
        
        try(Connection con = ConexaoBanco.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){
            
            stmt.setString(1,"%" + filtro + "%");
            stmt.setString(2,"%" + filtro + "%");
            
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Cliente cliente = new Cliente(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("sobrenome"),
                            rs.getString("rg"),
                            rs.getString("cpf"),
                            rs.getString("endereco")
                    );
                    clientes.add(cliente); 
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return clientes;
    }   
    public void excluirCliente(int idCliente){
        String sql = "delete from clientes where id = ?";
        
        try(Connection con = ConexaoBanco.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        } 
    }

    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("rg"),
                        rs.getString("cpf"),
                        rs.getString("endereco")
                );
                clientes.add(cliente);
            }
        }

        return clientes;
    }

    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, sobrenome = ?, rg = ?, cpf = ?, endereco = ? WHERE id = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement ps = conexao.prepareStatement(sql);) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getSobrenome());
            ps.setString(3, cliente.getRg());
            ps.setString(4, cliente.getCpf());
            ps.setString(5, cliente.getEndereco());
            ps.setInt(6, cliente.getId()); // Supondo que o ID seja único para identificar o cliente

            ps.executeUpdate(); // Executa a atualização no banco de dados
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Lança a exceção caso ocorra um erro
        }
    }

}
