package gsfieo.mvc.login.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.login.controller.BusLogin;
import gsfieo.mvc.permissao.controller.BusPermissao;
import gsfieo.mvc.login.model.Login;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.informacoes.ConstantesImagens;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LytEditarUsuario extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1775117941154469279L;

	private final ConstantesImagens c = new ConstantesImagens();
	private final BusLogin busLogin = new BusLogin();

	private final JLabel lblTitulo, lblUsuario, lblSenha, lblPermissao, lblRepSenha;
	private final JTextField txtUsuario;
	private final JPasswordField txtSenha, txtRepSenha;
	private final JComboBox<String> cbPermissao;
	private final JButton btnEditar, btnCance;

	private final int CD_LOGIN;

	public LytEditarUsuario(Frame fra, boolean bol, int cdLogin) {

		super(fra, bol);

		CD_LOGIN = cdLogin;

		setTitle("Editar");
		setSize(350, 400);
		setLocation(10, 110);
		setUndecorated(false);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(null);

		// -------- LABEL 'TÍTULO'
		lblTitulo = new JLabel("EDITAR USUÁRIO");
		lblTitulo.setLocation(80, 20);
		lblTitulo.setSize(300, 20);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));

		add(lblTitulo);

		// --------- CAMPO 'USUÁRIO'
		lblUsuario = new JLabel("Usuário:");
		lblUsuario.setSize(100, 20);
		lblUsuario.setLocation(20, 60);

		add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setSize(200, 25);
		txtUsuario.setLocation(35, 80);

		add(txtUsuario);

		// --------- CAMPO 'Senha'
		lblSenha = new JLabel("Senha:");
		lblSenha.setSize(130, 20);
		lblSenha.setLocation(20, 110);

		add(lblSenha);

		txtSenha = new JPasswordField();
		txtSenha.setSize(200, 25);
		txtSenha.setLocation(35, 130);
		txtSenha.setEchoChar('•');

		add(txtSenha);

		// --------- CAMPO 'Repetir Senha'
		lblRepSenha = new JLabel("Repetir senha:");
		lblRepSenha.setSize(200, 20);
		lblRepSenha.setLocation(20, 160);

		add(lblRepSenha);

		txtRepSenha = new JPasswordField();
		txtRepSenha.setSize(200, 25);
		txtRepSenha.setLocation(35, 180);
		txtRepSenha.setEchoChar('•');

		add(txtRepSenha);

		// --------- CAMPO 'Permissão'

		lblPermissao = new JLabel("Permissão:");
		lblPermissao.setSize(200, 20);
		lblPermissao.setLocation(20, 210);

		add(lblPermissao);

		cbPermissao = new JComboBox<>(GSFIEOUtil.matrizUnidimensional(new BusPermissao().buscarPermissoes()));
		cbPermissao.setSize(200, 25);
		cbPermissao.setLocation(35, 230);

		add(cbPermissao);

		// --------- BOTÕES
		// --- GRAVAR
		ImageIcon icGravar = new ImageIcon(this.getClass().getResource(c.getEditar()));

		btnEditar = new JButton(icGravar);
		btnEditar.setSize(150, 60);
		btnEditar.setLocation(15, 280);
		btnEditar.addActionListener(LytEditarUsuario.this);

		add(btnEditar);

		// --- CANCELAR
		ImageIcon icCanc = new ImageIcon(this.getClass().getResource(c.getSair()));

		btnCance = new JButton(icCanc);
		btnCance.setSize(150, 60);
		btnCance.setLocation(170, 280);
		btnCance.addActionListener(LytEditarUsuario.this);

		add(btnCance);

		preencherTela();

	}

	public static void main(String[] args) {

		JDialog janela = new LytEditarUsuario(null, true, 0);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCance) {

			this.dispose();

		} else if (e.getSource() == btnEditar) {

			if (txtUsuario.getText().equals("") && String.copyValueOf(txtSenha.getPassword()).equals("")) {

				GSFIEOUtil.mensagem(this, "Preencha os campos", GSFIEOConstants.MSG_AVISO);

			} else if (txtUsuario.getText().equals("") || String.copyValueOf(txtSenha.getPassword()).equals("")) {

				GSFIEOUtil.mensagem(this, "Existem campos em branco", GSFIEOConstants.MSG_AVISO);

			} else {

				if (String.copyValueOf(txtSenha.getPassword()).equals(String.copyValueOf(txtRepSenha.getPassword()))) {

					Login login = new Login();
					login.setCdLogin(CD_LOGIN);
					login.setUsuario(txtUsuario.getText());
					login.setSenha(txtSenha.getPassword());
					login.getPermissao().setPermissao(cbPermissao.getSelectedItem().toString());

					busLogin.editarLogin(login);

					GSFIEOUtil.mensagem(this, "Registro atualizado com sucesso!", GSFIEOConstants.MSG_INFORMACAO);

					this.dispose();

				} else {

					GSFIEOUtil.mensagem(this, "As senhas não coincidem", GSFIEOConstants.MSG_AVISO);
				}

			}

		}
	}

	public final void preencherTela() {

		Login login = new Login();
		login.setCdLogin(CD_LOGIN);

		String[] res = busLogin.listarInfoUsuario(login);

		txtUsuario.setText(res[0]);
		txtSenha.setText(res[1]);
		cbPermissao.setSelectedItem(res[2]);

	}

}
