package br.yardplanner.actions.bloco;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.* ;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import br.yardplanner.model.Block;
import br.yardplanner.model.ContainerSize;
import br.yardplanner.model.User;
import br.yardplanner.model.Yard;
import br.yardplanner.util.Title;
import br.yardplanner.util.YardPlannerSession;
import br.yardplanner.dao.BlockDAO;
import br.yardplanner.dao.ContainerSizeDAO;
import br.yardplanner.dao.YardDAO;
import br.yardplanner.exceptions.ContainerNotFoundException;
import br.yardplanner.exceptions.YardNotFoundException;

/**
 * Actions da p�gina de visualiza��o dos blocos de um p�tio
 * @author Alisson Perez
 */
@ParentPackage("default")
@Validations
public class IndexAction extends ActionSupport {
	
	/**
	 * Inst�ncia do Logger
	 */
	private Logger log ;
	
	/**
	 * Id do p�tio atual
	 */
	private Integer yardId ;
	
	/**
	 * Inst�ncia do p�tio atual
	 */
	private Yard yard ;
	
	/**
	 * Bloco que ser� populado via fomul�rio para cria��o de um novo bloco
	 */
	private Block block ;
	
	/**
	 * Lista de blocos do p�tio atual para exibi��o
	 */
	private Set<Block> blocks ;
	
	/**
	 * Lista de tamanhos de containers
	 */
	private List<ContainerSize> containerSizes ;
	
	/**
	 * Id populado via formul�rio do tamanho de containers aceitos em um p�tio
	 */
	private Integer containerSizeId ;
	
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
	 * Obtem o p�tio atual do banco de dados dado o id do mesmo vindo da URL da p�gina.
	 */
	private void instaceYard() {
		
		this.yard = null ;
		
		this.blocks = new HashSet<Block>() ;
		
		log.trace("Instanciando Yard") ;
		
		if ( this.yardId != null ) {
			User user = YardPlannerSession.getUser() ;
			
			log.trace("Id nao nulo: " + this.yardId ) ;
			
			try {
				
				YardDAO yardDAO = new YardDAO() ;
				
				Yard yard = yardDAO.getById( this.yardId ) ;
				
				log.trace("Checando dono..") ;
				
				// Verifica o dono do p�tio
				if ( yard.getOwner().getUserId() == user.getUserId() ) {
					
					// Finalmente setando p�tio
					this.yard = yard ;
					
					// Populando os blocos
					this.blocks = this.yard.getBlocks() ;
					
				}
				
			} catch (YardNotFoundException e) {
				log.trace("Yard nao encontrado..") ;
			}
		}
		
	}
	
	/**
	 * Action default da classe.<br>
	 * � passado o id do p�tio na url e mapeado na classe.
	 * 
	 * @return Nome do result para a view
	 */
	@Action(
		value = "index/{yardId}" ,
		results = {
			@Result( name = "ok" , location = "index.jsp" ) ,
			@Result( name = "erro" , type = "redirectAction" , params = { "actionName" , "index" , "namespace" , "/patio" } ) 
		},
		interceptorRefs = @InterceptorRef("autenticate")
	)
	public String execute() {
		// Instancia o p�tio atual
		this.instaceYard() ;
		
		// Incluindo o t�tulo da p�gina
		this.title.add( this.yard.getName() ) ;
		this.title.add( "Blocos" ) ;
		
		this.containerSizes = (new ContainerSizeDAO()).getAll() ;
		
		log.trace("Vendo se yard e null") ;
		
		if ( this.yard == null ) {
			log.trace("Erro no Yard..." ) ;
			return "erro" ;
		}
		
		return "ok" ;
	}
	
	/**
	 * Action para a cria��o de um novo bloco em um p�tio (yardID).
	 * @return Nome do result para a view
	 */
	@Action(
		value = "novo/{yardId}" ,
		results = {
			@Result( name = "ok" , type = "redirectAction" , params = { "actionName" , "index/${yardId}" } ) ,
			@Result( name = "input" , location = "index.jsp" ) ,
			@Result( name = "erro" , type = "redirectAction" , params = { "actionName" , "index" , "namespace" , "/patio" } )
		} , 
		interceptorRefs = @InterceptorRef("autenticate")
	)
	public String novo() {
		this.containerSizes = (new ContainerSizeDAO()).getAll() ;
		
		// Instanciando o patio atual
		this.instaceYard() ;
		
		if ( this.yard == null ) {
			log.trace("Yard nulo" ) ;
			return "erro" ;
		}
		
		log.debug("Numero de blocks:" + this.blocks.size()) ;
		
		log.debug("Patio agora: " + this.yard.getName() ) ;
		
		if ( this.block != null ) {
			
			try {
				this.block.setContainerSize((new ContainerSizeDAO().getById(this.containerSizeId))) ;			
			}
			catch(ContainerNotFoundException e) {
				
			}
			
			this.block.setYard( this.yard ) ;
			
			BlockDAO blockDAO = new BlockDAO() ;
			
			blockDAO.save(this.block) ;			
		}
		
		return "ok" ;		
	}
	
	/**
	 * Verifica��o dos dados preenchidos no formul�rio para a cria��o de um novo Bloco
	 */
	public void validate() {
		
		// Instanciando o patio atual
		this.instaceYard() ;
		
		this.containerSizes = (new ContainerSizeDAO()).getAll() ;
		
		if ( this.containerSizeId != null ) {
			if ( this.containerSizeId == -1 ) {
				super.addFieldError( "containerSizeId", "� necess�rio informar o tamanho dos containers" ) ;
			}
			else if ( ! (new ContainerSizeDAO()).ContainerSizeIdExists( this.containerSizeId ) ) {
				super.addFieldError( "containerSizeId" , "Tamanho de container n�o encontrado") ;
			}
		}
		
	}

	/**
	 * @return Retorna o id do p�tio
	 */
	public Integer getYardId() {
		return yardId;
	}

	/**
	 * Seta o id do p�tio atual.<br>
	 * � populado pelo struts ao carregar a URL
	 * 
	 * @param yardId Id do p�tio atual
	 */
	public void setYardId( Integer yardId ) {
		this.yardId = yardId;
	}

	/**
	 * @return Retorna o bloco que foi populado pelo usu�rio no formul�rio
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * Seta um bloco populado pelo formul�rio
	 * @param block Bloco populado pelo formul�rio
	 */
	@VisitorFieldValidator(key="")
	public void setBlock(Block block) {
		this.block = block;
	}

	/**
	 * @return Retorna o p�tio atual
	 */
	public Yard getYard() {
		return yard;
	}

	/**
	 * @return Retorna a lista de blocos do p�tio
	 */
	public Set<Block> getBlocks() {
		return blocks;
	}
	
	/**
	 * Retorna os tamanhos de containers dispon�veis para popular o selectBox do formul�rio.
	 * @return tamanhos dos containers
	 */
	public List<ContainerSize> getContainerSizes() {
		return containerSizes;
	}

	/**
	 * @return Id do tamanho de container aceito pelo bloco.<br>� preenchido pelo usu�rio no formul�rio.
	 */
	public Integer getContainerSizeId() {
		return containerSizeId ;
	}

	/**
	 * Seta o id do tamanho do container.<br>
	 * � populado via formul�rio
	 * 
	 * @param containerSizeId Id do tamanho do container aceito pelo p�tio
	 */
	public void setContainerSizeId( Integer containerSizeId ) {
		this.containerSizeId = containerSizeId ;
	}

	/**
	 * @return Retorna o t�tulo da p�gina
	 */
	public Title getTitle() {
		return title;
	}

}
