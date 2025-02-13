package Objetos;

/**
 *
 * @author natsa
 */
public class Nsr_Venda_Produto {

    private Nsr_Venda nsr_venda;
    private Nsr_Produto nsr_produto;
    private Double Nsr_quantidade;
    private Double Nsr_total_item;
    private Double Nsr_desconto_item;

    public Nsr_Venda getNsr_Venda() {
        return nsr_venda;
    }

    public void setNsr_Venda(Nsr_Venda nsr_venda) {
        this.nsr_venda = nsr_venda;
    }

    public Nsr_Produto getNsr_produto() {
        return nsr_produto;
    }

    public void setNsr_produto(Nsr_Produto nsr_produto) {
        this.nsr_produto = nsr_produto;
    }

    public Double getNsr_quantidade() {
        return Nsr_quantidade;
    }

    public void setNsr_quantidade(Double Nsr_quantidade) {
        this.Nsr_quantidade = Nsr_quantidade;
    }

    public Double getNsr_total_item() {
        return Nsr_total_item;
    }

    public void setNsr_total_item(Double Nsr_total_item) {
        this.Nsr_total_item = Nsr_total_item;
    }

    public Double getNsr_desconto_item() {
        return Nsr_desconto_item;
    }

    public void setNsr_desconto_item(Double Nsr_desconto_item) {
        this.Nsr_desconto_item = Nsr_desconto_item;
    }

}
