package gsfieo.mvc.tipoServico.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.tipoServico.controller.BusTipoServico;
import gsfieo.mvc.tipoServico.model.TipoServico;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.informacoes.ConstantesImagens;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LytEditarTipoServico extends JDialog implements ActionListener {

	private static final long serialVersionUID = -6165939798896370330L;

	private final ConstantesImagens c = new ConstantesImagens();
	private final BusTipoServico busTipoServico = new BusTipoServico();

	private final JLabel lblTitulo, lblTipoServ;
	private final JTextField txtTipoServ;
	private final JCheckBox chkStatus;
	private final JButton btnGravar, btnCance;
	private final JPanel pnStatus;

	private final int CD_SERVICO;

	public LytEditarTipoServico(Frame f, boolean bol, int cdServico) {

		super(f, bol);

		CD_SERVICO = cdServico;

		// ================== CONFIGURAÇÕES DA JANELA ==========================
		setTitle("TIPO DE SERVIÇO");
		setSize(400, 300);
		setLocation(10, 110);
		setLayout(null);
		setResizable(false);

		// ================== ABA 'GRAVAR' ==========================
		// -------- LABEL 'TÍTULO'
		lblTitulo = new JLabel("EDITAR TIPO DE SERVIÇO");
		lblTitulo.setSize(270, 25);
		lblTitulo.setLocation(70, 10);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));

		add(lblTitulo);

		// --------- CAMPO 'Tipo de Serviço'
		lblTipoServ = new JLabel("Tipo de Serviço");
		lblTipoServ.setSize(130, 25);
		lblTipoServ.setLocation(10, 50);

		add(lblTipoServ);

		txtTipoServ = new JTextField();
		txtTipoServ.setSize(300, 25);
		txtTipoServ.setLocation(20, 85);

		add(txtTipoServ);

		// --------- CAMPO 'Status'
		pnStatus = new JPanel();
		pnStatus.setLayout(null);
		pnStatus.setSize(90, 45);
		pnStatus.setLocation(20, 120);

		chkStatus = new JCheckBox("Ativo?");
		chkStatus.setSize(70, 25);
		chkStatus.setLocation(10, 15);

		pnStatus.setBorder(BorderFactory.createTitledBorder("Status"));
		pnStatus.add(chkStatus);

		add(pnStatus);

		// --------- BOTÕES
		// --- GRAVAR
		ImageIcon icGravar = new ImageIcon(this.getClass().getResource(c.getEditar()));
		btnGravar = new JButton(icGravar);

		btnGravar.setSize(150, 60);
		btnGravar.setLocation(30, 180);
		btnGravar.addActionListener(LytEditarTipoServico.this);

		add(btnGravar);

		// --- SAIR
		ImageIcon icCanc = new ImageIcon(this.getClass().getResource(c.getSair()));
		btnCance = new JButton(icCanc);

		btnCance.setSize(150, 60);
		btnCance.setLocation(190, 180);
		btnCance.addActionListener(LytEditarTipoServico.this);

		add(btnCance);

		preencherCampos();

	}

	public static void main(String[] args) {

		JDialog janela = new LytEditarTipoServico(null, true, 0);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCance) {

			this.dispose();

		} else if (e.getSource() == btnGravar) {

			if (txtTipoServ.getText().equals("")) {

				GSFIEOUtil.mensagem(this, "Existem campos em branco", GSFIEOConstants.MSG_AVISO);

			} else {

				TipoServico tipoServico = new TipoServico();
				tipoServico.setCdTipoServico(CD_SERVICO);
				tipoServico.setTipoServico(txtTipoServ.getText());
				tipoServico.setStatus(chkStatus.isSelected() ? '1' : '0');

				busTipoServico.editar(tipoServico);

				GSFIEOUtil.mensagem(LytEditarTipoServico.this, "Registro alterado com sucesso!",
						GSFIEOConstants.MSG_INFORMACAO);
				this.dispose();

			}

		}

	}

	private void preencherCampos() {

		TipoServico tipoServico = new TipoServico();
		tipoServico.setCdTipoServico(CD_SERVICO);

		String[] res = busTipoServico.listarTipoServico(tipoServico);
		char status;

		status = res[1].charAt(0);

		txtTipoServ.setText(res[0]);

		if (status == '0') {

			chkStatus.setSelected(false);

		} else {

			chkStatus.setSelected(true);

		}

	}

}
