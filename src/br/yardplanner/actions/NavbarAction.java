package br.yardplanner.actions;

import org.apache.struts2.convention.annotation.* ;

import br.yardplanner.model.User;
import br.yardplanner.util.YardPlannerSession;

/**
 * Actions da barra de navegação superior
 * @author Alisson Perez
 */
public class NavbarAction {
	
	/**
	 * Determina se o usuário está logado
	 */
	private boolean logged ;
	
	/**
	 * Usuário logado atualmente
	 */
	private User user ;
	
	/**
	 * Action principal da barra de navegação.
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
	 * @return Retorna true se o usuário está logado no sistema
	 */
	public boolean isLogged() {
		return this.logged ;
	}

	/**
	 * @return Retorna o usuário logado atualmente no sistema
	 */
	public User getUser() {
		return user;
	}
}
