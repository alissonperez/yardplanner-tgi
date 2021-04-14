package br.yardplanner.exceptions;

/**
 * N�o existe um container abaixo do que se deseja inserir.
 * @author Alisson
 */
public class ContainerFloatingException extends InsertionContainerException {

	/**
	 * Vers�o para serializa��o
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor sem passagem de mensagem
	 */
	public ContainerFloatingException() {	
		super() ;
	}

	/**
	 * Construtor que recebe uma mensagem como parametro
	 * @param message Mensagem
	 */
	public ContainerFloatingException( String message ) {
		super( message ) ;
	}
}
