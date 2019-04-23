package gsfieo.mvc.cardapio.controller;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.cardapio.model.Cardapio;
import gsfieo.mvc.principal.connectionFactory.BancoDados;

public class BusCardapio {

	private final BancoDados bd = new BancoDados();

	public String[][] buscarCardapioPedidos(String orderBy, Cardapio cardapio) {

		String strQuery;

		strQuery = "SELECT P.produto_desc, P.valor_unitario FROM produto P WHERE P.id_serv_FK ="
				+ " (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + cardapio.getServicos().getServico()
				+ "') AND (SELECT EP.qtdEstoque FROM estoqueXproduto EP WHERE EP.id_produto_FK = P.id_produto)"
				+ " > (SELECT EP.qtdMinima FROM estoqueXproduto EP WHERE EP.id_produto_FK = P.id_produto) AND"
				+ " P.produto_desc LIKE '%" + cardapio.getProduto().getProduto()
				+ "%' AND P.id_produto IN (SELECT EP.id_produto_FK FROM estoqueXproduto EP WHERE"
				+ " EP.id_estoque_FK = (SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK = (SELECT S.id_serv FROM servico S"
				+ " WHERE S.serv_desc = '" + cardapio.getServicos().getServico()
				+ "')) AND EP.id_produto_FK = P.id_produto) AND P.valor_unitario <> 0.00 ORDER BY  P.valor_unitario "
				+ orderBy;

		return bd.retorna_N_Registros(strQuery);

	}

}
