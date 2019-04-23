package gsfieo.utils.tableModel;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.tipoProduto.model.TipoProduto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TipoProdutoModel extends AbstractTableModel {

	private static final long serialVersionUID = 5057894124595105261L;

	private final int CODIGO = 0;
	private final int TIPO = 1;

	private List<TipoProduto> lstDados;
	private final String[] colunas = { "CÓDIGO", "TIPO" };

	public TipoProdutoModel(List<TipoProduto> lstDados) {

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
	public Object getValueAt(int rowIndex, int columnIndex) {

		TipoProduto tipoProduto = lstDados.get(rowIndex);

		switch (columnIndex) {

		case CODIGO:
			return tipoProduto.getCdTipoProduto();
		case TIPO:
			return tipoProduto.getTipoProduto();

		}

		return null;

	}

	@Override
	public String getColumnName(int columnIndex) {

		return colunas[columnIndex];

	}

	public void addRow(TipoProduto cln) {

		lstDados.add(cln);
		fireTableDataChanged();

	}

}
