package br.yardplanner.actions.usuario;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.* ;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.*;

import br.yardplanner.dao.UserDAO;
import br.yardplanner.model.User ;
import br.yardplanner.util.Title;

/**
 * Actions referentes ao registro de um novo usu·rio
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
	 * TÌtulo da p·gina
	 */
	private Title title ;
	
	/**
	 * Usu·rio populado pelo formul·rio
	 */
	private User user ;
	
	/**
	 * Senha de confirmaÁ„o do usu·rio
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
	 * Action respons·vel por exibir a p·gina de registro e gravar o novo usu·rio no banco.<br>
	 * As validaÁıes s„o feitas pelo mÈtodo validate.
	 */
	@Action( value = "registro" , results = {
		@Result( name = "ok" , location = "registro.jsp" ) ,
		@Result( name = "success" , location = "dados_sucesso.jsp" ) ,
	} )
	public String execute() {
		log.debug("Entrando na action") ;
		log.debug( this.user ) ;
		log.debug( this.passwordConfirmation ) ;
		
		this.title.add( "Novo usu·rio" ) ;
		
		if ( this.user != null ) {
			UserDAO userDao = new UserDAO();
			
			// Alterando a senha do usuário para MD5
			this.user.setPasswordMD5( this.user.getPassword() ) ;
			
			// Setando como n„o bloqueado e n„o administrador
			this.user.setBlocked( false ) ;
			this.user.setRoot( false ) ;
			
			userDao.save( this.user ) ;
			
			log.debug("Usuario registrado: " + this.user ) ;
			
			return "success" ;
		}
		
		return "ok" ;
	}

	/**
	 * Faz as devidas verificaÁıes no cadastro do usu·rio.<br>
	 * S„o feitas validaÁıes alÈm das que est„o no model do mesmo {@see User}
	 * Valida:
	 * <ul>
	 * <li>E-mail j· existe</li>
	 * <li>Senha e confirmaÁ„o diferentes</li>
	 * <li>Senha em branco</li>
	 * <li>ConfirmaÁ„o de senha em branco</li>
	 * </ul> 
	 */
	@Override
	public void validate() {
		UserDAO userDao = new UserDAO();
		
		log.trace("Validando usuario") ;
		
		// Validar se o e-mail já· existe
		if ( this.user != null && userDao.emailExists(user) ) {
			super.addFieldError( "user.email" , "E-mail '" + user.getEmail() + "' j· cadastrado" ) ;
		}
		
		if ( this.user != null ) {
			log.debug("Senha: " + this.user.getPassword() ) ;
			log.debug("Confirmação: " + this.passwordConfirmation ) ;
			
			log.debug("Diferentes?: " + ( ! this.user.getPassword().equals(this.passwordConfirmation) ) ) ;			
		}
		
		if ( this.user != null && this.user.getPassword().isEmpty() ) {
			super.addFieldError( "user.password", "Senha em branco" ) ;
		}
		
		if ( this.passwordConfirmation != null && this.passwordConfirmation.isEmpty() ) {
			super.addFieldError( "user.password", "ConfirmaÁ„o de senha em branco" ) ;
		}
		
		// Validar se as senhas digitadas correspodem-se
		if ( this.user != null && this.user.getPassword() != null && ! this.user.getPassword().isEmpty() && ! this.user.getPassword().equals( this.passwordConfirmation ) ) {
			super.addFieldError( "user.password", "Senha e ConfirmaÁ„ço diferentes" ) ;
		}
	}
	
	/**
	 * @return Retorna o tÌtulo da p·gina
	 */
	public Title getTitle() {
		return title;
	}

	/**
	 * @return Retorna o usu·rio preenchido pelo formul·rio de cadastro
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Seta o usu·rio preenchido pelo formul·rio e chama as validaÁıes do model do mesmo.
	 * @param user Usu·rio preenchido pelo formul·rio
	 */
	@VisitorFieldValidator( key="" )
	public void setUser( User user ) {
		this.user = user;
	}

	/**
	 * Senta a confirmaÁ„o de senha para posterior comparaÁ„o com a senha digitada pelo usu·rio
	 * @param passwordConfirmation A confirmaÁ„o de senha digitada no formul·rio pelo usu·rio
	 */
	public void setPasswordConfirmation( String passwordConfirmation ) {
		this.passwordConfirmation = passwordConfirmation;
	}

	/**
	 * Determina se a p·gina È de registro para exibir o formul·rio correto.
	 * @return Retorna true para a p·gina carregar as informaÁıes referentes ao registro do usu·rio
	 */
	public boolean isRegister() {
		return true ;
	}

}
