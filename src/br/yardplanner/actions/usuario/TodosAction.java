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
 * Action referente á listagem de todos os usuários e a edição das permissões dos mesmos feitas pelo administrador<br>
 * <b>OBS: Páginas disponíveis apenas para administradores.</b>
 * 
 * @author Alisson
 */
@ParentPackage("default")
public class TodosAction extends ActionSupport {
	
	/**
	 * InstIância da Logger
	 */
	private Logger log ;
	
	/**
	 * Titulo da página
	 */
	private Title title ;
	
	/**
	 * Lista de usuários
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
	 * Dao do usuário
	 */
	private UserDAO userDAO ;
	
	/**
	 * Botao gravar
	 */
	private String sendBtn ;
	
	/**
	 * Botão cancelar
	 */
	private String cancelBtn ;
	
	/**
	 * Mensagem para o usuário
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
	 * Action que exibe todos os usuários do sistema
	 * @todo Incluír paginação na lista de usuários, atualmente não existe
	 */
	@Action(
		results = {
			@Result( name = "ok" , location = "todos.jsp" )
		} ,
		interceptorRefs = @InterceptorRef( "root_access" )
	)
	public String execute() {
		this.title.add( "Usuários" ) ;
		
		this.users = this.userDAO.getAll() ;
		
		return "ok" ;
	}
	
	/**
	 * Edição de um usuário no sistema.<br>
	 * Só é permitida a alteração das seguintes opções:
	 * <ul><li><b>Administrador</b> Determina que o usuário pode visualizar os usuários do sistema e alterar as suas configurações de acesso e nível</li>
	 * <li><b>Bloqueado</b> Determina que se o usuário está ou não bloqueado para acessar o sistema.</li>
	 * </ul>
	 * 
	 * Caso o usuário não seja encontrado, é feito um redirecionamento para a lista de usuários.
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
		
		this.title.add( "Usuários" ) ;
		this.title.add( userInstance.getFirstName() + " " + userInstance.getLastName() ) ;
		
		User systemUser = YardPlannerSession.getUser() ;
		
		this.log.info( "Usuário " + systemUser.getUserId() + " do sistema editando usuário " + userInstance.getUserId() ) ;
		
		if ( this.sendBtn != null ) {
			userInstance.setRoot( this.user.isRoot() ) ;
			userInstance.setBlocked( this.user.isBlocked() ) ;
			
			this.log.info( "Usuário do sistema " + systemUser.getUserId() + " gravando alterações no usuário " + userInstance.getUserId() ) ;
			this.log.info( "Usuário do sistema " + systemUser.getUserId() + ", alterando usuário " + userInstance.getUserId() + " com a opção de root para: " + userInstance.isRoot() ) ;
			this.log.info( "Usuário do sistema " + systemUser.getUserId() + ", alterando usuário " + userInstance.getUserId() + " com a opção de bloqueado para: " + userInstance.isBlocked() ) ;
			
			this.userDAO.save( userInstance ) ;
			
			this.message = new Message( "Usuário salvo com sucesso" , "success" ) ;
		}
		
		return "ok" ;
	}

	/**
	 * @return Retorna uma lista de usuários do sistema obtidos do banco.
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @return Retorna o título da página
	 */
	public Title getTitle() {
		return title;
	}

	/**
	 * @return Retorna o usuário sendo visualizado no momento
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Seta um usuário populado pelo formulário.
	 * @param user Usuário populado pelo formulário
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Seta o id do usuário que deseja-se editar.
	 * @param userId Id do usuário para visuazação e edição das permissões do mesmmo.
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * Seta o botão cancelar caso tenha sido clicado.
	 * @param cancelBtn Botão cancelar que foi clicado, caso seja.
	 */
	public void setCancelBtn(String cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	/**
	 * Seta o botão gravar caso o mesmo tenha sido clicado.
	 * @param sendBtn Botão gravar que foi clicado, caso seja.
	 */
	public void setSendBtn(String sendBtn) {
		this.sendBtn = sendBtn;
	}

	/**
	 * @return Retorna uma mensagem para exibição na página.
	 */
	public Message getMessage() {
		return message;
	}
	
}
