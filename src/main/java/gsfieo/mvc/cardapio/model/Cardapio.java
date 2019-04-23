package gsfieo.mvc.cardapio.model;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.produtos.model.Produto;
import gsfieo.mvc.servico.model.Servicos;

public class Cardapio {

	private int _cdCardapio;
	private Produto _produto = new Produto();
	private Servicos _servicos = new Servicos();

	public int getCdCardapio() {

		return _cdCardapio;

	}

	public void setCdCardapio(int _cdCardapio) {

		this._cdCardapio = _cdCardapio;

	}

	public Produto getProduto() {
		return _produto;
	}

	public void setProduto(Produto _produto) {
		this._produto = _produto;
	}

	public Servicos getServicos() {
		return _servicos;
	}

	public void setServicos(Servicos _servicos) {
		this._servicos = _servicos;
	}

}
