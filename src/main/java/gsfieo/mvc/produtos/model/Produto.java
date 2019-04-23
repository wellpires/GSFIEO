package gsfieo.mvc.produtos.model;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.servico.model.Servicos;
import gsfieo.mvc.tipoProduto.model.TipoProduto;

public class Produto {

	private int _cdProduto;
	private String _produto;
	private int _estoqueMin;
	private int _estoqueMax;
	private int _qtd;
	private double _valorUnit;
	private String _strValorUnit;
	private double _subTotal;

	private TipoProduto tipoProduto = new TipoProduto();
	private Servicos servicos = new Servicos();

	public int getCdProduto() {

		return _cdProduto;

	}

	public void setCdProduto(int _cdProduto) {

		this._cdProduto = _cdProduto;

	}

	public String getProduto() {

		return _produto;

	}

	public void setProduto(String _produto) {

		this._produto = _produto.toUpperCase();

	}

	public int getEstoqueMin() {

		return _estoqueMin;

	}

	public void setEstoqueMin(int _estoqueMin) {

		this._estoqueMin = _estoqueMin;

	}

	public int getEstoqueMax() {

		return _estoqueMax;

	}

	public void setEstoqueMax(int _estoqueMax) {

		this._estoqueMax = _estoqueMax;

	}

	public int getQtd() {

		return _qtd;

	}

	public void setQtd(int _qtd) {

		this._qtd = _qtd;

	}

	public double getValorUnit() {

		return _valorUnit;

	}

	public void setValorUnit(double _valorUnit) {

		this._valorUnit = _valorUnit;

	}

	public String getStrValorUnit() {
		return _strValorUnit;
	}

	public void setStrValorUnit(String _strValorUnit) {
		this._strValorUnit = _strValorUnit;
	}

	public double getSubTotal() {
		return _subTotal;
	}

	public void setSubTotal(double _subTotal) {
		this._subTotal = _subTotal;
	}

	public Servicos getServicos() {
		return servicos;
	}

	public void setServicos(Servicos servicos) {
		this.servicos = servicos;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

}
