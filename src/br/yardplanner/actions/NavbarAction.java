package br.yardplanner.actions;

import org.apache.struts2.convention.annotation.* ;

import br.yardplanner.model.User;
import br.yardplanner.util.YardPlannerSession;

/**
 * Actions da barra de navega��o superior
 * @author Alisson Perez
 */
public class NavbarAction {
	
	/**
	 * Determina se o usu�rio est� logado
	 */
	private boolean logged ;
	
	/**
	 * Usu�rio logado atualmente
	 */
	private User user ;
	
	/**
	 * Action principal da barra de navega��o.
	 * @return
	 */
	@Action(
		results = {
			@Result( name = "ok" , location = "navbar.inc.jsp" )
		}
	)
	public String execute() {
		this.logged = YardPlannerSession.isLogged() ;
		
		if ( this.logged ) {
			this.user = YardPlannerSession.getUser() ;
		}
		
		return "ok" ;
	}
	
	/**
	 * @return Retorna true se o usu�rio est� logado no sistema
	 */
	public boolean isLogged() {
		return this.logged ;
	}

	/**
	 * @return Retorna o usu�rio logado atualmente no sistema
	 */
	public User getUser() {
		return user;
	}
}
