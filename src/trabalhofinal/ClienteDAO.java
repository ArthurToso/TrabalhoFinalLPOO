package trabalhofinal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public void adicionarCliente(Cliente cliente) throws SQLException{
        String sql = "insert into cliente (nome,sobrenome,rg,cpf,endereco) values (?,?,?,?,?)";
        
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
        String sql = "select * from cliente";
        
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
        String sql = "select * from cliente where nome like ? or sobrenome like ?";
        
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
        String sql = "delete from cliente where id = ?";
        
        try(Connection con = ConexaoBanco.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        } 
    }

}
