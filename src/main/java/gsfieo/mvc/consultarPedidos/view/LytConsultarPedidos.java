package gsfieo.mvc.consultarPedidos.view;

import gsfieo.mvc.comanda.controller.BusComanda;
import gsfieo.mvc.comanda.model.Comanda;
import gsfieo.mvc.confirmarPedidos.model.Pedidos;
import gsfieo.mvc.login.model.Login;
import gsfieo.utils.informacoes.ConstantesImagens;
import gsfieo.utils.tableModel.ComandaUsuarioModel;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @Author Wellington
 */

public class LytConsultarPedidos extends JDialog implements ActionListener {

	private static final long serialVersionUID = -9005663401627285164L;

	private final JPanel pnTitulo, pnPesquisa, pnComandas, pnBotoes;
	private final JLabel lblTitulo, lblPesquisa;
	private final JTextField txtPesquisa;
	private final JTable dgvComandas;
	private final JButton btnDetalhar, btnSair;

	private final ComandaUsuarioModel mComandaUsuario;
	private final Comanda COMANDA = new Comanda();
	private final BusComanda busComanda = new BusComanda();
	private final ConstantesImagens c = new ConstantesImagens();

	public LytConsultarPedidos(Frame f, boolean bol, Login login) {

		super(f, bol);

		COMANDA.setLogin(login);

		setTitle("CONSULTAR PEDIDOS");
		setSize(636, 514);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(null);
		setResizable(false);

		// TITULO
		pnTitulo = new JPanel();
		pnTitulo.setSize(610, 30);
		pnTitulo.setLocation(5, 5);
		add(pnTitulo);

		lblTitulo = new JLabel("MEUS PEDIDOS");
		lblTitulo.setSize(200, 30);
		lblTitulo.setLocation(5, 5);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));
		pnTitulo.add(lblTitulo);

		// PESQUISA
		pnPesquisa = new JPanel();
		pnPesquisa.setSize(600, 25);
		pnPesquisa.setLocation(10, 55);
		pnPesquisa.setLayout(null);
		add(pnPesquisa);

		lblPesquisa = new JLabel("Pesquisa:");
		lblPesquisa.setSize(60, 20);
		lblPesquisa.setLocation(0, 0);
		pnPesquisa.add(lblPesquisa);

		txtPesquisa = new JTextField();
		txtPesquisa.setSize(540, 25);
		txtPesquisa.setLocation(60, 0);
		pnPesquisa.add(txtPesquisa);

		// Comandas
		pnComandas = new JPanel(new GridLayout());
		pnComandas.setSize(600, 300);
		pnComandas.setLocation(10, 85);
		pnComandas.setBorder(BorderFactory.createTitledBorder(""));
		add(pnComandas);

		mComandaUsuario = new ComandaUsuarioModel(listarComandas());

		dgvComandas = new JTable(mComandaUsuario);
		dgvComandas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvComandas.getTableHeader().setReorderingAllowed(false);

		pnComandas.add(new JScrollPane(dgvComandas));

		// BOTÕES
		pnBotoes = new JPanel();
		pnBotoes.setSize(440, 80);
		pnBotoes.setLocation(90, 390);
		pnBotoes.setLayout(null);
		add(pnBotoes);

		ImageIcon icDetalhar = new ImageIcon(this.getClass().getResource(c.getDetalhar64x64()));
		btnDetalhar = new JButton(icDetalhar);
		btnDetalhar.setSize(200, 80);
		btnDetalhar.setLocation(0, 0);
		btnDetalhar.addActionListener(LytConsultarPedidos.this);
		pnBotoes.add(btnDetalhar);

		ImageIcon icSair = new ImageIcon(this.getClass().getResource(c.getSair64x64()));
		btnSair = new JButton(icSair);
		btnSair.setSize(200, 80);
		btnSair.setLocation(240, 0);
		btnSair.addActionListener(LytConsultarPedidos.this);
		pnBotoes.add(btnSair);

	}

	public static void main(String[] args) {

		Login login = new Login();
		login.setUsuario("TESTE");

		JDialog janela = new LytConsultarPedidos(null, true, login);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnDetalhar) {

			Pedidos pedidos = new Pedidos();

			pedidos.getComanda().setCdComanda((String) mComandaUsuario.getValueAt(dgvComandas.getSelectedRow(), 0));

			JDialog janela = new LytDetalharPedido(null, true, pedidos);
			janela.setVisible(true);

		} else if (e.getSource() == btnSair) {

			this.dispose();

		}

	}

	private List<Comanda> listarComandas() {

		List<Comanda> lstComandas = new ArrayList<>();

		COMANDA.setCdComanda(txtPesquisa.getText());
		COMANDA.setStatus(txtPesquisa.getText());
		COMANDA.setTotalCompra(Double.parseDouble(txtPesquisa.getText().equals("") ? "0.0" : txtPesquisa.getText()));
		COMANDA.getServico().setServico(txtPesquisa.getText());
		COMANDA.getFormaPgto().setFormaPagto(txtPesquisa.getText());

		String dados[][] = busComanda.listarComandasUsuario(COMANDA);

		for (String valor[] : dados) {

			Comanda comanda = new Comanda();

			comanda.setCdComanda(valor[0]);
			comanda.setStatus(valor[1]);
			comanda.setTotalCompra(Double.parseDouble(valor[2]));
			comanda.getServico().setServico(valor[3]);
			comanda.getFormaPgto().setFormaPagto(valor[4]);

			lstComandas.add(comanda);

		}

		return lstComandas;

	}

}
