package gsfieo.mvc.servico.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.servico.controller.BusServico;
import gsfieo.mvc.servico.model.Servicos;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LytEditarServicos extends JDialog implements ActionListener {

	private static final long serialVersionUID = -1662232733592215329L;

	private final ConstantesImagens c = new ConstantesImagens();
	private final BusServico busServico = new BusServico();
	private final BusTipoServico busTipoServico = new BusTipoServico();

	private final JLabel lblTitulo, lblTipoServico, lblServico;
	private final JTextField txtServico;
	private final JComboBox<String> cbTipoServico;
	private final JCheckBox chkStatus;
	private final JButton btnGravar, btnCance;

	private final int CD_SERVICO;

	public LytEditarServicos(Frame f, boolean bol, int cdServico) {

		super(f, bol);

		CD_SERVICO = cdServico;

		setTitle("EDITAR");
		setSize(350, 350);
		setLocation(10, 110);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// -------- LABEL 'TÍTULO'
		lblTitulo = new JLabel("Editar Serviços");
		lblTitulo.setSize(190, 25);
		lblTitulo.setLocation(90, 20);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));

		add(lblTitulo);

		// -------- CAMPO 'TIPO DE SERVIÇO'
		lblTipoServico = new JLabel("Tipos de Serviços");
		lblTipoServico.setSize(110, 25);
		lblTipoServico.setLocation(10, 55);

		add(lblTipoServico);

		cbTipoServico = new JComboBox<>(listarCombo());
		cbTipoServico.setSize(150, 25);
		cbTipoServico.setLocation(20, 85);

		add(cbTipoServico);

		// -------- CAMPO 'SERVIÇO'
		lblServico = new JLabel("Serviço:");
		lblServico.setSize(110, 25);
		lblServico.setLocation(10, 115);

		add(lblServico);

		txtServico = new JTextField();
		txtServico.setSize(300, 25);
		txtServico.setLocation(20, 145);

		add(txtServico);

		// -------- CAMPO 'Status'
		JPanel pnStatus = new JPanel();
		pnStatus.setSize(70, 45);
		pnStatus.setLocation(10, 180);
		pnStatus.setLayout(null);
		pnStatus.setBorder(BorderFactory.createTitledBorder("Status"));

		chkStatus = new JCheckBox("Ativo?");
		chkStatus.setSize(60, 15);
		chkStatus.setLocation(5, 20);

		pnStatus.add(chkStatus);

		add(pnStatus);

		// ======================

		// --------- BOTÕES
		// --- GRAVAR
		ImageIcon icGravar = new ImageIcon(this.getClass().getResource(c.getEditar()));
		btnGravar = new JButton(icGravar);

		btnGravar.setSize(150, 60);
		btnGravar.setLocation(10, 240);
		btnGravar.addActionListener(LytEditarServicos.this);

		add(btnGravar);

		// --- SAIR
		ImageIcon icCanc = new ImageIcon(this.getClass().getResource(c.getSair()));
		btnCance = new JButton(icCanc);

		btnCance.setSize(150, 60);
		btnCance.setLocation(170, 240);
		btnCance.addActionListener(LytEditarServicos.this);

		add(btnCance);

		preencherCampos();

	}

	public static void main(String[] args) {

		JDialog janela = new LytEditarServicos(null, true, 0);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCance) {

			this.dispose();

		} else if (e.getSource() == btnGravar) {

			Servicos servicos = new Servicos();

			servicos.setCdServicos(CD_SERVICO);
			servicos.setServico(txtServico.getText());
			servicos.setStatus(chkStatus.isSelected() ? '1' : '0');
			servicos.getTipoServico().setTipoServico(String.valueOf(cbTipoServico.getSelectedItem()));
			busServico.editar(servicos);

			GSFIEOUtil.mensagem(LytEditarServicos.this, "Registro alterado com sucesso!",
					GSFIEOConstants.MSG_INFORMACAO);
			this.dispose();

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

	private void preencherCampos() {

		Servicos servicos = new Servicos();
		servicos.setCdServicos(CD_SERVICO);

		String[] res = busServico.listarInfoServico(servicos);

		char status = res[2].charAt(0);

		if (status == '0') {

			chkStatus.setSelected(false);

		} else {

			chkStatus.setSelected(true);

		}

		cbTipoServico.setSelectedItem(res[3]);
		txtServico.setText(res[1]);
	}

}
