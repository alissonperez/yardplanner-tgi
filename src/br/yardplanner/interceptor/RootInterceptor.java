package br.yardplanner.interceptor;

import org.apache.log4j.Logger;

import br.yardplanner.model.User;
import br.yardplanner.util.YardPlannerSession;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Interceptor do administrador.<br>
 * Verifica se o usuário é um administrador, caso não, redireciona-o para a index do sistema.<br>
 * <b>OBS: Deve ser chamado após o método de autenticação</b>
 *  
 * @author Alisson
 */
public class RootInterceptor implements Interceptor {

	/**
	 * Instancia da logger
	 */
	private Logger log ;
	
	/**
	 * Construtor do interceptor. 
	 */
	public RootInterceptor() {
		this.log = Logger.getLogger( RootInterceptor.class.getName() ) ;
	}
	
	/**
	 * Destroy do objeto
	 */
	@Override
	public void destroy() {
		
	}

	/**
	 * Init do objeto
	 */
	@Override
	public void init() {
		
	}

	/**
	 * Verifica se o usuário é um administrador, caso não, retorna o result para redirecionamento do mesmo para
	 * a home do sistema.
	 * @param action Action que esta-se tentando acessar.
	 */
	@Override
	public String intercept(ActionInvocation action) throws Exception {
		User user = YardPlannerSession.getUser() ;
		
		if ( ! user.isRoot() ) {
			this.log.info( "Usuário " + user.getUserId() + " bloqueado ao acessar área restrita à administradores" ) ;
			return "root_required" ;
		}
		
		return action.invoke() ;
	}

}
