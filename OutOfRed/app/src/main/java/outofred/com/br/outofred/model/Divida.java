package outofred.com.br.outofred.model;

/**
 * Created by ivomancinelli on 13/10/17.
 */

public class Divida {

    private int idDivida;
    private double valor;
    private String dataVencimento;
    private int idCliente;
    private Cliente cliente;

    public Divida() {
    }

    public Divida(int idDivida, double valor, String dataVencimento, int idCliente) {
        this.idDivida = idDivida;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.idCliente = idCliente;
    }

    public int getIdDivida() {
        return idDivida;
    }

    public void setIdDivida(int idDivida) {
        this.idDivida = idDivida;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
