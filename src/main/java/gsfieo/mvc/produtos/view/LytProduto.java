package gsfieo.mvc.produtos.view;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import gsfieo.mvc.estoque.controller.BusEstoque;
import gsfieo.mvc.estoque.model.Estoque;
import gsfieo.mvc.principal.view.CadastroConsulta;
import gsfieo.mvc.produtos.controller.BusProdutos;
import gsfieo.mvc.produtos.model.Produto;
import gsfieo.mvc.tipoProduto.controller.BusTipoProduto;
import gsfieo.mvc.tipoProduto.model.TipoProduto;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.tableModel.ProdutoModel;

public class LytProduto extends CadastroConsulta implements ActionListener, ChangeListener, DocumentListener {

	private static final long serialVersionUID = -9031327268980676215L;

	private ProdutoModel pModel;

	private final BusProdutos busProdutos = new BusProdutos();
	private final BusEstoque busEstoque = new BusEstoque();
	private final BusTipoProduto busTipoProduto = new BusTipoProduto();

	private final String DESC_SERVICO;

	private final JLabel lblTitulo, lblTipo, lblProduto;
	private final JComboBox<String> cbTipo;
	private final JTextField txtProduto;
	private final JButton btnGravar, btnCance;

	public LytProduto(Frame f, boolean bol, String servico) {

		super(f, bol);

		setTitle("Produtos");
		setSize(340, 290);

		DESC_SERVICO = servico;

		// ======================================================================
		// ======================= ABA GRAVAR ==================================

		// -------- LABEL 'TÍTULO'
		lblTitulo = new JLabel("Cadastrar Produtos");
		lblTitulo.setSize(210, 25);
		lblTitulo.setLocation(10, 10);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));

		pnGravar.add(lblTitulo);

		// -------- CAMPO 'Tipo LytProduto'
		lblTipo = new JLabel("Tipo de produto:");
		lblTipo.setSize(210, 25);
		lblTipo.setLocation(10, 45);

		pnGravar.add(lblTipo);

		cbTipo = new JComboBox<>(listarCombo());
		cbTipo.setSize(200, 25);
		cbTipo.setLocation(20, 75);

		pnGravar.add(cbTipo);

		// -------- CAMPO 'LytProduto'
		lblProduto = new JLabel("Produto:");
		lblProduto.setSize(120, 25);
		lblProduto.setLocation(10, 110);

		pnGravar.add(lblProduto);

		txtProduto = new JTextField();
		txtProduto.setSize(280, 25);
		txtProduto.setLocation(20, 140);

		pnGravar.add(txtProduto);

		// --------- BOTÕES
		// --- GRAVAR
		ImageIcon icGravar = new ImageIcon(this.getClass().getResource(c.getGravar()));

		btnGravar = new JButton(icGravar);
		btnGravar.setSize(150, 60);
		btnGravar.setLocation(15, 180);
		btnGravar.addActionListener(LytProduto.this);

		pnGravar.add(btnGravar);

		// --- CANCELAR
		ImageIcon icCanc = new ImageIcon(this.getClass().getResource(c.getSair()));

		btnCance = new JButton(icCanc);
		btnCance.setSize(150, 60);
		btnCance.setLocation(170, 180);
		btnCance.addActionListener(LytProduto.this);

		pnGravar.add(btnCance);

		// =====================================================================
		// ======================= ABA CONSULTAR ===============================

		btnEditar.addActionListener(LytProduto.this);
		btnExcluir.addActionListener(LytProduto.this);
		btnSair.addActionListener(LytProduto.this);
		txtPesquisar.getDocument().addDocumentListener(LytProduto.this);

		// =====================================================================

		jtpAbas.addChangeListener(LytProduto.this);

		pModel = new ProdutoModel(getProdutos());

		criarGrid(pModel);

	}

	public static void main(String[] args) {

		CadastroConsulta janela = new LytProduto(null, true, "");
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCance || e.getSource() == btnSair) {

			this.dispose();

		} else if (e.getSource() == btnGravar) {

			if (txtProduto.getText().equals("")) {

				GSFIEOUtil.mensagem(this, "Existem campos em branco", GSFIEOConstants.MSG_AVISO);

			} else if (verificarProdutoExiste() == true) {

				GSFIEOUtil.mensagem(this, "Este produto já foi cadastrado neste estabelecimento",
						GSFIEOConstants.MSG_AVISO);

			} else {

				// GRAVAR PRODUTO
				Produto produto = new Produto();

				produto.setProduto(txtProduto.getText());
				produto.getTipoProduto().setTipoProduto(String.valueOf(cbTipo.getSelectedItem()));
				produto.getServicos().setServico(DESC_SERVICO);

				busProdutos.gravar(produto);

				// GRAVAR ESTOQUE
				Estoque estoque = new Estoque();

				estoque.getServico().setServico(DESC_SERVICO);
				estoque.setProduto(produto);

				busEstoque.gravarProdutoEstoque(estoque);

				GSFIEOUtil.mensagem(LytProduto.this, "Registro gravado com sucesso!", GSFIEOConstants.MSG_INFORMACAO);

				cbTipo.setSelectedIndex(0);
				txtProduto.setText("");

				pModel = new ProdutoModel(getProdutos());

				criarGrid(pModel);

			}

		} else if (e.getSource() == btnEditar) {

			if (dgvDados.getSelectedRow() >= 0) {

				LytEditarProduto janela = new LytEditarProduto(null, true,
						(int) pModel.getValueAt((int) dgvDados.getSelectedRow(), 0));
				janela.setVisible(true);

				pModel = new ProdutoModel(getProdutos());

				criarGrid(pModel);

			}

		} else if (e.getSource() == btnExcluir) {

			if (dgvDados.getSelectedRow() >= 0) {

				int opcao = JOptionPane.showConfirmDialog(LytProduto.this, "Tem certeza?", "Exclusão",
						JOptionPane.YES_NO_OPTION);

				if (opcao == 0) {

					Produto produto = new Produto();

					produto.setCdProduto((int) pModel.getValueAt(dgvDados.getSelectedRow(), 0));
					busProdutos.excluir(produto);

					GSFIEOUtil.mensagem(LytProduto.this, "Registro excluído com sucesso!",
							GSFIEOConstants.MSG_INFORMACAO);

					pModel = new ProdutoModel(getProdutos());

					criarGrid(pModel);

				}

			}

		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {

		int index = jtpAbas.getSelectedIndex();

		if (index == 0) {

			setSize(340, 290);

		} else if (index == 1) {

			setSize(525, 333);

		}

	}

	private String[] listarCombo() {

		TipoProduto tipoProduto = new TipoProduto();
		tipoProduto.setTipoProduto("");

		String[][] res = busTipoProduto.listarTipoProduto(tipoProduto);
		int MAX = res.length;

		String[] dados = new String[MAX];

		for (int i = 0; i < MAX; i++) {

			dados[i] = res[i][1];

		}

		return dados;

	}

	private List<Produto> getProdutos() {

		List<Produto> lstDados = new ArrayList<>();
		Produto produto = new Produto();

		produto.setProduto(txtPesquisar.getText());
		produto.getTipoProduto().setTipoProduto(txtPesquisar.getText());
		produto.getServicos().setServico(DESC_SERVICO);

		String[][] res = busProdutos.listarProdutos(produto);

		for (String valor[] : res) {

			Produto cln = new Produto();

			cln.setCdProduto(Integer.parseInt(valor[0]));
			cln.setProduto(valor[1]);
			cln.getTipoProduto().setTipoProduto(valor[2]);

			lstDados.add(cln);

		}

		return lstDados;
	}

	private boolean verificarProdutoExiste() {

		Produto produto = new Produto();

		produto.setProduto(txtProduto.getText());
		produto.getServicos().setServico(DESC_SERVICO);
		String dados = busProdutos.verificarExisteProduto(produto)[0];

		return dados != null;

	}

	@Override
	public void insertUpdate(DocumentEvent e) {

		pModel = new ProdutoModel(getProdutos());
		criarGrid(pModel);

	}

	@Override
	public void removeUpdate(DocumentEvent e) {

		pModel = new ProdutoModel(getProdutos());
		criarGrid(pModel);

	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

}
