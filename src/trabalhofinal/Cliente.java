/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhofinal;

/**
 *
 * @author arthu
 */
public class Cliente implements Comparable<Cliente> {
    //Variaveis
    private int id;
    private String nome;
    private String sobrenome;
    private String rg;
    private String cpf;
    private String endereco;
    
    //Construtor
    public Cliente(int id, String nome, String sobrenome, String rg, String cpf, String endereco){
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.rg = rg;
        this.cpf = cpf;
        this.endereco = endereco;
    }
    //Gets e Sets
    public int getId(){
        return id;
    }
    public void setId (int id){
        this.id = id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getSobrenome(){
        return sobrenome;
    }
    public void setSobrenome(String sobrenome){
        this.sobrenome = sobrenome;
    }
    public String getRg(){
        return rg;
    }
    public void setRg(String rg){
        this.rg = rg;
    }
    public String getCpf(){
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public String getEndereco(){
        return endereco;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    //funcao compareTo da interface comparable
    @Override
    public int compareTo(Cliente outroCliente) {
        return this.nome.compareToIgnoreCase(outroCliente.getNome());
    }
    
}
