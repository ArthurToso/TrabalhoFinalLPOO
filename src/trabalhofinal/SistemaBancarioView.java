package trabalhofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SistemaBancarioView extends JFrame {
    private ClienteController controller;
    private ClienteTableModel tableModel;
    private JTable tabela;
    
    public SistemaBancarioView(){
        setTitle("Sistema Bancario - Clientes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
