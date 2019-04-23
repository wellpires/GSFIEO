package gsfieo.mvc.tipoServico.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.principal.view.CadastroConsulta;
import gsfieo.mvc.tipoServico.controller.BusTipoServico;
import gsfieo.mvc.tipoServico.model.TipoServico;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.tableModel.TipoServicoModel;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LytTipoServico extends CadastroConsulta implements ActionListener, ChangeListener, DocumentListener {

	private static final long serialVersionUID = -5085819091921065720L;

	private TipoServicoModel tipo;
	private final BusTipoServico busTipoServico = new BusTipoServico();

	private final JLabel lblTitulo, lblTipoServ;
	private final JTextField txtTipoServ;
	private final JCheckBox chkStatus;
	private final JButton btnGravar, btnCance;
	private final JPanel pnStatus;

	public LytTipoServico(Frame f, boolean bol) {

		super(f, bol);

		// ================== CONFIGURAÇÕES DA JANELA ==========================
		setTitle("TIPO DE SERVIÇO");
		setSize(400, 300);

		// ================== ABA 'GRAVAR' ==========================
		// -------- LABEL 'TÍTULO'
		lblTitulo = new JLabel("GRAVAR TIPO DE SERVIÇO");
		lblTitulo.setSize(270, 25);
		lblTitulo.setLocation(70, 10);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));

		pnGravar.add(lblTitulo);

		// --------- CAMPO 'Tipo de Serviço'
		lblTipoServ = new JLabel("Tipo de Serviço");
		lblTipoServ.setSize(130, 25);
		lblTipoServ.setLocation(10, 50);

		pnGravar.add(lblTipoServ);

		txtTipoServ = new JTextField();
		txtTipoServ.setSize(300, 25);
		txtTipoServ.setLocation(20, 85);

		pnGravar.add(txtTipoServ);

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

		pnGravar.add(pnStatus);

		// --------- BOTÕES
		// --- GRAVAR
		ImageIcon icGravar = new ImageIcon(this.getClass().getResource(c.getGravar()));
		btnGravar = new JButton(icGravar);

		btnGravar.setSize(150, 60);
		btnGravar.setLocation(30, 180);
		btnGravar.addActionListener(LytTipoServico.this);

		pnGravar.add(btnGravar);

		// --- SAIR
		ImageIcon icCanc = new ImageIcon(this.getClass().getResource(c.getSair()));
		btnCance = new JButton(icCanc);

		btnCance.setSize(150, 60);
		btnCance.setLocation(190, 180);
		btnCance.addActionListener(LytTipoServico.this);

		pnGravar.add(btnCance);

		// =====================================================================
		// ======================= ABA CONSULTAR ===============================

		btnEditar.addActionListener(LytTipoServico.this);
		btnExcluir.addActionListener(LytTipoServico.this);
		btnSair.addActionListener(LytTipoServico.this);
		txtPesquisar.getDocument().addDocumentListener(LytTipoServico.this);

		criarGrid(new TipoServicoModel(getTipoServico()));

		// =====================================================================

		jtpAbas.addChangeListener(LytTipoServico.this);

	}

	public static void main(String[] args) {

		CadastroConsulta janela = new LytTipoServico(null, true);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCance || e.getSource() == btnSair) {

			this.dispose();

		} else if (e.getSource() == btnGravar) {

			if (txtTipoServ.getText().equals("")) {

				GSFIEOUtil.mensagem(this, "Existem campos em branco", GSFIEOConstants.MSG_AVISO);

			} else if (verificaTipoExiste() == true) {

				GSFIEOUtil.mensagem(this, "Este tipo de serviço já existe", GSFIEOConstants.MSG_AVISO);

			} else {

				TipoServico tipoServico = new TipoServico();
				tipoServico.setTipoServico(txtTipoServ.getText());
				tipoServico.setStatus(chkStatus.isSelected() ? '1' : '0');
				busTipoServico.gravar(tipoServico);

				GSFIEOUtil.mensagem(LytTipoServico.this, "Registro gravado com sucesso!",
						GSFIEOConstants.MSG_INFORMACAO);
				txtTipoServ.setText("");
				chkStatus.setSelected(false);

				criarGrid(new TipoServicoModel(getTipoServico()));

			}

		} else if (e.getSource() == btnEditar) {

			if (dgvDados.getSelectedRow() >= 0) {

				tipo = new TipoServicoModel(getTipoServico());

				JDialog janela = new LytEditarTipoServico(null, true,
						(int) (tipo.getValueAt((int) dgvDados.getSelectedRow(), 0)));
				janela.setVisible(true);

				criarGrid(new TipoServicoModel(getTipoServico()));

			}

		} else if (e.getSource() == btnExcluir) {

			if (dgvDados.getSelectedRow() >= 0) {

				int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza?", "Exclusão", JOptionPane.YES_NO_OPTION);

				if (txtPesquisar.getText().equals("")) {

					tipo = new TipoServicoModel(getTipoServico());

				}

				if (opcao == 0) {

					TipoServico tipoServico = new TipoServico();
					tipoServico.setCdTipoServico((int) tipo.getValueAt(dgvDados.getSelectedRow(), 0));
					busTipoServico.excluir(tipoServico);

					GSFIEOUtil.mensagem(LytTipoServico.this, "Registro excluído com sucesso!",
							GSFIEOConstants.MSG_INFORMACAO);

					tipo = new TipoServicoModel(getTipoServico());

					criarGrid(tipo);

				}

			}

		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {

		int index = jtpAbas.getSelectedIndex();

		if (index == 0) {

			setSize(400, 300);

		} else {

			setSize(525, 333);

		}

	}

	private List<TipoServico> getTipoServico() {

		List<TipoServico> lstDados = new ArrayList<>();
		TipoServico tipoServico = new TipoServico();

		tipoServico.setTipoServico(txtPesquisar.getText());
		String[][] res = busTipoServico.buscarTiposServico(tipoServico);

		for (String[] valor : res) {

			TipoServico cln = new TipoServico();

			cln.setCdTipoServico(Integer.parseInt(valor[0]));
			cln.setTipoServico(valor[1]);
			cln.setStatus(valor[2].charAt(0));
			cln.setStrStatus(valor[2].charAt(0));

			lstDados.add(cln);

		}

		return lstDados;

	}

	private boolean verificaTipoExiste() {

		TipoServico tipoServico = new TipoServico();
		tipoServico.setTipoServico(txtTipoServ.getText());
		String dados = busTipoServico.verificarTipoExiste(tipoServico)[0];

		return dados != null;

	}

	@Override
	public void insertUpdate(DocumentEvent e) {

		tipo = new TipoServicoModel(getTipoServico());
		criarGrid(tipo);

	}

	@Override
	public void removeUpdate(DocumentEvent e) {

		tipo = new TipoServicoModel(getTipoServico());
		criarGrid(tipo);

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

}
