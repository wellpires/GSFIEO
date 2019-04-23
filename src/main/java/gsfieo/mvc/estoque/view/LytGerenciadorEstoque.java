package gsfieo.mvc.estoque.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.estoque.controller.BusEstoque;
import gsfieo.mvc.estoque.model.Estoque;
import gsfieo.mvc.produtos.controller.BusProdutos;
import gsfieo.mvc.produtos.model.Produto;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.informacoes.ConstantesImagens;
import gsfieo.utils.tableModel.ProdutoModel;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LytGerenciadorEstoque extends JDialog implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = -3593860223585676535L;

	private ProdutoModel pModel;
	private final ConstantesImagens c = new ConstantesImagens();

	private final BusProdutos busProdutos = new BusProdutos();
	private final BusEstoque busEstoque = new BusEstoque();

	private final String DESC_SERVICO;

	private final JLabel lblTitulo, lblProduto, lblQtdeMaxima, lblQtdeMinima, lblQtde;
	private final JPanel pnProdutos, pnInformacoes;
	private final JTable dgvProdutos;
	private final JTextField txtProduto;
	private final JSpinner spQtdeMaxima, spQtdeMinima, spQtde;
	private final JButton btnConfirmar, btnSair;

	public LytGerenciadorEstoque(Frame f, boolean bol, String servicos) {

		super(f, bol);

		DESC_SERVICO = servicos;

		setTitle("Gerenciador de Estoque");
		setSize(900, 634);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		// ====================== TÍTULO ==========================

		lblTitulo = new JLabel("Gerenciador de Estoque");
		lblTitulo.setSize(500, 40);
		lblTitulo.setLocation(280, 10);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(30f));

		add(lblTitulo);

		// ========================================================
		// ===================== PRODUTOS =========================
		pnProdutos = new JPanel();
		pnProdutos.setSize(400, 500);
		pnProdutos.setLocation(50, 80);
		pnProdutos.setLayout(new GridLayout());

		add(pnProdutos);

		pModel = new ProdutoModel(listarProdutos());

		dgvProdutos = new JTable();
		dgvProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvProdutos.getTableHeader().setReorderingAllowed(false);
		dgvProdutos.setModel(pModel);
		dgvProdutos.getSelectionModel().addListSelectionListener(LytGerenciadorEstoque.this);

		pnProdutos.add(new JScrollPane(dgvProdutos));

		// ===========================================================
		// ===================== INFORMAÇÕES =========================
		pnInformacoes = new JPanel();
		pnInformacoes.setSize(350, 380);
		pnInformacoes.setLocation(480, 90);
		pnInformacoes.setBorder(BorderFactory.createTitledBorder("Informações:"));
		pnInformacoes.setLayout(null);

		add(pnInformacoes);

		// ----- CAMPO 'PRODUTO'
		lblProduto = new JLabel("Produto:");
		lblProduto.setSize(200, 25);
		lblProduto.setLocation(15, 25);

		pnInformacoes.add(lblProduto);

		txtProduto = new JTextField();
		txtProduto.setSize(250, 25);
		txtProduto.setLocation(25, 55);
		txtProduto.setEditable(false);

		pnInformacoes.add(txtProduto);

		// ----- CAMPO 'QTDE MINIMA'
		lblQtdeMinima = new JLabel("Qtde Minima:");
		lblQtdeMinima.setSize(200, 25);
		lblQtdeMinima.setLocation(15, 90);

		pnInformacoes.add(lblQtdeMinima);

		SpinnerNumberModel spQtdeMinimaModel = new SpinnerNumberModel(0, 0, 999, 1);

		spQtdeMinima = new JSpinner(spQtdeMinimaModel);
		spQtdeMinima.setSize(150, 25);
		spQtdeMinima.setLocation(25, 120);

		pnInformacoes.add(spQtdeMinima);

		// ----- CAMPO 'QTDE MAXIMA'
		lblQtdeMaxima = new JLabel("Qtde Máxima:");
		lblQtdeMaxima.setSize(200, 25);
		lblQtdeMaxima.setLocation(15, 150);

		pnInformacoes.add(lblQtdeMaxima);

		SpinnerNumberModel spQtdeMaximaModel = new SpinnerNumberModel(0, 0, 1000, 1);

		spQtdeMaxima = new JSpinner(spQtdeMaximaModel);
		spQtdeMaxima.setSize(150, 25);
		spQtdeMaxima.setLocation(25, 180);

		pnInformacoes.add(spQtdeMaxima);

		// ----- CAMPO 'QTDE'
		lblQtde = new JLabel("Quantidade:");
		lblQtde.setSize(200, 25);
		lblQtde.setLocation(15, 210);

		pnInformacoes.add(lblQtde);

		SpinnerNumberModel spQtdeModel = new SpinnerNumberModel(0, 0, 1000, 1);

		spQtde = new JSpinner(spQtdeModel);
		spQtde.setSize(150, 25);
		spQtde.setLocation(25, 235);

		pnInformacoes.add(spQtde);

		// ----- BOTÃO 'CONFIRMAR'
		ImageIcon icConfirmar = new ImageIcon(this.getClass().getResource(c.getConfirmar()));
		btnConfirmar = new JButton(icConfirmar);
		btnConfirmar.setSize(300, 70);
		btnConfirmar.setLocation(25, 280);
		btnConfirmar.addActionListener(LytGerenciadorEstoque.this);

		pnInformacoes.add(btnConfirmar);

		// ===========================================================
		// ----- BOTÃO 'SAIR'
		ImageIcon icSair = new ImageIcon(this.getClass().getResource(c.getSair64x64()));
		btnSair = new JButton(icSair);
		btnSair.setSize(250, 99);
		btnSair.setLocation(578, 480);
		btnSair.addActionListener(LytGerenciadorEstoque.this);

		add(btnSair);

	}

	public static void main(String[] args) {

		JDialog janela = new LytGerenciadorEstoque(null, true, "RONALDO");
		janela.setVisible(true);

	}

	private List<Produto> listarProdutos() {

		List<Produto> lstProduto = new ArrayList<>();
		Produto produto = new Produto();

		produto.getServicos().setServico(DESC_SERVICO);

		String dados[][] = busProdutos.listarProdutos_2(produto);

		for (String[] valor : dados) {

			Produto cln = new Produto();

			cln.setCdProduto(Integer.parseInt(valor[0]));
			cln.setProduto(valor[1]);
			cln.getTipoProduto().setTipoProduto(valor[2]);

			lstProduto.add(cln);

		}

		return lstProduto;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnSair) {

			this.dispose();

		} else if (e.getSource() == btnConfirmar) {

			if (((int) spQtdeMinima.getValue()) >= ((int) spQtdeMaxima.getValue())) {

				GSFIEOUtil.mensagem(this, "A quantidade mínima deve ser menor que a quantidade máxima",
						GSFIEOConstants.MSG_AVISO);

			} else if (((int) spQtdeMaxima.getValue()) <= ((int) spQtdeMinima.getValue())) {

				GSFIEOUtil.mensagem(this, "A quantidade máxima deve ser maior que a quantidade mínima",
						GSFIEOConstants.MSG_AVISO);

			} else if (((int) spQtde.getValue()) < ((int) spQtdeMinima.getValue())
					|| ((int) spQtde.getValue()) > ((int) spQtdeMaxima.getValue())) {

				GSFIEOUtil.mensagem(this,
						"Q quantidade em estoque deve ser maior que a quantidade mínima e menor que a quantidade máxima",
						GSFIEOConstants.MSG_AVISO);

			} else {

				Estoque estoque = new Estoque();

				estoque.setQtd((int) spQtde.getValue());
				estoque.setQtdMaxima((int) spQtdeMaxima.getValue());
				estoque.setQtdMinima((int) spQtdeMinima.getValue());
				estoque.getProduto().setProduto(txtProduto.getText());
				estoque.getServico().setServico(DESC_SERVICO);

				busEstoque.editar(estoque);

				pModel = new ProdutoModel(listarProdutos());

				dgvProdutos.setModel(pModel);

				txtProduto.setText("");
				spQtde.setValue(0);
				spQtdeMaxima.setValue(0);
				spQtdeMinima.setValue(0);

			}

		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		try {

			Estoque estoque = new Estoque();

			estoque.getProduto().setProduto(String.valueOf(pModel.getValueAt(dgvProdutos.getSelectedRow(), 1)));
			estoque.getServico().setServico(DESC_SERVICO);

			String dados[] = busEstoque.listarProdutosEstoque(estoque);

			txtProduto.setText(dados[0]);
			spQtdeMinima.setValue(Integer.parseInt(dados[1]));
			spQtdeMaxima.setValue(Integer.parseInt(dados[2]));
			spQtde.setValue(Integer.parseInt(dados[3]));

		} catch (Exception erro) {

		}

	}

}
