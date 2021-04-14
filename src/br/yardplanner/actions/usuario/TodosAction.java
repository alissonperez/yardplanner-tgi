package br.yardplanner.actions.usuario;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.*;

import br.yardplanner.util.Message;
import br.yardplanner.util.Title;
import br.yardplanner.util.YardPlannerSession;
import br.yardplanner.dao.UserDAO;
import br.yardplanner.model.User;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action referente � listagem de todos os usu�rios e a edi��o das permiss�es dos mesmos feitas pelo administrador<br>
 * <b>OBS: P�ginas dispon�veis apenas para administradores.</b>
 * 
 * @author Alisson
 */
@ParentPackage("default")
public class TodosAction extends ActionSupport {
	
	/**
	 * InstI�ncia da Logger
	 */
	private Logger log ;
	
	/**
	 * Titulo da p�gina
	 */
	private Title title ;
	
	/**
	 * Lista de usu�rios
	 */
	private List<User> users ;
	
	/**
	 * Id do usuario
	 */
	private Integer userId ;
	
	/**
	 * Usuario
	 */
	private User user ;
	
	/**
	 * Dao do usu�rio
	 */
	private UserDAO userDAO ;
	
	/**
	 * Botao gravar
	 */
	private String sendBtn ;
	
	/**
	 * Bot�o cancelar
	 */
	private String cancelBtn ;
	
	/**
	 * Mensagem para o usu�rio
	 */
	private Message message ;
	
	/**
	 * Construtor
	 */
	public TodosAction() {
		this.title = new Title() ;
		this.userDAO = new UserDAO() ;
		this.log = Logger.getLogger( TodosAction.class.getName() ) ;
	}
	
	/**
	 * Action que exibe todos os usu�rios do sistema
	 * @todo Inclu�r pagina��o na lista de usu�rios, atualmente n�o existe
	 */
	@Action(
		results = {
			@Result( name = "ok" , location = "todos.jsp" )
		} ,
		interceptorRefs = @InterceptorRef( "root_access" )
	)
	public String execute() {
		this.title.add( "Usu�rios" ) ;
		
		this.users = this.userDAO.getAll() ;
		
		return "ok" ;
	}
	
	/**
	 * Edi��o de um usu�rio no sistema.<br>
	 * S� � permitida a altera��o das seguintes op��es:
	 * <ul><li><b>Administrador</b> Determina que o usu�rio pode visualizar os usu�rios do sistema e alterar as suas configura��es de acesso e n�vel</li>
	 * <li><b>Bloqueado</b> Determina que se o usu�rio est� ou n�o bloqueado para acessar o sistema.</li>
	 * </ul>
	 * 
	 * Caso o usu�rio n�o seja encontrado, � feito um redirecionamento para a lista de usu�rios.
	 * @return
	 */
	@Action(
		value = "usuario/{userId}" ,
		results = {
			@Result( name = "ok" , location = "editar_usuario.jsp" ) ,
			@Result( name = "userNotFound" , type = "redirectAction" , params = { "actionName" , "todos" , "namespace" , "/usuario" } ) ,
			@Result( name = "cancel" , type = "redirectAction" , params = { "actionName" , "todos" , "namespace" , "/usuario" } )
		} ,
		interceptorRefs = @InterceptorRef( "root_access" )
	)
	public String editUser() {
		this.message = null ;
		
		if ( this.cancelBtn != null ) {
			return "cancel" ;
		}
		
		if ( this.userId == null ) {
			return "userNotFound" ;
		}
		
		User userInstance = this.userDAO.getById( this.userId ) ;
		
		if ( this.user == null ) {
			this.user = userInstance ;
			
			if ( this.user == null ) {
				return "userNotFound" ;
			}
		}
		
		this.title.add( "Usu�rios" ) ;
		this.title.add( userInstance.getFirstName() + " " + userInstance.getLastName() ) ;
		
		User systemUser = YardPlannerSession.getUser() ;
		
		this.log.info( "Usu�rio " + systemUser.getUserId() + " do sistema editando usu�rio " + userInstance.getUserId() ) ;
		
		if ( this.sendBtn != null ) {
			userInstance.setRoot( this.user.isRoot() ) ;
			userInstance.setBlocked( this.user.isBlocked() ) ;
			
			this.log.info( "Usu�rio do sistema " + systemUser.getUserId() + " gravando altera��es no usu�rio " + userInstance.getUserId() ) ;
			this.log.info( "Usu�rio do sistema " + systemUser.getUserId() + ", alterando usu�rio " + userInstance.getUserId() + " com a op��o de root para: " + userInstance.isRoot() ) ;
			this.log.info( "Usu�rio do sistema " + systemUser.getUserId() + ", alterando usu�rio " + userInstance.getUserId() + " com a op��o de bloqueado para: " + userInstance.isBlocked() ) ;
			
			this.userDAO.save( userInstance ) ;
			
			this.message = new Message( "Usu�rio salvo com sucesso" , "success" ) ;
		}
		
		return "ok" ;
	}

	/**
	 * @return Retorna uma lista de usu�rios do sistema obtidos do banco.
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @return Retorna o t�tulo da p�gina
	 */
	public Title getTitle() {
		return title;
	}

	/**
	 * @return Retorna o usu�rio sendo visualizado no momento
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Seta um usu�rio populado pelo formul�rio.
	 * @param user Usu�rio populado pelo formul�rio
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Seta o id do usu�rio que deseja-se editar.
	 * @param userId Id do usu�rio para visuaza��o e edi��o das permiss�es do mesmmo.
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * Seta o bot�o cancelar caso tenha sido clicado.
	 * @param cancelBtn Bot�o cancelar que foi clicado, caso seja.
	 */
	public void setCancelBtn(String cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	/**
	 * Seta o bot�o gravar caso o mesmo tenha sido clicado.
	 * @param sendBtn Bot�o gravar que foi clicado, caso seja.
	 */
	public void setSendBtn(String sendBtn) {
		this.sendBtn = sendBtn;
	}

	/**
	 * @return Retorna uma mensagem para exibi��o na p�gina.
	 */
	public Message getMessage() {
		return message;
	}
	
}
