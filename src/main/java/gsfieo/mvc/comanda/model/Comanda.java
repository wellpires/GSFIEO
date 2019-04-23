package gsfieo.mvc.comanda.model;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.confirmarPedidos.model.FormaPagto;
import gsfieo.mvc.login.model.Login;
import gsfieo.mvc.servico.model.Servicos;

public class Comanda {

	private String _cdComanda;
	private String _status;
	private double _totalCompra;
	private int _qtdTotal;
	private String _dataVenda;
	private String _dataConfirmacao;
	private Servicos servico = new Servicos();
	private FormaPagto formaPgto = new FormaPagto();
	private Login login = new Login();

	public String getCdComanda() {
		return _cdComanda;
	}

	public void setCdComanda(String _cdComanda) {
		this._cdComanda = _cdComanda;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String _status) {
		this._status = _status;
	}

	public double getTotalCompra() {
		return _totalCompra;
	}

	public void setTotalCompra(double _totalCompra) {
		this._totalCompra = _totalCompra;
	}

	public int getQtdTotal() {
		return _qtdTotal;
	}

	public void setQtdTotal(int _qtdTotal) {
		this._qtdTotal = _qtdTotal;
	}

	public String getDataVenda() {
		return _dataVenda;
	}

	public void setDataVenda(String _dataVenda) {
		this._dataVenda = _dataVenda;
	}

	public String getDataConfirmacao() {
		return _dataConfirmacao;
	}

	public void setDataConfirmacao(String _dataConfirmacao) {
		this._dataConfirmacao = _dataConfirmacao;
	}

	public Servicos getServico() {
		return servico;
	}

	public void setServico(Servicos servico) {
		this.servico = servico;
	}

	public FormaPagto getFormaPgto() {
		return formaPgto;
	}

	public void setFormaPgto(FormaPagto formaPgto) {
		this.formaPgto = formaPgto;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

}
