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
 * Actions da minpulação dos pátios
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
	 * Lista de pátios
	 */
	private Set<Yard> yards ;
	
	/**
	 * Título da página
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
	 * Obtem os pátios do usuário logado no sistema
	 */
	private void getYardsFromUser() {
		User user = YardPlannerSession.getUser() ;
		
		UserDAO userDao = new UserDAO() ;
		userDao.refresh(user) ;
		
		this.yards = user.getYards() ;		
	}
	
	/**
	 * Mostrar os pátios que o usuário possui
	 * 
	 * @return Retorna o nome do resultado para carregar a action
	 */
	@Action(
		results = { @Result( name = "ok" , location = "index.jsp" ) } ,
		interceptorRefs = @InterceptorRef("autenticate")
	)
	public String execute() {
		this.title.add( "Pátios" ) ;
		this.getYardsFromUser() ;
		return "ok" ;
	}
	
	/**
	 * Action para a criação de um novo pátio
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
		this.title.add( "Pátios" ) ;
		
		this.getYardsFromUser() ;
		
		if ( this.yard != null ) {
			
			log.debug("Nome do Pátio" + this.yard.getName()) ;
			
			System.out.println( "#PATIO pátio :" + this.yard.getName() ) ;
			
			// Dono do p‡tio
			User user = YardPlannerSession.getUser() ;
			log.trace(user) ;
			
			this.yard.setOwner( user ) ;
			
			log.trace("Salvando um p‡tio") ;
			
			YardDAO yardDao = new YardDAO() ;
			yardDao.save( this.yard ) ;
			
			log.trace( "p‡tio salvo.. " + this.yard.getYardId() ) ;
			
			return "ok" ;
		}
		
		log.trace("Finalizando novo") ;
		
		return "ok" ;
	}
	
	/**
	 * Valida a entrada de um novo pátio<br>
	 * Não há implementação de códigos pois a unica validação é feita no model do Pátio (valida o nome)
	 */
	public void validate() {
		
		// Instanciando o patio atual
		this.getYardsFromUser() ;
		
	}
	
	/**
	 * @return Retorna o pátio atual populado pelo formulário
	 */
	public Yard getYard() {
		return yard;
	}

	/**
	 * @param yard Seta o pátio populado pelo formulário
	 */
	@VisitorFieldValidator(key="")
	public void setYard(Yard yard) {
		this.yard = yard;
	}

	/**
	 * @return Retorna os pátios do usuário
	 */
	public Set<Yard> getYards() {
		return yards;
	}
	
	/**
	 * @return Retorna o título da página 
	 */
	public Title getTitle() {
		return title;
	}

}