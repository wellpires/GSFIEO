package gsfieo.utils.tableModel;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.servico.model.Servicos;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ComprarModel extends AbstractTableModel {

	private static final long serialVersionUID = -7802107272122290081L;

	private final int SERVICO = 0;

	List<Servicos> lstDados;
	private final String[] colunas = { "SERVIÇO" };

	public ComprarModel(List<Servicos> lstDados) {

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

		Servicos cln = lstDados.get(rowIndex);

		switch (columnIndex) {

		case SERVICO:
			return cln.getServico();

		}

		return null;

	}

	@Override
	public String getColumnName(int columnIndex) {

		return colunas[columnIndex];

	}

	public void addRow(Servicos cln) {

		this.lstDados.add(cln);
		fireTableDataChanged();

	}

}
