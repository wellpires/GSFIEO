package gsfieo.mvc.tipoServico.model;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

public class TipoServico {

	private int _cdTipoServico;
	private String _tipoServico;
	private char _status;
	private String _strStatus;

	public int getCdTipoServico() {

		return _cdTipoServico;

	}

	public void setCdTipoServico(int _cdTipoServico) {

		this._cdTipoServico = _cdTipoServico;

	}

	public String getTipoServico() {

		return _tipoServico;

	}

	public void setTipoServico(String _tipoServico) {

		this._tipoServico = _tipoServico.toUpperCase();

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

}
