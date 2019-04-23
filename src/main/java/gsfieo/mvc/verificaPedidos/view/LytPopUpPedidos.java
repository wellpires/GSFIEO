package gsfieo.mvc.verificaPedidos.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.comanda.controller.BusComanda;
import gsfieo.mvc.comanda.model.Comanda;
import gsfieo.mvc.confirmarPedidos.model.Pedidos;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.informacoes.ConstantesImagens;
import gsfieo.utils.tableModel.ComandaModel;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LytPopUpPedidos extends JDialog implements ActionListener, DocumentListener, MouseListener {

	private static final long serialVersionUID = -6968313980285967584L;

	private final JPanel pnTitulo, pnComandas;
	private final JLabel lblTitulo, lblComanda;
	private final JTextField txtComanda;
	private final JTable dgvComandas;
	private final JButton btnConfirmar;

	private final BusComanda busComanda = new BusComanda();
	private final Pedidos PEDIDOS;
	private final ConstantesImagens c = new ConstantesImagens();
	private ComandaModel mComanda;

	public LytPopUpPedidos(Frame f, boolean bol, Pedidos pedidos) {

		super(f, bol);

		this.PEDIDOS = pedidos;

		Border borda = BorderFactory.createLineBorder(Color.BLACK, 1);

		setSize(525, 455);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(null);
		this.getRootPane().setBorder(borda);

		// TITULO
		pnTitulo = new JPanel();
		pnTitulo.setSize(498, 50);
		pnTitulo.setLocation(0, 5);
		add(pnTitulo);

		lblTitulo = new JLabel("Pedidos para " + pedidos.getComanda().getLogin().getUsuario());
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));
		pnTitulo.add(lblTitulo);

		// Campo 'COMANDA'
		lblComanda = new JLabel("Comanda:");
		lblComanda.setSize(60, 25);
		lblComanda.setLocation(10, 60);
		add(lblComanda);

		txtComanda = new JTextField();
		txtComanda.setSize(435, 25);
		txtComanda.setLocation(75, 60);
		txtComanda.getDocument().addDocumentListener(LytPopUpPedidos.this);
		add(txtComanda);

		// COMANDAS
		pnComandas = new JPanel(new GridLayout());
		pnComandas.setSize(500, 250);
		pnComandas.setLocation(10, 95);
		pnComandas.setBorder(BorderFactory.createTitledBorder(""));
		add(pnComandas);

		mComanda = new ComandaModel(listarComandas());

		dgvComandas = new JTable(mComanda);
		dgvComandas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvComandas.getTableHeader().setReorderingAllowed(false);
		dgvComandas.addMouseListener(LytPopUpPedidos.this);

		pnComandas.add(new JScrollPane(dgvComandas));

		// BOTÕES

		ImageIcon icConfirmar = new ImageIcon(this.getClass().getResource(c.getConfirmar64x64()));
		btnConfirmar = new JButton(icConfirmar);
		btnConfirmar.setSize(200, 80);
		btnConfirmar.setLocation(160, 355);
		btnConfirmar.addActionListener(LytPopUpPedidos.this);
		add(btnConfirmar);

	}

	public static void main(String[] args) {

		Comanda comanda = new Comanda();
		comanda.getLogin().setUsuario("TESTE");
		comanda.getServico().setServico("JAILSON");

		Pedidos p = new Pedidos();
		p.setComanda(comanda);

		JDialog janela = new LytPopUpPedidos(null, true, p);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnConfirmar) {

			if (dgvComandas.getSelectedRow() > -1) {

				confirmarPedido();

			} else {

				GSFIEOUtil.mensagem(LytPopUpPedidos.this, "Selecione um pedido", GSFIEOConstants.MSG_AVISO);

			}

		}

	}

	private List<Comanda> listarComandas() {

		List<Comanda> lstComanda = new ArrayList<>();

		PEDIDOS.getComanda().setCdComanda(txtComanda.getText());

		String dados[][] = busComanda.listarComandas(PEDIDOS.getComanda());

		for (String valor[] : dados) {

			Comanda comanda = new Comanda();

			comanda.setCdComanda(valor[0]);
			comanda.setTotalCompra(Double.parseDouble(valor[1]));
			comanda.setQtdTotal(Integer.parseInt(valor[2]));
			comanda.setDataVenda(valor[3]);

			lstComanda.add(comanda);

		}

		return lstComanda;

	}

	@Override
	public void insertUpdate(DocumentEvent e) {

		mComanda = new ComandaModel(listarComandas());
		dgvComandas.setModel(mComanda);

	}

	@Override
	public void removeUpdate(DocumentEvent e) {

		mComanda = new ComandaModel(listarComandas());
		dgvComandas.setModel(mComanda);

	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getClickCount() == 2 && e.getSource() == dgvComandas) {

			confirmarPedido();

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

	private void confirmarPedido() {

		PEDIDOS.getComanda().setCdComanda((String) mComanda.getValueAt(dgvComandas.getSelectedRow(), 0));
		PEDIDOS.getComanda().getLogin().setUsuario("");
		this.dispose();

	}

}
