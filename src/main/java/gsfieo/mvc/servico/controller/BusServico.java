package gsfieo.mvc.servico.controller;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.principal.connectionFactory.BancoDados;
import gsfieo.mvc.servico.model.Servicos;

public class BusServico {

	private final BancoDados bd = new BancoDados();

	public void gravar(Servicos servico) {

		String strQuery;

		strQuery = "INSERT INTO servico(serv_desc,status,id_tipo_serv_FK,id_login_FK) " + "VALUES('"
				+ servico.getServico() + "','" + servico.getStatus() + "'"
				+ ",(SELECT TP.id_tipo_serv FROM tipo_servico TP WHERE" + " TP.tipo_serv_desc = '"
				+ servico.getTipoServico().getTipoServico() + "'),"
				+ "(SELECT L.id_login FROM Login L WHERE L.usuario = '" + servico.getLogin().getUsuario() + "' "
				+ "AND L.senha = '" + servico.getLogin().getSenha() + "'))";

		bd.executaComando(strQuery);

	}

	public void editar(Servicos servico) {

		String strQuery;

		strQuery = "UPDATE servico SET serv_desc = '" + servico.getServico() + "'," + "status = '" + servico.getStatus()
				+ "'," + "id_tipo_serv = (SELECT TP.id_tipo_serv" + " FROM tipo_servico TP WHERE TP.tipo_serv_desc = '"
				+ servico.getTipoServico().getTipoServico() + "')" + " WHERE id_serv = '" + servico.getCdServicos()
				+ "'";

		bd.executaComando(strQuery);

	}

	public void excluir(Servicos servico) {

		String strQuery;

		strQuery = "DELETE FROM servico WHERE id_serv = " + servico.getCdServicos();

		bd.executaComando(strQuery);

	}

	public String[][] listarServicos(Servicos servico) {

		String strQuery;

		strQuery = "SELECT id_serv ," + "serv_desc ," + "status ,"
				+ "(SELECT TS.tipo_serv_desc FROM tipo_servico TS WHERE TS.id_tipo_serv = S.id_tipo_serv_FK) "
				+ " FROM servico S WHERE S.serv_desc LIKE '%" + servico.getServico() + "%' OR"
				+ " S.id_tipo_serv_FK = (SELECT TS.id_tipo_serv FROM tipo_servico TS WHERE TS.tipo_serv_desc LIKE '%"
				+ servico.getTipoServico().getTipoServico() + "%' AND S.id_tipo_serv_FK = TS.id_tipo_serv)";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[][] listarServicosCardapio() {

		String strQuery;

		strQuery = "SELECT S.id_serv ," + "S.serv_desc ," + "status ,"
				+ "(SELECT TS.tipo_serv_desc FROM tipo_servico TS WHERE TS.id_tipo_serv = S.id_tipo_serv_FK)"
				+ " FROM servico S WHERE S.id_serv IN (SELECT P.id_serv_FK FROM produto P)";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[] listarInfoServico(Servicos servico) {

		String strQuery;

		strQuery = "SELECT id_serv," + "serv_desc," + "status,(SELECT TS.tipo_serv_desc"
				+ " FROM tipo_servico TS WHERE TS.id_tipo_serv = "
				+ "S.id_tipo_serv_FK) FROM servico S WHERE S.id_serv = " + servico.getCdServicos();

		return bd.retornaRegistro(strQuery);

	}

	public String[] verificarServicoExiste(Servicos servico) {

		String strQuery;

		strQuery = "SELECT S.serv_desc  FROM servico S WHERE S.serv_desc = '" + servico.getServico() + "'";

		return bd.retornaRegistro(strQuery);

	}

	public String[] buscarCodServico(Servicos servicos) {

		String strQuery;

		strQuery = "SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + servicos.getServico() + "'";

		return bd.retornaRegistro(strQuery);

	}

}
