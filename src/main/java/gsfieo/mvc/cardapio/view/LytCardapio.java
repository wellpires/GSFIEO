package gsfieo.mvc.cardapio.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.produtos.controller.BusProdutos;
import gsfieo.mvc.produtos.model.Produto;
import gsfieo.mvc.servico.model.Servicos;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.informacoes.ConstantesImagens;
import gsfieo.utils.tableModel.CardapioModel;
import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

public class LytCardapio extends JDialog implements ActionListener {

	private static final long serialVersionUID = -394175602298867424L;

	private final Border borda = BorderFactory.createLineBorder(Color.BLACK, 2);
	private final BusProdutos busProdutos = new BusProdutos();
	private final ConstantesImagens c = new ConstantesImagens();

	private final String DESC_SERVICO;

	private final CardapioModel cModel;
	private final ImageIcon icGravar = new ImageIcon(this.getClass().getResource(c.getGravar72x72()));

	private final JLabel lblTitulo;
	private final JPanel pnProdutos, pnTitulo;
	private final JTable dgvProdutos;
	private final JButton btnSair, btnGravar;

	public LytCardapio(Frame f, boolean bol, Servicos servicos) {

		super(f, bol);

		DESC_SERVICO = servicos.getServico();

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(null);
		setUndecorated(true);
		setSize(669, 650);
		setLocationRelativeTo(null);
		getRootPane().setBorder(borda);

		// ----- LABEL 'TITULO'
		pnTitulo = new JPanel();
		pnTitulo.setSize(665, 50);
		pnTitulo.setLocation(0, 10);

		add(pnTitulo);

		lblTitulo = new JLabel("CARDÁPIO");
		lblTitulo.setSize(300, 40);
		lblTitulo.setLocation(100, 20);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(30f));

		pnTitulo.add(lblTitulo);

		// ==================== PRODUTOS ===================
		pnProdutos = new JPanel(new GridLayout());
		pnProdutos.setSize(460, 430);
		pnProdutos.setLocation(90, 70);
		pnProdutos.setBorder(BorderFactory.createTitledBorder(""));
		add(pnProdutos);

		cModel = new CardapioModel(getProdutos());

		dgvProdutos = new JTable(cModel);
		dgvProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvProdutos.getTableHeader().setReorderingAllowed(false);

		pnProdutos.add(new JScrollPane(dgvProdutos));

		// ============= BOTÕES ==========
		ImageIcon icSair = new ImageIcon(this.getClass().getResource(c.getSair72x72()));
		btnSair = new JButton(icSair);
		btnSair.setSize(250, 110);
		btnSair.setLocation(350, 510);
		btnSair.addActionListener(LytCardapio.this);

		add(btnSair);

		btnGravar = new JButton(icGravar);
		btnGravar.setSize(250, 110);
		btnGravar.setLocation(50, 510);
		btnGravar.addActionListener(LytCardapio.this);

		add(btnGravar);

	}

	public static void main(String[] args) {

		Servicos servicos = new Servicos();

		servicos.setServico("CANIBAL");

		JDialog janela = new LytCardapio(null, true, servicos);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnSair) {

			this.dispose();

		} else if (e.getSource() == btnGravar) {

			int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza?", "GSFIEO", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (opcao == JOptionPane.YES_OPTION) {

				Produto produto = new Produto();

				for (int i = 0; i < cModel.getRowCount(); i++) {

					produto.setCdProduto(Integer.parseInt(String.valueOf(cModel.getValueAt(i, 0))));
					produto.setValorUnit(Double.parseDouble(String.valueOf(cModel.getValueAt(i, 3)).replace(',', '.')));

					busProdutos.editarPreco(produto);

				}

			}

			GSFIEOUtil.mensagem(LytCardapio.this, "Cardápio alterado com sucesso!", GSFIEOConstants.MSG_INFORMACAO);

		}

	}

	private List<Produto> getProdutos() {

		List<Produto> lstDados = new ArrayList<>();

		Produto produto = new Produto();

		produto.setProduto("");
		produto.getTipoProduto().setTipoProduto("");
		produto.getServicos().setServico(DESC_SERVICO);

		String res[][] = busProdutos.listarProdutosComPreco(produto);

		for (String[] item : res) {

			Produto cln = new Produto();

			cln.setCdProduto(Integer.parseInt(item[0]));
			cln.setProduto(item[1]);
			cln.getTipoProduto().setTipoProduto(item[2]);
			cln.setValorUnit(Double.parseDouble(item[3] == null ? "0" : item[3]));

			lstDados.add(cln);

		}

		return lstDados;

	}

}
