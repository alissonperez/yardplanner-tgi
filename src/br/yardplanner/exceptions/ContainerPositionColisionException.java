package br.yardplanner.exceptions;

import org.hibernate.annotations.Parent;

/**
 * Colis�o de containers em uma posi��o do bloco
 * @author Alisson
 *
 */
public class ContainerPositionColisionException extends InsertionContainerException {

	/**
	 * Vers�o para serializa��o
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtor sem mensagens
	 */
	public ContainerPositionColisionException() {
		super() ;
	}
	
	/**
	 * Construtor que recebe uma mensagem como parametro
	 * @param message
	 */
	public ContainerPositionColisionException( String message ) {
		super( message ) ;
	}
		
}
