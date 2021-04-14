package br.yardplanner.util;

import com.opensymphony.xwork2.ActionContext;

import br.yardplanner.model.User;

/**
 * Classe que gerencia a sessão do usuário
 * @author Alisson
 */
public class YardPlannerSession {
	
	/**
	 * @return Retorna o usuário logado atualmente na sessão, caso não encontrado, retorna null; 
	 */
	public static User getUser() {
		User user = (User) ActionContext.getContext().getSession().get( "user_session" ) ;
		
		return user ;
	}
	
	/**
	 * Verifica se o usuário esta logado
	 * @return true se o usuário estiver logado
	 */
	public static boolean isLogged() {
		return ActionContext.getContext().getSession().containsKey("user_session") ;
	}
	
	/**
	 * Seta um usuário na sessão
	 * @param user para gravar na sessão
	 */
	public static void setUserSession( User user ) {
		
		ActionContext.getContext().getSession().put( "user_session", user ) ;
		ActionContext.getContext().getSession().put( "user_logged" , "YES" ) ;
		
	}
	
	
}