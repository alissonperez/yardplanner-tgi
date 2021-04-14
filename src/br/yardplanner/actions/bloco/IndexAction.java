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
 * Actions da página de visualização dos blocos de um pátio
 * @author Alisson Perez
 */
@ParentPackage("default")
@Validations
public class IndexAction extends ActionSupport {
	
	/**
	 * Instância do Logger
	 */
	private Logger log ;
	
	/**
	 * Id do pátio atual
	 */
	private Integer yardId ;
	
	/**
	 * Instância do pátio atual
	 */
	private Yard yard ;
	
	/**
	 * Bloco que será populado via fomulário para criação de um novo bloco
	 */
	private Block block ;
	
	/**
	 * Lista de blocos do pátio atual para exibição
	 */
	private Set<Block> blocks ;
	
	/**
	 * Lista de tamanhos de containers
	 */
	private List<ContainerSize> containerSizes ;
	
	/**
	 * Id populado via formulário do tamanho de containers aceitos em um pátio
	 */
	private Integer containerSizeId ;
	
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
	 * Obtem o pátio atual do banco de dados dado o id do mesmo vindo da URL da página.
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
				
				// Verifica o dono do p‡tio
				if ( yard.getOwner().getUserId() == user.getUserId() ) {
					
					// Finalmente setando p‡tio
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
	 * É passado o id do pátio na url e mapeado na classe.
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
		// Instancia o p‡tio atual
		this.instaceYard() ;
		
		// Incluindo o título da página
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
	 * Action para a criação de um novo bloco em um pátio (yardID).
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
	 * Verificação dos dados preenchidos no formulário para a criação de um novo Bloco
	 */
	public void validate() {
		
		// Instanciando o patio atual
		this.instaceYard() ;
		
		this.containerSizes = (new ContainerSizeDAO()).getAll() ;
		
		if ( this.containerSizeId != null ) {
			if ( this.containerSizeId == -1 ) {
				super.addFieldError( "containerSizeId", "É necessário informar o tamanho dos containers" ) ;
			}
			else if ( ! (new ContainerSizeDAO()).ContainerSizeIdExists( this.containerSizeId ) ) {
				super.addFieldError( "containerSizeId" , "Tamanho de container não encontrado") ;
			}
		}
		
	}

	/**
	 * @return Retorna o id do pátio
	 */
	public Integer getYardId() {
		return yardId;
	}

	/**
	 * Seta o id do pátio atual.<br>
	 * É populado pelo struts ao carregar a URL
	 * 
	 * @param yardId Id do pátio atual
	 */
	public void setYardId( Integer yardId ) {
		this.yardId = yardId;
	}

	/**
	 * @return Retorna o bloco que foi populado pelo usuário no formulário
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * Seta um bloco populado pelo formulário
	 * @param block Bloco populado pelo formulário
	 */
	@VisitorFieldValidator(key="")
	public void setBlock(Block block) {
		this.block = block;
	}

	/**
	 * @return Retorna o pátio atual
	 */
	public Yard getYard() {
		return yard;
	}

	/**
	 * @return Retorna a lista de blocos do pátio
	 */
	public Set<Block> getBlocks() {
		return blocks;
	}
	
	/**
	 * Retorna os tamanhos de containers disponíveis para popular o selectBox do formulário.
	 * @return tamanhos dos containers
	 */
	public List<ContainerSize> getContainerSizes() {
		return containerSizes;
	}

	/**
	 * @return Id do tamanho de container aceito pelo bloco.<br>É preenchido pelo usuário no formulário.
	 */
	public Integer getContainerSizeId() {
		return containerSizeId ;
	}

	/**
	 * Seta o id do tamanho do container.<br>
	 * É populado via formulário
	 * 
	 * @param containerSizeId Id do tamanho do container aceito pelo pátio
	 */
	public void setContainerSizeId( Integer containerSizeId ) {
		this.containerSizeId = containerSizeId ;
	}

	/**
	 * @return Retorna o título da página
	 */
	public Title getTitle() {
		return title;
	}

}
