package br.yardplanner.exceptions;

/**
 * Posição informada está fora das extremidades do bloco
 * @author Alisson
 */
public class PositionOnOutsideException extends InsertionContainerException {

	private static final long serialVersionUID = 1L ;

	/**
	 * Construtor sem passagem de mensagem
	 */
	public PositionOnOutsideException() {
		super() ;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Construtor que recebe uma mensagem do erro.
	 * @param message Mensagem do erro
	 */
	public PositionOnOutsideException(String message) {
		super(message) ;
		// TODO Auto-generated constructor stub
	}

}