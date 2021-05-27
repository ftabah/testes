package com.tabah.demo.controller;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tabah.demo.validators.Validator;

@RestController
@RequestMapping(value = "/")
public class Controller {
	
	@GetMapping("validaCPF")
	public boolean validaCPF(@RequestParam(name = "cpf", defaultValue = "000.000.002-72") String cpf) {
		return Validator.validaCpf(cpf);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("sorteioSena")
	public String sorteioSena(@RequestParam(name = "numeros", defaultValue = "1,2,3,4,5,6") String numeros) {
		Object o = Validator.validaInputSena(numeros);
		if (o instanceof String) {
			return (String) o;
		} 
		else if (o instanceof Set<?>) {
			Set<Integer> nbrs = (Set<Integer>) o;
			int contador = 0;
			do {
				contador++;
			} while (!sorteio().containsAll(nbrs));
			NumberFormat nf = NumberFormat.getNumberInstance();
			return "Foram necessários " + nf.format(contador) + " sorteios";
		}
		else {
			return "Ocorreu um erro inesperado";
		}
	}
	
	private Set<Integer> sorteio() {
		Set<Integer> numerosMS = new HashSet<Integer>();
		while (numerosMS.size() != 6) {
			numerosMS.add(1 + (int) (Math.random() * 60));
		}
		return numerosMS;
	}
	
}
