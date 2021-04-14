package br.yardplanner.actions.usuario;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.* ;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.*;

import br.yardplanner.dao.UserDAO;
import br.yardplanner.model.User ;
import br.yardplanner.util.Title;

/**
 * Actions referentes ao registro de um novo usu�rio
 * @author Alisson
 *
 */
@Validations
public class RegistroAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia da classe Logger
	 */
	private Logger log ;
	
	/**
	 * T�tulo da p�gina
	 */
	private Title title ;
	
	/**
	 * Usu�rio populado pelo formul�rio
	 */
	private User user ;
	
	/**
	 * Senha de confirma��o do usu�rio
	 */
	private String passwordConfirmation ;
	
	/**
	 * Construtor
	 */
	public RegistroAction() {
		this.title = new Title() ;
		this.log = Logger.getLogger( RegistroAction.class.getName() ) ;
	}

	/**
	 * Action respons�vel por exibir a p�gina de registro e gravar o novo usu�rio no banco.<br>
	 * As valida��es s�o feitas pelo m�todo validate.
	 */
	@Action( value = "registro" , results = {
		@Result( name = "ok" , location = "registro.jsp" ) ,
		@Result( name = "success" , location = "dados_sucesso.jsp" ) ,
	} )
	public String execute() {
		log.debug("Entrando na action") ;
		log.debug( this.user ) ;
		log.debug( this.passwordConfirmation ) ;
		
		this.title.add( "Novo usu�rio" ) ;
		
		if ( this.user != null ) {
			UserDAO userDao = new UserDAO();
			
			// Alterando a senha do usu�rio para MD5
			this.user.setPasswordMD5( this.user.getPassword() ) ;
			
			// Setando como n�o bloqueado e n�o administrador
			this.user.setBlocked( false ) ;
			this.user.setRoot( false ) ;
			
			userDao.save( this.user ) ;
			
			log.debug("Usuario registrado: " + this.user ) ;
			
			return "success" ;
		}
		
		return "ok" ;
	}

	/**
	 * Faz as devidas verifica��es no cadastro do usu�rio.<br>
	 * S�o feitas valida��es al�m das que est�o no model do mesmo {@see User}
	 * Valida:
	 * <ul>
	 * <li>E-mail j� existe</li>
	 * <li>Senha e confirma��o diferentes</li>
	 * <li>Senha em branco</li>
	 * <li>Confirma��o de senha em branco</li>
	 * </ul> 
	 */
	@Override
	public void validate() {
		UserDAO userDao = new UserDAO();
		
		log.trace("Validando usuario") ;
		
		// Validar se o e-mail j�� existe
		if ( this.user != null && userDao.emailExists(user) ) {
			super.addFieldError( "user.email" , "E-mail '" + user.getEmail() + "' j� cadastrado" ) ;
		}
		
		if ( this.user != null ) {
			log.debug("Senha: " + this.user.getPassword() ) ;
			log.debug("Confirma��o: " + this.passwordConfirmation ) ;
			
			log.debug("Diferentes?: " + ( ! this.user.getPassword().equals(this.passwordConfirmation) ) ) ;			
		}
		
		if ( this.user != null && this.user.getPassword().isEmpty() ) {
			super.addFieldError( "user.password", "Senha em branco" ) ;
		}
		
		if ( this.passwordConfirmation != null && this.passwordConfirmation.isEmpty() ) {
			super.addFieldError( "user.password", "Confirma��o de senha em branco" ) ;
		}
		
		// Validar se as senhas digitadas correspodem-se
		if ( this.user != null && this.user.getPassword() != null && ! this.user.getPassword().isEmpty() && ! this.user.getPassword().equals( this.passwordConfirmation ) ) {
			super.addFieldError( "user.password", "Senha e Confirma��o diferentes" ) ;
		}
	}
	
	/**
	 * @return Retorna o t�tulo da p�gina
	 */
	public Title getTitle() {
		return title;
	}

	/**
	 * @return Retorna o usu�rio preenchido pelo formul�rio de cadastro
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Seta o usu�rio preenchido pelo formul�rio e chama as valida��es do model do mesmo.
	 * @param user Usu�rio preenchido pelo formul�rio
	 */
	@VisitorFieldValidator( key="" )
	public void setUser( User user ) {
		this.user = user;
	}

	/**
	 * Senta a confirma��o de senha para posterior compara��o com a senha digitada pelo usu�rio
	 * @param passwordConfirmation A confirma��o de senha digitada no formul�rio pelo usu�rio
	 */
	public void setPasswordConfirmation( String passwordConfirmation ) {
		this.passwordConfirmation = passwordConfirmation;
	}

	/**
	 * Determina se a p�gina � de registro para exibir o formul�rio correto.
	 * @return Retorna true para a p�gina carregar as informa��es referentes ao registro do usu�rio
	 */
	public boolean isRegister() {
		return true ;
	}

}
