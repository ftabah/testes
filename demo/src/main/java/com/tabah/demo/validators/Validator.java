package com.tabah.demo.validators;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;


public class Validator {
	
	public static Object validaInputSena(String input) {
		String i = input.replaceAll("[^0-9,]", "");
		if(input.equals(i)){
			String ns[] = i.split(",");
			if (ns.length == 6){
				Set<Integer> numeros = new HashSet<Integer>();
				for (String s : ns) {
					int n = Integer.parseInt(s);
					if (n >60 || n <1) {
						return s + " não é um número válido (1~60)";
					}
					numeros.add(n);
				}
				if (numeros.size() < 6) {
					return "Existem numeros repetidos";
				}
				else {
					return numeros;
				}
			}
			else {
				return "São necessário exatos 6 números na entrada";
			}
		}
		else {
			return "Caractere inesperado encontrado, usar apenas vírgula e números!";
		}
	}

	/**
	 * Remove caracteres não numericos sem pontos e hifen
	 * @param cpf
	 * @return boolean
	 */
	public static boolean validaCpf(String cpf) {
		// Testa caracteres nao numericos diferentes de ponto e de hifen
		if(Normalizer.normalize(cpf, Normalizer.Form.NFD)
				 .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
				 .replaceAll("[^\\p{ASCII}]", " ")
				 .replaceAll("[^0-9-.]", "").equals(cpf)) {
			// Testa o hifen, se houver
			if(cpf.contains("-")) {
				if (cpf.split("-").length != 2) return false;
				if (cpf.charAt(cpf.length()-3) != '-') return false;
				cpf = cpf.replaceAll("[^0-9.]", "");
			}
			// Testa os pontos, se houver
			if(cpf.contains(".")) {
				if (cpf.length() < 14 && cpf.length() > 10 ){
					if (cpf.split("\\.").length != 3) return false;
					if (cpf.charAt(cpf.length()-6) != '.') return false;
					if (cpf.charAt(cpf.length()-10) != '.') return false;
				}
				else if (cpf.length() < 10 && cpf.length() > 6) {
					if (cpf.split("\\.").length != 2) return false;
					if (cpf.charAt(cpf.length()-6) != '.') return false;
				}
				else return false;
			}
			cpf = cpf.replaceAll("[^0-9]", "");
			// regra oficial de calculo de validacao do cpf
			if (cpf.length() > 2 && cpf.length() < 12) {
				// garante que existam 11 numeros
				while (cpf.length() < 11) {
					cpf = "0" + cpf;
				}
				int soma1 = 0;
				int soma2 = 0;
				for(int i = 0; i<9; i++) {
					soma1 += Integer.valueOf(""+cpf.charAt(i)) * (10-i);
					soma2 += Integer.valueOf(""+cpf.charAt(i)) * (11-i);
				}
				soma1 = 11 - (soma1%11) > 9 ? 0 : 11 - (soma1%11) ;
				if ((""+soma1).equals(""+cpf.charAt(9))) {
					soma2 += Integer.valueOf(""+cpf.charAt(9)) * (2);
					soma2 = 11 - (soma2%11) > 9 ? 0 : 11 - (soma2%11) ;
					return (""+soma2).equals(""+cpf.charAt(10));
				}
			}
		}
		return false;
	}	
	
}
