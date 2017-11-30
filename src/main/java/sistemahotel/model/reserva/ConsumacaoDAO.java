package sistemahotel.model.reserva;

import org.hibernate.annotations.Where;
import sistemahotel.model.infraestrutura.Persistencia;
import sistemahotel.model.produto.Produto;
import sistemahotel.model.produto.ProdutoDAO;

import javax.persistence.Entity;

/**
 * Created by tatsunori on 30/11/17.
 */
@Entity
@Where(clause = "ativo = 1")
public class ConsumacaoDAO {
    private static ConsumacaoDAO instancia = null;
    private Persistencia persistencia = Persistencia.getInstancia();

    private ConsumacaoDAO(){

    }

    public static ConsumacaoDAO getInstancia(){
        if(instancia == null){
            instancia = new ConsumacaoDAO();
        }
        return instancia;
    }

    public Boolean addConsumo(Produto produto, String quantidade, Reserva reserva) {
        int qtd = Integer.valueOf(quantidade);
        int estoque = Integer.valueOf(produto.getQuantidade());
        int resultado = estoque - qtd;

        if( resultado >= 0){
            Consumacao consumacao = new Consumacao();
            consumacao.setProduto(produto.getNome());
            consumacao.setPreco(produto.getPreco());
            consumacao.setQuantidade(String.valueOf(resultado));
            consumacao.setReserva(reserva);
            consumacao.setQuantidade(String.valueOf(qtd));
            persistencia.persistir(consumacao);
            produto.setQuantidade(String.valueOf(resultado));
            persistencia.alterar(produto);

            return true;
        }

        return false;
    }

    public void deleteConsumacao(Consumacao consumacao){
        consumacao.setAtivo(false);
        Persistencia.getInstancia().alterar(consumacao);
    }


}
