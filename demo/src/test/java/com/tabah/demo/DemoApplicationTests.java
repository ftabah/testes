package com.tabah.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.tabah.demo.controller.Controller;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {
	
	@Autowired
	private Controller controller;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void testaValidacaoCPF() throws Exception {
		this.mockMvc.perform(get("/validaCPF?cpf=272"))
			.andExpect(content().string(containsString("true")));
		this.mockMvc.perform(get("/validaCPF?cpf=2-72"))
			.andExpect(content().string(containsString("true")));
		this.mockMvc.perform(get("/validaCPF?cpf=000.000.002-72"))
			.andExpect(content().string(containsString("true")));
		this.mockMvc.perform(get("/validaCPF?cpf=000.00.0002-72"))
			.andExpect(content().string(containsString("false")));
		this.mockMvc.perform(get("/validaCPF?cpf=1272"))
			.andExpect(content().string(containsString("false")));
		this.mockMvc.perform(get("/validaCPF?cpf=FabioTabah"))
			.andExpect(content().string(containsString("false")));
	}
	
	@Test
	public void testaInputsInvalidosMegaSena() throws Exception {
		this.mockMvc.perform(get("/sorteioSena?numeros=1"))
			.andExpect(content().string(containsString("São necessário exatos 6 números na entrada")));
		this.mockMvc.perform(get("/sorteioSena?numeros=0,1,2,3,4,5"))
			.andExpect(content().string(containsString("0 não é um número válido (1~60)")));
		this.mockMvc.perform(get("/sorteioSena?numeros=1,2,3,4,5,6,7"))
			.andExpect(content().string(containsString("São necessário exatos 6 números na entrada")));
		this.mockMvc.perform(get("/sorteioSena?numeros=1,2e,3,4,5,6"))
			.andExpect(content().string(containsString("Caractere inesperado encontrado, usar apenas vírgula e números!")));
		this.mockMvc.perform(get("/sorteioSena?numeros=1,2,3,4,5,62"))
			.andExpect(content().string(containsString("62 não é um número válido (1~60)")));
		this.mockMvc.perform(get("/sorteioSena?numeros=1,2,2,3,4,5"))
			.andExpect(content().string(containsString("Existem numeros repetidos")));
	}
	

}
