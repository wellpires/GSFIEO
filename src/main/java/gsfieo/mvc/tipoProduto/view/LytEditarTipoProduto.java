package gsfieo.mvc.tipoProduto.view;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LytEditarTipoProduto extends JDialog implements ActionListener {

	private static final long serialVersionUID = -2231058164713945726L;

	private final ConstantesImagens c = new ConstantesImagens();
	private final BusTipoProduto busTipoProduto = new BusTipoProduto();

	private final JLabel lblTitulo, lblTipoProdu;
	private final JTextField txtTipoProduto;
	private final JButton btnGravar, btnCance;

	private final int CD_TIPO_PRODUTO;

	public LytEditarTipoProduto(Frame f, boolean bol, int cdTipoProduto) {

		super(f, bol);

		CD_TIPO_PRODUTO = cdTipoProduto;

		setTitle("Tipo de produto");
		setSize(350, 230);
		setLocation(10, 110);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// -------- LABEL 'TÍTULO'
		lblTitulo = new JLabel("Editar tipo de produto");
		lblTitulo.setSize(300, 25);
		lblTitulo.setLocation(30, 10);
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));

		add(lblTitulo);

		// -------- CAMPO 'Tipo de produto'
		lblTipoProdu = new JLabel("Tipo:");
		lblTipoProdu.setSize(120, 25);
		lblTipoProdu.setLocation(10, 45);

		add(lblTipoProdu);

		txtTipoProduto = new JTextField();
		txtTipoProduto.setSize(200, 25);
		txtTipoProduto.setLocation(20, 75);

		add(txtTipoProduto);

		// --------- BOTÕES
		// --- GRAVAR
		ImageIcon icGravar = new ImageIcon(this.getClass().getResource(c.getEditar()));
		btnGravar = new JButton(icGravar);

		btnGravar.setSize(150, 60);
		btnGravar.setLocation(15, 120);
		btnGravar.addActionListener(LytEditarTipoProduto.this);

		add(btnGravar);

		// --- SAIR
		ImageIcon icCanc = new ImageIcon(this.getClass().getResource(c.getSair()));
		btnCance = new JButton(icCanc);

		btnCance.setSize(150, 60);
		btnCance.setLocation(175, 120);
		btnCance.addActionListener(LytEditarTipoProduto.this);

		add(btnCance);

		preencherCampos();

	}

	public static void main(String[] args) {

		LytEditarTipoProduto janela = new LytEditarTipoProduto(null, true, 0);
		janela.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCance) {

			this.dispose();

		} else if (e.getSource() == btnGravar) {

			TipoProduto tipoProduto = new TipoProduto();

			tipoProduto.setCdTipoProduto(CD_TIPO_PRODUTO);
			tipoProduto.setTipoProduto(txtTipoProduto.getText());

			busTipoProduto.editar(tipoProduto);

			GSFIEOUtil.mensagem(LytEditarTipoProduto.this, "Registro alterado com sucesso!",
					GSFIEOConstants.MSG_INFORMACAO);
			this.dispose();

		}

	}

	private void preencherCampos() {

		TipoProduto tipoProduto = new TipoProduto();
		tipoProduto.setCdTipoProduto(CD_TIPO_PRODUTO);

		String[] res = busTipoProduto.listarInfoTipoProduto(tipoProduto);

		txtTipoProduto.setText(res[1]);

	}

}
