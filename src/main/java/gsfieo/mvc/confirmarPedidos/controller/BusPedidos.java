package gsfieo.mvc.confirmarPedidos.controller;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.confirmarPedidos.model.Pedidos;
import gsfieo.mvc.principal.connectionFactory.BancoDados;

public class BusPedidos {

	private final BancoDados bd = new BancoDados();

	public void gravar(int index, Pedidos pedidos) {

		String strQuery;

		strQuery = "INSERT INTO pedidos (qtd,subTotal,id_comanda_FK,id_produto_FK)" + " VALUES('"
				+ pedidos.getProdutos().get(index).getQtd() + "'," + "'"
				+ pedidos.getProdutos().get(index).getSubTotal() + "'," + "'" + pedidos.getComanda().getCdComanda()
				+ "'," + "(SELECT P.id_produto FROM produto P WHERE P.produto_desc = '"
				+ pedidos.getProdutos().get(index).getProduto() + "' "
				+ "AND P.id_serv_FK = (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '"
				+ pedidos.getComanda().getServico().getServico() + "')))";

		bd.executaComando(strQuery);

	}

	public String[] buscarPedidos(Pedidos pedidos) {

		String strQuery;

		strQuery = "SELECT C.id_comanda," + "(SELECT L.usuario FROM Login L WHERE L.id_login = C.id_login_FK) ,"
				+ "(SELECT FP.forma_pagamento FROM FORMA_PAGAMENTO FP WHERE FP.id_forma_pagamento = C.id_forma_pagamento_FK) ,"
				+ "(SELECT S.status FROM Status S WHERE S.id_status = C.id_status_FK) ,"
				+ "C.totalCompra  FROM comanda C" + " WHERE (C.id_comanda = '" + pedidos.getComanda().getCdComanda()
				+ "' OR" + " C.id_login_FK = (SELECT L.id_login FROM Login L WHERE L.usuario = '"
				+ pedidos.getComanda().getLogin().getUsuario() + "')) "
				+ "AND C.id_status_FK = 2 AND C.id_serv_FK = (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '"
				+ pedidos.getComanda().getServico().getServico() + "')";

		return bd.retornaRegistro(strQuery);

	}

	public String[] buscarQtdPedidos(Pedidos pedidos) {

		String strQuery;

		strQuery = "SELECT COUNT(C.id_comanda) " + " FROM comanda C WHERE C.id_login_FK = "
				+ "(SELECT L.id_login FROM Login L WHERE L.usuario = '" + pedidos.getComanda().getLogin().getUsuario()
				+ "') AND" + " C.id_serv_FK = (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '"
				+ pedidos.getComanda().getServico().getServico() + "') " + "AND C.id_status_FK = 2";

		return bd.retornaRegistro(strQuery);

	}

	public String[][] buscarPedidosComanda(Pedidos pedidos) {

		String strQuery;

		strQuery = "SELECT (SELECT PR.produto_desc FROM produto PR WHERE PR.id_produto = P.id_produto_FK) ,"
				+ "(SELECT TP.tipo_produto_desc FROM tipo_produto TP WHERE TP.id_tipo_produto ="
				+ " (SELECT PR.id_tipo_produto_FK FROM produto PR WHERE PR.id_produto = P.id_produto_FK)) ,"
				+ "(SELECT PR.valor_unitario FROM produto PR WHERE PR.id_produto = P.id_produto_FK)  "
				+ "FROM pedidos P WHERE P.id_comanda_FK = '" + pedidos.getComanda().getCdComanda() + "'";

		return bd.retorna_N_Registros(strQuery);

	}

}
