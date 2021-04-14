package br.yardplanner.actions;

import org.apache.struts2.convention.annotation.* ;

import br.yardplanner.util.Title ;

/**
 * Actions iniciais da aplicação
 * @author Alisson Perez <alissonperez@gmail.com>
 */
public class IndexAction {
	
	/**
	 * Título da página
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
	 * @return Título da página
	 */
	public Title getTitle() {
		return this.title ;
	}

}
