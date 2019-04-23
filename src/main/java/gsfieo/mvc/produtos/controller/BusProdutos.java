package gsfieo.mvc.produtos.controller;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.confirmarPedidos.model.Pedidos;
import gsfieo.mvc.principal.connectionFactory.BancoDados;
import gsfieo.mvc.produtos.model.Produto;

public class BusProdutos {

	private final BancoDados bd = new BancoDados();

	public void gravar(Produto produto) {

		String strQuery;

		strQuery = "INSERT INTO produto (produto_desc,id_tipo_produto_FK,id_serv_FK)" + " VALUES('"
				+ produto.getProduto() + "', (SELECT TP.id_tipo_produto"
				+ " FROM tipo_produto TP WHERE TP.tipo_produto_desc = '" + produto.getTipoProduto().getTipoProduto()
				+ "')" + ", (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + produto.getServicos().getServico()
				+ "'))";

		bd.executaComando(strQuery);

	}

	public void editar(Produto produto) {

		String strQuery;

		strQuery = "UPDATE produto SET produto_desc = '" + produto.getProduto() + "'," + "valor_unitario = '"
				+ produto.getValorUnit() + "',"
				+ "id_tipo_produto = (SELECT TP.id_tipo_produto FROM tipo_produto TP WHERE TP.tipo_produto_desc = '"
				+ produto.getTipoProduto().getTipoProduto() + "') " + "WHERE id_produto = " + produto.getCdProduto();

		bd.executaComando(strQuery);

	}

	public void excluir(Produto produto) {

		String strQuery;

		strQuery = "DELETE FROM produto WHERE id_produto = " + produto.getCdProduto();

		bd.executaComando(strQuery);

	}

	public void editarPreco(Produto produto) {

		String strQuery = "UPDATE produto SET valor_unitario = " + produto.getValorUnit() + " WHERE id_produto = "
				+ produto.getCdProduto();

		bd.executaComando(strQuery);

	}

	public String[][] listarProdutos(Produto produto) {

		String strQuery;

		strQuery = "SELECT P.id_produto ," + "P.produto_desc , "
				+ "(SELECT TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto = P.id_tipo_produto_FK)  "
				+ "FROM produto P WHERE P.id_produto IN (SELECT EP.id_produto_FK FROM estoqueXproduto EP WHERE EP.id_estoque_FK = "
				+ "(SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK ="
				+ " (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + produto.getServicos().getServico()
				+ "')) AND EP.qtdEstoque > 0) AND P.produto_desc LIKE '%" + produto.getProduto() + "%'"
				+ " AND P.id_tipo_produto_FK = (SELECT TP.id_tipo_produto FROM tipo_produto TP WHERE TP.tipo_produto_desc LIKE '%"
				+ produto.getTipoProduto().getTipoProduto() + "%' AND TP.id_tipo_produto = P.id_tipo_produto_FK)";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[][] listarProdutosComPreco(Produto produto) {

		String strQuery;

		strQuery = "SELECT P.id_produto ," + "P.produto_desc , "
				+ "(SELECT TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto = P.id_tipo_produto_FK)  "
				+ ",P.valor_unitario "
				+ "FROM produto P WHERE P.id_produto IN (SELECT EP.id_produto_FK FROM estoqueXproduto EP WHERE EP.id_estoque_FK = "
				+ "(SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK ="
				+ " (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + produto.getServicos().getServico()
				+ "')) AND EP.qtdEstoque > 0) AND P.produto_desc LIKE '%" + produto.getProduto() + "%'"
				+ " AND P.id_tipo_produto_FK = (SELECT TP.id_tipo_produto FROM tipo_produto TP WHERE TP.tipo_produto_desc LIKE '%"
				+ produto.getTipoProduto().getTipoProduto() + "%' AND TP.id_tipo_produto = P.id_tipo_produto_FK)";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[][] pesquisarProdutosCardapio(Produto produto) {

		String strQuery;

		strQuery = "SELECT P.id_produto ," + "P.produto_desc , "
				+ "(SELECT TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto = P.id_tipo_produto_FK)  "
				+ "FROM produto P WHERE P.id_produto IN (SELECT EP.id_produto_FK FROM estoqueXproduto EP WHERE EP.id_estoque_FK = "
				+ "(SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK ="
				+ " (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + produto.getServicos().getServico()
				+ "')) AND EP.qtdEstoque > 0) AND P.produto_desc LIKE '%" + produto.getProduto() + "%'"
				+ " AND P.id_tipo_produto = (SELECT TP.id_tipo_produto_FK FROM tipo_produto TP WHERE TP.tipo_produto_desc LIKE '%"
				+ produto.getTipoProduto().getTipoProduto() + "%' AND TP.id_tipo_produto = P.id_tipo_produto_FK)";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[] listarInfoProduto(Produto produto) {

		String strQuery;

		strQuery = "SELECT P.produto_desc,"
				+ "(SELECT TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto = P.id_tipo_produto_FK),"
				+ "valor_unitario FROM produto P WHERE P.id_produto = " + produto.getCdProduto();

		return bd.retornaRegistro(strQuery);

	}

	public String[][] pesquisarProduto(Produto produto) {

		String strQuery;

		strQuery = "SELECT P.id_produto," + "P.produto_desc, "
				+ "(SELECT TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto = P.id_tipo_produto_FK) FROM produto P"
				+ " WHERE P.produto_desc = '" + produto.getProduto()
				+ "' OR P.id_tipo_produto_FK = (SELECT TP.id_tipo_produto FROM tipo_produto"
				+ " TP WHERE TP.tipo_produto_desc = '" + produto.getTipoProduto().getTipoProduto() + "')";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[] pegarProduto(Produto produto) {

		String strQuery;

		strQuery = "SELECT P.id_produto," + "P.produto_desc,"
				+ "(SELECT TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto = P.id_tipo_produto_fk) FROM produto P"
				+ " WHERE P.id_produto = " + produto.getCdProduto();

		return bd.retornaRegistro(strQuery);

	}

	public String[][] pesquisarProdutos(Produto produto) {

		String strQuery;

		strQuery = "SELECT P.id_produto," + "P.produto_desc,"
				+ "(SELECT TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto = P.id_tipo_produto_FK)"
				+ " FROM produto P WHERE P.id_produto IN"
				+ " (SELECT EP.id_produto_FK FROM estoqueXproduto EP WHERE EP.id_estoque_FK ="
				+ " (SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK ="
				+ " (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + produto.getServicos().getServico()
				+ "'))) AND P.produto_desc LIKE '%" + produto.getProduto();

		return bd.retorna_N_Registros(strQuery);

	}

	public String[][] pesquisarProdutos_2(Produto produto) {

		String strQuery;

		strQuery = "SELECT P.id_produto," + "P.produto_desc,"
				+ "(SELECT TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto = P.id_tipo_produto_FK) "
				+ "FROM produto P WHERE"
				+ " P.id_tipo_produto_FK = (SELECT TP.id_tipo_produto FROM tipo_produto TP WHERE TP.tipo_produto_desc = '"
				+ produto.getTipoProduto().getTipoProduto() + "')";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[][] listarProdutosCardapio(Produto produto) {

		String strQuery;

		strQuery = "SELECT P.produto_desc,P.valor_unitario FROM produto P WHERE P.produto_desc = '"
				+ produto.getProduto() + "'";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[][] listarProdutos_2(Produto produto) {

		String strQuery;

		strQuery = "SELECT P.id_produto ," + "P.produto_desc ,"
				+ " (SELECT TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto = P.id_tipo_produto_FK) "
				+ " FROM produto P WHERE P.id_produto IN"
				+ " (SELECT EP.id_produto_FK FROM estoqueXproduto EP WHERE EP.id_estoque_FK ="
				+ " (SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK ="
				+ " (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + produto.getServicos().getServico()
				+ "')))";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[] verificarExisteProduto(Produto produto) {

		String strQuery;

		strQuery = "SELECT P.id_produto ," + "P.produto_desc ,"
				+ " (SELECT TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto = P.id_tipo_produto_FK) "
				+ " FROM produto P WHERE P.id_produto IN"
				+ " (SELECT EP.id_produto_FK FROM estoqueXproduto EP WHERE EP.id_estoque_FK ="
				+ " (SELECT E.id_estoque FROM estoque E WHERE E.id_serv_FK ="
				+ " (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + produto.getServicos().getServico()
				+ "')) AND EP.qtdEstoque > 0) AND P.produto_desc = '" + produto.getProduto() + "'";

		return bd.retornaRegistro(strQuery);

	}

	public String[][] buscarProdutos(Pedidos pedidos) {

		String strQuery;

		strQuery = "SELECT (SELECT PR.produto_desc FROM produto PR WHERE PR.id_produto = P.id_produto_FK) ,"
				+ "(SELECT PR.valor_unitario FROM produto PR WHERE PR.id_produto = P.id_produto_FK) ,"
				+ "P.qtd  FROM pedidos P WHERE P.id_comanda_FK = '" + pedidos.getComanda().getCdComanda() + "'"
				+ " AND P.id_comanda_FK IN (SELECT C.id_comanda FROM comanda C WHERE C.id_status_FK = 2)";

		return bd.retorna_N_Registros(strQuery);

	}

}
