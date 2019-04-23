package gsfieo.utils;

/*
 * @Author: Wellington Gonçalves Pires
 *
 */

import java.awt.Component;
import javax.swing.JOptionPane;

public class GSFIEOUtil {

	public static String[] matrizUnidimensional(String[][] matriz2D) {

		// Transformar vetores Multidimensionais em unidimensionais

		int coluna;

		coluna = matriz2D.length;

		String[] matriz1D = new String[coluna];

		for (int i = 0; i < coluna; i++) {

			matriz1D[i] = matriz2D[i][0];

		}

		return matriz1D;

	}

	public static String[] matrizUnidimensional_2(String[][] matriz2D) {

		// Transformar vetores Multidimensionais em unidimensionais

		int coluna;

		coluna = matriz2D[0].length;

		String[] matriz1D = new String[coluna];

		for (int i = 0; i < coluna; i++) {

			matriz1D[i] = matriz2D[0][i];

		}

		return matriz1D;

	}

	public static void mensagem(Component frame, String msg, int opcao) {

		JOptionPane.showMessageDialog(frame, msg, "GSFIEO", opcao);

	}

}
