package gsfieo.mvc.servico.model;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.login.model.Login;
import gsfieo.mvc.tipoServico.model.TipoServico;

public class Servicos {

	private int _cdServicos;
	private String _servico;
	private char _status;
	private String _strStatus;
	private TipoServico tipoServico = new TipoServico();
	private Login login = new Login();

	public int getCdServicos() {

		return _cdServicos;

	}

	public void setCdServicos(int _cdServicos) {

		this._cdServicos = _cdServicos;

	}

	public String getServico() {

		return _servico;

	}

	public void setServico(String _servico) {

		this._servico = _servico.toUpperCase();

	}

	public char getStatus() {

		return _status;

	}

	public void setStatus(char _status) {

		this._status = _status;

	}

	public String getStrStatus() {
		return _strStatus;
	}

	public void setStrStatus(char _strStatus) {

		if (_strStatus == '1') {

			this._strStatus = "ATIVO";

		} else {

			this._strStatus = "INATIVO";

		}

	}

	public Login getLogin() {

		return login;

	}

	public void setLogin(Login login) {

		this.login = login;

	}

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

}
