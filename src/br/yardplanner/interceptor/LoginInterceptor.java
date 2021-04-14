package br.yardplanner.interceptor;


import org.apache.log4j.Logger;
import br.yardplanner.util.YardPlannerSession;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Interceptor do login.<br>
 * Verifica se o usuário está logado, caso não, retorna o nome nome do result<br>
 * global do struts para o login.<br><br>
 * <b>Deve ser chamado em todas as páginas que exigem autenticação</b>
 * 
 * @author Alisson
 */
public class LoginInterceptor implements Interceptor {
	
	/**
	 * Instancia da logger
	 */
	private Logger log ;

	/**
	 * Destroy do objeto
	 */
	@Override
	public void destroy() {
		
	}

	/**
	 * Init do objeto, aqui é instanciada a classe de log.
	 */
	@Override
	public void init() {
		this.log = Logger.getLogger( LoginInterceptor.class.getName() ) ;
	}

	/**
	 * Verifica se o usuário está logado, caso não, retorna o result para o login.
	 * @todo Tentar saber a página que o usuário está tentando acessar para poder redirecioná-lo posteriormente.
	 * @param action Action que o usuário está tentando acessar.
	 */
	@Override
	public String intercept(ActionInvocation action) throws Exception {
		
		this.log.info( "Tentando acessar action " + action.getClass().getName() ) ;
		
		if ( ! YardPlannerSession.isLogged() ) {
			this.log.info( "Login necessário" ) ;
			return "login_required" ;
		}
		
		return action.invoke() ;
	}

}
