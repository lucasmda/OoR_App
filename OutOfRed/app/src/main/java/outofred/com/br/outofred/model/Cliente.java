package outofred.com.br.outofred.model;

/**
 * Created by ivomancinelli on 13/10/17.
 */

public class Cliente {

    private int id;
    private String nome;
    private String senha;
    private String cep;
    private String telefone;
    private String cpf;
    private String dataNascimento;
    private String email;
    private double saldo;

    public Cliente() {
    }

    public Cliente(int id, String nome, String senha, String cep, String telefone, String cpf, String dataNascimento, String email, double saldo) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.cep = cep;
        this.telefone = telefone;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
