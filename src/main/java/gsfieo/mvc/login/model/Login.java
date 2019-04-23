package gsfieo.mvc.login.model;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.permissao.model.Permissao;

public class Login {

	private int _cdLogin;
	private String _usuario;
	private String _senha;
	private char _status;
	private Permissao permissao = new Permissao();

	public int getCdLogin() {

		return _cdLogin;

	}

	public void setCdLogin(int _cdLogin) {

		this._cdLogin = _cdLogin;

	}

	public String getUsuario() {

		return _usuario;

	}

	public void setUsuario(String _usuario) {

		this._usuario = _usuario.toUpperCase();

	}

	public String getSenha() {

		return _senha;

	}

	public void setSenha(char[] _senha) {

		this._senha = String.copyValueOf(_senha);

	}

	public char getStatus() {

		return _status;

	}

	public void setStatus(char _status) {

		this._status = _status;

	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

}
