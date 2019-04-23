package gsfieo.mvc.tipoProduto.model;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

public class TipoProduto {

	private int _cdTipoProduto;
	private String _tipoProduto;

	public int getCdTipoProduto() {

		return _cdTipoProduto;

	}

	public void setCdTipoProduto(int _cdTipoProduto) {

		this._cdTipoProduto = _cdTipoProduto;

	}

	public String getTipoProduto() {

		return _tipoProduto;

	}

	public void setTipoProduto(String _tipoProduto) {

		this._tipoProduto = _tipoProduto.toUpperCase();

	}

}
