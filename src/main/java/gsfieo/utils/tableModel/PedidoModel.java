package gsfieo.utils.tableModel;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.comanda.model.Comanda;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PedidoModel extends AbstractTableModel {

	private static final long serialVersionUID = 1101840640860913515L;

	private final int COMANDA = 0;
	private final int QTD = 1;
	private final int TOTAL = 2;
	private final int STATUS = 3;
	private final int DATA_VENDA = 4;
	private final int DATA_CONFIRMACAO = 5;

	private List<Comanda> lstPedidos;
	private final String colunas[] = { "COMANDA", "QTD PRODUTOS", "TOTAL", "STATUS", "DATA VENDA", "DATA CONFIRMACAO" };

	public PedidoModel(List<Comanda> lstPedidos) {

		this.lstPedidos = lstPedidos;

	}

	@Override
	public int getRowCount() {

		return lstPedidos.size();

	}

	@Override
	public int getColumnCount() {

		return colunas.length;

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Comanda cln = lstPedidos.get(rowIndex);

		switch (columnIndex) {

		case COMANDA:
			return cln.getCdComanda();
		case QTD:
			return cln.getQtdTotal();
		case TOTAL:
			return "R$" + new DecimalFormat("0.00").format(cln.getTotalCompra());
		case STATUS:
			return cln.getStatus();
		case DATA_VENDA:
			return cln.getDataVenda();
		case DATA_CONFIRMACAO:
			return cln.getDataConfirmacao();

		}

		return null;

	}

	@Override
	public String getColumnName(int index) {

		return colunas[index];

	}

}
