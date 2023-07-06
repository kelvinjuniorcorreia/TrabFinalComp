package lexico;

public class Token {
	//Cria tokens para atribuir para as entradas verificadas
	public static final int TK_ID			= 0;
	public static final int TK_DIGITO		= 1;
	public static final int TK_MAIS			= 2;
	public static final int TK_MENOS 		= 3;
	public static final int TK_DIV      	= 4;
	public static final int TK_MULT      	= 5;
	public static final int TK_IGUAL    	= 6;
	public static final int TK_COMP_IGUAL   = 7;
	public static final int TK_COMP_DIF     = 8;
	public static final int TK_AND  		= 9;
	public static final int TK_OR      		= 10;
	public static final int TK_IF    		= 11;
	public static final int TK_ELSE 		= 12;
	public static final int TK_FOR      	= 13;
	public static final int TK_WHILE      	= 14;
	public static final int TK_INT    		= 15;
	public static final int TK_FLOAT     	= 16;
	public static final int TK_PRINT      	= 17;
	public static final int TK_MAIOR  		= 18;
	public static final int TK_MENOR      	= 19;
	public static final int TK_PAR_ABRE    	= 20;
	public static final int TK_PAR_FECHA 	= 21;
	public static final int TK_CHAV_ABRE    = 22;
	public static final int TK_CHAV_FECHA   = 23;
	public static final int TK_COMENT 		= 24;
	public static final int TK_ESPACE 		= 25;
	public static final int TK_NEGACAO 		= 26;
	
	// Tokens são controlados por números, por isso da criação desse vetor para mostrar o nome referente a cada token
	public static final String TK_TEXT[] = {
		"ID", 
		"DIGITO", 
		"+", 
		"-", 
		"/", 
		"*", 
		"=", 
		"==", 
		"!=",
		"AND", 
		"OR", 
		"IF", 
		"ELSE", 
		"FOR", 
		"WHILE", 
		"INT", 
		"FLOAT", 
		"PRINT",
		"MAIOR", 
		"MENOR", 
		"(", 
		")", 
		"{", 
		"}",
		"COMENTARIO",
		"ESPACO",
		"!"
	};
	
	private int    type;
	private String text;
	private int    line;
	private int    column;
	
	public Token(int type, String text) {
		super();
		this.type = type;
		this.text = text;
	}

	public Token() {
		super();
	}

	public int getType() {

		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Token [type=" + type + ", text=" + text + "]";
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
