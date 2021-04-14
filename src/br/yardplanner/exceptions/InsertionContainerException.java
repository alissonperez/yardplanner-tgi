package br.yardplanner.exceptions;

/**
 * Erro na inser��o de um container.<br>
 * Classe m�e para problemas mais espec�ficos na inser��o de containers
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