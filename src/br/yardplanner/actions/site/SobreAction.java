package br.yardplanner.actions.site;

import org.apache.struts2.convention.annotation.Action ;
import org.apache.struts2.convention.annotation.Result ;

import br.yardplanner.util.Title;

/**
 * Action da p‡gina "Sobre"
 * @author Alisson
 *
 */
public class SobreAction {
	
	private Title title ;
	
	/**
	 * Construtor
	 */
	public SobreAction() {
		this.title = new Title() ;
	}
	
	/**
	 * P‡gina inicial da p‡gina sobre
	 * @return
	 */
	@Action(
		results = {
			@Result( name = "ok" , location = "sobre.jsp" )
		}
	)
	public String execute() {
		this.title.add( "Sobre" ) ;
		return "ok" ;
	}

	/**
	 * @return O t’tulo da p‡gina
	 */
	public Title getTitle() {
		return title;
	}	
}
