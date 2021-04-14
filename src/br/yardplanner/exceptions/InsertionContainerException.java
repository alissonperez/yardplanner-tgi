package br.yardplanner.exceptions;

/**
 * Erro na inserção de um container.<br>
 * Classe mãe para problemas mais específicos na inserção de containers
 * @author Alisson
 *
 */
public class InsertionContainerException extends Exception {

	/**
	 * Construtor sem passagem de mensagem
	 */
	public InsertionContainerException() {
		super();
	}
	
	/**
	 * Construro com passagem de mensagem e causa
	 * @param message Mensagem
	 * @param cause Causa
	 */
	public InsertionContainerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Construtor que recebe uma mensagem como parametro
	 * @param message Mensagem
	 */
	public InsertionContainerException(String message) {
		super(message);
	}
	
	/**
	 * Construtor que recebe uma causa como parametro
	 * @param cause Causa
	 */
	public InsertionContainerException(Throwable cause) {
		super(cause);
	}
	
}