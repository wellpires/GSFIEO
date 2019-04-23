package gsfieo.utils.tableModel;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.produtos.model.Produto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProdutoModel extends AbstractTableModel {

	private static final long serialVersionUID = 1411510015715438282L;

	private final int CODIGO = 0;
	private final int PRODUTO = 1;
	private final int TIPO = 2;

	private List<Produto> lstDados;
	private final String[] colunas = { "CÓDIGO", "PRODUTO", "TIPO" };

	public ProdutoModel(List<Produto> lstDados) {

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

		}

		return null;

	}

	public void onAdd(Produto clnProd) {

		lstDados.add(clnProd);
		fireTableRowsInserted(indexOf(clnProd), indexOf(clnProd));

	}

	public void onAddAll(List<Produto> indices) {

		this.lstDados.addAll(indices);
		fireTableDataChanged();

	}

	public int indexOf(Produto clnProd) {

		return lstDados.indexOf(clnProd);

	}

	public void onRemove(int index) {

		lstDados.remove(index);
		fireTableRowsDeleted(index, index);

	}

	public void onRemoveAll(List<Produto> indices) {

		lstDados.removeAll(indices);

		fireTableDataChanged();

	}

	public Produto getValue(int row) {

		return lstDados.get(row);

	}

}
