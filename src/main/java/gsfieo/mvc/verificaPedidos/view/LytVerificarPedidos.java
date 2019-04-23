package gsfieo.mvc.verificaPedidos.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.comanda.controller.BusComanda;
import gsfieo.mvc.comanda.model.Comanda;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.informacoes.ConstantesImagens;
import gsfieo.utils.tableModel.PedidoModel;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class LytVerificarPedidos extends JDialog implements ActionListener, ItemListener {

	private static final long serialVersionUID = -8949308877163488947L;

	private final ConstantesImagens c = new ConstantesImagens();
	private PedidoModel mPedido;

	private final BusComanda busComanda = new BusComanda();

	private final JPanel pnPedidos;
	private final JLabel lblTitulo, lblStatus;
	private final JComboBox<String> cbStatus;
	private final JTable dgvPedidos;
	private final JButton btnSair;

	private final String DESC_SERVICO;

	public LytVerificarPedidos(Frame f, boolean bol, String servico) {

		super(f, bol);

		DESC_SERVICO = servico;

		setTitle("Verificar pedidos");
		setSize(839, 588);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ===================================================================
		// ============ CAMPOS 'TÍTULO'
		lblTitulo = new JLabel("Verificar Pedidos");
		lblTitulo.setSize(300, 50);
		lblTitulo.setLocation(280, 20);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(30f));

		add(lblTitulo);

		// ============ CAMPO 'STATUS'
		lblStatus = new JLabel("Status:");
		lblStatus.setSize(200, 25);
		lblStatus.setLocation(40, 95);

		add(lblStatus);

		cbStatus = new JComboBox<>();
		cbStatus.setSize(200, 25);
		cbStatus.setLocation(90, 95);
		cbStatus.addItemListener(LytVerificarPedidos.this);
		cbStatus.addItem("TODOS");

		String dados[] = GSFIEOUtil.matrizUnidimensional(busComanda.buscarStatus());

		for (String valor : dados) {

			cbStatus.addItem(valor);

		}

		add(cbStatus);

		// ===================================================================
		// ==================== CONTÊINER 'PEDIDOS'

		pnPedidos = new JPanel(new GridLayout());
		pnPedidos.setSize(800, 300);
		pnPedidos.setLocation(10, 130);
		pnPedidos.setBorder(BorderFactory.createTitledBorder("Pedidos"));

		add(pnPedidos);

		mPedido = new PedidoModel(buscarComandas());

		dgvPedidos = new JTable(mPedido);
		dgvPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvPedidos.getTableHeader().setReorderingAllowed(false);

		pnPedidos.add(new JScrollPane(dgvPedidos));

		// ====================================================================
		// ====================================================================

		ImageIcon icSair = new ImageIcon(this.getClass().getResource(c.getSair64x64()));
		btnSair = new JButton(icSair);
		btnSair.setSize(200, 100);
		btnSair.setLocation(600, 440);
		btnSair.addActionListener(LytVerificarPedidos.this);

		add(btnSair);

	}

	public static void main(String[] args) {

		JDialog janela = new LytVerificarPedidos(null, true, "");
		janela.setVisible(true);

	}

	private List<Comanda> buscarComandas() {

		List<Comanda> lstDados = new ArrayList<>();
		Comanda comanda = new Comanda();

		comanda.getServico().setServico(DESC_SERVICO);

		switch (cbStatus.getSelectedIndex()) {

		case 0:
			comanda.setStatus("");
			break;
		case 1:
			comanda.setStatus("APROVADO");
			break;
		case 2:
			comanda.setStatus("REPROVADO");
			break;
		case 3:
			comanda.setStatus("PRE_APROVADO");
			break;

		}

		String[][] dados = busComanda.buscarComandas(comanda);

		for (String valor[] : dados) {

			Comanda cln = new Comanda();

			cln.setCdComanda(valor[0]);
			cln.setQtdTotal(Integer.parseInt(valor[1]));
			cln.setTotalCompra(Double.parseDouble(valor[2]));
			cln.setStatus(valor[3]);
			cln.setDataVenda(valor[4]);
			cln.setDataConfirmacao(valor[5]);

			lstDados.add(cln);

		}

		return lstDados;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnSair) {

			this.dispose();

		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getSource() == cbStatus) {

			mPedido = new PedidoModel(buscarComandas());

			if (dgvPedidos != null) {

				dgvPedidos.setModel(mPedido);

			}

		}

	}

}
