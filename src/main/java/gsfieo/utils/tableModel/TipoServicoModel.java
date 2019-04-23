package gsfieo.utils.tableModel;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.tipoServico.model.TipoServico;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TipoServicoModel extends AbstractTableModel {

	private static final long serialVersionUID = 7312299008431973414L;

	private final int CODIGO = 0;
	private final int TIPO = 1;
	private final int STATUS = 2;

	private List<TipoServico> lstDados;
	private final String[] colunas = { "CODIGO", "TIPO", "STATUS" };

	public TipoServicoModel(List<TipoServico> lstDados) {

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

		TipoServico cln = lstDados.get(rowIndex);

		switch (columnIndex) {

		case CODIGO:
			return cln.getCdTipoServico();
		case TIPO:
			return cln.getTipoServico();
		case STATUS:
			return cln.getStrStatus();

		}

		return null;

	}

	public void addRow(TipoServico cln) {

		lstDados.add(cln);
		this.fireTableDataChanged();

	}

	@Override
	public String getColumnName(int columnIndex) {

		return colunas[columnIndex];

	}

}
