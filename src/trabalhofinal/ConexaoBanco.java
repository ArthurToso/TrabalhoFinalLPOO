package trabalhofinal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static final String URL = "jdbc:mysql://localhost/jdbc";
    private static final String USER = "root";
    private static final String SENHA = "novaSenha";
   
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, SENHA);
    }
    
    public static void closeConnection(Connection conn){
        if(conn != null){
            try{
                conn.close();
            }catch (SQLException e){
                System.out.println("Erro" + e);
            }
        }
    }
}
