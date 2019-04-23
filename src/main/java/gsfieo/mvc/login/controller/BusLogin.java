package gsfieo.mvc.login.controller;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.principal.connectionFactory.BancoDados;
import gsfieo.mvc.login.model.Login;

public class BusLogin {

	private final BancoDados bd = new BancoDados();

	public boolean logarSe(Login login) {

		String usuario = login.getUsuario();
		String senha = login.getSenha();

		String[] res = logon(login);

		if (usuario.equals(res[0]) && senha.equals(res[1])) {
			login.setUsuario(res[0]);
			login.getPermissao().setPermissao(res[2]);
			return true;
		} else {
			return false;
		}

	}

	public void gravarLogin(Login login) {

		String strQuery;

		strQuery = "INSERT INTO Login (usuario,senha,id_permissao_FK) VALUES" + "('" + login.getUsuario() + "','"
				+ login.getSenha() + "'," + "(SELECT id_permissao FROM Permissao WHERE permissao = '"
				+ login.getPermissao().getPermissao() + "'));";

		bd.executaComando(strQuery);

	}

	public void excluirLogin(Login login) {

		String strQuery;

		strQuery = "DELETE FROM Login WHERE id_login = " + login.getCdLogin();

		bd.executaComando(strQuery);

	}

	public void editarLogin(Login login) {

		String strQuery;

		strQuery = "UPDATE Login SET usuario = '" + login.getUsuario() + "', senha = '" + login.getSenha()
				+ "',id_permissao_FK = (SELECT id_permissao FROM Permissao WHERE permissao = '"
				+ login.getPermissao().getPermissao() + "') WHERE id_login = '" + login.getCdLogin() + "'";

		bd.executaComando(strQuery);

	}

	public String[] logon(Login login) {

		String strQuery;

		strQuery = "SELECT l.usuario,l.senha,(SELECT P.permissao FROM permissao P WHERE l.id_permissao_FK = P.id_permissao AND P.status = '1') FROM Login l WHERE l.usuario = '"
				+ login.getUsuario() + "' AND l.senha = '" + login.getSenha() + "'";

		return bd.retornaRegistro(strQuery);

	}

	public String[][] buscarUsuarios(Login login) {

		String strQuery;

		strQuery = "SELECT l.id_login ," + "l.usuario ,"
				+ "(SELECT Permissao FROM Permissao p WHERE P.id_permissao = l.id_permissao_FK) "
				+ " FROM Login l WHERE l.usuario LIKE '%" + login.getUsuario() + "%' OR id_permissao_FK ="
				+ " (SELECT id_permissao FROM Permissao P WHERE P.permissao LIKE '%"
				+ login.getPermissao().getPermissao() + "%' AND P.id_permissao = L.id_permissao_FK)";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[] listarInfoUsuario(Login login) {

		String strQuery;

		strQuery = "SELECT l.usuario ," + "l.senha ," + "(SELECT p.permissao FROM Permissao p "
				+ "WHERE P.id_permissao = l.id_permissao_FK)  FROM Login l WHERE l.id_login = " + login.getCdLogin();

		return bd.retornaRegistro(strQuery);

	}

	public String[] listarPerfil(Login login) {

		String strQuery;

		strQuery = "SELECT L.usuario ," + "(SELECT P.permissao FROM permissao P"
				+ " WHERE P.id_permissao = L.id_permissao_FK) ,"
				+ "(SELECT S.serv_desc FROM servico S WHERE S.id_login_FK = L.id_login) ,"
				+ "(SELECT S.id_serv FROM servico S WHERE S.id_login_FK = L.id_login)  FROM Login L WHERE L.usuario = '"
				+ login.getUsuario() + "'";

		return bd.retornaRegistro(strQuery);

	}

	public String[] listarPerfilPadrao(Login login) {

		String strQuery;

		strQuery = "SELECT L.id_login ," + "L.usuario ,"
				+ "(SELECT P.permissao FROM permissao P WHERE P.id_permissao = L.id_permissao_FK)  "
				+ "FROM Login L WHERE L.usuario = '" + login.getUsuario() + "'";

		return bd.retornaRegistro(strQuery);

	}

	public String[] verificarUsuarioExiste(Login login) {

		String strQuery;

		strQuery = "SELECT L.usuario  FROM Login L WHERE L.usuario = '" + login.getUsuario() + "'";

		return bd.retornaRegistro(strQuery);

	}

}
