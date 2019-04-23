package gsfieo.utils.tableModel;

import gsfieo.mvc.comanda.model.Comanda;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Wellington
 */
public class ComandaUsuarioModel extends AbstractTableModel {

	private static final long serialVersionUID = 5609029583189418137L;

	private final int COMANDA = 0;
	private final int STATUS = 1;
	private final int TOTAL_COMPRA = 2;
	private final int SERVICO = 3;
	private final int FORMA_PAGTO = 4;

	private final String colunas[] = { "COMANDA", "STATUS", "TOTAL DE COMPRA", "SERVIÇO", "FORMA DE PAGTO" };
	private List<Comanda> lstComanda;

	public ComandaUsuarioModel(List<Comanda> lstComanda) {

		this.lstComanda = lstComanda;

	}

	public ComandaUsuarioModel() {
	}

	@Override
	public int getRowCount() {

		return lstComanda.size();

	}

	@Override
	public int getColumnCount() {

		return colunas.length;

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Comanda c = lstComanda.get(rowIndex);

		switch (columnIndex) {

		case COMANDA:
			return c.getCdComanda();
		case STATUS:
			return c.getStatus();
		case TOTAL_COMPRA:
			return "R$" + new DecimalFormat("0.00").format(c.getTotalCompra());
		case SERVICO:
			return c.getServico().getServico();
		case FORMA_PAGTO:
			return c.getFormaPgto().getFormaPagto();
		default:
			return null;

		}

	}

	@Override
	public String getColumnName(int index) {

		return colunas[index];

	}

}
