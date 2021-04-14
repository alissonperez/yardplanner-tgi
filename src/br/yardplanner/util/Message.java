package br.yardplanner.util;

/**
 * Classe responsável por comunicar uma mensagem à view
 * Exemplo: <pre>
 * Message msg = new Message( "Alterações salvas com sucesso" , "success" ) ;
 * </pre>
 * Opções para o level (seguindo o padão de classes CSS do bootstrap):
 * <ul>
 * <li><b>error</b>: Erro.</li>
 * <li><b>success</b>: Sucesso.</li>
 * <li><b>info</b>: Informativo.</li>
 * <li><b>block</b>: Alerta normal (pode ser com o atributo level vazio).</li>
 * </ul>
 * 
 * @author Alisson
 */
public class Message {
	
	/**
	 * Mensagem
	 */
	private String message ;
	
	
	private String level ;

	/**
	 * Mensagem e level da mesma, de acordo com o bootstrap, o level pode ser:
	 * <ul>
	 * <li><b>error</b>: Erro.</li>
	 * <li><b>success</b>: Sucesso.</li>
	 * <li><b>info</b>: Informativo.</li>
	 * <li><b>block</b>: Alerta normal (pode ser com o atributo level vazio).</li>
	 * </ul>
	 */
	public Message(String message, String level) {
		super();
		this.message = message;
		this.level = level;
	}

	/**
	 * @return Mensagem
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message Mensagem desejada
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Level da mensagem
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level Level da mensagem
	 */
	public void setLevel(String level) {
		this.level = level;
	}

}