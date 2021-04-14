package br.yardplanner.actions;

import org.apache.struts2.convention.annotation.* ;

import br.yardplanner.util.Title ;

/**
 * Actions iniciais da aplica��o
 * @author Alisson Perez <alissonperez@gmail.com>
 */
public class IndexAction {
	
	/**
	 * T�tulo da p�gina
	 */
	private Title title ;
	
	/**
	 * Construtor
	 */
	public IndexAction() {
		this.title = new Title() ;
	}
	
	/**
	 * Carrega a index do sistema
	 * 
	 * @return nome do resultado para carregar a view
	 */
	@Action(
		results = {
			@Result( name = "ok" , location = "index.jsp" )
		}
	)
	public String execute() {
		
		return "ok" ;
	}

	/**
	 * @return T�tulo da p�gina
	 */
	public Title getTitle() {
		return this.title ;
	}

}
