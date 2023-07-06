package parser;

import lexico.IsiScanner;
import lexico.Token;

public class IsiParser {
	private IsiScanner 	scanner;
	private Token      	token;
	public 	String[] 	aErrosSint = new String[100];
	private boolean 	controller = true;
	public 	int nContPara = 0;
	public 	int nContChav = 0;
	private int nCont = 0;
	
	public IsiParser(IsiScanner scanner) { //Construtor esperando arquivo
		this.scanner = scanner;
	}

	public IsiParser() {} //Construtor vazio
	
	public void S() {
		token = scanner.nextToken();
		controller = true;
		if (token != null) {
			if(token.getType() == token.TK_PRINT){
				D();
				A();
				D();
				B();
				D();
				C();
			}else if(token.getType() == token.TK_INT || token.getType() == token.TK_FLOAT){
				D();
				E();
				D();
				F();
				D();
				B();
			} else if (token.getType() == token.TK_IF){
				D();
				H();
				D();
				B();
				D();
				J();
				D();
				B();
				D();
				I();
			} else if(token.getType() == token.TK_ELSE){
				D();
				A();
			} else if (token.getType() == token.TK_AND || token.getType() == token.TK_OR){
				D();
				H();
				D();
				B();
				D();
				J();
				D();
				B();
				D();
				I();
			} else if (token.getType() == token.TK_WHILE){
				D();
				H();
				D();
				E();
				D();
				J();
				D();
				B();
				D();
				I();
				D();
				A();
			} else if (token.getType() == token.TK_FOR){
				D();
				H();
				D();
				B();
				D();
				I();
				D();
				H();
				D();
				E();
				D();
				J();
				D();
				B();
				D();
				I();
				D();
				A();
			}
			S();
		}else{
			contCP();
		}
	}

	public void AjusController(){
		this.controller = true;
	}

	public void A(){
		token = scanner.nextToken();
		if (token.getType() != Token.TK_CHAV_ABRE && controller == true) {
			this.aErrosSint[nCont] = "Linha: " + scanner.line + " Coluna: " + scanner.column + ", erro de sintaxe, esperado {.";
			this.nCont++;
			controller = false;
		}
	}

	public void B(){
		token = scanner.nextToken();
		if ((token.getType() != Token.TK_ID && token.getType() != Token.TK_DIGITO) && controller == true) {
			this.aErrosSint[nCont] = "Linha: " + scanner.line + " Coluna: " + scanner.column + ", erro de sintaxe, esperado ID/DIGITO.";
			this.nCont++;
			controller = false;
		}
	}

	public void C(){
		token = scanner.nextToken();
		if (token.getType() != Token.TK_CHAV_FECHA && controller == true) {
			this.aErrosSint[nCont] = "Linha: " + scanner.line + " Coluna: " + scanner.column + ", erro de sintaxe, esperado }.";
			this.nCont++;
			this.controller = false;
			controller = false;
		}
	}

	public void D(){
		token = scanner.nextToken();
		if (token.getType() != Token.TK_ESPACE && controller == true) {
			this.aErrosSint[nCont] = "Linha: " + token.getLine() + " Coluna: " + token.getColumn() + ", erro de sintaxe, esperado ESPAÇO.";
			this.nCont++;
			controller = false;
		}
	}

	public void E(){
		token = scanner.nextToken();
		if (token.getType() != Token.TK_ID && controller == true) {
			this.aErrosSint[nCont] = "Linha: " + scanner.line + " Coluna: " + scanner.column + ", erro de sintaxe, esperado ID.";
			this.nCont++;
			controller = false;
		}
	}

	public void F(){
		token = scanner.nextToken();
		if (token.getType() != Token.TK_IGUAL && controller == true) {
			this.aErrosSint[nCont] = "Linha: " + scanner.line + " Coluna: " + scanner.column + ", ero de sintaxe, esperado =.";
			this.nCont++;
			controller = false;
		}
	}

	public void G(){
		token = scanner.nextToken();
		if (token.getType() != Token.TK_DIGITO && controller == true) {
			this.aErrosSint[nCont] = "Linha: " + scanner.line + " Coluna: " + scanner.column + ", erro de sintaxe, esperado DIGITO.";
			this.nCont++;
			controller = false;
		}
	}

	public void H(){
		token = scanner.nextToken();
		if (token.getType() != Token.TK_PAR_ABRE && controller == true) {
			this.aErrosSint[nCont] = "Linha: " + scanner.line + " Coluna: " + scanner.column + ", erro de sintaxe, esperado (.";
			this.nCont++;
			controller = false;
		}
	}

	public void I(){
		token = scanner.nextToken();
		if (token.getType() != Token.TK_PAR_FECHA && controller == true) {
			this.aErrosSint[nCont] = "Linha: " + scanner.line + " Coluna: " + scanner.column + ", erro de sintaxe, esperado ).";
			this.nCont++;
			controller = false;
		}
	}

	public void J(){
		token = scanner.nextToken();
		if (token.getType() != Token.TK_COMP_IGUAL && token.getType() != Token.TK_COMP_DIF && token.getType() != Token.TK_MAIOR && token.getType() != Token.TK_MENOR && controller == true) {
			this.aErrosSint[nCont] = "Linha: " + scanner.line + " Coluna: " + scanner.column + ", erro de sintaxe, esperado ==/!=.";
			this.nCont++;
			controller = false;
		}
	}

	public void getErrosSint(){
		System.out.println("------LISTA DE ERROS SINTATICO------");
		for(int i=0 ; i < aErrosSint.length ; i++){
			if(aErrosSint[i] != null){
				System.out.println(aErrosSint[i]);
			}
		};
		System.out.println("----------------------------------");
	}

	public void contCP(){
		if(nContChav > 0){
			this.aErrosSint[nCont] = "Total de chaves iniciadas difere de total de chaves finalizadas";
			this.nCont++;
		} else if (nContChav < 0){
			this.aErrosSint[nCont] = "Total de chaves finalizadas difere de total de chaves iniciadas";
			this.nCont++;
		}
		if(nContPara > 0){
			this.aErrosSint[nCont] = "Total de parenteses inciados difere do total de parenteses finalizados";
			this.nCont++;
		} else if(nContPara < 0){
			this.aErrosSint[nCont] = "Total de parenteses finalizados difere do total de parenteses inciados";
			this.nCont++;
		}
	}
}
