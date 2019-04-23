package gsfieo.mvc.comprar.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.cardapio.controller.BusCardapio;
import gsfieo.mvc.cardapio.model.Cardapio;
import gsfieo.mvc.comanda.controller.BusComanda;
import gsfieo.mvc.confirmarPedidos.controller.BusPedidos;
import gsfieo.mvc.confirmarPedidos.model.Pedidos;
import gsfieo.mvc.produtos.model.Produto;
import gsfieo.mvc.servico.controller.BusServico;
import gsfieo.mvc.servico.model.Servicos;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.informacoes.ConstantesImagens;
import gsfieo.utils.tableModel.CardapioModel_Pedidos;
import gsfieo.utils.tableModel.CarrinhoModel;
import gsfieo.utils.tableModel.ComprarModel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LytComprarProduto extends JDialog implements ActionListener, ChangeListener, ListSelectionListener,
		MouseListener, ItemListener, DocumentListener {

	private static final long serialVersionUID = 32964409828754577L;

	private final Border borda = BorderFactory.createLineBorder(Color.BLACK, 1);
	private final ConstantesImagens c = new ConstantesImagens();
	private final Pedidos ficha = new Pedidos();

	private final BusComanda busComanda = new BusComanda();
	private final BusPedidos busPedidos = new BusPedidos();
	private final BusServico busServico = new BusServico();
	private final BusCardapio busCardapio = new BusCardapio();

	// ------------------ SERVICOS
	private final ComprarModel cModel;

	private final JPanel pnServico, pnGridServico;
	private final JLabel lblTituloComprar;
	private final JTable dgvServicos;
	private final JButton btnProximoServico;

	// ------------------ CARDÁPIO
	private CardapioModel_Pedidos cpModel;
	private final CarrinhoModel carrinhoModel;

	private final JPanel pnCardapio, pnGridCardapio, pnInfoProdu, pnCarrinho, pnBotoesCardapio, pnTotal;
	private final JLabel lblTituloCardapio, lblNome, lblPreco, lblQtd, lblSubTotal, lblPedido, lblTotal, lblTotalDesc;
	private final JTextField txtPesquisarCardapio, txtNome, txtPreco, txtSubTotal;
	private final JComboBox<String> cbFiltro;
	private final JTable dgvCardapio, dgvCarrinho;
	private final JSpinner spQtd;
	private final JButton btnConfirmar, btnAnteriorCardapio, btnProximoCardapio;

	private double preco_unitario, subTotal, total;
	private String produto;
	private int qtd, qtdTotal;

	// ------------------ PAGAMENTO
	private final JPanel pnPagto, pnFormaPagamento, pnBotoesPagto, pnTotalPagto;
	private final JLabel lblTituloPagto, lblTotalPagto;
	private final JRadioButton rbDinheiro, rbDebito, rbCredito;
	private final JButton btnAnteriorPagto, btnProximoPagto;

	// ------------------ FINALIZAR

	private final JPanel pnFinaliza, pnServicoFinalizar, pnPedidosFinalizar, pnFormaPagtoFinalizar, pnTotalFinalizar,
			pnBotoesFinalizar;
	private final JLabel lblTituloFinalizar, lblFormaPagamentoFinalizar, lblTotalFinalizarDesc;
	private final JTextField txtServicoFinalizar;
	private final JTable dgvPedidosFinalizar;
	private final JButton btnAnteriorFinalizar, btnProximoFinalizar;

	// ------------------ CODIGO
	private final JPanel pnCodigo, pnCodigoGerado;
	private final JLabel lblTituloCodigo, lblDescCodigo;
	private final JTextField txtCodigo;
	private final JButton btnSairCodigo;

	private final JTabbedPane jtpComprar;

	public LytComprarProduto(Frame f, boolean bol, Servicos servicos) {

		super(f, bol);

		setTitle("COMPRAR PRODUTOS");
		setSize(1366, 700);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// =====================================================================

		ficha.getComanda().getLogin().setUsuario(servicos.getLogin().getUsuario());
		ficha.getComanda().getServico().setServico(servicos.getServico());

		// ========================== COMPRAR PRODUTOS =========================

		jtpComprar = new JTabbedPane();
		jtpComprar.setBorder(borda);

		// ========================== SERVIÇO ==================================

		pnServico = new JPanel();
		pnServico.setLayout(null);

		// ---- LABEL 'TITULO'

		lblTituloComprar = new JLabel("ESCOLHA O SERVIÇO");
		lblTituloComprar.setSize(320, 40);
		lblTituloComprar.setLocation(450, 20);
		lblTituloComprar.setFont(lblTituloComprar.getFont().deriveFont(30f));

		pnServico.add(lblTituloComprar);

		// ---- GRID 'SERVIÇO'

		pnGridServico = new JPanel(new FlowLayout());
		pnGridServico.setSize(500, 450);
		pnGridServico.setLocation(360, 70);

		cModel = new ComprarModel(listarServico());

		dgvServicos = new JTable(cModel);
		dgvServicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvServicos.getTableHeader().setReorderingAllowed(false);

		pnGridServico.add(new JScrollPane(dgvServicos));

		pnServico.add(pnGridServico);

		// ---- BOTÃO 'PROXIMO'

		ImageIcon icProximoServico = new ImageIcon(this.getClass().getResource(c.getProximo()));
		btnProximoServico = new JButton(icProximoServico);
		btnProximoServico.setSize(300, 80);
		btnProximoServico.setLocation(460, 520);
		btnProximoServico.addActionListener(LytComprarProduto.this);

		pnServico.add(btnProximoServico);

		jtpComprar.addTab("SERVICO", pnServico);

		// ========================== PEDIDO =================================

		pnCardapio = new JPanel();
		pnCardapio.setLayout(null);

		// ---- TITULO 'PEDIDOS'

		lblTituloCardapio = new JLabel("FAÇA SEU PEDIDO");
		lblTituloCardapio.setSize(280, 35);
		lblTituloCardapio.setLocation(530, 20);
		lblTituloCardapio.setFont(lblTituloComprar.getFont().deriveFont(30f));

		pnCardapio.add(lblTituloCardapio);

		// ---- CAMPO 'PESQUISAR CARDÁPIO'

		txtPesquisarCardapio = new JTextField();
		txtPesquisarCardapio.setSize(310, 25);
		txtPesquisarCardapio.setLocation(25, 70);
		txtPesquisarCardapio.getDocument().addDocumentListener(LytComprarProduto.this);

		pnCardapio.add(txtPesquisarCardapio);

		String valores[] = new String[2];
		valores[0] = "MENOR PREÇO";
		valores[1] = "MAIOR PREÇO";

		cbFiltro = new JComboBox<String>(valores);
		cbFiltro.setSize(130, 25);
		cbFiltro.setLocation(345, 70);
		cbFiltro.addItemListener(LytComprarProduto.this);

		pnCardapio.add(cbFiltro);

		// ---- GRID 'CARDÁPIO'
		pnGridCardapio = new JPanel(new FlowLayout());
		pnGridCardapio.setSize(480, 430);
		pnGridCardapio.setLocation(10, 95);

		dgvCardapio = new JTable();
		dgvCardapio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvCardapio.getTableHeader().setReorderingAllowed(false);
		dgvCardapio.getSelectionModel().addListSelectionListener(LytComprarProduto.this);

		pnGridCardapio.add(new JScrollPane(dgvCardapio));

		pnCardapio.add(pnGridCardapio);

		// ---- CONTÊINER 'INFORMAÇÕES'

		pnInfoProdu = new JPanel();
		pnInfoProdu.setSize(300, 400);
		pnInfoProdu.setLocation(510, 120);
		pnInfoProdu.setLayout(null);
		pnInfoProdu.setBorder(BorderFactory.createTitledBorder("Informações"));

		// ---- CAMPO 'NOME'
		lblNome = new JLabel("Produto:");
		lblNome.setSize(100, 20);
		lblNome.setLocation(10, 30);

		pnInfoProdu.add(lblNome);

		txtNome = new JTextField();
		txtNome.setSize(250, 25);
		txtNome.setLocation(20, 50);
		txtNome.setEditable(false);

		pnInfoProdu.add(txtNome);

		// ---- CAMPO 'PREÇO'
		lblPreco = new JLabel("Preço unitário:");
		lblPreco.setSize(120, 25);
		lblPreco.setLocation(10, 95);

		pnInfoProdu.add(lblPreco);

		txtPreco = new JTextField();
		txtPreco.setSize(140, 25);
		txtPreco.setLocation(20, 120);
		txtPreco.setEditable(false);

		pnInfoProdu.add(txtPreco);

		// ---- CAMPO 'PREÇO'
		lblQtd = new JLabel("Quantidade:");
		lblQtd.setSize(150, 20);
		lblQtd.setLocation(10, 165);

		pnInfoProdu.add(lblQtd);

		SpinnerNumberModel snm = new SpinnerNumberModel(1, 1, 100, 1);

		spQtd = new JSpinner(snm);
		spQtd.setSize(130, 25);
		spQtd.setLocation(20, 185);
		spQtd.addChangeListener(LytComprarProduto.this);

		pnInfoProdu.add(spQtd);

		// ---- CAMPO 'SUBTOTAL'
		lblSubTotal = new JLabel("SUBTOTAL:");
		lblSubTotal.setSize(110, 25);
		lblSubTotal.setLocation(10, 230);
		lblSubTotal.setFont(lblSubTotal.getFont().deriveFont(15f));

		pnInfoProdu.add(lblSubTotal);

		txtSubTotal = new JTextField();
		txtSubTotal.setSize(120, 25);
		txtSubTotal.setLocation(20, 255);
		txtSubTotal.setFont(txtSubTotal.getFont().deriveFont(15f));
		txtSubTotal.setEditable(false);

		pnInfoProdu.add(txtSubTotal);

		// ---- BOTÃO 'CONFIRMAR'
		btnConfirmar = new JButton(new ImageIcon(this.getClass().getResource(c.getConfirmar())));
		btnConfirmar.setSize(260, 70);
		btnConfirmar.setLocation(20, 300);
		btnConfirmar.addActionListener(LytComprarProduto.this);

		pnInfoProdu.add(btnConfirmar);

		pnCardapio.add(pnInfoProdu);

		// ---- GRID 'PEDIDO'
		lblPedido = new JLabel("PEDIDO");
		lblPedido.setSize(100, 25);
		lblPedido.setLocation(1010, 60);
		lblPedido.setFont(lblPedido.getFont().deriveFont(25f));

		pnCardapio.add(lblPedido);

		pnCarrinho = new JPanel(new FlowLayout());
		pnCarrinho.setSize(480, 430);
		pnCarrinho.setLocation(830, 95);

		carrinhoModel = new CarrinhoModel(inicializarCarrinho());

		dgvCarrinho = new JTable(carrinhoModel);
		dgvCarrinho.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvCarrinho.getTableHeader().setReorderingAllowed(false);
		dgvCarrinho.addMouseListener(LytComprarProduto.this);

		pnCarrinho.add(new JScrollPane(dgvCarrinho));

		pnCardapio.add(pnCarrinho);

		// ---- CAMPO 'TOTAL'
		TitledBorder borda2 = BorderFactory.createTitledBorder("");
		pnTotal = new JPanel();
		pnTotal.setSize(280, 90);
		pnTotal.setLocation(1015, 525);
		pnTotal.setLayout(null);
		pnTotal.setBorder(borda2);

		lblTotal = new JLabel("TOTAL:");
		lblTotal.setSize(150, 25);
		lblTotal.setLocation(10, 10);

		pnTotal.add(lblTotal);

		lblTotalDesc = new JLabel();
		lblTotalDesc.setSize(210, 40);
		lblTotalDesc.setLocation(60, 30);
		lblTotalDesc.setFont(lblTotalDesc.getFont().deriveFont(30f));
		lblTotalDesc.setBorder(null);

		pnTotal.add(lblTotalDesc);

		pnCardapio.add(pnTotal);

		// ---- BOTÕES
		pnBotoesCardapio = new JPanel();
		pnBotoesCardapio.setSize(650, 90);
		pnBotoesCardapio.setLocation(310, 530);
		pnBotoesCardapio.setLayout(null);

		pnCardapio.add(pnBotoesCardapio);

		ImageIcon icAnteriorCardapio = new ImageIcon(this.getClass().getResource(c.getAnterior()));
		btnAnteriorCardapio = new JButton(icAnteriorCardapio);
		btnAnteriorCardapio.setSize(300, 70);
		btnAnteriorCardapio.setLocation(10, 10);
		btnAnteriorCardapio.addActionListener(LytComprarProduto.this);

		pnBotoesCardapio.add(btnAnteriorCardapio);

		ImageIcon icProximoCardapio = new ImageIcon(this.getClass().getResource(c.getProximo()));
		btnProximoCardapio = new JButton(icProximoCardapio);
		btnProximoCardapio.setSize(300, 70);
		btnProximoCardapio.setLocation(340, 10);
		btnProximoCardapio.addActionListener(LytComprarProduto.this);

		pnBotoesCardapio.add(btnProximoCardapio);

		jtpComprar.addTab("CARDÁPIO", pnCardapio);

		// ========================== PAGAMENTO ================================

		pnPagto = new JPanel();
		pnPagto.setLayout(null);

		// ----- TITULO 'PAGAMENTO'
		lblTituloPagto = new JLabel("PAGAMENTO");
		lblTituloPagto.setSize(250, 30);
		lblTituloPagto.setLocation(520, 10);
		lblTituloPagto.setFont(lblTituloPagto.getFont().deriveFont(30f));

		pnPagto.add(lblTituloPagto);

		// ----- 'FORMA DE PAGAMENTO'
		TitledBorder tituloBorda = BorderFactory.createTitledBorder("Forma de pagamento");
		pnFormaPagamento = new JPanel();
		pnFormaPagamento.setSize(500, 200);
		pnFormaPagamento.setLocation(380, 50);
		pnFormaPagamento.setLayout(null);
		pnFormaPagamento.setBorder(tituloBorda);

		pnPagto.add(pnFormaPagamento);

		// ----- OPÇÕES 'FORMA DE PAGAMENTO'
		// ----- DINHEIRO
		rbDinheiro = new JRadioButton("Dinheiro");
		rbDinheiro.setSize(150, 30);
		rbDinheiro.setLocation(10, 30);
		rbDinheiro.setFont(rbDinheiro.getFont().deriveFont(20f));

		pnFormaPagamento.add(rbDinheiro);

		// ----- CARTÃO DE DÉBITO
		rbDebito = new JRadioButton("Cartão de débito");
		rbDebito.setSize(190, 30);
		rbDebito.setLocation(10, 80);
		rbDebito.setFont(rbDebito.getFont().deriveFont(20f));

		pnFormaPagamento.add(rbDebito);

		// ----- CARTÃO DE CRÉDITO
		rbCredito = new JRadioButton("Cartão de crédito");
		rbCredito.setSize(190, 30);
		rbCredito.setLocation(10, 130);
		rbCredito.setFont(rbCredito.getFont().deriveFont(20f));

		pnFormaPagamento.add(rbCredito);

		ButtonGroup bgGrupo = new ButtonGroup();
		bgGrupo.add(rbDinheiro);
		bgGrupo.add(rbDebito);
		bgGrupo.add(rbCredito);

		// ---- 'TOTAL DE PAGAMENTO'
		pnTotalPagto = new JPanel();
		pnTotalPagto.setSize(501, 151);
		pnTotalPagto.setLocation(380, 260);
		pnTotalPagto.setLayout(null);
		pnTotalPagto.setBorder(BorderFactory.createTitledBorder("TOTAL:"));

		pnPagto.add(pnTotalPagto);

		lblTotalPagto = new JLabel();
		lblTotalPagto.setSize(200, 40);
		lblTotalPagto.setLocation(150, 60);
		lblTotalPagto.setFont(lblTotalPagto.getFont().deriveFont(40f));

		pnTotalPagto.add(lblTotalPagto);

		// ---- BOTÕES
		pnBotoesPagto = new JPanel();
		pnBotoesPagto.setSize(650, 90);
		pnBotoesPagto.setLocation(310, 530);
		pnBotoesPagto.setLayout(null);

		pnPagto.add(pnBotoesPagto);

		ImageIcon icAnteriorPagto = new ImageIcon(this.getClass().getResource(c.getAnterior()));
		btnAnteriorPagto = new JButton(icAnteriorPagto);
		btnAnteriorPagto.setSize(300, 70);
		btnAnteriorPagto.setLocation(10, 10);
		btnAnteriorPagto.addActionListener(LytComprarProduto.this);

		pnBotoesPagto.add(btnAnteriorPagto);

		ImageIcon icProximoPagto = new ImageIcon(this.getClass().getResource(c.getProximo()));
		btnProximoPagto = new JButton(icProximoPagto);
		btnProximoPagto.setSize(300, 70);
		btnProximoPagto.setLocation(340, 10);
		btnProximoPagto.addActionListener(LytComprarProduto.this);

		pnBotoesPagto.add(btnProximoPagto);

		jtpComprar.addTab("PAGAMENTO", pnPagto);

		// ======================== FINALIZAÇÃO ================================

		pnFinaliza = new JPanel();
		pnFinaliza.setLayout(null);

		lblTituloFinalizar = new JLabel("INFORMAÇÕES");
		lblTituloFinalizar.setSize(250, 40);
		lblTituloFinalizar.setLocation(540, 10);
		lblTituloFinalizar.setFont(lblTituloFinalizar.getFont().deriveFont(30f));

		pnFinaliza.add(lblTituloFinalizar);

		// ----- CAMPO 'SERVIÇO'
		pnServicoFinalizar = new JPanel();
		pnServicoFinalizar.setSize(350, 80);
		pnServicoFinalizar.setLocation(30, 70);
		pnServicoFinalizar.setLayout(null);
		pnServicoFinalizar.setBorder(BorderFactory.createTitledBorder("Serviço:"));

		pnFinaliza.add(pnServicoFinalizar);

		txtServicoFinalizar = new JTextField();
		txtServicoFinalizar.setSize(300, 35);
		txtServicoFinalizar.setLocation(20, 25);
		txtServicoFinalizar.setEditable(false);

		pnServicoFinalizar.add(txtServicoFinalizar);

		// ----- GRID 'PEDIDOS'
		pnPedidosFinalizar = new JPanel(new GridLayout());
		pnPedidosFinalizar.setSize(480, 460);
		pnPedidosFinalizar.setLocation(430, 70);
		pnPedidosFinalizar.setBorder(BorderFactory.createTitledBorder("Pedidos"));

		dgvPedidosFinalizar = new JTable(carrinhoModel);
		dgvPedidosFinalizar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvPedidosFinalizar.getTableHeader().setReorderingAllowed(false);

		pnPedidosFinalizar.add(new JScrollPane(dgvPedidosFinalizar));

		pnFinaliza.add(pnPedidosFinalizar);

		// ------- CAMPO 'FORMA DE PAGAMENTO'
		pnFormaPagtoFinalizar = new JPanel();
		pnFormaPagtoFinalizar.setSize(380, 100);
		pnFormaPagtoFinalizar.setLocation(940, 70);
		pnFormaPagtoFinalizar.setBorder(BorderFactory.createTitledBorder("Forma de pagamento"));
		pnFormaPagtoFinalizar.setLayout(null);

		pnFinaliza.add(pnFormaPagtoFinalizar);

		lblFormaPagamentoFinalizar = new JLabel("Cartão de Crédito");
		lblFormaPagamentoFinalizar.setSize(280, 35);
		lblFormaPagamentoFinalizar.setLocation(20, 30);
		lblFormaPagamentoFinalizar.setFont(lblFormaPagamentoFinalizar.getFont().deriveFont(30f));

		pnFormaPagtoFinalizar.add(lblFormaPagamentoFinalizar);

		// ------- CAMPO 'TOTAL'
		pnTotalFinalizar = new JPanel();
		pnTotalFinalizar.setSize(380, 100);
		pnTotalFinalizar.setLocation(940, 200);
		pnTotalFinalizar.setLayout(null);
		pnTotalFinalizar.setBorder(BorderFactory.createTitledBorder("TOTAL"));

		pnFinaliza.add(pnTotalFinalizar);

		lblTotalFinalizarDesc = new JLabel("R$1000.00");
		lblTotalFinalizarDesc.setSize(300, 35);
		lblTotalFinalizarDesc.setLocation(20, 30);
		lblTotalFinalizarDesc.setFont(lblTotalFinalizarDesc.getFont().deriveFont(30f));

		pnTotalFinalizar.add(lblTotalFinalizarDesc);

		// ---- BOTÕES
		pnBotoesFinalizar = new JPanel();
		pnBotoesFinalizar.setSize(650, 90);
		pnBotoesFinalizar.setLocation(310, 530);
		pnBotoesFinalizar.setLayout(null);

		pnFinaliza.add(pnBotoesFinalizar);

		ImageIcon icAnteriorFinalizar = new ImageIcon(this.getClass().getResource(c.getAnterior()));
		btnAnteriorFinalizar = new JButton(icAnteriorFinalizar);
		btnAnteriorFinalizar.setSize(300, 70);
		btnAnteriorFinalizar.setLocation(10, 10);
		btnAnteriorFinalizar.addActionListener(LytComprarProduto.this);

		pnBotoesFinalizar.add(btnAnteriorFinalizar);

		ImageIcon icProximoFinalizar = new ImageIcon(this.getClass().getResource(c.getProximo()));
		btnProximoFinalizar = new JButton(icProximoFinalizar);
		btnProximoFinalizar.setSize(300, 70);
		btnProximoFinalizar.setLocation(340, 10);
		btnProximoFinalizar.addActionListener(LytComprarProduto.this);

		pnBotoesFinalizar.add(btnProximoFinalizar);

		jtpComprar.addTab("FINALIZAR", pnFinaliza);

		// =========================== CÓDIGO ==================================

		pnCodigo = new JPanel();
		pnCodigo.setLayout(null);

		lblTituloCodigo = new JLabel("CÓDIGO");
		lblTituloCodigo.setSize(250, 40);
		lblTituloCodigo.setLocation(550, 130);
		lblTituloCodigo.setFont(lblTituloCodigo.getFont().deriveFont(40f));

		pnCodigo.add(lblTituloCodigo);

		pnCodigoGerado = new JPanel();
		pnCodigoGerado.setSize(650, 200);
		pnCodigoGerado.setLocation(320, 180);
		pnCodigoGerado.setBorder(borda);
		pnCodigoGerado.setLayout(null);

		pnCodigo.add(pnCodigoGerado);

		txtCodigo = new JTextField("0582450124");
		txtCodigo.setSize(500, 80);
		txtCodigo.setLocation(80, 70);
		txtCodigo.setFont(txtCodigo.getFont().deriveFont(60f));
		txtCodigo.setEditable(false);
		txtCodigo.setHorizontalAlignment(JTextField.CENTER);

		pnCodigoGerado.add(txtCodigo);

		lblDescCodigo = new JLabel("ACIMA ESTÁ A SUA COMANDA, APRESENTE-A PARA O PRESTADOR DO SERVIÇO");
		lblDescCodigo.setSize(1000, 30);
		lblDescCodigo.setLocation(260, 420);
		lblDescCodigo.setFont(lblDescCodigo.getFont().deriveFont(20f));

		pnCodigo.add(lblDescCodigo);

		ImageIcon icSairCodigo = new ImageIcon(this.getClass().getResource(c.getSair64x64()));
		btnSairCodigo = new JButton(icSairCodigo);
		btnSairCodigo.setSize(400, 100);
		btnSairCodigo.setLocation(450, 500);
		btnSairCodigo.addActionListener(LytComprarProduto.this);

		pnCodigo.add(btnSairCodigo);

		jtpComprar.addTab("CÓDIGO", pnCodigo);

		// =====================================================================

		add(jtpComprar);

		jtpComprar.setEnabledAt(0, true);
		jtpComprar.setEnabledAt(1, false);
		jtpComprar.setEnabledAt(2, false);
		jtpComprar.setEnabledAt(3, false);
		jtpComprar.setEnabledAt(4, false);

	}

	public static void main(String[] args) {

		JDialog janela = new LytComprarProduto(null, true, null);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnProximoServico) {

			// PÁGINA DE ESCOLHA DE SERVIÇO

			int index = dgvServicos.getSelectedRow();

			if (index >= 0) {

				proximaPagina();

				jtpComprar.setEnabledAt(0, false);
				jtpComprar.setEnabledAt(1, true);
				jtpComprar.setEnabledAt(2, false);
				jtpComprar.setEnabledAt(3, false);
				jtpComprar.setEnabledAt(4, false);

				cpModel = new CardapioModel_Pedidos(
						listarCardapio(String.valueOf(cModel.getValueAt(dgvServicos.getSelectedRow(), 0))));
				dgvCardapio.setModel(cpModel);

				ficha.getComanda().getServico()
						.setServico(String.valueOf(cModel.getValueAt(dgvServicos.getSelectedRow(), 0)));

			} else {

				GSFIEOUtil.mensagem(this, "Escolha um serviço", GSFIEOConstants.MSG_AVISO);

			}

		} else if (e.getSource() == btnProximoCardapio) {// LINHA DO CAPIROTO

			// PÁGINA DE CRIAÇÃO DE PEDIDOS

			if (carrinhoModel.getRowCount() > 0) {

				ficha.setProdutos(carrinhoModel.getList());
				ficha.getComanda().setTotalCompra(total);

				proximaPagina();

				jtpComprar.setEnabledAt(0, false);
				jtpComprar.setEnabledAt(1, false);
				jtpComprar.setEnabledAt(2, true);
				jtpComprar.setEnabledAt(3, false);
				jtpComprar.setEnabledAt(4, false);

				lblTotalPagto.setText("R$" + formatarPreco(ficha.getComanda().getTotalCompra()));

				int MAX = carrinhoModel.getRowCount();

				for (int i = 0; i < MAX; i++) {

					qtdTotal += Integer.parseInt(String.valueOf(carrinhoModel.getValueAt(i, 2)));

				}

			} else {

				GSFIEOUtil.mensagem(this, "Para prosseguir você deve fazer o seu pedido", GSFIEOConstants.MSG_AVISO);

			}

		} else if (e.getSource() == btnProximoPagto) {

			// PÁGINA DE FORMA DE PAGAMENTO

			if (rbDebito.isSelected() || rbCredito.isSelected() || rbDinheiro.isSelected()) {

				if (rbDebito.isSelected()) {

					ficha.getComanda().getFormaPgto().setFormaPagto(rbDebito.getText());
					ficha.getComanda().setStatus("2");

				} else if (rbCredito.isSelected()) {

					ficha.getComanda().getFormaPgto().setFormaPagto(rbCredito.getText());
					ficha.getComanda().setStatus("2");

				} else if (rbDinheiro.isSelected()) {

					ficha.getComanda().getFormaPgto().setFormaPagto(rbDinheiro.getText());
					ficha.getComanda().setStatus("2");

				}

				proximaPagina();

				jtpComprar.setEnabledAt(0, false);
				jtpComprar.setEnabledAt(1, false);
				jtpComprar.setEnabledAt(2, false);
				jtpComprar.setEnabledAt(3, true);
				jtpComprar.setEnabledAt(4, false);

				txtServicoFinalizar.setText(ficha.getComanda().getServico().getServico());
				lblFormaPagamentoFinalizar.setText(ficha.getComanda().getFormaPgto().getFormaPagto());
				lblTotalFinalizarDesc.setText("R$" + formatarPreco(ficha.getComanda().getTotalCompra()));

			} else {

				GSFIEOUtil.mensagem(this, "Escolha uma forma de pagamento", GSFIEOConstants.MSG_AVISO);

			}

		} else if (e.getSource() == btnProximoFinalizar) {
			// PÁGINA DE INFORMAÇÕES DOS PEDIDOS

			proximaPagina();

			jtpComprar.setEnabledAt(0, false);
			jtpComprar.setEnabledAt(1, false);
			jtpComprar.setEnabledAt(2, false);
			jtpComprar.setEnabledAt(3, false);
			jtpComprar.setEnabledAt(4, true);

			String valores[];

			do {

				ficha.getComanda().setCdComanda(busComanda.popularVetores());

				valores = busComanda.verificarCodigo(ficha.getComanda());

			} while (valores[0] != null);

			// GRAVAR A COMANDA
			ficha.getComanda().setQtdTotal(qtdTotal);

			busComanda.gravar(ficha.getComanda());

			// GRAVAR OS PRODUTOS DA COMANDA
			int MAX = ficha.getRowsCount();

			for (int i = 0; i < MAX; i++) {

				busPedidos.gravar(i, ficha);

			}

			txtCodigo.setText(ficha.getComanda().getCdComanda());

		} else if (e.getSource() == btnAnteriorCardapio) {

			paginaAnterior();

			jtpComprar.setEnabledAt(0, true);
			jtpComprar.setEnabledAt(1, false);
			jtpComprar.setEnabledAt(2, false);
			jtpComprar.setEnabledAt(3, false);
			jtpComprar.setEnabledAt(4, false);

		} else if (e.getSource() == btnAnteriorPagto) {

			paginaAnterior();

			jtpComprar.setEnabledAt(0, false);
			jtpComprar.setEnabledAt(1, true);
			jtpComprar.setEnabledAt(2, false);
			jtpComprar.setEnabledAt(3, false);
			jtpComprar.setEnabledAt(4, false);

		} else if (e.getSource() == btnAnteriorFinalizar) {

			paginaAnterior();

			jtpComprar.setEnabledAt(0, false);
			jtpComprar.setEnabledAt(1, false);
			jtpComprar.setEnabledAt(2, true);
			jtpComprar.setEnabledAt(3, false);
			jtpComprar.setEnabledAt(4, false);

		} else if (e.getSource() == btnSairCodigo) {

			this.dispose();

			jtpComprar.setEnabledAt(0, false);
			jtpComprar.setEnabledAt(1, false);
			jtpComprar.setEnabledAt(2, false);
			jtpComprar.setEnabledAt(3, true);
			jtpComprar.setEnabledAt(4, false);

		} else if (e.getSource() == btnConfirmar) {

			if (txtNome.getText().equals("") || txtPreco.getText().equals("") || txtSubTotal.getText().equals("")) {

				GSFIEOUtil.mensagem(this, "Escolha o produto antes de confirmar", GSFIEOConstants.MSG_AVISO);

			} else {

				Produto prod = new Produto();

				produto = txtNome.getText();
				qtd = Integer.parseInt(String.valueOf(spQtd.getValue()));

				prod.setProduto(produto);
				prod.setValorUnit(preco_unitario);
				prod.setQtd(qtd);
				prod.setSubTotal(subTotal);

				carrinhoModel.addRow(prod);

				total += prod.getSubTotal();

				lblTotalDesc.setText("R$" + String.valueOf(formatarPreco(total)));

				txtNome.setText("");
				txtPreco.setText("");
				spQtd.setValue(1);
				txtSubTotal.setText("");

			}

		}

	}

	private void proximaPagina() {

		jtpComprar.setSelectedIndex(jtpComprar.getSelectedIndex() + 1);

	}

	private void paginaAnterior() {

		jtpComprar.setSelectedIndex(jtpComprar.getSelectedIndex() - 1);

	}

	private List<Servicos> listarServico() {

		List<Servicos> lstDados = new ArrayList<>();
		String dados[][] = busServico.listarServicosCardapio();

		for (String valor[] : dados) {

			Servicos cln = new Servicos();

			cln.setServico(valor[1]);

			lstDados.add(cln);

		}

		return lstDados;

	}

	private List<Produto> listarCardapio(String servico) {

		List<Produto> lstProdutos = new ArrayList<>();
		Cardapio card = new Cardapio();

		String orderBy = "";

		if (cbFiltro.getSelectedIndex() == 0) {

			orderBy = "ASC";

		} else if (cbFiltro.getSelectedIndex() == 1) {

			orderBy = "DESC";

		}

		card.getServicos().setServico(servico);
		card.getProduto().setProduto(txtPesquisarCardapio.getText());

		String res[][] = busCardapio.buscarCardapioPedidos(orderBy, card);

		for (String valor[] : res) {

			Produto cln = new Produto();

			cln.setProduto(valor[0]);
			cln.setValorUnit(Double.parseDouble(valor[1]));

			lstProdutos.add(cln);

		}

		return lstProdutos;

	}

	private List<Produto> inicializarCarrinho() {

		List<Produto> lstDados = new ArrayList<>();

		Produto cln = new Produto();

		lstDados.add(cln);
		lstDados.remove(0);

		return lstDados;

	}

	private String formatarPreco(double preco) {

		String strPreco = String.valueOf(new DecimalFormat("0.00").format(preco));

		return strPreco;

	}

	@Override
	public void stateChanged(ChangeEvent e) {

		subTotal = preco_unitario * Integer.parseInt(String.valueOf(spQtd.getValue()));

		txtSubTotal.setText(String.valueOf("R$" + String.valueOf(formatarPreco(subTotal))));

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		txtNome.setText(String.valueOf(cpModel.getValueAt(dgvCardapio.getSelectedRow(), 0)));
		preco_unitario = Double.parseDouble(String.valueOf((cpModel.pegarValor(dgvCardapio.getSelectedRow(), 1))));

		txtPreco.setText("R$" + String.valueOf(formatarPreco(preco_unitario)));
		subTotal = preco_unitario * Integer.parseInt(String.valueOf(spQtd.getValue()));

		txtSubTotal.setText("R$" + String.valueOf(formatarPreco(subTotal)));

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (dgvCarrinho.getSelectedRow() >= 0) {

			if (e.getClickCount() == 2) {

				qtd -= Integer.parseInt(String.valueOf(carrinhoModel.getValueAt(dgvCarrinho.getSelectedRow(), 2)));
				total -= Double.parseDouble(String.valueOf(carrinhoModel.pegarValor(dgvCarrinho.getSelectedRow(), 3)));

				carrinhoModel.removeRow(carrinhoModel.getValue(dgvCarrinho.getSelectedRow()));

				lblTotalDesc.setText("R$" + String.valueOf(formatarPreco(total)));

			}

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		buscarCardapio();

	}

	private void buscarCardapio() {

		cpModel = new CardapioModel_Pedidos(
				listarCardapio(String.valueOf(cModel.getValueAt(dgvServicos.getSelectedRow(), 0))));
		dgvCardapio.setModel(cpModel);

	}

	@Override
	public void insertUpdate(DocumentEvent e) {

		buscarCardapio();

	}

	@Override
	public void removeUpdate(DocumentEvent e) {

		buscarCardapio();

	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

}