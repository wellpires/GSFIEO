package gsfieo.mvc.confirmarPedidos.model;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.comanda.model.Comanda;
import gsfieo.mvc.produtos.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class Pedidos {

	private int _cdPedido;
	private List<Produto> produtos = new ArrayList<>();
	private Comanda comanda = new Comanda();

	public int getCdPedido() {
		return _cdPedido;
	}

	public void setCdPedido(int cdPedido) {
		this._cdPedido = cdPedido;
	}

	public Comanda getComanda() {
		return comanda;
	}

	public void setComanda(Comanda comanda) {
		this.comanda = comanda;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public int getRowsCount() {

		return produtos.size();

	}

}
