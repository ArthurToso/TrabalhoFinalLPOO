package trabalhofinal;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ClienteTableModel extends AbstractTableModel{
    private List<Cliente> clientes;
    private String[] colunas = {"ID", "Nome", "Sobrenome", "RG", "CPF", "Endereço"};
    
    public ClienteTableModel(List<Cliente> clientes){
        this.clientes = clientes;
    }
    
    public void atualizarLista(List<Cliente> clientes){
        this.clientes = clientes;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount(){
        return clientes == null ? 0 : clientes.size();
    }
    
    @Override
    public int getColumnCount(){
        return colunas.length;
    }

    public Cliente getCliente(int linha) {
        return clientes.get(linha); // listaClientes é a lista usada para armazenar os clientes
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        Cliente cliente = clientes.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> cliente.getId();
            case 1 -> cliente.getNome();
            case 2 -> cliente.getSobrenome();
            case 3 -> cliente.getRg();
            case 4 -> cliente.getCpf();
            case 5 -> cliente.getEndereco();
            default -> null;
        };
    }
    @Override
    public String getColumnName(int column){
        return colunas[column];
    }
}
