package gsfieo.utils.tableModel;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import gsfieo.mvc.login.model.Login;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class LoginModel extends AbstractTableModel {

	private static final long serialVersionUID = 863407540297939509L;

	private final int CODIGO = 0;
	private final int USUARIO = 1;
	private final int PERMISSAO = 2;

	private final String colunas[] = { "CODIGO", "USUÁRIO", "PERMISSÃO" };
	private final List<Login> dados;

	public LoginModel(List<Login> dados) {

		this.dados = dados;

	}

	@Override
	public int getRowCount() {

		return dados.size();

	}

	@Override
	public int getColumnCount() {

		return colunas.length;

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Login login = dados.get(rowIndex);

		switch (columnIndex) {

		case CODIGO:
			return login.getCdLogin();
		case USUARIO:
			return login.getUsuario();
		case PERMISSAO:
			return login.getPermissao().getPermissao();
		default:
			throw new IndexOutOfBoundsException("Coluna inválida");

		}

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		switch (columnIndex) {

		case CODIGO:
			return int.class;
		case USUARIO:
			return String.class;
		case PERMISSAO:
			return String.class;
		default:
			throw new IndexOutOfBoundsException("Coluna inválida");

		}

	}

	@Override
	public String getColumnName(int columnIndex) {

		return colunas[columnIndex];

	}

	public Login getValue(int rowIndex) {

		return dados.get(rowIndex);

	}

	public int indexOf(Login login) {

		return dados.indexOf(login);

	}

	public void onAdd(Login login) {

		dados.add(login);
		fireTableRowsInserted(indexOf(login), indexOf(login));

	}

	public void onAddAll(List<Login> dadosIn) {

		dados.addAll(dadosIn);
		fireTableDataChanged();

	}

	public void onRemove(int rowIndex) {

		dados.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);

	}

	public void onRemove(Login login) {

		dados.remove(login);
		fireTableRowsDeleted(indexOf(login), indexOf(login));

	}

	public void onRemoveAll() {

		dados.clear();
		fireTableDataChanged();

	}

}
