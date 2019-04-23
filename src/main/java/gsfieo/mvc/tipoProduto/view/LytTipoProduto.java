package gsfieo.mvc.tipoProduto.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.principal.view.CadastroConsulta;
import gsfieo.mvc.tipoProduto.controller.BusTipoProduto;
import gsfieo.mvc.tipoProduto.model.TipoProduto;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.tableModel.TipoProdutoModel;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LytTipoProduto extends CadastroConsulta implements ActionListener, ChangeListener, DocumentListener {

	private static final long serialVersionUID = 575507605693485446L;

	private final BusTipoProduto busTipoProduto = new BusTipoProduto();
	private TipoProdutoModel tpModel;

	private final JLabel lblTitulo, lblTipoProdu;
	private final JTextField txtTipoProduto;
	private final JButton btnGravar, btnCance;

	public LytTipoProduto(Frame f, boolean bol) {

		super(f, bol);

		setTitle("Tipo de produto");
		setSize(350, 230);

		// ======================================================================
		// ======================= ABA GRAVAR ==================================

		// -------- LABEL 'TÍTULO'
		lblTitulo = new JLabel("Cadastrar tipo de produto");
		lblTitulo.setSize(300, 25);
		lblTitulo.setLocation(30, 10);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));

		pnGravar.add(lblTitulo);

		// -------- CAMPO 'Tipo de produto'
		lblTipoProdu = new JLabel("Tipo:");
		lblTipoProdu.setSize(120, 25);
		lblTipoProdu.setLocation(10, 45);

		pnGravar.add(lblTipoProdu);

		txtTipoProduto = new JTextField();
		txtTipoProduto.setSize(200, 25);
		txtTipoProduto.setLocation(20, 75);

		pnGravar.add(txtTipoProduto);

		// --------- BOTÕES
		// --- GRAVAR
		ImageIcon icGravar = new ImageIcon(this.getClass().getResource(c.getGravar()));
		btnGravar = new JButton(icGravar);

		btnGravar.setSize(150, 60);
		btnGravar.setLocation(15, 120);
		btnGravar.addActionListener(LytTipoProduto.this);

		pnGravar.add(btnGravar);

		// --- SAIR
		ImageIcon icCanc = new ImageIcon(this.getClass().getResource(c.getSair()));
		btnCance = new JButton(icCanc);

		btnCance.setSize(150, 60);
		btnCance.setLocation(175, 120);
		btnCance.addActionListener(LytTipoProduto.this);

		pnGravar.add(btnCance);

		// =====================================================================
		// ======================= ABA CONSULTAR ===============================

		btnEditar.addActionListener(LytTipoProduto.this);
		btnExcluir.addActionListener(LytTipoProduto.this);
		btnSair.addActionListener(LytTipoProduto.this);
		txtPesquisar.getDocument().addDocumentListener(LytTipoProduto.this);

		// =====================================================================

		jtpAbas.addChangeListener(LytTipoProduto.this);

		tpModel = new TipoProdutoModel(getTipoProduto());

		criarGrid(tpModel);

	}

	public static void main(String[] args) {

		LytTipoProduto janela = new LytTipoProduto(null, true);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCance || e.getSource() == btnSair) {

			this.dispose();

		} else if (e.getSource() == btnGravar) {

			if (txtTipoProduto.getText().equals("")) {

				GSFIEOUtil.mensagem(this, "Existem campos em branco", GSFIEOConstants.MSG_AVISO);

			} else if (verificaTipoExiste() == true) {

				GSFIEOUtil.mensagem(this, "Este tipo de produto já existe", GSFIEOConstants.MSG_AVISO);

			}

			else {

				TipoProduto tipoProduto = new TipoProduto();
				tipoProduto.setTipoProduto(txtTipoProduto.getText());

				busTipoProduto.gravar(tipoProduto);

				GSFIEOUtil.mensagem(LytTipoProduto.this, "Registro gravado com sucesso!",
						GSFIEOConstants.MSG_INFORMACAO);

				txtTipoProduto.setText("");

				tpModel = new TipoProdutoModel(getTipoProduto());

				criarGrid(tpModel);
			}

		} else if (e.getSource() == btnEditar) {

			if (dgvDados.getSelectedRow() >= 0) {

				LytEditarTipoProduto janela = new LytEditarTipoProduto(null, true,
						(int) tpModel.getValueAt(dgvDados.getSelectedRow(), 0));
				janela.setVisible(true);

				tpModel = new TipoProdutoModel(getTipoProduto());

				criarGrid(tpModel);

			}

		} else if (e.getSource() == btnExcluir) {

			if (dgvDados.getSelectedRow() >= 0) {

				int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza?", "Exclusão", JOptionPane.YES_NO_OPTION);

				if (opcao == 0) {

					TipoProduto tipoProduto = new TipoProduto();

					tipoProduto.setCdTipoProduto((int) tpModel.getValueAt(dgvDados.getSelectedRow(), 0));
					busTipoProduto.excluir(tipoProduto);

					GSFIEOUtil.mensagem(LytTipoProduto.this, "Registro excluído com sucesso!",
							GSFIEOConstants.MSG_INFORMACAO);

					tpModel = new TipoProdutoModel(getTipoProduto());

					criarGrid(tpModel);

				}

			}

		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {

		int index = jtpAbas.getSelectedIndex();

		if (index == 0) {

			setSize(350, 230);

		} else if (index == 1) {

			setSize(525, 333);

		}

	}

	private List<TipoProduto> getTipoProduto() {

		List<TipoProduto> lstDados = new ArrayList<>();
		TipoProduto tipoProduto = new TipoProduto();

		tipoProduto.setTipoProduto(txtPesquisar.getText());
		String[][] res = busTipoProduto.listarTipoProduto(tipoProduto);

		for (String[] valor : res) {

			TipoProduto cln = new TipoProduto();

			cln.setCdTipoProduto(Integer.parseInt(valor[0]));
			cln.setTipoProduto(valor[1]);

			lstDados.add(cln);

		}

		return lstDados;

	}

	private boolean verificaTipoExiste() {

		TipoProduto tipoProduto = new TipoProduto();
		tipoProduto.setTipoProduto(txtTipoProduto.getText());

		String dados = busTipoProduto.verificaTipoProdutoExiste(tipoProduto)[0];

		return dados != null;

	}

	@Override
	public void insertUpdate(DocumentEvent e) {

		tpModel = new TipoProdutoModel(getTipoProduto());

		criarGrid(tpModel);

	}

	@Override
	public void removeUpdate(DocumentEvent e) {

		tpModel = new TipoProdutoModel(getTipoProduto());

		criarGrid(tpModel);

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

}
