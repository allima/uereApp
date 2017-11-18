package br.edu.ifmg.polo.pedidovenda.util;

public class RemoveAcentosUtil {

	private static final String acentuado = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
	private static final String semAcento = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";
	private static char[] tabela;

	static {
		tabela = new char[256];
		for (int i = 0; i < tabela.length; ++i) {
			tabela[i] = (char) i;
		}
		for (int i = 0; i < acentuado.length(); ++i) {
			tabela[acentuado.charAt(i)] = semAcento.charAt(i);
		}
	}

	public static String removerAcentos(final String palavra) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < palavra.length(); ++i) {
			char ch = palavra.charAt(i);
			if (ch < 256) {
				sb.append(tabela[ch]);
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
}
