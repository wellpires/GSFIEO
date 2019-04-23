package gsfieo.utils.tableModel;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.comanda.model.Comanda;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ComandaModel extends AbstractTableModel {

	private static final long serialVersionUID = 3429829896623773317L;

	private final int COMANDA = 0;
	private final int TOTAL_COMPRA = 1;
	private final int QTD_TOTAL = 2;
	private final int DATA_VENDA = 3;

	private final String colunas[] = { "COMANDA", "TOTAL DA COMPRA", "QTD DE PRODUTOS", "DATA DE VENDA" };
	private final List<Comanda> lstComandas;

	public ComandaModel(List<Comanda> lstComandas) {

		this.lstComandas = lstComandas;

	}

	@Override
	public int getRowCount() {

		return lstComandas.size();

	}

	@Override
	public int getColumnCount() {

		return colunas.length;

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Comanda comanda = lstComandas.get(rowIndex);

		switch (columnIndex) {

		case COMANDA:
			return comanda.getCdComanda();
		case TOTAL_COMPRA:
			return "R$" + new DecimalFormat("0.00").format(comanda.getTotalCompra());
		case QTD_TOTAL:
			return comanda.getQtdTotal();
		case DATA_VENDA:
			return comanda.getDataVenda();
		default:
			return null;

		}

	}

	@Override
	public String getColumnName(int columnIndex) {

		return colunas[columnIndex];

	}

}
