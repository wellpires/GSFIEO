package gsfieo.mvc.estoque.controller;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.estoque.model.Estoque;
import gsfieo.mvc.principal.connectionFactory.BancoDados;
import gsfieo.mvc.produtos.model.Produto;

public class BusEstoque {

	private final BancoDados bd = new BancoDados();

	public void daBaixaEstoque(Estoque estoque) {

		String strQuery = String.format("CALL SP_DA_BAIXA_ESTOQUE(%d, '%s', '%s')", estoque.getQtd(),
				estoque.getServico().getServico(), estoque.getProduto().getProduto());

		bd.executaComando(strQuery);

	}

	public void gravarEstoque(Estoque estoque) {

		String strQuery;

		strQuery = "INSERT INTO estoque (id_serv_FK)"
				+ " VALUES ((SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + estoque.getServico().getServico()
				+ "'))";

		bd.executaComando(strQuery);

	}

	public void gravarProdutoEstoque(Estoque estoque) {

		String strQuery;

		strQuery = "INSERT INTO estoqueXproduto (id_produto_FK,id_estoque_FK)"
				+ " VALUES ((SELECT P.id_produto FROM produto P WHERE P.produto_desc = '"
				+ estoque.getProduto().getProduto() + "'"
				+ " AND P.id_serv_FK = (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '"
				+ estoque.getServico().getServico() + "')),"
				+ "(SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK = (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '"
				+ estoque.getServico().getServico() + "')))";

		bd.executaComando(strQuery);

	}

	public void editar(Estoque estoque) {

		String strQuery;

		strQuery = "UPDATE estoqueXproduto SET qtdEstoque = '" + estoque.getQtd() + "'," + " qtdMaxima = '"
				+ estoque.getQtdMaxima() + "'," + " qtdMinima = '" + estoque.getQtdMinima() + "' "
				+ "WHERE id_produto_FK = (SELECT P.id_produto FROM produto P WHERE P.produto_desc = '"
				+ estoque.getProduto().getProduto() + "'"
				+ " AND id_produto IN (SELECT EP.id_produto_FK FROM estoqueXproduto EP WHERE EP.id_estoque_FK ="
				+ " (SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK = (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '"
				+ estoque.getServico().getServico() + "'))))"
				+ " AND id_estoque_FK = (SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK ="
				+ " (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + estoque.getServico().getServico() + "'))";

		bd.executaComando(strQuery);

	}

	public String[] listarProdutosEstoque(Estoque estoque) {

		String strQuery;

		strQuery = "SELECT (SELECT P.produto_desc FROM produto P WHERE P.id_produto = EP.id_produto_FK) ,"
				+ "EP.qtdMinima ," + "EP.qtdMaxima ," + "EP.qtdEstoque "
				+ " FROM estoqueXproduto EP WHERE EP.id_estoque_FK ="
				+ " (SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK ="
				+ " (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + estoque.getServico().getServico() + "'))"
				+ " AND EP.id_produto_FK = (SELECT P.id_produto FROM produto P WHERE P.produto_desc = '"
				+ estoque.getProduto().getProduto() + "' AND id_produto = EP.id_produto_FK)";

		return bd.retornaRegistro(strQuery);

	}

	public void excluirVinculacaoProdutoEstoque(Produto produto) {

		String strQuery = "DELETE FROM estoqueXproduto WHERE id_produto_fk = %d";

		bd.executaComando(String.format(strQuery, produto.getCdProduto()));

	}

}
