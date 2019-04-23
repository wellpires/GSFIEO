package gsfieo.mvc.estoque.model;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.produtos.model.Produto;
import gsfieo.mvc.servico.model.Servicos;

public class Estoque {

	private int _cdEstoque;
	private int _qtd;
	private int _qtdMaxima;
	private int _qtdMinima;
	private Produto produto = new Produto();
	private Servicos servico = new Servicos();

	public int getCdEstoque() {
		return _cdEstoque;
	}

	public void setCdEstoque(int _cdEstoque) {
		this._cdEstoque = _cdEstoque;
	}

	public int getQtd() {
		return _qtd;
	}

	public void setQtd(int _qtd) {
		this._qtd = _qtd;
	}

	public int getQtdMaxima() {
		return _qtdMaxima;
	}

	public void setQtdMaxima(int _qtdMaxima) {
		this._qtdMaxima = _qtdMaxima;
	}

	public int getQtdMinima() {
		return _qtdMinima;
	}

	public void setQtdMinima(int _qtdMinima) {
		this._qtdMinima = _qtdMinima;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Servicos getServico() {
		return servico;
	}

	public void setServico(Servicos servico) {
		this.servico = servico;
	}

}
