package br.yardplanner.actions.usuario;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.*;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validations;

import br.yardplanner.dao.UserDAO;
import br.yardplanner.exceptions.UserBlockedException;
import br.yardplanner.exceptions.userNotFoundException;
import br.yardplanner.model.User;
import br.yardplanner.util.Title;
import br.yardplanner.util.YardPlannerSession;

/**
 * Actions referentes ao login do usu�rio
 * @author Alisson
 *
 */
@Validations
public class LoginAction extends ActionSupport {
	
	/**
	 * Instancia da classe Logger
	 */
	private Logger log ;
	
	/**
	 * Vers�o da Classe
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * T�tulo da p�gina
	 */
	private Title title ;
	
	/**
	 * Usu�rio que deseja logar-se
	 */
	private User user ;
	
	/**
	 * Construtor da classe
	 */
	public LoginAction() {
		this.log = Logger.getLogger( LoginAction.class.getName() ) ;
		this.log.trace( "Passando pelo construtor da action" ) ;
		
		this.title = new Title() ;
	}
	
	/**
	 * Action principal, referente ao login do usu�rio.<br>
	 * Se o usu�rio passou nas valida��es, apenas guard�-lo na sess�o e redireciona-lo para a index do sistema.
	 */
	@Action( value = "login" , results = {
		@Result( name = "ok" , location = "login.jsp" ) ,
		@Result( name = "input" , location = "login.jsp" ) ,
		@Result( name = "logged" , type = "redirectAction" , params = { "actionName" , "index" , "namespace" , "/" } )
	} )
	public String execute() {
		this.title.add( "Login" ) ;
		
		if ( this.user != null ) {
			
			log.trace("Usu�rio nao nulo") ;
			
			YardPlannerSession.setUserSession( this.user ) ;
			
			return "logged" ;			
		}
		
		return "ok" ;
	}
	
	/**
	 * Action de logout do usu�rio.<br>
	 * Limpa a sess�o do mesmo.
	 */
	@Action( value = "logout" , results = {
		@Result( name = "ok" , location = "login.jsp" )
	})
	public String logout() {
		
		this.log.trace( "Logout do usu�rio" ) ;
		
		this.title.add( "Logout" ) ;
		
		ActionContext.getContext().getSession().clear() ;
		
		return "ok" ;
	}
	
	/**
	 * Valida��es do login do usu�rio<br>
	 * Verifica:
	 * <ul>
	 * <li>Usu�rio foi preenchido corretamente</li>
	 * <li>Usu�rio foi encontrado no banco com os dados fornecidos de e-mail e senha</li>
	 * <li>Usu�rio n�o est� bloqueado no sistema</li>
	 * </ul>
	 */
	@Override
	public void validate() {
		log.trace("Validando usuario") ;
		
		if ( this.user != null ) {
			try {
				
				// Tentando validar o usu�rio
				UserDAO userDao = new UserDAO();
				
				User user_login = userDao.login( this.user ) ;
				
				this.user = user_login ;
				
				log.debug( this.user ) ;
				
			} catch (userNotFoundException e) {
				log.debug( "Usuario nao encontrado..." ) ;
				super.addFieldError("user.email", "Usu�rio n�o encontrado" ) ;
			} catch (UserBlockedException e) {
				log.debug( "Usuario bloqueado..." ) ;
				super.addFieldError("user.email", "Usu�rio bloqueado" ) ;
			}			
		}		
	}

	/**
	 * @return Retorna o t�tulo da p�gina
	 */
	public Title getTitle() {
		return title;
	}

	/**
	 * @return Retorna o usu�rio preenchido pelo formul�rio
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user Usu�rio preenchido no formul�rio
	 */
	public void setUser(User user) {
		this.user = user;
	}	

}
