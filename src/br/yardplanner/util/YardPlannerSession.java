package br.yardplanner.util;

import com.opensymphony.xwork2.ActionContext;

import br.yardplanner.model.User;

/**
 * Classe que gerencia a sess�o do usu�rio
 * @author Alisson
 */
public class YardPlannerSession {
	
	/**
	 * @return Retorna o usu�rio logado atualmente na sess�o, caso n�o encontrado, retorna null; 
	 */
	public static User getUser() {
		User user = (User) ActionContext.getContext().getSession().get( "user_session" ) ;
		
		return user ;
	}
	
	/**
	 * Verifica se o usu�rio esta logado
	 * @return true se o usu�rio estiver logado
	 */
	public static boolean isLogged() {
		return ActionContext.getContext().getSession().containsKey("user_session") ;
	}
	
	/**
	 * Seta um usu�rio na sess�o
	 * @param user para gravar na sess�o
	 */
	public static void setUserSession( User user ) {
		
		ActionContext.getContext().getSession().put( "user_session", user ) ;
		ActionContext.getContext().getSession().put( "user_logged" , "YES" ) ;
		
	}
	
	
}