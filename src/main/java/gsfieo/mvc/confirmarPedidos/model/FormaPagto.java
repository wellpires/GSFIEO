package gsfieo.mvc.confirmarPedidos.model;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

public class FormaPagto {

	private int cdFormaPagto;
	private String formaPagto;

	public int getCdFormaPagto() {
		return cdFormaPagto;
	}

	public void setCdFormaPagto(int cdFormaPagto) {

		this.cdFormaPagto = cdFormaPagto;
	}

	public String getFormaPagto() {

		switch (formaPagto) {

		case "DINHEIRO":
			return "Dinheiro";
		case "CARTAO_DE_DEBITO":
			return "Cartão de débito";
		case "CARTAO_DE_CREDITO":
			return "Cartão de crédito";

		default:
			return "";

		}
	}

	public String getFormaPagto_2() {

		return formaPagto;

	}

	public void setFormaPagto(String formaPagto) {

		switch (formaPagto) {

		case "Dinheiro":
			formaPagto = "DINHEIRO";
			break;
		case "Cartão de débito":
			formaPagto = "CARTAO_DE_DEBITO";
			break;
		case "Cartão de crédito":
			formaPagto = "CARTAO_DE_CREDITO";
			break;

		}

		this.formaPagto = formaPagto;
	}

}
