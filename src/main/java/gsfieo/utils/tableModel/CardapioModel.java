package gsfieo.utils.tableModel;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.produtos.model.Produto;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CardapioModel extends AbstractTableModel {

	private static final long serialVersionUID = -3046580643572556567L;

	private final int CODIGO = 0;
	private final int PRODUTO = 1;
	private final int TIPO = 2;
	private final int PRECO = 3;

	private List<Produto> lstDados;
	private final String[] colunas = { "CÓDIGO", "PRODUTO", "TIPO", "PREÇO" };

	public CardapioModel(List<Produto> lstDados) {

		this.lstDados = lstDados;

	}

	@Override
	public int getRowCount() {

		return lstDados.size();

	}

	@Override
	public int getColumnCount() {

		return colunas.length;

	}

	@Override
	public String getColumnName(int columnIndex) {

		return colunas[columnIndex];

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Produto cln = lstDados.get(rowIndex);

		switch (columnIndex) {

		case CODIGO:
			return cln.getCdProduto();
		case PRODUTO:
			return cln.getProduto();
		case TIPO:
			return cln.getTipoProduto().getTipoProduto();
		case PRECO:
			return new DecimalFormat("0.00")
					.format(Double.parseDouble(String.valueOf(cln.getValorUnit()).replace(',', '.')));

		}

		return null;

	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {

		Produto produto = lstDados.get(rowIndex);

		switch (columnIndex) {

		case PRECO:
			produto.setValorUnit(Double.parseDouble(String.valueOf(value).replace(',', '.')));

		}

		fireTableDataChanged();

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		return columnIndex == PRECO;

	}

	public Produto getValue(int rowIndex) {

		return lstDados.get(rowIndex);

	}

	public void onAdd(Produto clnProd) {

		lstDados.add(clnProd);
		fireTableRowsInserted(indexOf(clnProd), indexOf(clnProd));

	}

	public void onAddAll(List<Produto> produtos) {

		this.lstDados.addAll(produtos);
		fireTableDataChanged();

	}

	public int indexOf(Produto clnProd) {

		return lstDados.indexOf(clnProd);

	}

	public void onRemove(int index) {

		lstDados.remove(index);
		fireTableRowsDeleted(index, index);

	}

	public void onRemoveAll(List<Produto> valores) {

		lstDados.removeAll(valores);
		fireTableDataChanged();

	}

}
