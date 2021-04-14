package br.yardplanner.actions.patio;

import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.* ;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import br.yardplanner.dao.UserDAO;
import br.yardplanner.dao.YardDAO;
import br.yardplanner.model.User;
import br.yardplanner.model.Yard;
import br.yardplanner.util.Title;
import br.yardplanner.util.YardPlannerSession;

/**
 * Actions da minpula��o dos p�tios
 * @author Alisson
 *
 */
@ParentPackage("default")
@Validations
public class IndexAction extends ActionSupport {
	
	/**
	 * Instancia da classe de log
	 */
	private Logger log ;
	
	/**
	 * Patio atual
	 */
	private Yard yard ;
	
	/**
	 * Lista de p�tios
	 */
	private Set<Yard> yards ;
	
	/**
	 * T�tulo da p�gina
	 */
	private Title title ;
	
	/**
	 * Construtor
	 */
	public IndexAction() {
		this.title = new Title() ;
		this.log = Logger.getLogger( IndexAction.class.getName() ) ;
	}
	
	/**
	 * Obtem os p�tios do usu�rio logado no sistema
	 */
	private void getYardsFromUser() {
		User user = YardPlannerSession.getUser() ;
		
		UserDAO userDao = new UserDAO() ;
		userDao.refresh(user) ;
		
		this.yards = user.getYards() ;		
	}
	
	/**
	 * Mostrar os p�tios que o usu�rio possui
	 * 
	 * @return Retorna o nome do resultado para carregar a action
	 */
	@Action(
		results = { @Result( name = "ok" , location = "index.jsp" ) } ,
		interceptorRefs = @InterceptorRef("autenticate")
	)
	public String execute() {
		this.title.add( "P�tios" ) ;
		this.getYardsFromUser() ;
		return "ok" ;
	}
	
	/**
	 * Action para a cria��o de um novo p�tio
	 * @return Nome do resultado para carregar a view
	 */
	@Action( value = "novo" ,
		results = {
			@Result( name = "ok" , type = "redirectAction" , params = { "actionName" , "index" } ) ,
			@Result( name = "input" , location = "index.jsp" )
		} ,
		interceptorRefs = @InterceptorRef("autenticate")
	)
	public String novo() {
		this.title.add( "P�tios" ) ;
		
		this.getYardsFromUser() ;
		
		if ( this.yard != null ) {
			
			log.debug("Nome do P�tio" + this.yard.getName()) ;
			
			System.out.println( "#PATIO p�tio :" + this.yard.getName() ) ;
			
			// Dono do p�tio
			User user = YardPlannerSession.getUser() ;
			log.trace(user) ;
			
			this.yard.setOwner( user ) ;
			
			log.trace("Salvando um p�tio") ;
			
			YardDAO yardDao = new YardDAO() ;
			yardDao.save( this.yard ) ;
			
			log.trace( "p�tio salvo.. " + this.yard.getYardId() ) ;
			
			return "ok" ;
		}
		
		log.trace("Finalizando novo") ;
		
		return "ok" ;
	}
	
	/**
	 * Valida a entrada de um novo p�tio<br>
	 * N�o h� implementa��o de c�digos pois a unica valida��o � feita no model do P�tio (valida o nome)
	 */
	public void validate() {
		
		// Instanciando o patio atual
		this.getYardsFromUser() ;
		
	}
	
	/**
	 * @return Retorna o p�tio atual populado pelo formul�rio
	 */
	public Yard getYard() {
		return yard;
	}

	/**
	 * @param yard Seta o p�tio populado pelo formul�rio
	 */
	@VisitorFieldValidator(key="")
	public void setYard(Yard yard) {
		this.yard = yard;
	}

	/**
	 * @return Retorna os p�tios do usu�rio
	 */
	public Set<Yard> getYards() {
		return yards;
	}
	
	/**
	 * @return Retorna o t�tulo da p�gina 
	 */
	public Title getTitle() {
		return title;
	}

}