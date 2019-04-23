package gsfieo.mvc.tipoProduto.controller;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.principal.connectionFactory.BancoDados;
import gsfieo.mvc.tipoProduto.model.TipoProduto;

public class BusTipoProduto {

	private final BancoDados bd = new BancoDados();

	public void gravar(TipoProduto tipoProduto) {

		String strQuery;

		strQuery = "INSERT INTO tipo_produto(tipo_produto_desc) VALUES ('" + tipoProduto.getTipoProduto() + "')";

		bd.executaComando(strQuery);

	}

	public void editar(TipoProduto tipoProduto) {

		String strQuery;

		strQuery = "UPDATE tipo_produto SET tipo_produto_desc = '" + tipoProduto.getTipoProduto()
				+ "' WHERE id_tipo_produto = " + tipoProduto.getCdTipoProduto();

		bd.executaComando(strQuery);

	}

	public void excluir(TipoProduto tipoProduto) {

		String strQuery;

		strQuery = "DELETE FROM tipo_produto WHERE id_tipo_produto = '" + tipoProduto.getCdTipoProduto() + "'";

		bd.executaComando(strQuery);

	}

	public String[][] listarTipoProduto(TipoProduto tipoProduto) {

		String strQuery;

		strQuery = "SELECT TP.id_tipo_produto,"
				+ "TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.tipo_produto_desc LIKE '%"
				+ tipoProduto.getTipoProduto() + "%'";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[] listarInfoTipoProduto(TipoProduto tipoProduto) {

		String strQuery;

		strQuery = "SELECT TP.id_tipo_produto,TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto = "
				+ tipoProduto.getCdTipoProduto();

		return bd.retornaRegistro(strQuery);

	}

	public String[] verificaTipoProdutoExiste(TipoProduto tipoProduto) {

		String strQuery;

		strQuery = "SELECT TP.tipo_produto_desc  FROM tipo_produto TP WHERE TP.tipo_produto_desc = '"
				+ tipoProduto.getTipoProduto() + "'";

		return bd.retornaRegistro(strQuery);

	}

}
