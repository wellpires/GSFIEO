package gsfieo.mvc.principal.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.cardapio.view.LytCardapio;
import gsfieo.mvc.comprar.view.LytComprarProduto;
import gsfieo.mvc.confirmarPedidos.view.LytConfirmarPedido;
import gsfieo.mvc.consultarPedidos.view.LytConsultarPedidos;
import gsfieo.mvc.estoque.view.LytGerenciadorEstoque;
import gsfieo.mvc.login.controller.BusLogin;
import gsfieo.mvc.login.model.Login;
import gsfieo.mvc.login.view.LytUsuario;
import gsfieo.mvc.produtos.view.LytProduto;
import gsfieo.mvc.servico.model.Servicos;
import gsfieo.mvc.servico.view.LytServicos;
import gsfieo.mvc.tipoProduto.view.LytTipoProduto;
import gsfieo.mvc.tipoServico.view.LytTipoServico;
import gsfieo.mvc.verificaPedidos.view.LytVerificarPedidos;
import gsfieo.utils.informacoes.ConstantesImagens;

public class LytGSFIEO extends JFrame implements ActionListener {

	private static final long serialVersionUID = -4159614927171669857L;

	private final ConstantesImagens c = new ConstantesImagens();
	private final BusLogin busLogin = new BusLogin();
//	private final BusServico busServico = new BusServico();
	private Login login = new Login();

//	private final int CD_SERVICO;

	JMenuBar menuBar;

	// --------- LOGIN
	JButton btnLogin;

	// --------- TIPO SERVICO
	JButton btnTipoServico;

	// --------- SERVIÇOS
	JButton btnServicos;

	// --------- TIPO PRODUTO
	JButton btnTipoProduto;

	// --------- PRODUTO
	JButton btnProdutos;

	// --------- CARDÁPIO
	JButton btnCardapio;

	// -------- COMPRAR PRODUTO
	JButton btnComprar;

	// -------- CONFIRMAR PEDIDOS
	JButton btnConfirmarPedidos;

	// -------- VERIFICAR PEDIDOS
	JButton btnVerificarPedidos;

	// -------- ESTOQUE
	JButton btnEstoque;

	// -------- MEUS PEDIDOS
	JButton btnMeusPedidos;

	// --------- LOGOFF
	JButton btnLogoff;

	// -------- LISTAR PERFIL
	JPanel pnPerfil;

	// --- USUÁRIO e PERMISSÃO
	JLabel lblUsuario, lblPermissao, lblServico;
	JTextField txtUsuario, txtPermissao, txtServico;

	// --- USUÁRIO e PERMISSÃO
	JLabel lblInformacoes;

	public LytGSFIEO(Login login) {

		setTitle("GSFIEO - TELA PRINCIPAL");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		setLayout(null);
		this.login = login;

		// ================ CRIANDO OS MENUS ===================================
		menuBar = new JMenuBar();

		// --------- MENU 'Login'
		ImageIcon icLogin = new ImageIcon(this.getClass().getResource(c.getLogin()));
		btnLogin = new JButton(icLogin);

		btnLogin.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnLogin.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLogin.setText("Login");
		btnLogin.setFocusPainted(false);
		btnLogin.addActionListener(LytGSFIEO.this);

		menuBar.add(btnLogin);

		// --------- MENU 'Tipo Serviços'
		ImageIcon icTipoServ = new ImageIcon(this.getClass().getResource(c.getTipoServico()));
		btnTipoServico = new JButton(icTipoServ);

		btnTipoServico.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnTipoServico.setHorizontalTextPosition(SwingConstants.CENTER);
		btnTipoServico.setText("Tipo de Serviço");
		btnTipoServico.setFocusPainted(false);
		btnTipoServico.addActionListener(LytGSFIEO.this);

		menuBar.add(btnTipoServico);

		// --------- MENU 'Serviços'
		ImageIcon icServico = new ImageIcon(this.getClass().getResource(c.getServicos()));
		btnServicos = new JButton(icServico);

		btnServicos.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnServicos.setHorizontalTextPosition(SwingConstants.CENTER);
		btnServicos.setText("Serviços");
		btnServicos.setFocusPainted(false);
		btnServicos.addActionListener(LytGSFIEO.this);

		menuBar.add(btnServicos);

		// --------- MENU 'Tipo de Produtos'
		ImageIcon icTipoProduto = new ImageIcon(this.getClass().getResource(c.getTipoProduto()));
		btnTipoProduto = new JButton(icTipoProduto);

		btnTipoProduto.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnTipoProduto.setHorizontalTextPosition(SwingConstants.CENTER);
		btnTipoProduto.setText("Tipo de Produto");
		btnTipoProduto.setFocusPainted(false);
		btnTipoProduto.addActionListener(LytGSFIEO.this);

		menuBar.add(btnTipoProduto);

		// --------- MENU 'Produtos'
		ImageIcon icProduto = new ImageIcon(this.getClass().getResource(c.getProduto()));
		btnProdutos = new JButton(icProduto);

		btnProdutos.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnProdutos.setHorizontalTextPosition(SwingConstants.CENTER);
		btnProdutos.setText("Produtos");
		btnProdutos.setFocusPainted(false);
		btnProdutos.addActionListener(LytGSFIEO.this);

		menuBar.add(btnProdutos);

		// --------- MENU 'Estoque'
		ImageIcon icEstoque = new ImageIcon(this.getClass().getResource(c.getEstoque()));
		btnEstoque = new JButton(icEstoque);

		btnEstoque.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEstoque.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEstoque.setText("Estoque");
		btnEstoque.setFocusPainted(false);
		btnEstoque.addActionListener(LytGSFIEO.this);

		menuBar.add(btnEstoque);

		// --------- MENU 'Cardapio'
		ImageIcon icCardapio = new ImageIcon(this.getClass().getResource(c.getCadapio()));
		btnCardapio = new JButton(icCardapio);

		btnCardapio.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCardapio.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCardapio.setText("Cardápio");
		btnCardapio.setFocusPainted(false);
		btnCardapio.addActionListener(LytGSFIEO.this);

		menuBar.add(btnCardapio);

		// --------- MENU 'Comprar Produtos'
		ImageIcon icComprar = new ImageIcon(this.getClass().getResource(c.getComprarProdutos()));
		btnComprar = new JButton(icComprar);

		btnComprar.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnComprar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnComprar.setText("Comprar produtos");
		btnComprar.setFocusPainted(false);
		btnComprar.addActionListener(LytGSFIEO.this);

		menuBar.add(btnComprar);

		// --------- MENU 'Confirmar Pedidos'
		ImageIcon icConfirmarPedido = new ImageIcon(this.getClass().getResource(c.getPermissoes()));
		btnConfirmarPedidos = new JButton(icConfirmarPedido);

		btnConfirmarPedidos.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnConfirmarPedidos.setHorizontalTextPosition(SwingConstants.CENTER);
		btnConfirmarPedidos.setText("Confirmar Pedidos");
		btnConfirmarPedidos.setFocusPainted(false);
		btnConfirmarPedidos.addActionListener(LytGSFIEO.this);

		menuBar.add(btnConfirmarPedidos);

		// --------- MENU 'Verificar Pedidos'
		ImageIcon icVerificarPedido = new ImageIcon(this.getClass().getResource(c.getVerificarPedido()));
		btnVerificarPedidos = new JButton(icVerificarPedido);

		btnVerificarPedidos.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnVerificarPedidos.setHorizontalTextPosition(SwingConstants.CENTER);
		btnVerificarPedidos.setText("Verificar Pedidos");
		btnVerificarPedidos.setFocusPainted(false);
		btnVerificarPedidos.addActionListener(LytGSFIEO.this);

		menuBar.add(btnVerificarPedidos);

		// --------- MENU 'Meus Pedidos'
		ImageIcon icMeusPedidos = new ImageIcon(this.getClass().getResource(c.getInformacao64x64()));
		btnMeusPedidos = new JButton(icMeusPedidos);

		btnMeusPedidos.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnMeusPedidos.setHorizontalTextPosition(SwingConstants.CENTER);
		btnMeusPedidos.setText("Meus Pedidos");
		btnMeusPedidos.setFocusPainted(false);
		btnMeusPedidos.addActionListener(LytGSFIEO.this);

		menuBar.add(btnMeusPedidos);

		setJMenuBar(menuBar);

		/* =================================== */

		/* ================ CRIANDO OS BOTÕES =================================== */

		// --------- BOTÃO 'Logoff'
		ImageIcon icLogoff = new ImageIcon(this.getClass().getResource(c.getLogoff()));
		btnLogoff = new JButton(icLogoff);
		btnLogoff.setLocation(1280, 530); //
		btnLogoff.setSize(80, 80);
		btnLogoff.setBackground(Color.red);
		btnLogoff.addActionListener(LytGSFIEO.this);

		add(btnLogoff);

		/* =================================== */
		/* ================ LISTANDO PERFIL ================================= */

		pnPerfil = new JPanel();
		pnPerfil.setSize(450, 260);
		pnPerfil.setLocation(910, 0);
		pnPerfil.setBorder(BorderFactory.createTitledBorder("DADOS:"));
		pnPerfil.setLayout(null);

		add(pnPerfil);

		// ---- USUÁRIO
		lblUsuario = new JLabel("Usuário:");
		lblUsuario.setSize(100, 25);
		lblUsuario.setLocation(10, 20);
		lblUsuario.setFont(lblUsuario.getFont().deriveFont(20f));

		pnPerfil.add(lblUsuario);

		txtUsuario = new JTextField("asdasdasd".toUpperCase());
		txtUsuario.setSize(400, 35);
		txtUsuario.setLocation(20, 50);
		txtUsuario.setEditable(false);
		txtUsuario.setFont(txtUsuario.getFont().deriveFont(20f));

		pnPerfil.add(txtUsuario);

		// ---- PERFIL
		lblPermissao = new JLabel("Permissão: ");
		lblPermissao.setSize(130, 25);
		lblPermissao.setLocation(10, 95);
		lblPermissao.setFont(lblPermissao.getFont().deriveFont(20f));

		pnPerfil.add(lblPermissao);

		txtPermissao = new JTextField("asdasdasd".toUpperCase());
		txtPermissao.setSize(400, 35);
		txtPermissao.setLocation(20, 125);
		txtPermissao.setEditable(false);
		txtPermissao.setFont(txtPermissao.getFont().deriveFont(20f));

		pnPerfil.add(txtPermissao);

		// ---- SERVICO
		lblServico = new JLabel("Serviço: ");
		lblServico.setSize(130, 25);
		lblServico.setLocation(10, 170);
		lblServico.setFont(lblServico.getFont().deriveFont(20f));

		pnPerfil.add(lblServico);

		txtServico = new JTextField();
		txtServico.setSize(400, 35);
		txtServico.setLocation(20, 200);
		txtServico.setEditable(false);
		txtServico.setFont(txtServico.getFont().deriveFont(20f));

		pnPerfil.add(txtServico);

		/* =================================================== */
		/* ================ CRIANDO PERFIL =================================== */

		if (login.getPermissao().getPermissao() != null) {

			switch (login.getPermissao().getPermissao().toUpperCase()) {

			case "ADMINISTRADOR":

				btnLogin.setVisible(true);
				btnTipoServico.setVisible(true);
				btnServicos.setVisible(true);
				btnTipoProduto.setVisible(true);

				btnProdutos.setVisible(false);
				btnCardapio.setVisible(false);
				btnComprar.setVisible(false);
				btnConfirmarPedidos.setVisible(false);
				btnVerificarPedidos.setVisible(false);
				btnEstoque.setVisible(false);
				btnMeusPedidos.setVisible(false);

				lblServico.setVisible(true);
				txtServico.setVisible(true);

				listarInfo();

				break;

			case "MODERADOR":

				btnLogin.setVisible(false);
				btnTipoServico.setVisible(false);
				btnServicos.setVisible(false);
				btnTipoProduto.setVisible(false);
				btnComprar.setVisible(false);
				btnMeusPedidos.setVisible(false);

				btnProdutos.setVisible(true);
				btnCardapio.setVisible(true);
				btnConfirmarPedidos.setVisible(true);
				btnVerificarPedidos.setVisible(true);
				btnEstoque.setVisible(true);

				lblServico.setVisible(true);
				txtServico.setVisible(true);

				listarInfo();

				break;

			case "PADRÃO":

				btnLogin.setVisible(false);
				btnTipoServico.setVisible(false);
				btnServicos.setVisible(false);
				btnTipoProduto.setVisible(false);

				btnProdutos.setVisible(false);
				btnCardapio.setVisible(false);
				btnConfirmarPedidos.setVisible(false);
				btnVerificarPedidos.setVisible(false);
				btnEstoque.setVisible(false);

				btnComprar.setVisible(true);
				btnMeusPedidos.setVisible(true);

				lblServico.setVisible(false);
				txtServico.setVisible(false);

				listarPadraoPerfil();

				break;

			}
		}

		Servicos servicos = new Servicos();
		servicos.setServico(txtServico.getText());

//		if (busServico.buscarCodServico(servicos)[0] == null) {
//
//			CD_SERVICO = 0;
//
//		} else {
//
//			CD_SERVICO = Integer.parseInt(busServico.buscarCodServico(servicos)[0]);
//
//		}

	}

	public static void main(String[] args) {

		JFrame janela = new LytGSFIEO(null);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnLogin) {

			CadastroConsulta l = new LytUsuario(null, true);
			l.setVisible(true);

		} else if (e.getSource() == btnTipoServico) {

			CadastroConsulta tipo = new LytTipoServico(null, true);
			tipo.setVisible(true);

		} else if (e.getSource() == btnServicos) {

			CadastroConsulta servico = new LytServicos(null, true);
			servico.setVisible(true);

		} else if (e.getSource() == btnTipoProduto) {

			CadastroConsulta tipoProduto = new LytTipoProduto(null, true);
			tipoProduto.setVisible(true);

		} else if (e.getSource() == btnProdutos) {

			CadastroConsulta produto = new LytProduto(null, true, txtServico.getText());
			produto.setVisible(true);

		} else if (e.getSource() == btnEstoque) {

			JDialog janela = new LytGerenciadorEstoque(null, true, txtServico.getText());
			janela.setVisible(true);

		} else if (e.getSource() == btnCardapio) {

			Servicos servicos = new Servicos();

			servicos.setCdServicos(listarInfo());
			servicos.setServico(txtServico.getText());

			JDialog cardapio = new LytCardapio(null, true, servicos);
			cardapio.setVisible(true);

		} else if (e.getSource() == btnConfirmarPedidos) {

			JDialog janela = new LytConfirmarPedido(null, true, txtServico.getText());
			janela.setVisible(true);

		} else if (e.getSource() == btnComprar) {

			Servicos servicos = new Servicos();
			servicos.getLogin().setUsuario(txtUsuario.getText());
			servicos.setServico(txtServico.getText());

			JDialog janela = new LytComprarProduto(null, true, servicos);
			janela.setVisible(true);

		} else if (e.getSource() == btnVerificarPedidos) {

			JDialog janela = new LytVerificarPedidos(null, true, txtServico.getText());
			janela.setVisible(true);

		} else if (e.getSource() == btnMeusPedidos) {

			JDialog janela = new LytConsultarPedidos(this, true, login);
			janela.setVisible(true);

		}

		else if (e.getSource() == btnLogoff) {

			int valor = JOptionPane.showConfirmDialog(null, "Você tem certeza?", "LOGOFF", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (valor == 0) {

				this.dispose();

			}

		}

	}

	private int listarInfo() {

		String[] res = busLogin.listarPerfil(login);

		if (res[2] == null) {

			txtUsuario.setText("ADM");
			txtPermissao.setText("ADMINISTRADOR");
			txtServico.setText("ADMINISTRADOR");

			return 0;

		} else {

			txtUsuario.setText(res[0]);
			txtPermissao.setText(res[1]);
			txtServico.setText(res[2]);

			return Integer.parseInt(res[3]);

		}

	}

	private int listarPadraoPerfil() {

		String[] res = busLogin.listarPerfilPadrao(login);

		txtUsuario.setText(res[1]);
		txtPermissao.setText(res[2]);

		return Integer.parseInt(res[0]);

	}

}
