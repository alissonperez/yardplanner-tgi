package br.yardplanner.interceptor;


import org.apache.log4j.Logger;
import br.yardplanner.util.YardPlannerSession;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Interceptor do login.<br>
 * Verifica se o usu�rio est� logado, caso n�o, retorna o nome nome do result<br>
 * global do struts para o login.<br><br>
 * <b>Deve ser chamado em todas as p�ginas que exigem autentica��o</b>
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
	 * Init do objeto, aqui � instanciada a classe de log.
	 */
	@Override
	public void init() {
		this.log = Logger.getLogger( LoginInterceptor.class.getName() ) ;
	}

	/**
	 * Verifica se o usu�rio est� logado, caso n�o, retorna o result para o login.
	 * @todo Tentar saber a p�gina que o usu�rio est� tentando acessar para poder redirecion�-lo posteriormente.
	 * @param action Action que o usu�rio est� tentando acessar.
	 */
	@Override
	public String intercept(ActionInvocation action) throws Exception {
		
		this.log.info( "Tentando acessar action " + action.getClass().getName() ) ;
		
		if ( ! YardPlannerSession.isLogged() ) {
			this.log.info( "Login necess�rio" ) ;
			return "login_required" ;
		}
		
		return action.invoke() ;
	}

}
