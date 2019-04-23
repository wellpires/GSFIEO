package gsfieo.mvc.comanda.controller;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.comanda.model.Comanda;
import gsfieo.mvc.principal.connectionFactory.BancoDados;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class BusComanda {

	private final BancoDados bd = new BancoDados();

	public String popularVetores() {

		int MAX = 10;

		String codigo = "";

		int valoresInteiros[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		String valoresTextos[] = new String[MAX];

		String codigoFinal[] = new String[MAX];

		for (int i = 0; i < MAX; i++) {

			int valor;

			do {

				valor = gerarValorRandomico(35, 123);

			} while (verificarValores(valor) == false);

			valoresTextos[i] = String.valueOf((char) valor);

		}

		int cont = 0;

		while (cont < MAX - 1) {

			codigoFinal[cont] = valoresTextos[gerarValorRandomico(0, 9)];
			cont++;
			codigoFinal[cont] = String.valueOf(valoresInteiros[gerarValorRandomico(0, 9)]);
			cont++;

		}

		for (String valor : codigoFinal) {

			codigo += valor;

		}

		return codigo;

	}

	private boolean verificarValores(int valor) {

		int valores[] = { 35, 36, 38, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73,
				74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104,
				105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };
		boolean flag = false;

		for (int aux : valores) {

			if (aux == valor) {

				flag = true;

			}

		}

		return flag;

	}

	private int gerarValorRandomico(int min, int max) {

		Random gerar = new Random();

		int valor;

		valor = gerar.nextInt(max - min) + min;

		return valor;

	}

	public void gravar(Comanda comanda) {

		String strQuery;

		strQuery = "INSERT INTO comanda(id_comanda,id_status_FK,totalCompra,qtdTotal,dataVenda,id_forma_pagamento_FK,id_serv_FK,id_login_FK) VALUES"
				+ " ('" + comanda.getCdComanda() + "'," + "'" + comanda.getStatus() + "'," + "'"
				+ comanda.getTotalCompra() + "'," + "'" + comanda.getQtdTotal() + "'," + "'" + pegarDataHoje() + "',"
				+ "(SELECT FP.id_forma_pagamento FROM FORMA_PAGAMENTO FP WHERE FP.forma_pagamento = '"
				+ comanda.getFormaPgto().getFormaPagto_2() + "'),"
				+ "(SELECT S.id_serv FROM servico S WHERE S.serv_desc = '" + comanda.getServico().getServico() + "'),"
				+ "(SELECT L.id_login FROM Login L WHERE L.usuario = '" + comanda.getLogin().getUsuario() + "'))";

		bd.executaComando(strQuery);

	}

	public void alterarStatus(Comanda comanda) {

		String strQuery;

		strQuery = "UPDATE comanda SET id_status_FK = " + comanda.getStatus() + "," + "dataConfirmacao = '"
				+ pegarDataHoje() + "' WHERE id_comanda = '" + comanda.getCdComanda() + "'";

		bd.executaComando(strQuery);

	}

	public String[] verificarCodigo(Comanda comanda) {

		String strQuery;

		strQuery = "SELECT C.id_comanda FROM comanda C WHERE C.id_comanda = '" + comanda.getCdComanda() + "'";

		return bd.retornaRegistro(strQuery);

	}

	private String pegarDataHoje() {

		Date dataHoje = new Date();
		DateFormat hoje = DateFormat.getDateInstance();

		return hoje.format(dataHoje);

	}

	public String[][] buscarComandas(Comanda comanda) {

		String strQuery;

		strQuery = "SELECT C.id_comanda, C.qtdTotal, C.totalCompra,"
				+ "(SELECT S.status FROM Status S WHERE S.id_status = C.id_status_FK),"
				+ "C.dataVenda, C.dataConfirmacao "
				+ "FROM comanda C WHERE C.id_serv_FK = (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '"
				+ comanda.getServico().getServico() + "') "
				+ "AND C.id_status_FK = (SELECT S.id_status FROM Status S WHERE S.status LIKE '" + comanda.getStatus()
				+ "%' AND S.id_status = C.id_status_FK)";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[][] buscarStatus() {

		String strQuery;

		strQuery = "SELECT S.status FROM Status S";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[][] listarComandas(Comanda comanda) {

		String strQuery;

		strQuery = "SELECT C.id_comanda, C.totalCompra, C.qtdTotal, C.dataVenda FROM comanda C"
				+ " WHERE C.id_login_FK = (SELECT L.id_login FROM Login L WHERE L.usuario = '"
				+ comanda.getLogin().getUsuario() + "') AND"
				+ " C.id_serv_FK = (SELECT S.id_serv FROM servico S WHERE S.serv_desc = '"
				+ comanda.getServico().getServico() + "') AND " + "C.id_status_FK = 2 AND C.id_comanda LIKE '%"
				+ comanda.getCdComanda() + "%'";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[][] listarComandasUsuario(Comanda comanda) {

		String strQuery;

		strQuery = "SELECT C.id_comanda, (SELECT S.status FROM Status S WHERE S.id_status = C.id_status_FK),"
				+ "C.totalCompra, (SELECT S.serv_desc FROM servico S WHERE S.id_serv = C.id_serv_FK),"
				+ "(SELECT FP.forma_pagamento FROM FORMA_PAGAMENTO FP WHERE FP.id_forma_pagamento = C.id_forma_pagamento_FK) "
				+ " FROM comanda C WHERE C.id_login_FK = (SELECT L.id_login FROM Login L WHERE L.usuario = '"
				+ comanda.getLogin().getUsuario() + "') AND" + " C.id_comanda LIKE '%" + comanda.getCdComanda()
				+ "%' OR" + " C.id_status_FK = (SELECT S.id_status FROM Status S WHERE S.status = '"
				+ comanda.getStatus() + "') OR "
				+ " C.id_serv_FK = (SELECT S.id_serv FROM servico S WHERE S.serv_desc LIKE '%"
				+ comanda.getServico().getServico() + "%') OR"
				+ " C.id_forma_pagamento_FK = (SELECT FP.id_forma_pagamento FROM FORMA_PAGAMENTO FP WHERE FP.forma_pagamento LIKE '%"
				+ comanda.getFormaPgto().getFormaPagto() + "%')";

		return bd.retorna_N_Registros(strQuery);

	}

	public String[] detalharComanda(Comanda comanda) {

		String strQuery;

		strQuery = "SELECT (SELECT S.serv_desc FROM servico S WHERE S.id_serv = C.id_serv_FK),"
				+ "C.dataVenda, C.dataConfirmacao," + "C.qtdTotal, C.totalCompra,"
				+ "(SELECT FP.forma_pagamento FROM FORMA_PAGAMENTO FP WHERE FP.id_forma_pagamento = C.id_forma_pagamento_FK),"
				+ " (SELECT S.status FROM Status S WHERE S.id_status = C.id_status_FK) FROM comanda C WHERE C.id_comanda = '"
				+ comanda.getCdComanda() + "'";

		return bd.retornaRegistro(strQuery);

	}

}
