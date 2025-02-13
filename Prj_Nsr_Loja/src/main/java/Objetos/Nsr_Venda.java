package Objetos;

/**
 *
 * @author natsa
 */
public class Nsr_Venda {

    private int nsr_idVenda;
    private String nsr_data_venda;
    private Double nsr_total_venda;
    private Nsr_Cliente nsr_cliente;

    public int getNsr_idVenda() {
        return nsr_idVenda;
    }

    public void setNsr_idVenda(int nsr_idVenda) {
        this.nsr_idVenda = nsr_idVenda;
    }

    public String getNsr_data_venda() {
        return nsr_data_venda;
    }

    public void setNsr_data_venda(String nsr_data_venda) {
        this.nsr_data_venda = nsr_data_venda;
    }

    public Double getNsr_total_venda() {
        return nsr_total_venda;
    }

    public void setNsr_total_venda(Double nsr_total_venda) {
        this.nsr_total_venda = nsr_total_venda;
    }

    public Nsr_Cliente getNsr_cliente() {
        return nsr_cliente;
    }

    public void setNsr_cliente(Nsr_Cliente nsr_cliente) {
        this.nsr_cliente = nsr_cliente;
    }

}
