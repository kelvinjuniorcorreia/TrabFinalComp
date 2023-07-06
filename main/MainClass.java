package main;

import lexico.IsiScanner;
import parser.IsiParser;

public class MainClass {
	public static void main(String[] args) {
		IsiScanner sc = new IsiScanner("input.isi"); //le arquivo e valida tokens pelo analisador léxico
		IsiParser  pa = new IsiParser(sc); //cria objeto responsável pelo analisado sintático

		pa.S(); //Chama a regra principal do analisador sintático
		System.out.println("\n");
		sc.getErros(); //mostra erros léxicos
		pa.getErrosSint(); //mostra erros sintáticos

		System.out.println("Compilation Successful!");
	}
}
