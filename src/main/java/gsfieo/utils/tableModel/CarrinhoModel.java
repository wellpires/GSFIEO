package gsfieo.utils.tableModel;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.produtos.model.Produto;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CarrinhoModel extends AbstractTableModel {

	private static final long serialVersionUID = 7492938995461209634L;

	private final int PRODUTO = 0;
	private final int PRECO = 1;
	private final int QTD = 2;
	private final int SUBTOTAL = 3;

	private final List<Produto> lstProdutos;
	private final String colunas[] = { "PRODUTO", "PREÇO", "QUANTIDADE", "SUBTOTAL" };

	public CarrinhoModel(List<Produto> lstProdutos) {

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
		case QTD:
			return prod.getQtd();
		case SUBTOTAL:
			return "R$" + String.valueOf(new DecimalFormat("0.00").format(prod.getSubTotal()));

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
		case QTD:
			return prod.getQtd();
		case SUBTOTAL:
			return prod.getSubTotal();

		}

		return null;

	}

	@Override
	public String getColumnName(int index) {

		return colunas[index];

	}

	public void addRow(Produto produto) {

		lstProdutos.add(produto);
		fireTableDataChanged();

	}

	public List<Produto> getList() {

		return lstProdutos;

	}

	public Produto getValue(int index) {

		return lstProdutos.get(index);

	}

	public void removeRow(Produto prod) {

		lstProdutos.remove(indexOf(prod));
		fireTableRowsDeleted(indexOf(prod), indexOf(prod));

	}

	private int indexOf(Produto prod) {

		return lstProdutos.indexOf(prod);

	}

}
