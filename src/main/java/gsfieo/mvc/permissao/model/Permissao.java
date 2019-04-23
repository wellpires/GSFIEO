package gsfieo.mvc.permissao.model;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

public class Permissao {

	private int cdPermissao;
	private String permissao;
	private int status;

	public int getCdPermissao() {
		return cdPermissao;
	}

	public void setCdPermissao(int cdPermissao) {
		this.cdPermissao = cdPermissao;
	}

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
