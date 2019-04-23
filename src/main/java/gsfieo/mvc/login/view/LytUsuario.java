package gsfieo.mvc.login.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.login.controller.BusLogin;
import gsfieo.mvc.login.model.Login;
import gsfieo.mvc.principal.view.CadastroConsulta;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.tableModel.LoginModel;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LytUsuario extends CadastroConsulta implements ActionListener, ChangeListener, DocumentListener {

	private static final long serialVersionUID = -3936926043969844967L;

	private final BusLogin busLogin;
	private LoginModel mLogin;

	// =========== ABA GRAVAR ============
	private final JPanel pnTitulo;
	private final JLabel lblTitulo, lblUsuario, lblSenha, lblRepSenha;
	private final JTextField txtUsuario;
	private final JPasswordField txtSenha, txtRepSenha;
	private final JButton btnGravar, btnCance;

	public LytUsuario(Frame f, boolean bol) {

		super(f, bol);

		this.busLogin = new BusLogin();

		// ================== CONFIGURAÇÕES DA JANELA ==========================
		setTitle("LOGIN");
		setSize(350, 335);

		// ================== ABA 'GRAVAR' ==========================
		// -------- LABEL 'TÍTULO'

		pnTitulo = new JPanel();
		pnTitulo.setSize(340, 40);
		pnTitulo.setLocation(0, 5);
		pnGravar.add(pnTitulo);

		lblTitulo = new JLabel("CADASTRAR USUÁRIO");
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));

		pnTitulo.add(lblTitulo);

		// --------- CAMPO 'LytUsuario'
		lblUsuario = new JLabel("Usuário:");
		lblUsuario.setSize(100, 20);
		lblUsuario.setLocation(20, 60);

		pnGravar.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setSize(200, 25);
		txtUsuario.setLocation(35, 80);

		pnGravar.add(txtUsuario);

		// --------- CAMPO 'Senha'
		lblSenha = new JLabel("Senha:");
		lblSenha.setSize(130, 20);
		lblSenha.setLocation(20, 110);

		pnGravar.add(lblSenha);

		txtSenha = new JPasswordField();
		txtSenha.setSize(200, 25);
		txtSenha.setLocation(35, 130);
		txtSenha.setEchoChar('•');

		pnGravar.add(txtSenha);

		// --------- CAMPO 'Repetir Senha'
		lblRepSenha = new JLabel("Repetir senha:");
		lblRepSenha.setSize(200, 20);
		lblRepSenha.setLocation(20, 160);

		pnGravar.add(lblRepSenha);

		txtRepSenha = new JPasswordField();
		txtRepSenha.setSize(200, 25);
		txtRepSenha.setLocation(35, 180);
		txtRepSenha.setEchoChar('•');

		pnGravar.add(txtRepSenha);

		// --------- BOTÕES
		// --- GRAVAR
		ImageIcon icGravar = new ImageIcon(this.getClass().getResource(c.getGravar()));

		btnGravar = new JButton(icGravar);
		btnGravar.setSize(150, 60);
		btnGravar.setLocation(15, 225);
		btnGravar.addActionListener(LytUsuario.this);

		pnGravar.add(btnGravar);

		// --- CANCELAR
		ImageIcon icCanc = new ImageIcon(this.getClass().getResource(c.getSair()));

		btnCance = new JButton(icCanc);
		btnCance.setSize(150, 60);
		btnCance.setLocation(170, 225);
		btnCance.addActionListener(LytUsuario.this);

		pnGravar.add(btnCance);

		// =====================================================================
		// ======================= ABA CONSULTAR ===============================

		btnEditar.addActionListener(LytUsuario.this);
		btnExcluir.addActionListener(LytUsuario.this);
		btnSair.addActionListener(LytUsuario.this);
		txtPesquisar.getDocument().addDocumentListener(LytUsuario.this);

		mLogin = new LoginModel(getListaLogin());

		criarGrid(mLogin);

		jtpAbas.addChangeListener(LytUsuario.this);

	}

	public static void main(String[] args) {

		CadastroConsulta janela = new LytUsuario(null, true);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCance || e.getSource() == btnSair) {

			// -- FECHAR JANELA
			this.dispose();

		} else if (e.getSource() == btnGravar) {

			if (txtUsuario.getText().equals("") && String.copyValueOf(txtSenha.getPassword()).equals("")) {

				GSFIEOUtil.mensagem(this, "Preencha os campos", GSFIEOConstants.MSG_AVISO);

			} else if (txtUsuario.getText().equals("") || String.copyValueOf(txtSenha.getPassword()).equals("")) {

				GSFIEOUtil.mensagem(this, "Existem campos em branco", GSFIEOConstants.MSG_AVISO);

			} else if (verificarUsuarioExiste() == true) {

				GSFIEOUtil.mensagem(this, "Usuário já existe", GSFIEOConstants.MSG_AVISO);

			} else {

				if (String.copyValueOf(txtSenha.getPassword()).equals(String.copyValueOf(txtRepSenha.getPassword()))) {

					Login login = new Login();
					login.setUsuario(txtUsuario.getText());
					login.setSenha(txtSenha.getPassword());
					login.getPermissao().setPermissao("PADRÃO");

					busLogin.gravarLogin(login);

					GSFIEOUtil.mensagem(this, "Registro gravado com sucesso!", GSFIEOConstants.MSG_INFORMACAO);

					txtUsuario.setText("");
					txtSenha.setText("");
					txtRepSenha.setText("");

					mLogin = new LoginModel(getListaLogin());

					criarGrid(mLogin);

				} else {

					GSFIEOUtil.mensagem(this, "As senhas não coincidem", GSFIEOConstants.MSG_AVISO);

				}
			}

		} else if (e.getSource() == btnEditar) {

			if (dgvDados.getSelectedRow() >= 0) {

				JDialog janela = new LytEditarUsuario(null, true,
						(int) mLogin.getValueAt(dgvDados.getSelectedRow(), 0));
				janela.setVisible(true);

				mLogin = new LoginModel(getListaLogin());

				criarGrid(mLogin);

			}

		} else if (e.getSource() == btnExcluir) {

			if (dgvDados.getSelectedRow() >= 0) {

				int resp;

				resp = JOptionPane.showConfirmDialog(null, "Tem certeza?", "Exclusão", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (resp == 0) {

					int cod = (int) mLogin.getValueAt(dgvDados.getSelectedRow(), 0);
					Login login = new Login();

					login.setCdLogin(cod);
					busLogin.excluirLogin(login);

					GSFIEOUtil.mensagem(null, "Registro excluído com sucesso!", GSFIEOConstants.MSG_INFORMACAO);

					mLogin = new LoginModel(getListaLogin());

					criarGrid(mLogin);

				}

			}

		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {

		int i = jtpAbas.getSelectedIndex();

		if (i == 0) {
			// ========= ABA GRAVAR ========
			setSize(350, 335);

		} else if (i == 1) {
			// ========= ABA CONSULTAR ========

			setSize(525, 333);

		}

	}

	private List<Login> getListaLogin() {

		List<Login> lstLogin = new ArrayList<>();
		Login login = new Login();

		login.setUsuario(txtPesquisar.getText());
		login.getPermissao().setPermissao(txtPesquisar.getText());

		String[][] res = busLogin.buscarUsuarios(login);

		for (String dados[] : res) {

			Login cln = new Login();
			cln.setCdLogin(Integer.parseInt(dados[0]));
			cln.setUsuario(dados[1]);
			cln.getPermissao().setPermissao(dados[2]);

			lstLogin.add(cln);

		}

		return lstLogin;

	}

	private boolean verificarUsuarioExiste() {

		Login login = new Login();
		login.setUsuario(txtUsuario.getText());

		return busLogin.verificarUsuarioExiste(login)[0] != null;

	}

	@Override
	public void insertUpdate(DocumentEvent e) {

		mLogin = new LoginModel(getListaLogin());
		criarGrid(mLogin);

	}

	@Override
	public void removeUpdate(DocumentEvent e) {

		mLogin = new LoginModel(getListaLogin());
		criarGrid(mLogin);

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

}
