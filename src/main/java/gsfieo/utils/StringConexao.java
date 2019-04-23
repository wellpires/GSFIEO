package gsfieo.utils;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StringConexao {

	private String server;
	private String nomeBancoDados;
	private String usuario;
	private String senha;
	private String caminho;

	public StringConexao() {

		Properties propriedades = new Properties();

		try {

			InputStream projetoCaminho = this.getClass().getResourceAsStream("bancoDados.properties");
			propriedades.load(projetoCaminho);

		} catch (FileNotFoundException ex) {
			Logger.getLogger(StringConexao.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(StringConexao.class.getName()).log(Level.SEVERE, null, ex);
		}

		nomeBancoDados = propriedades.getProperty("banco");
		usuario = propriedades.getProperty("usuario");
		senha = propriedades.getProperty("senha");
		server = propriedades.getProperty("servidor");

//        caminho = "jdbc:sqlserver://" + server + ";databaseName=" + nomeBancoDados;
		caminho = String.format("jdbc:postgresql://%s:5432/%s", server, nomeBancoDados);

	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getNomeBancoDados() {
		return nomeBancoDados;
	}

	public void setNomeBancoDados(String nomeBancoDados) {
		this.nomeBancoDados = nomeBancoDados;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCaminho() {

		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

}
