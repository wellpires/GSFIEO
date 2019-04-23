package gsfieo.utils.tableModel;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.servico.model.Servicos;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ServicoModel extends AbstractTableModel {

	private static final long serialVersionUID = 2259304189486751415L;

	private final int CODIGO = 0;
	private final int SERVICO = 1;
	private final int STATUS = 2;
	private final int TIPO = 3;

	List<Servicos> lstDados;
	private final String[] colunas = { "CODIGO", "SERVIÇO", "STATUS", "TIPO" };

	public ServicoModel(List<Servicos> lstDados) {

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

		case CODIGO:
			return cln.getCdServicos();
		case SERVICO:
			return cln.getServico();
		case STATUS:
			return cln.getStrStatus();
		case TIPO:
			return cln.getTipoServico().getTipoServico();

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
