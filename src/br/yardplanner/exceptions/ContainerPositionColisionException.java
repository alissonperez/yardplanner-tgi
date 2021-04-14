package br.yardplanner.exceptions;

import org.hibernate.annotations.Parent;

/**
 * Colisão de containers em uma posição do bloco
 * @author Alisson
 *
 */
public class ContainerPositionColisionException extends InsertionContainerException {

	/**
	 * Versão para serialização
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
