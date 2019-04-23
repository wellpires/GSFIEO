package gsfieo.mvc.servico.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.estoque.controller.BusEstoque;
import gsfieo.mvc.estoque.model.Estoque;
import gsfieo.mvc.login.controller.BusLogin;
import gsfieo.mvc.login.model.Login;
import gsfieo.mvc.principal.view.CadastroConsulta;
import gsfieo.mvc.servico.controller.BusServico;
import gsfieo.mvc.servico.model.Servicos;
import gsfieo.mvc.tipoServico.controller.BusTipoServico;
import gsfieo.mvc.tipoServico.model.TipoServico;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.tableModel.ServicoModel;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

public class LytServicos extends CadastroConsulta implements ActionListener, ChangeListener, DocumentListener {

	private static final long serialVersionUID = -8621126654664888165L;

	private ServicoModel sModel;
	private final BusServico busServico = new BusServico();
	private final BusEstoque busEstoque = new BusEstoque();
	private final BusTipoServico busTipoServico = new BusTipoServico();
	private final BusLogin busLogin = new BusLogin();

	private final JLabel lblTitulo, lblTipoServico, lblServico, lblUsuario, lblSenha, lblRepSenha;
	private final JTextField txtServico, txtUsuario;
	private final JPasswordField txtSenha, txtRepSenha;
	private final JComboBox<String> cbTipoServico;
	private final JCheckBox chkStatus;
	private final JButton btnGravar, btnCance;
	private final JPanel pnStatus, pnLogin;

	public LytServicos(Frame f, boolean bol) {

		super(f, bol);

		setTitle("Serviços");
		setSize(350, 530);

		// ======================================================================
		// ======================= ABA GRAVAR ==================================

		// -------- LABEL 'TÍTULO'
		lblTitulo = new JLabel("Cadastrar Serviços");
		lblTitulo.setSize(190, 25);
		lblTitulo.setLocation(90, 20);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));

		pnGravar.add(lblTitulo);

		// -------- CAMPO 'TIPO DE SERVIÇO'
		lblTipoServico = new JLabel("Tipos de Serviços");
		lblTipoServico.setSize(110, 25);
		lblTipoServico.setLocation(10, 55);

		pnGravar.add(lblTipoServico);

		cbTipoServico = new JComboBox<>(listarCombo());
		cbTipoServico.setSize(150, 25);
		cbTipoServico.setLocation(20, 85);

		pnGravar.add(cbTipoServico);

		// -------- CAMPO 'SERVIÇO'
		lblServico = new JLabel("Serviço:");
		lblServico.setSize(110, 25);
		lblServico.setLocation(10, 115);

		pnGravar.add(lblServico);

		txtServico = new JTextField();
		txtServico.setSize(300, 25);
		txtServico.setLocation(20, 145);

		pnGravar.add(txtServico);

		// -------- CAMPO 'Status'
		pnStatus = new JPanel();
		pnStatus.setSize(70, 45);
		pnStatus.setLocation(10, 180);
		pnStatus.setLayout(null);
		pnStatus.setBorder(BorderFactory.createTitledBorder("Status"));

		chkStatus = new JCheckBox("Ativo?");
		chkStatus.setSize(60, 15);
		chkStatus.setLocation(5, 20);

		pnStatus.add(chkStatus);

		pnGravar.add(pnStatus);

		// =========== LOGIN
		pnLogin = new JPanel();
		pnLogin.setSize(325, 190);
		pnLogin.setLocation(10, 235);
		pnLogin.setLayout(null);
		pnLogin.setBorder(BorderFactory.createTitledBorder("Login"));

		// -------- CAMPO 'Usuario'
		lblUsuario = new JLabel("Usuário:");
		lblUsuario.setSize(100, 10);
		lblUsuario.setLocation(20, 20);

		pnLogin.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setSize(280, 25);
		txtUsuario.setLocation(35, 40);

		pnLogin.add(txtUsuario);

		// --------- CAMPO 'Senha'
		lblSenha = new JLabel("Senha:");
		lblSenha.setSize(130, 10);
		lblSenha.setLocation(20, 70);

		pnLogin.add(lblSenha);

		txtSenha = new JPasswordField();
		txtSenha.setSize(200, 25);
		txtSenha.setLocation(35, 90);
		txtSenha.setEchoChar('•');

		pnLogin.add(txtSenha);

		// --------- CAMPO 'Repetir Senha'
		lblRepSenha = new JLabel("Repetir senha:");
		lblRepSenha.setSize(200, 15);
		lblRepSenha.setLocation(20, 120);

		pnLogin.add(lblRepSenha);

		txtRepSenha = new JPasswordField();
		txtRepSenha.setSize(200, 25);
		txtRepSenha.setLocation(35, 145);
		txtRepSenha.setEchoChar('•');

		pnLogin.add(txtRepSenha);

		pnGravar.add(pnLogin);

		// ======================

		// --------- BOTÕES
		// --- GRAVAR
		ImageIcon icGravar = new ImageIcon(this.getClass().getResource(c.getGravar()));
		btnGravar = new JButton(icGravar);

		btnGravar.setSize(150, 60);
		btnGravar.setLocation(18, 430);
		btnGravar.addActionListener(LytServicos.this);

		pnGravar.add(btnGravar);

		// --- SAIR
		ImageIcon icCanc = new ImageIcon(this.getClass().getResource(c.getSair()));
		btnCance = new JButton(icCanc);

		btnCance.setSize(150, 60);
		btnCance.setLocation(178, 430);
		btnCance.addActionListener(LytServicos.this);

		pnGravar.add(btnCance);

		// =====================================================================
		// ======================= ABA CONSULTAR ===============================

		btnEditar.addActionListener(LytServicos.this);
		btnExcluir.addActionListener(LytServicos.this);
		btnSair.addActionListener(LytServicos.this);
		txtPesquisar.getDocument().addDocumentListener(LytServicos.this);

		// =====================================================================

		jtpAbas.addChangeListener(LytServicos.this);

		sModel = new ServicoModel(getServicos());

		criarGrid(sModel);

	}

	public static void main(String[] args) {

		CadastroConsulta janela = new LytServicos(null, true);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCance || e.getSource() == btnSair) {

			this.dispose();

		} else if (e.getSource() == btnGravar) {

			if (txtServico.getText().equals("") || txtUsuario.getText().equals("")
					|| String.copyValueOf(txtSenha.getPassword()).equals("")) {

				GSFIEOUtil.mensagem(this, "Existem campos em branco", GSFIEOConstants.MSG_AVISO);

			} else if (verificarServicoExiste() == true) {

				GSFIEOUtil.mensagem(this, "Este serviço já existe", GSFIEOConstants.MSG_AVISO);

			} else if (verificarUsuarioExiste() == true) {

				GSFIEOUtil.mensagem(this, "Este usuário já existe", GSFIEOConstants.MSG_AVISO);

			} else {

				if (String.copyValueOf(txtSenha.getPassword()).equals(String.copyValueOf(txtRepSenha.getPassword()))) {

					// GRAVAR LOGIN
					Login login = new Login();

					login.setUsuario(txtUsuario.getText());
					login.setSenha(txtSenha.getPassword());
					login.getPermissao().setPermissao("MODERADOR");

					busLogin.gravarLogin(login);

					// GRAVAR SERVICOS

					char status = chkStatus.isSelected() ? '1' : '0';
					Servicos servicos = new Servicos();

					servicos.setServico(txtServico.getText());
					servicos.setStatus(status);
					servicos.getTipoServico().setTipoServico(String.valueOf(cbTipoServico.getSelectedItem()));
					servicos.setLogin(login);

					busServico.gravar(servicos);

					// GRAVAR ESTOQUE
					Estoque estoque = new Estoque();

					estoque.setServico(servicos);
					busEstoque.gravarEstoque(estoque);

					GSFIEOUtil.mensagem(LytServicos.this, "Registro gravado com sucesso!",
							GSFIEOConstants.MSG_INFORMACAO);

					cbTipoServico.setSelectedIndex(0);
					txtServico.setText("");
					chkStatus.setSelected(false);

					txtUsuario.setText("");
					txtSenha.setText("");
					txtRepSenha.setText("");

					sModel = new ServicoModel(getServicos());

					criarGrid(sModel);

				} else {

					GSFIEOUtil.mensagem(this, "As senhas não coincidem", GSFIEOConstants.MSG_AVISO);

				}

			}

		} else if (e.getSource() == btnEditar) {

			if (dgvDados.getSelectedRow() >= 0) {

				JDialog janela = new LytEditarServicos(null, true,
						(int) sModel.getValueAt((int) dgvDados.getSelectedRow(), 0));
				janela.setVisible(true);

				sModel = new ServicoModel(getServicos());

				criarGrid(sModel);

			}

		} else if (e.getSource() == btnExcluir) {

			if (dgvDados.getSelectedRow() >= 0) {

				int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza?", "Exclusão", JOptionPane.YES_NO_OPTION);

				if (opcao == 0) {

					Servicos servicos = new Servicos();

					servicos.setCdServicos((int) sModel.getValueAt(dgvDados.getSelectedRow(), 0));
					busServico.excluir(servicos);

					GSFIEOUtil.mensagem(LytServicos.this, "Registro excluído com sucesso!",
							GSFIEOConstants.MSG_INFORMACAO);

				}

				sModel = new ServicoModel(getServicos());

				criarGrid(sModel);

			}

		}

	}

	private String[] listarCombo() {

		TipoServico tipoServico = new TipoServico();

		tipoServico.setTipoServico("");
		String res[][] = busTipoServico.buscarTiposServico(tipoServico);

		int MAX = res.length;
		String dadosTipos[] = new String[MAX];

		for (int i = 0; i < MAX; i++) {

			dadosTipos[i] = res[i][1];

		}

		return dadosTipos;

	}

	@Override
	public void stateChanged(ChangeEvent e) {

		int index = jtpAbas.getSelectedIndex();

		if (index == 0) {

			setSize(350, 530);

		} else if (index == 1) {

			setSize(525, 333);

		}

	}

	private List<Servicos> getServicos() {

		List<Servicos> lstDados = new ArrayList<>();
		Servicos servicos = new Servicos();

		servicos.setServico(txtPesquisar.getText());
		servicos.getTipoServico().setTipoServico(txtPesquisar.getText());

		String[][] res = busServico.listarServicos(servicos);

		for (String valor[] : res) {

			Servicos clnServi = new Servicos();

			clnServi.setCdServicos(Integer.parseInt(valor[0]));
			clnServi.setServico(valor[1]);
			clnServi.setStatus(valor[2].charAt(0));
			clnServi.setStrStatus(valor[2].charAt(0));
			clnServi.getTipoServico().setTipoServico(valor[3]);

			lstDados.add(clnServi);

		}

		return lstDados;

	}

	private boolean verificarServicoExiste() {

		Servicos servicos = new Servicos();
		servicos.setServico(txtServico.getText());

		String dados = busServico.verificarServicoExiste(servicos)[0];

		return dados != null;

	}

	private boolean verificarUsuarioExiste() {

		Login login = new Login();
		login.setUsuario(txtUsuario.getText());

		String dados = busLogin.verificarUsuarioExiste(login)[0];

		return dados != null;

	}

	@Override
	public void insertUpdate(DocumentEvent e) {

		sModel = new ServicoModel(getServicos());

		criarGrid(sModel);

	}

	@Override
	public void removeUpdate(DocumentEvent e) {

		sModel = new ServicoModel(getServicos());

		criarGrid(sModel);

	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

}
