package gsfieo.utils.tableModel;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.produtos.model.Produto;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CardapioModel_Pedidos extends AbstractTableModel {

	private static final long serialVersionUID = -4356330788405795778L;

	private final int PRODUTO = 0;
	private final int PRECO = 1;

	private final List<Produto> lstProdutos;
	private final String colunas[] = { "PRODUTO", "PREÇO" };

	public CardapioModel_Pedidos(List<Produto> lstProdutos) {

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

		Produto prod = lstProdutos.get(rowIndex);

		switch (columnIndex) {

		case PRODUTO:
			return prod.getProduto();
		case PRECO:
			return "R$" + String.valueOf(new DecimalFormat("0.00").format(prod.getValorUnit()));

		}

		return null;

	}

	public Object pegarValor(int linha, int coluna) {

		Produto prod = lstProdutos.get(linha);

		switch (coluna) {

		case PRODUTO:
			return prod.getProduto();
		case PRECO:
			return prod.getValorUnit();

		}

		return null;

	}

	@Override
	public String getColumnName(int index) {

		return colunas[index];

	}

}
