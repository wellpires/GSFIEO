package gsfieo.utils.tableModel;

import gsfieo.mvc.produtos.model.Produto;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @Author Wellington
 */

public class ProdutosComandaModel extends AbstractTableModel {

	private static final long serialVersionUID = -7683435198610714092L;

	private final int PRODUTO = 0;
	private final int TIPO_PRODUTO = 1;
	private final int PRECO = 2;

	private final String colunas[] = { "PRODUTO", "TIPO DE PRODUTO", "PREÇO" };
	private final List<Produto> lstProdutos;

	public ProdutosComandaModel(List<Produto> lstProdutos) {

		this.lstProdutos = lstProdutos;

	}

	@Override
	public int getRowCount() {

		return lstProdutos.size();

	}

	@Override
	public int getColumnCount() {

		return colunas.length;

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Produto produto = lstProdutos.get(rowIndex);

		switch (columnIndex) {

		case PRODUTO:
			return produto.getProduto();
		case TIPO_PRODUTO:
			return produto.getTipoProduto().getTipoProduto();
		case PRECO:
			return "R$" + new DecimalFormat("0.00").format(produto.getValorUnit());
		default:
			return null;

		}

	}

	@Override
	public String getColumnName(int index) {

		return colunas[index];

	}

}
