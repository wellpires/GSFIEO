package gsfieo.mvc.consultarPedidos.view;

import gsfieo.mvc.comanda.controller.BusComanda;
import gsfieo.mvc.confirmarPedidos.controller.BusPedidos;
import gsfieo.mvc.confirmarPedidos.model.Pedidos;
import gsfieo.mvc.produtos.model.Produto;
import gsfieo.utils.informacoes.ConstantesImagens;
import gsfieo.utils.tableModel.ProdutosComandaModel;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @Author Wellington
 */

public class LytDetalharPedido extends JDialog implements ActionListener {

	private static final long serialVersionUID = 4970181177635724829L;

	private final JLabel lblComanda, lblServico, lblDataVenda, lblDataConfirmacao, lblStatus, lblQtd, lblTotal,
			lblFormaPagto;
	private final JTextField txtComanda, txtServico, txtDataVenda, txtDataConfirmacao, txtStatus, txtQtd, txtTotal,
			txtFormaPagto;
	private final JPanel pnProduto, pnQtdTotal;
	private final JTable dgvProduto;
	private final JButton btnSair;

	private final ConstantesImagens c = new ConstantesImagens();
	private final BusPedidos busPedidos = new BusPedidos();
	private final BusComanda busComanda = new BusComanda();
	private final Pedidos PEDIDOS;
	private ProdutosComandaModel mProdutosComanda;

	public LytDetalharPedido(Frame f, boolean bol, Pedidos pedidos) {

		super(f, bol);

		PEDIDOS = pedidos;

		setSize(420, 525);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(null);
		setUndecorated(true);
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		// COMANDA
		lblComanda = new JLabel("Comanda:");
		lblComanda.setSize(60, 20);
		lblComanda.setLocation(10, 10);
		add(lblComanda);

		txtComanda = new JTextField();
		txtComanda.setSize(315, 25);
		txtComanda.setLocation(75, 10);
		txtComanda.setEditable(false);
		add(txtComanda);

		// SERVIÇO
		lblServico = new JLabel("Servico:");
		lblServico.setSize(50, 20);
		lblServico.setLocation(20, 45);
		add(lblServico);

		txtServico = new JTextField();
		txtServico.setSize(315, 25);
		txtServico.setLocation(75, 45);
		txtServico.setEditable(false);
		add(txtServico);

		// DATA DE VENDA
		lblDataVenda = new JLabel("Data de Venda:");
		lblDataVenda.setSize(90, 20);
		lblDataVenda.setLocation(10, 80);
		add(lblDataVenda);

		txtDataVenda = new JTextField();
		txtDataVenda.setSize(80, 25);
		txtDataVenda.setLocation(100, 80);
		txtDataVenda.setEditable(false);
		add(txtDataVenda);

		// DATA DE CONFIRMAÇÃO
		lblDataConfirmacao = new JLabel("Data de Confirmação:");
		lblDataConfirmacao.setSize(130, 25);
		lblDataConfirmacao.setLocation(185, 80);
		add(lblDataConfirmacao);

		txtDataConfirmacao = new JTextField();
		txtDataConfirmacao.setSize(80, 25);
		txtDataConfirmacao.setLocation(310, 80);
		txtDataConfirmacao.setEditable(false);
		add(txtDataConfirmacao);

		// PRODUTOS
		pnProduto = new JPanel(new GridLayout());
		pnProduto.setSize(370, 200);
		pnProduto.setLocation(25, 110);// 150
		pnProduto.setBorder(BorderFactory.createTitledBorder("Produtos:"));
		add(pnProduto);

		mProdutosComanda = new ProdutosComandaModel(listarProdutos());

		dgvProduto = new JTable(mProdutosComanda);
		dgvProduto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvProduto.getTableHeader().setReorderingAllowed(false);
		pnProduto.add(new JScrollPane(dgvProduto));

		// ====== QUANTIDADE E QTD
		pnQtdTotal = new JPanel();
		pnQtdTotal.setSize(295, 25);
		pnQtdTotal.setLocation(63, 315);
		pnQtdTotal.setLayout(null);
		add(pnQtdTotal);

		// QUANTIDADE
		lblQtd = new JLabel("Qtd:");
		lblQtd.setSize(30, 20);
		lblQtd.setLocation(0, 0);
		pnQtdTotal.add(lblQtd);

		txtQtd = new JTextField();
		txtQtd.setSize(100, 25);
		txtQtd.setLocation(32, 0);
		txtQtd.setEditable(false);
		pnQtdTotal.add(txtQtd);

		// TOTAL
		lblTotal = new JLabel("TOTAL:");
		lblTotal.setSize(50, 20);
		lblTotal.setLocation(142, 0);
		pnQtdTotal.add(lblTotal);

		txtTotal = new JTextField();
		txtTotal.setSize(100, 25);
		txtTotal.setLocation(194, 0);
		txtTotal.setEditable(false);
		pnQtdTotal.add(txtTotal);

		// FORMA PAGTO
		lblFormaPagto = new JLabel("Forma Pagto.:");
		lblFormaPagto.setSize(80, 20);
		lblFormaPagto.setLocation(30, 350);
		add(lblFormaPagto);

		txtFormaPagto = new JTextField();
		txtFormaPagto.setSize(200, 25);
		txtFormaPagto.setLocation(115, 350);
		txtFormaPagto.setEditable(false);
		add(txtFormaPagto);

		// STATUS
		lblStatus = new JLabel("Status:");
		lblStatus.setSize(60, 20);
		lblStatus.setLocation(69, 385);
		add(lblStatus);

		txtStatus = new JTextField();
		txtStatus.setSize(200, 25);
		txtStatus.setLocation(115, 385);
		txtStatus.setEditable(false);
		add(txtStatus);
		// =====================

		ImageIcon icSair = new ImageIcon(this.getClass().getResource(c.getSair64x64()));
		btnSair = new JButton(icSair);
		btnSair.setSize(220, 70);
		btnSair.setLocation(100, 430);
		btnSair.addActionListener(LytDetalharPedido.this);
		add(btnSair);

		detalharComanda();

	}

	public static void main(String[] args) {

		Pedidos pedidos = new Pedidos();

		pedidos.getComanda().setCdComanda("j6c0j0P7A1");

		JDialog janela = new LytDetalharPedido(null, true, pedidos);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnSair) {

			this.dispose();

		}

	}

	private List<Produto> listarProdutos() {

		List<Produto> lstProduto = new ArrayList<>();

		String dados[][] = busPedidos.buscarPedidosComanda(PEDIDOS);

		for (String valor[] : dados) {

			Produto p = new Produto();

			p.setProduto(valor[0]);
			p.getTipoProduto().setTipoProduto(valor[1]);
			p.setValorUnit(Double.parseDouble(valor[2]));

			lstProduto.add(p);

		}

		return lstProduto;

	}

	private void detalharComanda() {

		String dados[] = busComanda.detalharComanda(PEDIDOS.getComanda());

		txtComanda.setText(PEDIDOS.getComanda().getCdComanda());
		txtServico.setText(dados[0]);
		txtDataVenda.setText(dados[1]);
		txtDataConfirmacao.setText(dados[2]);
		txtQtd.setText(dados[3]);
		txtTotal.setText("R$" + new DecimalFormat("0.00").format(Double.parseDouble(dados[4])));
		txtFormaPagto.setText(dados[5]);
		txtStatus.setText(dados[6]);

	}

}
