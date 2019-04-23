package gsfieo.mvc.principal.connectionFactory;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.utils.StringConexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BancoDados {

	private Statement sta;
	private ResultSet rs;
	private static Connection con = null;
	private final static String driver = "org.postgresql.Driver";
	private final String caminho; // Outro computador: jdbc:sqlserver://localhost:8089;databaseName=GSFIEO
	private final String usuario;
	private final String senha;

	public BancoDados() {

		StringConexao sc = new StringConexao();

		caminho = sc.getCaminho();
		usuario = sc.getUsuario();
		senha = sc.getSenha();

	}

	private boolean conexao() {

		try {

			Class.forName(driver);

			con = DriverManager.getConnection(caminho, usuario, senha);
			sta = con.createStatement();

			return true;

		} catch (ClassNotFoundException | SQLException e) {

			System.out.println("Erro ao conectar com o banco:\n " + e.toString());

			return false;

		}

	}

	public void executaComando(String sql) {

		/*
		 * EXECUTA COMANDOS SEM RETORNO DE INFORMAÇÕES EXEMPLOS: DELETE, INSERT, UPDATE
		 * E ETC...
		 */

		if (conexao()) {

			try {

				sta.executeUpdate(sql);

			} catch (SQLException e) {

				System.out.println("Erro ao executar um comando:\n " + e.toString());

			}

		}

	}

	public String[][] retorna_N_Registros(String sql) {

		// RETORNA VÁRIOS REGISTROS

		String dado;
		String[][] nRegistro = null;
		int linha, coluna;

		linha = 0;

		if (conexao()) {

			try {

				rs = sta.executeQuery(sql);

				while (rs.next()) {

					linha++;

				}

				coluna = rs.getMetaData().getColumnCount();

				nRegistro = new String[linha][coluna];

				int auxLinha, auxColuna;

				auxLinha = 0;
				auxColuna = 0;

				rs = sta.executeQuery(sql);

				while (rs.next()) {

					for (int i = 1; i <= coluna; i++) {

						dado = rs.getString(i);

						nRegistro[auxLinha][auxColuna] = dado;

						auxColuna++;

					}

					auxColuna = 0;
					auxLinha++;
				}

			} catch (SQLException e) {

				System.out.println("Erro ao executar um comando que retorna vários registros:\n " + e.toString());

			} finally {

				fecharConexoes();

			}

		}

		return nRegistro;

	}

	public String[] retornaRegistro(String sql) {

		// RETORNA UM REGISTRO

		String[] nRegistro = null;

		if (conexao()) {

			try {

				int coluna;

				rs = sta.executeQuery(sql);

				coluna = rs.getMetaData().getColumnCount();

				nRegistro = new String[coluna];

				while (rs.next()) {

					for (int i = 0; i < coluna; i++) {

						nRegistro[i] = rs.getString(i + 1);

					}
				}

			} catch (SQLException e) {

				System.out.println("Erro ao executar um comando que retorna um registro:\n " + e.toString());

			} finally {

				fecharConexoes();

			}

		}

		return nRegistro;

	}

	private void fecharConexoes() {

		fecharResultSet();
		fecharStatement();
		fecharConexaoBanco();

	}

	private void fecharResultSet() {

		try {

			if (rs.isClosed() == false) {

				rs.close();

			}

		} catch (SQLException e) {

			System.out.println("Erro ao fechar o Result Set:\n " + e.toString());

		}

	}

	private void fecharStatement() {

		try {

			if (sta.isClosed() == false) {

				sta.close();

			}

		} catch (SQLException e) {

			System.out.println("Erro ao fechar o Statement:\n " + e.toString());

		}

	}

	private void fecharConexaoBanco() {

		try {

			if (con.isClosed() == false) {

				con.close();

			}

		} catch (SQLException e) {

			System.out.println("Erro ao fechar a conexão com o Banco de Dados:\n " + e.toString());

		}

	}

}