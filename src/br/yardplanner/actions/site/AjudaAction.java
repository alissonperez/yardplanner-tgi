package br.yardplanner.actions.site;

import org.apache.struts2.convention.annotation.Action ;
import org.apache.struts2.convention.annotation.Result ;

import br.yardplanner.util.Title;

/**
 * Action da p‡gina de ajuda do site
 * @author Alisson
 *
 */
public class AjudaAction {
	
	private Title title ;
	
	/**
	 * Construtor
	 */
	public AjudaAction() {
		this.title = new Title() ;
	}
	
	/**
	 * P‡gina inicial da p‡gina de ajuda
	 * @return
	 */
	@Action(
		results = {
			@Result( name = "ok" , location = "ajuda.jsp" )
		}
	)
	public String execute() {
		this.title.add( "Ajuda" ) ;
		return "ok" ;
	}

	/**
	 * @return O t’tulo da p‡gina
	 */
	public Title getTitle() {
		return title;
	}	
}
