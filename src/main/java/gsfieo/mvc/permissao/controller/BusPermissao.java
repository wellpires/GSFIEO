package gsfieo.mvc.permissao.controller;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.principal.connectionFactory.BancoDados;
import gsfieo.mvc.permissao.model.Permissao;

public class BusPermissao {

	private final BancoDados bd = new BancoDados();

	public void gravarPermissao(Permissao permissao) {

		String strQuery;

		strQuery = "INSERT INTO permissao (permissao,status) VALUES ('" + permissao.getPermissao() + "',"
				+ permissao.getStatus() + ")";

		bd.executaComando(strQuery);

	}

	public void editarPermissao(Permissao permissao) {

		String strQuery;

		strQuery = "UPDATE permissao SET permissao = '" + permissao.getPermissao() + "'," + " status = "
				+ permissao.getStatus() + " WHERE id_permissao = " + permissao.getCdPermissao();

		bd.executaComando(strQuery);

	}

	public void excluirPermissao(Permissao permissao) {

		String strQuery;

		strQuery = "DELETE FROM permissao WHERE id_permissao = " + permissao.getPermissao();

		bd.executaComando(strQuery);

	}

	public String[][] buscarPermissoes() {

		String strQuery;

		strQuery = "SELECT P.permissao  FROM Permissao P WHERE P.status = 1";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[][] listarPermissoes() {

		String strQuery;

		strQuery = "SELECT id_permissao,permissao,status FROM permissao";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[] listarInfoPermissao(Permissao permissao) {

		String strQuery;

		strQuery = "SELECT permissao,status FROM permissao WHERE id_permissao = " + permissao.getCdPermissao();

		return bd.retornaRegistro(strQuery);

	}

	public String[][] pesquisaPermissao(Permissao permissao) {

		String strQuery;

		strQuery = "SELECT id_permissao,permissao,status FROM permissao P WHERE P.permissao = '"
				+ permissao.getPermissao() + "'";

		return bd.retorna_N_Registros(strQuery);

	}

}
