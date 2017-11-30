package sistemahotel.model.reserva;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by tatsunori on 30/11/17.
 */
@Entity
public class Consumacao implements Comparable<Consumacao>{

    @Id
    @GeneratedValue
    private Long id;

    private String produto; //apenas atributo nome
    private String preco;
    private String quantidade;
    @ManyToOne
    private Reserva reserva; //chave
    private Boolean ativo = true;
    @Override
    public String toString(){
        return this.produto;
    }

    @Override
    public int compareTo(Consumacao consumacao) {
        return produto.compareTo(consumacao.getProduto());
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}
