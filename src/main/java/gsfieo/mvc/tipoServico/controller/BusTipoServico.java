package gsfieo.mvc.tipoServico.controller;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.principal.connectionFactory.BancoDados;
import gsfieo.mvc.tipoServico.model.TipoServico;

public class BusTipoServico {

	private final BancoDados bd = new BancoDados();

	public void gravar(TipoServico tipoServico) {

		String strQuery;

		strQuery = "INSERT INTO tipo_servico(tipo_serv_desc,status) VALUES ('" + tipoServico.getTipoServico() + "','"
				+ tipoServico.getStatus() + "')";

		bd.executaComando(strQuery);

	}

	public void editar(TipoServico tipoServico) {

		String strQuery;

		strQuery = "UPDATE tipo_servico SET tipo_serv_desc = '" + tipoServico.getTipoServico() + "'," + " status = '"
				+ tipoServico.getStatus() + "' WHERE id_tipo_serv = " + tipoServico.getCdTipoServico();

		bd.executaComando(strQuery);

	}

	public void excluir(TipoServico tipoServico) {

		String strQuery;

		strQuery = "DELETE FROM tipo_servico WHERE id_tipo_serv = " + tipoServico.getCdTipoServico();

		bd.executaComando(strQuery);

	}

	public String[][] buscarTiposServico(TipoServico tipoServico) {

		String strQuery;

		strQuery = "SELECT TS.id_tipo_serv," + "TS.tipo_serv_desc,"
				+ "TS.status FROM tipo_servico TS WHERE TS.tipo_serv_desc LIKE '%" + tipoServico.getTipoServico()
				+ "%'";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[] listarTipoServico(TipoServico tipoServico) {

		String strQuery;

		strQuery = "SELECT TS.tipo_serv_desc,TS.status FROM tipo_servico TS WHERE TS.id_tipo_serv = "
				+ tipoServico.getCdTipoServico();

		return bd.retornaRegistro(strQuery);

	}

	public String[] verificarTipoExiste(TipoServico tipoServico) {

		String strQuery;

		strQuery = "SELECT TS.tipo_serv_desc  FROM tipo_servico TS WHERE TS.tipo_serv_desc = '"
				+ tipoServico.getTipoServico() + "'";

		return bd.retornaRegistro(strQuery);

	}

}
