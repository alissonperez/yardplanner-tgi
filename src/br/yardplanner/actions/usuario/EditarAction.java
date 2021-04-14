package br.yardplanner.actions.usuario;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import br.yardplanner.dao.UserDAO;
import br.yardplanner.model.User;
import br.yardplanner.util.Title;
import br.yardplanner.util.Util;
import br.yardplanner.util.YardPlannerSession;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * Actions da edi��o do usu�rio
 * @author Alisson
 *
 */
@ParentPackage("default")
@Validations 
public class EditarAction extends ActionSupport {
	
	/**
	 * Instancia da classe logger
	 */
	private Logger log ;
	
	/**
	 * Usu�rio sendo editado
	 */
	private User user ;
	
	/**
	 * T�tulo da p�gina
	 */
	private Title title ;
	
	/**
	 * Senha de confirma��o do usu�rio<br>
	 * Posteriormente ser� comparada com a senha informada para o usu�rio.
	 */
	private String passwordConfirmation ;
	
	/**
	 * Senha antiga do usu�rio
	 */
	private String oldPassword ;
	
	/**
	 * Construtor
	 */
	public EditarAction() {
		this.title = new Title() ;
		this.log = Logger.getLogger( EditarAction.class.getName() ) ;
	}
	
	/**
	 * Action principal, carrega a tela com o formul�rio para a edi��o do usu�rio
	 * @return Nome do resultado para carregar a view
	 */
	@Action(
		results = {
			@Result( name = "ok" , location = "editar.jsp" ) ,
			@Result( name = "success" , location = "dados_sucesso.jsp" ) ,
		} ,
		interceptorRefs = @InterceptorRef( "autenticate" )
	)
	public String execute() {
		log.debug("Entrando na action EDICAO") ;
		log.debug( this.user ) ;
		log.debug( this.passwordConfirmation ) ;
		
		this.title.add( "Meus dados" ) ;
		
		if ( this.user == null ) {
			this.user = YardPlannerSession.getUser() ;
		}
		else {
			UserDAO userDao = new UserDAO() ;
			User originalUser = userDao.getById( YardPlannerSession.getUser().getUserId() ) ;
			
			originalUser.setFirstName( this.user.getFirstName() ) ;
			originalUser.setLastName( this.user.getLastName() ) ;
			originalUser.setEmail( this.user.getEmail() ) ;
			
			if ( this.oldPassword != null && ! this.oldPassword.isEmpty() ) {
				log.trace( "Alterando a senha do usu�rio" ) ;
				
				// Alterando a senha do usu�rio para MD5
				originalUser.setPasswordMD5( this.user.getPassword() ) ;				
			}
			YardPlannerSession.setUserSession( originalUser ) ;
			userDao.update( originalUser ) ;
			
			log.debug( "Usuario atualizado: " + this.user ) ;
			
			return "success" ;
		}
		
		return "ok" ;
	}
	
	/**
	 * Faz as valida��es das entradas do usu�rio<br>
	 * Verifica:
	 * <ul><li>Se a senha antiga est� correta</li>
	 * <li>Se a senha nova bate com a confirma��o da mesma</li>
	 * <li>Se o e-mail do usu�rio j� n�o est� em uso no sistema</li>
	 * </ul>
	 */
	@Override
	public void validate() {
		UserDAO userDao = new UserDAO() ;
		User originalUser = YardPlannerSession.getUser() ;
		
		if ( this.user != null ) {
			this.user.setUserId( originalUser.getUserId() ) ;			
		}
		
		log.trace("Validando usuario") ;
		
		// Validar se o e-mail j� existe
		if ( this.user != null && userDao.emailExists(user) ) {
			super.addFieldError( "user.email" , "E-mail '" + user.getEmail() + "' j� cadastrado" ) ;
		}
		
		if ( this.user != null ) {
			log.debug("Senha: " + this.user.getPassword() ) ;
			log.debug("Confirma��o: " + this.passwordConfirmation ) ;
			
			log.debug("Diferentes?: " + ( ! this.user.getPassword().equals( this.passwordConfirmation ) ) ) ;			
		}
		
		log.debug( "Senha antiga: " + this.oldPassword ) ;
		
		// Validar se a senha antiga digitada est� correta
		if ( this.oldPassword != null && this.user != null && ! this.oldPassword.isEmpty() ) {
			String oldPasswordMD5 = Util.MD5( this.oldPassword ) ;
			
			log.trace( "Senha antiga e digitada: " + oldPasswordMD5 + " - " + originalUser.getPassword() ) ;
			
			if ( ! oldPasswordMD5.equals( originalUser.getPassword() ) ) {
				super.addFieldError( "oldPassword", "Senha atual n�o corresponde � digitada" ) ;
			}
			else {
				
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
		}
	}
	
	/**
	 * @return Retorna o usu�rio populado pelo form
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Seta um usu�rio populado pelo form e faz as devidas valida��es do model do usu�rio {@see User} 
	 * @param user Usu�rio populado pelo form
	 */
	@VisitorFieldValidator( key="" )
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return Retorna o t�tulo da p�gina
	 */
	public Title getTitle() {
		return title;
	}
	
	/**
	 * Seta a senha de confirma��o
	 * @param passwordConfirmation senha de confirma��o informada pelo usu�rio no form
	 */
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	
	/**
	 * Seta a senha antiga informada pelo usu�rio
	 * @param oldPassword Senha antiga informada pelo usu�rio no form
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * Caso o usu�rio esteja registrado, � uma tela de edi��o de cadastro.
	 * Caso n�o, � a tela de registro.
	 * @return Retorna se trata-se de um registro ou da edi��o do usu�rio j� existente.
	 */
	public Boolean isRegister() {
		return false ;
	}
	
}
