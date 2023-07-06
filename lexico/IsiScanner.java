package lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import parser.IsiParser;

public class IsiScanner {
	private int	estado;
	private int	pos;
	public	int	line;
	public	int	column;
	private int	nCont = 0;
	private	char[]		content;
	private String[]	aErros = new String[100];
	IsiParser contAbert = new IsiParser();

	public IsiScanner(String filename) {
		try {
			line = 1;
			column = 0;
			String txtConteudo;
			txtConteudo = new String(Files.readAllBytes(Paths.get(filename)),StandardCharsets.UTF_8);
			System.out.println("----------ENTRADA----------");
			System.out.println(txtConteudo);
			System.out.println("\n----------TOKEN----------");
			content = txtConteudo.toCharArray();
			pos=0;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Token nextToken() {
		char 	currentChar;
		Token 	token;
		String 	term	= "";
		int 	contComent = 0;
		
		if (isEOF()) {
			return null;
		}
		estado = 0;
		while (true) {
			currentChar = nextChar();
			column++;
			
			switch(estado) {
			case 0:
				if (isChar(currentChar)) {
					term += currentChar;
					estado = 1;
				} else if (isDigit(currentChar)) {
					estado = 2;
					term += currentChar;
				} else if (isSpace(currentChar)) {
					token = new Token();
					token.setType(token.TK_ESPACE);
					estado = 0;
					return token;
				} else if (isOperator(currentChar)) {
					term += currentChar;
					estado = 4;
				} else if(isBlock(currentChar)) {
					estado = 3;
					term += currentChar;
				} else {
					setErros(Character.toString(currentChar));
				}
				break;
			case 1: // IDENTIFICADOR
				if (isChar(currentChar) || isDigit(currentChar) || isUnderline(currentChar)) {
					estado = 1;
					term += currentChar;
				}
				else if (isSpace(currentChar) || isOperator(currentChar) || isEOF(currentChar)){
					if (!isEOF(currentChar)){
						back();
					}
					token = new Token();
					if (term.equals("int")){
						token.setType(Token.TK_INT);
					} else if (term.equals("float")){
						token.setType(Token.TK_FLOAT);
					} else if (term.equals("if")){
						token.setType(Token.TK_IF);
					} else if (term.equals("else")){
						token.setType(Token.TK_ELSE);
					} else if (term.equals("for")){
						token.setType(Token.TK_FOR);
					} else if (term.equals("while")){
						token.setType(Token.TK_WHILE);
					} else if (term.equals("and")){
						token.setType(Token.TK_AND);
					} else if (term.equals("or")){
						token.setType(Token.TK_OR);
					} else if (term.equals("print")){
						token.setType(Token.TK_PRINT);
					} else {
						token.setType(Token.TK_ID);
					}

					token.setText(term);
					token.setLine(line);
					token.setColumn(column - term.length());

					System.out.println("[" + term + ", " + token.TK_TEXT[token.getType()] + "]");
					
					return token;
				} else {
					setErros(term);
				}
				break;
			case 2: // DIGITO
				if (isDigit(currentChar) || currentChar == '.') {
					estado = 2;
					term += currentChar;
				} else if (!isChar(currentChar) || isEOF(currentChar)) {
					if (!isEOF(currentChar))
						back();
					token = new Token();
					token.setType(Token.TK_DIGITO);
					token.setText(term);
					token.setLine(line);
					token.setColumn(column - term.length());
					System.out.println("[" + term + ", " + token.TK_TEXT[token.getType()] + "]");
					return token;
				} else {
					setErros(term);
				}
				break;
			case 3: // PARENTESES E CHAVES
				if (isBlock(currentChar)) {
					estado = 3;
					term += currentChar;
				} else if (!isChar(currentChar) || isEOF(currentChar)) {
					if (!isEOF(currentChar))
						back();
					token = new Token();
					if(term.equals("(")){
						token.setType(Token.TK_PAR_ABRE);
						this.contAbert.nContPara++;
					} else if(term.equals(")")){
						token.setType(Token.TK_PAR_FECHA);
						this.contAbert.nContPara--;
					} else if(term.equals("{")){
						token.setType(Token.TK_CHAV_ABRE);
						this.contAbert.nContChav++;
					} else if(term.equals("}")){
						token.setType(Token.TK_CHAV_FECHA);
						this.contAbert.nContChav--;
					}

					token.setText(term);
					token.setLine(line);
					token.setColumn(column - term.length());
					System.out.println("[" + term + ", " + token.TK_TEXT[token.getType()] + "]");
					return token;
				} else {
					setErros(term);
				}
				break;
			case 4: // OPERADORES
				if (currentChar == '=') {
					estado = 5;
					term += currentChar;
				} else if (currentChar == '*') {
					estado = 6;
					term += currentChar;
				} else if (!isChar(currentChar) || isEOF(currentChar)) {
					if (!isEOF(currentChar))
						back();
					token = new Token();
					if(term.equals("+")){
						token.setType(Token.TK_MAIS);
					} else if(term.equals("-")){
						token.setType(Token.TK_MENOS);
					} else if(term.equals("*")){
						token.setType(Token.TK_MAIS);
					} else if(term.equals("/")){
						token.setType(Token.TK_DIV);
					} else if(term.equals("<")){
						token.setType(Token.TK_MENOR);
					} else if(term.equals(">")){
						token.setType(Token.TK_MAIOR);
					} else if(term.equals("=")){
						token.setType(Token.TK_IGUAL);
					} else if(term.equals("!")){
						token.setType(Token.TK_NEGACAO);
					}
					token.setText(term);
					token.setLine(line);
					token.setColumn(column - term.length());

					System.out.println("[" + term + ", " + token.TK_TEXT[token.getType()] + "]");
					return token;
				} else {
					setErros(term);
				}
				break;
			case 5: // OPERADORES DE COMPARAÇÃO
				boolean lAux2 = true;
				if (!isChar(currentChar) || isEOF(currentChar)) {
					if (!isEOF(currentChar)){
						back();
					}
					if(lAux2 == true){
						token = new Token();
						if(term.equals("!=")){
							token.setType(Token.TK_COMP_DIF);
						} else if(term.equals("==")){
							token.setType(Token.TK_COMP_IGUAL);
						}
						token.setText(term);
						token.setLine(line);
						token.setColumn(column - term.length());
						System.out.println("[" + term + ", " + token.TK_TEXT[token.getType()] + "]");
						return token;
					}
				} else {
					setErros(term);
				}
				break;
			case 6: // COMENTARIOS
				if(contComent < 2){
					if (currentChar == '*'){
						estado = 6;
						term += currentChar;
						contComent = 1;
					} else if (currentChar == '/'){
						estado = 6;
						term += currentChar;
						if(contComent == 1){
							contComent = 2;
						}
					} else if (isChar(currentChar) || isDigit(currentChar) || isUnderline(currentChar)) {
						estado = 6;
						term += currentChar;
						contComent = 0;
					}
				} else {
					if (!isChar(currentChar) || isEOF(currentChar)) {
						if (!isEOF(currentChar))
							back();
						token = new Token();
						token.setType(Token.TK_COMENT);
						token.setText(term);
						token.setLine(line);
						token.setColumn(column - term.length());
						System.out.println("[" + term + ", " + token.TK_TEXT[token.getType()] + "]");
						return token;
					} else {
						setErros(term);
					}
				}
				break;
			}		
		}		
	}

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	private boolean isChar(char c) {
		return (c >= 'a' && c <= 'z') || (c>='A' && c <= 'Z');
	}

	private boolean isUnderline(char c) {
		return c == '_';
	}
	
	private boolean isOperator(char c) {
		return c == '>' || c == '<' || c == '!' || c == '=' || c == '+' || c == '-' || c == '*' || c == '/';
	}

	private boolean isBlock(char c) {
		return c == '(' || c == ')' || c == '{' || c == '}';
	}

	private boolean isSpace(char c) {
		if (c == '\n') {
			line++;
			column=0;
		}
		return c == ' ' || c == '\t' || c == '\n' || c == '\r'; 
	}
	
	private char nextChar() {
		if (isEOF()) {
			return '\0';
		}
		return content[pos++];
	}
	private boolean isEOF() {
		return pos >= content.length;
	}
	
    private void back() {
    	pos--;
    	column--;
    }
    
    private boolean isEOF(char c) {
    	return c == '\0';
    }
	
	public void setErros(String term){
		this.aErros[nCont] = "Linha: " + line + " Coluna: " + column + ", não valido simbolo: " + term;
		this.nCont++;
	}

	public void getErros(){
		System.out.println("------LISTA DE ERROS LEXICOS------");
		for(int i=0 ; i < aErros.length ; i++){
			if(aErros[i] != null){
				System.out.println(aErros[i]);
			}
		};
		System.out.println("----------------------------------");
	}	
}