package gsfieo.mvc.produtos.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.produtos.controller.BusProdutos;
import gsfieo.mvc.produtos.model.Produto;
import gsfieo.mvc.tipoProduto.controller.BusTipoProduto;
import gsfieo.mvc.tipoProduto.model.TipoProduto;
import gsfieo.utils.GSFIEOConstants;
import gsfieo.utils.GSFIEOUtil;
import gsfieo.utils.informacoes.ConstantesImagens;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LytEditarProduto extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1044868484100157548L;

	private final ConstantesImagens c = new ConstantesImagens();
	private final BusProdutos busProdutos = new BusProdutos();
	private final BusTipoProduto busTipoProduto = new BusTipoProduto();

	private final JLabel lblTitulo, lblTipo, lblProduto, lblValorUnit;
	private final JComboBox<String> cbTipo;
	private final JTextField txtProduto, txtValorUnit;
	private final JButton btnGravar, btnCance;

	private final int CD_PRODUTO;

	public LytEditarProduto(Frame f, boolean bol, int cdProduto) {

		super(f, bol);

		CD_PRODUTO = cdProduto;

		setTitle("Produtos");
		setSize(340, 350);
		setLocation(10, 110);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ======================================================================
		// ======================= ABA GRAVAR ==================================

		// -------- LABEL 'TÍTULO'
		lblTitulo = new JLabel("Editar Produtos");
		lblTitulo.setSize(210, 25);
		lblTitulo.setLocation(10, 10);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));

		add(lblTitulo);

		// -------- CAMPO 'Tipo Produto'
		lblTipo = new JLabel("Tipo de produto:");
		lblTipo.setSize(210, 25);
		lblTipo.setLocation(10, 45);

		add(lblTipo);

		cbTipo = new JComboBox<>(listarCombo());
		cbTipo.setSize(200, 25);
		cbTipo.setLocation(20, 75);

		add(cbTipo);

		// -------- CAMPO 'Produto'
		lblProduto = new JLabel("Produto:");
		lblProduto.setSize(120, 25);
		lblProduto.setLocation(10, 110);

		add(lblProduto);

		txtProduto = new JTextField();
		txtProduto.setSize(200, 25);
		txtProduto.setLocation(20, 140);

		add(txtProduto);

		// -------- CAMPO 'VALOR UNITARIO'
		lblValorUnit = new JLabel("Valor unitário:");
		lblValorUnit.setSize(100, 20);
		lblValorUnit.setLocation(10, 175);

		add(lblValorUnit);

		txtValorUnit = new JTextField();
		txtValorUnit.setSize(200, 25);
		txtValorUnit.setLocation(20, 200);

		add(txtValorUnit);

		// --------- BOTÕES
		// --- GRAVAR
		ImageIcon icGravar = new ImageIcon(this.getClass().getResource(c.getGravar()));

		btnGravar = new JButton(icGravar);
		btnGravar.setSize(150, 60);
		btnGravar.setLocation(15, 245);
		btnGravar.addActionListener(LytEditarProduto.this);

		add(btnGravar);

		// --- CANCELAR
		ImageIcon icCanc = new ImageIcon(this.getClass().getResource(c.getSair()));

		btnCance = new JButton(icCanc);
		btnCance.setSize(150, 60);
		btnCance.setLocation(170, 245);
		btnCance.addActionListener(LytEditarProduto.this);

		add(btnCance);

		preencherCampos();

	}

	public static void main(String[] args) {

		JDialog janela = new LytEditarProduto(null, true, 0);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCance) {

			this.dispose();

		} else if (e.getSource() == btnGravar) {

			Produto produto = new Produto();
			produto.setCdProduto(CD_PRODUTO);
			produto.setProduto(txtProduto.getText());
			produto.getTipoProduto().setTipoProduto(String.valueOf(cbTipo.getSelectedItem()));
			produto.setValorUnit(Double.parseDouble(txtValorUnit.getText().replace(',', '.')));

			busProdutos.editar(produto);

			GSFIEOUtil.mensagem(LytEditarProduto.this, "Registro alterado com sucesso!",
					GSFIEOConstants.MSG_INFORMACAO);

			this.dispose();

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

	private void preencherCampos() {

		if (CD_PRODUTO != 0) {

			Produto produto = new Produto();
			produto.setCdProduto(CD_PRODUTO);

			String[] res = busProdutos.listarInfoProduto(produto);

			txtProduto.setText(res[0]);
			cbTipo.setSelectedItem(String.valueOf(res[1]));
			txtValorUnit.setText(res[2]);

		}

	}

}
