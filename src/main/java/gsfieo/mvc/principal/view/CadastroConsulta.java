package gsfieo.mvc.principal.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.utils.informacoes.ConstantesImagens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;

public class CadastroConsulta extends JDialog implements ChangeListener {

	private static final long serialVersionUID = 4197086032834999095L;

	public final ConstantesImagens c = new ConstantesImagens();
	public final JTabbedPane jtpAbas;
	public final Border borda = BorderFactory.createLineBorder(Color.BLACK, 1);

	// =========== ABA GRAVAR ============
	public final JPanel pnGravar;

	// =========== ABA CONSULTAR ============
	public final JPanel pnConsulta, pnGrid;
	public final JLabel lblPesquisa;
	public final JTextField txtPesquisar;
	public final JButton btnEditar, btnExcluir, btnSair;
	public final JTable dgvDados = new JTable();

	public CadastroConsulta(Frame f, boolean bol) {

		super(f, bol);

		setSize(350, 400);
		setLocation(10, 110);
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// =====================================================================
		jtpAbas = new JTabbedPane();
		jtpAbas.setBorder(borda);

		// ======================= ABA GRAVAR ==================================
		pnGravar = new JPanel();
		pnGravar.setLayout(null);

		jtpAbas.addTab("Cadastro", pnGravar);

		// =====================================================================
		// ======================= ABA CONSULTAR ===============================

		pnConsulta = new JPanel();
		pnConsulta.setLayout(null);

		// --------- CAMPO 'Pesquisar'
		lblPesquisa = new JLabel("Pesquisar:");
		lblPesquisa.setSize(71, 20);
		lblPesquisa.setLocation(10, 15);

		pnConsulta.add(lblPesquisa);

		txtPesquisar = new JTextField();
		txtPesquisar.setSize(347, 25);
		txtPesquisar.setLocation(75, 15);

		pnConsulta.add(txtPesquisar);

		// --------- BOTÕES PARA MANIPULAÇÃO DOS REGISTROS
		// --- EDITAR
		ImageIcon icEditar = new ImageIcon(this.getClass().getResource(c.getEditar()));

		btnEditar = new JButton(icEditar);
		btnEditar.setSize(88, 58);
		btnEditar.setLocation(426, 45);
		btnEditar.setToolTipText("Editar");

		pnConsulta.add(btnEditar);

		// --- EXCLUIR
		ImageIcon icExcluir = new ImageIcon(this.getClass().getResource(c.getExcluir()));

		btnExcluir = new JButton(icExcluir);
		btnExcluir.setSize(88, 58);
		btnExcluir.setLocation(426, 110);
		btnExcluir.setToolTipText("Excluir");

		pnConsulta.add(btnExcluir);

		// --- SAIR
		ImageIcon icSair = new ImageIcon(this.getClass().getResource(c.getSair()));

		btnSair = new JButton(icSair);
		btnSair.setSize(88, 58);
		btnSair.setLocation(426, 198);
		btnSair.setToolTipText("Sair");

		pnConsulta.add(btnSair);

		// --------- GRID PARA LISTAR OS REGISTROS DO BANCO DE DADOS

		pnGrid = new JPanel();
		pnGrid.setSize(411, 230);
		pnGrid.setLocation(10, 40);

		JScrollPane pane = new JScrollPane(dgvDados);
		pnGrid.setLayout(new GridLayout());
		pnGrid.add(pane);
		pnConsulta.add(pnGrid);

		jtpAbas.addTab("Consulta", pnConsulta);
		add(jtpAbas);

		jtpAbas.addChangeListener(CadastroConsulta.this);

	}

	public static void main(String[] args) {

		JDialog janela = new CadastroConsulta(null, true);
		janela.setVisible(true);

	}

	public void criarGrid(AbstractTableModel modelo) {

		dgvDados.setModel(modelo);
		dgvDados.setPreferredScrollableViewportSize(new Dimension(411, 230));
		dgvDados.setFillsViewportHeight(true);
		dgvDados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dgvDados.getTableHeader().setReorderingAllowed(false);
		dgvDados.setBorder(borda);

		if (dgvDados.getRowCount() <= 0) {

			btnEditar.setEnabled(false);
			btnExcluir.setEnabled(false);

		} else {

			btnEditar.setEnabled(true);
			btnExcluir.setEnabled(true);

		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {

		int index = jtpAbas.getSelectedIndex();

		if (index == 0) {

			setSize(350, 400);

		} else if (index == 1) {

			setSize(525, 333);

		}

	}

}
