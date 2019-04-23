package gsfieo.mvc.confirmarPedidos.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.comanda.controller.BusComanda;
import gsfieo.mvc.comanda.model.Comanda;
import gsfieo.mvc.confirmarPedidos.controller.BusPedidos;
import gsfieo.mvc.confirmarPedidos.model.Pedidos;
import gsfieo.mvc.estoque.controller.BusEstoque;
import gsfieo.mvc.estoque.model.Estoque;
import gsfieo.mvc.produtos.controller.BusProdutos;
import gsfieo.mvc.produtos.model.Produto;
import gsfieo.mvc.verificaPedidos.view.LytPopUpPedidos;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.informacoes.ConstantesImagens;
import gsfieo.utils.tableModel.CarrinhoModel;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class LytConfirmarPedido extends JDialog implements ActionListener, ItemListener, FocusListener {

	private static final long serialVersionUID = 3146868845415646352L;

	private CarrinhoModel carrinho;
	private final JButton btnConfirmar, btnRecusar, btnSair;
	private final ConstantesImagens c = new ConstantesImagens();

	private final BusComanda busComanda = new BusComanda();
	private final BusEstoque busEstoque = new BusEstoque();
	private final BusPedidos busPedidos = new BusPedidos();
	private final BusProdutos busProdutos = new BusProdutos();

	private final String DESC_SERVICO;

	// ============= PESQUISA
	private final JPanel pnPesquisa;
	private final JLabel lblPesquisaComanda, lblPesquisaProntuario;
	private final JTextField txtPesquisaComanda, txtPesquisaProntuario;
	private final JRadioButton rbPesquisaComanda, rbPesquisaProntuario;

	// ============= INFORMAÇÕES
	private final JPanel pnInformacoes, pnInfoTotal, pnInfoProdutos;
	private final JLabel lblInfoComanda, lblInfoProntuario, lblInfoFormaPagto, lblInfoStatus, lblInfoTotal;
	private final JTextField txtInfoComanda, txtInfoProntuario, txtInfoFormaPagto, txtInfoStatus;
	private final JTable dgvInfoPedido;

	public LytConfirmarPedido(Frame f, boolean bol, String servico) {

		super(f, bol);

		setTitle("PEDIDOS");
		setSize(830, 690);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(null);
		setResizable(false);

		DESC_SERVICO = servico;

		// ====================================================
		// ===== CONTÊINER 'PESQUISA'
		pnPesquisa = new JPanel();
		pnPesquisa.setSize(320, 160);
		pnPesquisa.setLocation(40, 20);
		pnPesquisa.setLayout(null);
		pnPesquisa.setBorder(BorderFactory.createTitledBorder("PESQUISA"));

		add(pnPesquisa);

		// ---- CAMPO 'COMANDA'
		lblPesquisaComanda = new JLabel("Comanda");
		lblPesquisaComanda.setSize(150, 30);
		lblPesquisaComanda.setLocation(60, 15);
		lblPesquisaComanda.setFont(lblPesquisaComanda.getFont().deriveFont(15f));

		pnPesquisa.add(lblPesquisaComanda);

		rbPesquisaComanda = new JRadioButton();
		rbPesquisaComanda.setSize(20, 20);
		rbPesquisaComanda.setLocation(20, 45);
		rbPesquisaComanda.setSelected(true);
		rbPesquisaComanda.addItemListener(LytConfirmarPedido.this);

		pnPesquisa.add(rbPesquisaComanda);

		txtPesquisaComanda = new JTextField();
		txtPesquisaComanda.setSize(250, 25);
		txtPesquisaComanda.setLocation(42, 45);
		txtPesquisaComanda.addFocusListener(LytConfirmarPedido.this);

		pnPesquisa.add(txtPesquisaComanda);

		// ---- CAMPO 'PRONTUÁRIO'
		lblPesquisaProntuario = new JLabel("Prontuário");
		lblPesquisaProntuario.setSize(150, 30);
		lblPesquisaProntuario.setLocation(60, 80);
		lblPesquisaProntuario.setFont(lblPesquisaProntuario.getFont().deriveFont(15f));

		pnPesquisa.add(lblPesquisaProntuario);

		rbPesquisaProntuario = new JRadioButton();
		rbPesquisaProntuario.setSize(20, 20);
		rbPesquisaProntuario.setLocation(20, 110);
		rbPesquisaProntuario.addItemListener(LytConfirmarPedido.this);

		pnPesquisa.add(rbPesquisaProntuario);

		txtPesquisaProntuario = new JTextField();
		txtPesquisaProntuario.setSize(250, 25);
		txtPesquisaProntuario.setLocation(42, 110);
		txtPesquisaProntuario.setEnabled(false);
		txtPesquisaProntuario.addFocusListener(LytConfirmarPedido.this);

		pnPesquisa.add(txtPesquisaProntuario);

		ButtonGroup bgRadio = new ButtonGroup();
		bgRadio.add(rbPesquisaComanda);
		bgRadio.add(rbPesquisaProntuario);

		// ====================================================
		// ===== CONTÊINER 'INFORMAÇÕES'
		pnInformacoes = new JPanel();
		pnInformacoes.setSize(780, 350);
		pnInformacoes.setLocation(20, 200);
		pnInformacoes.setLayout(null);
		pnInformacoes.setBorder(BorderFactory.createTitledBorder("Informações"));

		add(pnInformacoes);

		// ---- CAMPO 'COMANDA'
		lblInfoComanda = new JLabel("Comanda");
		lblInfoComanda.setSize(150, 30);
		lblInfoComanda.setLocation(15, 15);
		lblInfoComanda.setFont(lblInfoComanda.getFont().deriveFont(15f));

		pnInformacoes.add(lblInfoComanda);

		txtInfoComanda = new JTextField();
		txtInfoComanda.setSize(250, 25);
		txtInfoComanda.setLocation(15, 45);
		txtInfoComanda.setEditable(false);

		pnInformacoes.add(txtInfoComanda);

		// ---- CAMPO 'PRONTUÁRIO'
		lblInfoProntuario = new JLabel("Prontuário");
		lblInfoProntuario.setSize(150, 30);
		lblInfoProntuario.setLocation(15, 75);
		lblInfoProntuario.setFont(lblInfoProntuario.getFont().deriveFont(15f));

		pnInformacoes.add(lblInfoProntuario);

		txtInfoProntuario = new JTextField();
		txtInfoProntuario.setSize(250, 25);
		txtInfoProntuario.setLocation(15, 105);
		txtInfoProntuario.setEditable(false);

		pnInformacoes.add(txtInfoProntuario);

		// ---- CAMPO 'FORMA PAGTO'
		lblInfoFormaPagto = new JLabel("Forma de pagamento");
		lblInfoFormaPagto.setSize(200, 30);
		lblInfoFormaPagto.setLocation(15, 140);
		lblInfoFormaPagto.setFont(lblInfoFormaPagto.getFont().deriveFont(15f));

		pnInformacoes.add(lblInfoFormaPagto);

		txtInfoFormaPagto = new JTextField();
		txtInfoFormaPagto.setSize(250, 25);
		txtInfoFormaPagto.setLocation(15, 170);
		txtInfoFormaPagto.setEditable(false);

		pnInformacoes.add(txtInfoFormaPagto);

		// ---- CAMPO 'STATUS'
		lblInfoStatus = new JLabel("Status");
		lblInfoStatus.setSize(250, 25);
		lblInfoStatus.setLocation(15, 205);
		lblInfoStatus.setFont(lblInfoStatus.getFont().deriveFont(15f));

		pnInformacoes.add(lblInfoStatus);

		txtInfoStatus = new JTextField();
		txtInfoStatus.setSize(250, 25);
		txtInfoStatus.setLocation(15, 230);
		txtInfoStatus.setEditable(false);

		pnInformacoes.add(txtInfoStatus);

		// ---- CAMPO 'TOTAL'
		pnInfoTotal = new JPanel();
		pnInfoTotal.setSize(250, 80);
		pnInfoTotal.setLocation(15, 260);
		pnInfoTotal.setLayout(null);
		pnInfoTotal.setBorder(BorderFactory.createTitledBorder("TOTAL"));

		pnInformacoes.add(pnInfoTotal);

		lblInfoTotal = new JLabel("");
		lblInfoTotal.setSize(150, 30);
		lblInfoTotal.setLocation(50, 25);
		lblInfoTotal.setFont(lblInfoTotal.getFont().deriveFont(30f));

		pnInfoTotal.add(lblInfoTotal);

		// ---- GRID 'PEDIDO'
		pnInfoProdutos = new JPanel();
		pnInfoProdutos.setSize(480, 320);
		pnInfoProdutos.setLocation(285, 20);
		pnInfoProdutos.setLayout(new GridLayout());
		pnInfoProdutos.setBorder(BorderFactory.createTitledBorder("Pedidos"));

		pnInformacoes.add(pnInfoProdutos);

		carrinho = new CarrinhoModel(inicializarCarrinho());

		dgvInfoPedido = new JTable();
		dgvInfoPedido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvInfoPedido.getTableHeader().setReorderingAllowed(false);
		dgvInfoPedido.setModel(carrinho);

		pnInfoProdutos.add(new JScrollPane(dgvInfoPedido));

		// ================================================
		ImageIcon icAceitar = new ImageIcon(this.getClass().getResource(c.getAceitar()));
		btnConfirmar = new JButton(icAceitar);
		btnConfirmar.setSize(300, 80);
		btnConfirmar.setLocation(100, 560);
		btnConfirmar.setToolTipText("Aceitar");
		btnConfirmar.setEnabled(false);
		btnConfirmar.addActionListener(LytConfirmarPedido.this);

		add(btnConfirmar);

		ImageIcon icRecusar = new ImageIcon(this.getClass().getResource(c.getRecusar()));
		btnRecusar = new JButton(icRecusar);
		btnRecusar.setSize(300, 80);
		btnRecusar.setLocation(430, 560);
		btnRecusar.setToolTipText("Recusar");
		btnRecusar.setEnabled(false);
		btnRecusar.addActionListener(LytConfirmarPedido.this);

		add(btnRecusar);

		ImageIcon icSair = new ImageIcon(this.getClass().getResource(c.getSair64x64()));
		btnSair = new JButton(icSair);
		btnSair.setSize(130, 100);
		btnSair.setLocation(670, 10);
		btnSair.addActionListener(LytConfirmarPedido.this);

		add(btnSair);

	}

	public static void main(String[] args) {

		JDialog janela = new LytConfirmarPedido(null, true, null);
		janela.setVisible(true);

	}

	private List<Produto> inicializarCarrinho() {

		List<Produto> lstDados = new ArrayList<>();

		Produto produto = new Produto();

		lstDados.add(produto);
		lstDados.remove(0);

		return lstDados;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnConfirmar) {

			int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza?", "Confirmar", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (opcao == JOptionPane.YES_OPTION) {

				Comanda comanda = new Comanda();

				comanda.setStatus("0");
				comanda.setCdComanda(txtInfoComanda.getText());

				busComanda.alterarStatus(comanda);

				Estoque estoque = new Estoque();
				estoque.getServico().setServico(DESC_SERVICO);

				int MAX = carrinho.getRowCount();

				for (int i = 0; i < MAX; i++) {

					String produto = String.valueOf(carrinho.getValueAt(i, 0));
					int qtd = (int) carrinho.getValueAt(i, 2);

					estoque.setQtd(qtd);
					estoque.getProduto().setProduto(produto);

					busEstoque.daBaixaEstoque(estoque);

				}

				GSFIEOUtil.mensagem(LytConfirmarPedido.this, "Pedido aceito com sucesso!",
						GSFIEOConstants.MSG_INFORMACAO);

				limparCampos();

			}

		} else if (e.getSource() == btnRecusar) {

			int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza?", "Recusar", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (opcao == 0) {

				Comanda comanda = new Comanda();

				comanda.setStatus("1");
				comanda.setCdComanda(txtInfoComanda.getText());

				busComanda.alterarStatus(comanda);

			}

			GSFIEOUtil.mensagem(LytConfirmarPedido.this, "Pedido recusado com sucesso!",
					GSFIEOConstants.MSG_INFORMACAO);

			limparCampos();

		} else if (e.getSource() == btnSair) {

			this.dispose();

		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (rbPesquisaComanda.isSelected()) {

			txtPesquisaComanda.setEnabled(true);
			txtPesquisaProntuario.setEnabled(false);
			txtPesquisaProntuario.setText("");
			txtInfoComanda.setText("");
			txtInfoFormaPagto.setText("");
			txtInfoProntuario.setText("");
			txtInfoStatus.setText("");
			lblInfoTotal.setText("");
			carrinho = new CarrinhoModel(inicializarCarrinho());

		} else if (rbPesquisaProntuario.isSelected()) {

			txtPesquisaProntuario.setEnabled(true);
			txtPesquisaComanda.setEnabled(false);
			txtPesquisaComanda.setText("");
			txtInfoComanda.setText("");
			txtInfoFormaPagto.setText("");
			txtInfoProntuario.setText("");
			txtInfoStatus.setText("");
			lblInfoTotal.setText("");

			carrinho = new CarrinhoModel(inicializarCarrinho());

		}

		dgvInfoPedido.setModel(carrinho);

	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {

		if (e.getSource() == txtPesquisaComanda && rbPesquisaComanda.isSelected()) {

			listarCampos();

		} else if (e.getSource() == txtPesquisaProntuario && rbPesquisaProntuario.isSelected()) {

			listarCampos();

		}

	}

	private void limparCampos() {

		txtPesquisaComanda.setText("");
		txtPesquisaProntuario.setText("");
		txtPesquisaProntuario.setText("");
		txtInfoComanda.setText("");
		txtInfoFormaPagto.setText("");
		txtInfoProntuario.setText("");
		txtInfoStatus.setText("");
		lblInfoTotal.setText("");

		carrinho = new CarrinhoModel(inicializarCarrinho());
		dgvInfoPedido.setModel(carrinho);

		btnConfirmar.setEnabled(false);
		btnRecusar.setEnabled(false);

	}

	private void listarCampos() {

		String pesquisa = "";

		if (!txtPesquisaComanda.getText().equals("")) {

			pesquisa = txtPesquisaComanda.getText();

		} else if (!txtPesquisaProntuario.getText().equals("")) {

			pesquisa = txtPesquisaProntuario.getText();

		}

		if (!pesquisa.equals("")) {

			Pedidos pedidos = new Pedidos();

			pedidos.getComanda().setCdComanda(pesquisa);
			pedidos.getComanda().getLogin().setUsuario(pesquisa);
			pedidos.getComanda().getServico().setServico(DESC_SERVICO);

			if (rbPesquisaProntuario.isSelected()) {

				int qtd = Integer.parseInt(busPedidos.buscarQtdPedidos(pedidos)[0]);

				if (qtd > 1) {

					JDialog janela = new LytPopUpPedidos(null, true, pedidos);
					janela.setVisible(true);

				}

			}

			String dados[] = busPedidos.buscarPedidos(pedidos);

			if (dados[0] != null) {

				txtInfoComanda.setText(dados[0]);
				txtInfoProntuario.setText(dados[1]);
				txtInfoFormaPagto.setText(dados[2]);
				txtInfoStatus.setText(dados[3]);
				lblInfoTotal.setText("R$" + new DecimalFormat("0.00").format(Double.parseDouble(dados[4])));

				pedidos.getComanda().setCdComanda(dados[0]);

				carrinho = new CarrinhoModel(listarProdutos(pedidos));

				dgvInfoPedido.setModel(carrinho);

				btnConfirmar.setEnabled(true);
				btnRecusar.setEnabled(true);

			} else {

				GSFIEOUtil.mensagem(LytConfirmarPedido.this,
						"Sem registros encontrados para esta comanda ou prontuário!", GSFIEOConstants.MSG_AVISO);

				limparCampos();

			}
		}

	}

	private List<Produto> listarProdutos(Pedidos pedidos) {

		List<Produto> lstDados = new ArrayList<>();
		String dados[][] = busProdutos.buscarProdutos(pedidos);

		for (String valor[] : dados) {

			Produto cln = new Produto();

			cln.setProduto(valor[0]);
			cln.setValorUnit(Double.parseDouble(valor[1]));
			cln.setQtd(Integer.parseInt(valor[2]));
			cln.setSubTotal(Double.parseDouble(valor[3]));

			lstDados.add(cln);

		}

		return lstDados;

	}

}
