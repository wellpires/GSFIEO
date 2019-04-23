package gsfieo.mvc.login.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.login.controller.BusLogin;
import gsfieo.mvc.login.model.Login;
import gsfieo.mvc.principal.view.LytGSFIEO;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.informacoes.ConstantesImagens;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LytLogin extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 2237780254709315461L;

	private final ConstantesImagens c = new ConstantesImagens();

	private final JLabel lblTitulo, lblUsuario, lblSenha, lblImagem;
	private final JTextField txtUsuario;
	private final JPasswordField txtSenha;
	private final JButton btnLogar, btnSair;

	public LytLogin() {

		lookAndFeel();

		setTitle("LOGIN");
		setSize(300, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(null);
		setLayout(null);

		// ------ IMAGEM -------
		ImageIcon icLogin = new ImageIcon(this.getClass().getResource(c.getLogoLogin()));
		lblImagem = new JLabel(icLogin);
		lblImagem.setSize(128, 128);
		lblImagem.setLocation(30, 30);

		add(lblImagem);

		// ------ TITULO -------
		lblTitulo = new JLabel("LOGIN");
		lblTitulo.setSize(63, 25);
		lblTitulo.setLocation(160, 110);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));

		add(lblTitulo);

		// --------- CAMPO 'Prontuário'
		lblUsuario = new JLabel("Prontuário:");
		lblUsuario.setSize(66, 25);
		lblUsuario.setLocation(20, 180);

		add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setSize(250, 25);
		txtUsuario.setLocation(20, 205);
		txtUsuario.addKeyListener(LytLogin.this);

		add(txtUsuario);

		// --------- CAMPO 'Senha'
		lblSenha = new JLabel("Senha:");
		lblSenha.setSize(42, 20);
		lblSenha.setLocation(20, 245);

		add(lblSenha);

		txtSenha = new JPasswordField();
		txtSenha.setSize(250, 25);
		txtSenha.setLocation(20, 265);
		txtSenha.setEchoChar('•');
		txtSenha.addKeyListener(LytLogin.this);

		add(txtSenha);

		// --------- BOTÕES
		// --- BOTÃO 'Login'
		ImageIcon icLogar = new ImageIcon(this.getClass().getResource(c.getLogar()));

		btnLogar = new JButton(icLogar);
		btnLogar.setSize(100, 40);
		btnLogar.setLocation(40, 300);
		btnLogar.addActionListener(LytLogin.this);

		add(btnLogar);

		// --- BOTÃO 'Sair'
		ImageIcon icSair = new ImageIcon(this.getClass().getResource(c.getSair32x32()));

		btnSair = new JButton(icSair);
		btnSair.setSize(100, 40);
		btnSair.setLocation(150, 300);
		btnSair.addActionListener(LytLogin.this);

		add(btnSair);

		// ---------------------------------------------------------------------

	}

	public static void main(String[] args) {

		JFrame janela = new LytLogin();
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnSair) {

			this.dispose();

		} else if (e.getSource() == btnLogar) {

			logar();

		}

	}

	private void logar() {

		BusLogin busLogin = new BusLogin();
		Login login = new Login();

		login.setUsuario(txtUsuario.getText());
		login.setSenha(txtSenha.getPassword());

		if (busLogin.logarSe(login)) {

			if (verificarResolucao()) {

				int opcao = JOptionPane.showConfirmDialog(LytLogin.this,
						"Foi verificado que sua resolução de tela é inferior a 1366x768, isto pode causar uma distorção na tela.\nDeseja continuar?",
						"GSFIEO", JOptionPane.YES_NO_OPTION, GSFIEOConstants.MSG_PERGUNTA);

				if (opcao == JOptionPane.YES_OPTION) {

					txtUsuario.setText("");
					txtSenha.setText("");
					txtUsuario.requestFocus();
					LytGSFIEO fieo = new LytGSFIEO(login);
					fieo.setVisible(true);

				}

			} else {

				txtUsuario.setText("");
				txtSenha.setText("");
				txtUsuario.requestFocus();
				LytGSFIEO fieo = new LytGSFIEO(login);
				fieo.setVisible(true);

			}

		} else {

			GSFIEOUtil.mensagem(this, "Usuário e/ou senha inválidos", GSFIEOConstants.MSG_ERRO);

		}

	}

	private static void lookAndFeel() {

		// =================== TROCAR DESIGN (LOOK AND FEEL)
		// ==================================
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(LytLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

		if (e.getKeyCode() != KeyEvent.VK_ENTER) {

			char letra = e.getKeyChar();
			e.setKeyChar(caixaAlta(letra));

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			logar();

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	private char caixaAlta(char caracter) {

		return String.valueOf(caracter).toUpperCase().charAt(0);

	}

	private boolean verificarResolucao() {

		Dimension resolucao = Toolkit.getDefaultToolkit().getScreenSize();

		double largura = resolucao.getWidth();
		double altura = resolucao.getHeight();

		return largura < 1366 || altura < 768;

		// GSFIEOUtil.mensagem(LytLogin.this, "Foi verificado que sua resolução de tela
		// é inferior a 1366x768, isto pode causar uma distorção na tela.",
		// GSFIEOConstants.MSG_AVISO);
	}

}
