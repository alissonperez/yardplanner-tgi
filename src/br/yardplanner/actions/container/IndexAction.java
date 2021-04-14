package br.yardplanner.actions.container;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.* ;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import br.yardplanner.dao.BlockDAO;
import br.yardplanner.dao.ContainerDAO;
import br.yardplanner.dao.ContainerSizeDAO;
import br.yardplanner.exceptions.BlockNotFoundException;
import br.yardplanner.exceptions.ContainerCodeInvalidException;
import br.yardplanner.exceptions.InsertionContainerException;
import br.yardplanner.exceptions.PositionOnOutsideException;
import br.yardplanner.model.Block;
import br.yardplanner.model.Container;
import br.yardplanner.model.ContainerCode;
import br.yardplanner.model.User;
import br.yardplanner.model.Yard;
import br.yardplanner.util.Position;
import br.yardplanner.util.Title;
import br.yardplanner.util.YardPlannerSession;

/**
 * Actions principais para a exibiÁ„o dos containers do bloco
 * @author Alisson Perez
 */
@ParentPackage("default")
@Validations
public class IndexAction extends ActionSupport {
	
	/**
	 * Instancia da Logger
	 */
	private Logger log ;
	
	/**
	 * Id do bloco atual
	 */
	private Integer blockId ;
	
	/**
	 * Container que ser· inserido
	 */
	private Container container ;
	
	/**
	 * Bloco do container
	 */
	private Block block ;
	
	/**
	 * P·tio do bloco
	 */
	private Yard yard ;
	
	/**
	 * RepresentaÁ„o do p·tio via arrayList
	 */
	private ArrayList<ArrayList<ArrayList<Container>>> blockList ;
	
	/**
	 * Dao do bloco
	 */
	private BlockDAO blockDAO ;
	
	/**
	 * Upload do arquivo XML<br>
	 * Preenchido via formul·rio
	 */
	private File xmlUpload ;
	
	/**
	 * Nome do arquivo de upload
	 */
	private String xmlUploadFileName ;
	
	/**
	 * ContentType do arquivo de upload
	 */
	private String xmlUploadContentType ;
	
	/**
	 * Se 'yes', limpa o bloco atual pelo conte˙do do XML
	 */
	private String clearBlock ;
	
	/**
	 * TÌtulo da p·gina
	 */
	private Title title ;
	
	/**
	 * Construtor
	 */
	public IndexAction() {
		this.title = new Title() ;
		this.log = Logger.getLogger(IndexAction.class.getName()) ;
	}
	
	/**
	 * Busca o p·tio informado na URL<br>
	 * Popula a representaÁ„o do bloco em lista<br>
	 * Obtem o p·tio do bloco<br><br>
	 * Caso o bloco n„o seja encontrado, n„o instancia nada.
	 */
	private void instaceBlock() {
		
		this.yard = null ;
		
		if ( this.blockId != null ) {
			User user = YardPlannerSession.getUser() ;
			
			try {
				
				this.blockDAO = new BlockDAO() ;
				
				Block block = this.blockDAO.getById( this.blockId ) ;
								
				Yard yard = block.getYard() ;
				
				// Verifica o dono do p·tio
				if ( yard.getOwner().getUserId() == user.getUserId() ) {
					
					// Setando o bloco
					this.block = block ;
					
					// Obtem a represetançÁ„o do bloco em ArrayList
					this.blockList = block.getListRepresentation() ;
					
					// Finalmente setando p·tio
					this.yard = yard ;
					
				}
				
			} catch (BlockNotFoundException e) {

			}
		}
		
	}
	
	/**
	 * Action principal que lista os containers do bloco
	 * @return nome do resultado para carregar a view
	 */
	@Action(
		value = "index/{blockId}" ,
		results = {
			@Result( name = "ok" , location = "index.jsp" ) ,
			@Result( name = "erro" , type = "redirectAction" , params = { "actionName" , "index" , "namespace" , "/patio" } )
		} ,
		interceptorRefs = @InterceptorRef("autenticate")
	)
	public String execute() {
		
		// Instancia o block
		this.instaceBlock() ;
		
		// Acertando o tÌtulo das p·ginas
		this.title.add( this.yard.getName() ) ;
		this.title.add( this.block.getName() ) ;
		this.title.add( "Containers" ) ;
		
		if ( this.yard == null || this.block == null ) {
			return "erro" ;
		}
		
		return "ok" ;
	}
	
	/**
	 * Action da criaÁ„o de um novo container
	 * @return nome do resultado para carregar a view
	 */
	@Action(
		value = "novo/{blockId}" ,
		results = {
			@Result( name = "ok" , type = "redirectAction" , params = { "actionName" , "index/%{blockId}" , "namespace" , "/container" } ) ,
			@Result( name = "erro" , type = "redirectAction" , params = { "actionName" , "index" , "namespace" , "/patio" } ) ,
			@Result( name = "input" , location = "index.jsp" )
		} ,
		interceptorRefs = @InterceptorRef("autenticate")
	) 
	public String novo() {
		this.instaceBlock() ;
				
		if ( this.yard == null || this.block == null ) {
			return "erro" ;
		}
		
		// Acertando o tÌtulo das p·ginas
		this.title.add( this.yard.getName() ) ;
		this.title.add( this.block.getName() ) ;
		this.title.add( "Containers" ) ;
		
		this.log.trace( "Entrou.." ) ;
		
		if ( this.container != null ) {
			
			ContainerDAO containerDAO = new ContainerDAO() ;
			
			this.container.setBlock( this.block ) ;
			this.container.setYard( this.yard ) ;
			this.container.setSize( this.block.getContainerSize() ) ;
			if ( this.container.isEmptyCode() ) {
				this.container.generateRandomCode() ;
			}
			
			containerDAO.save( this.container ) ;
			
			
			this.log.trace( "Container nao nulo..." ) ;
		}
		
		return "ok" ;
	}
	
	/**
	 * Action respons·vel pelo upload de XMLs.
	 * @return nome do resultado para carregar a view
	 */
	@Action(
		value = "xmlUpload/{blockId}" ,
		results = {
			@Result( name = "ok" , type = "redirectAction" , params = { "actionName" , "index/%{blockId}" , "namespace" , "/container" } ) ,
			@Result( name = "input" , location = "index.jsp" )
		} ,
		interceptorRefs = @InterceptorRef( "autenticate" )
	)
	public String upload() {
		this.instaceBlock() ;
		
		// Acertando o tÌtulo das p·ginas
		this.title.add( this.yard.getName() ) ;
		this.title.add( this.block.getName() ) ;
		this.title.add( "Containers" ) ;
		
		Set<Container> containersSet = this.block.getContainers() ;
		if ( this.clearBlock != null && this.clearBlock.equals( "yes" ) ) {
			containersSet.clear() ;
		}
		this.block.getContainersFromXML( this.xmlUpload , containersSet ) ;
		this.blockDAO.update( this.block ) ;
		this.blockDAO.refresh( this.block ) ;
		
		this.log.trace("Finalizando upload...") ;
		
		return "ok" ;
	}
	
	/**
	 * ValidaÁ„o da entrada dos formul·rios que populam a action<br>
	 * Formul·rios:<br><ul>
	 * <li>Entrada de um novo container manualmente</li>
	 * <li>Upload de arquivo XML</li>
	 */
	public void validate() {
		log.trace( "validando entrada.." ) ;
		this.log.debug( ContainerCode.getRandomCode().getCode() ) ;
		
		this.instaceBlock() ;
		
		// Validando a entrada de um novo container manualmente
		if ( this.yard != null && this.block != null && this.container != null ) {
			
			if ( ! this.container.isEmptyCode() ) {
				try {
					this.container.checkCode() ;
				} catch ( InsertionContainerException e) {
					super.addFieldError( "" , e.getMessage() ) ;
				}
			}
			
			// Validando as posicıes
			if ( this.container.getPosition() != null ) {
				try {
					this.block.isPositionOnInside( this.container.getPosition() ) ;
				} catch ( InsertionContainerException e ) {
					super.addFieldError( "" , e.getMessage() ) ;
				}
			}
			
			Position position ;
			Container container ;
			
			// Verificando se h· um container j· colocado nesta posiÁ„o
			if ( this.container.getPosX() != null && this.container.getPosY() != null && this.container.getPosZ() != null ) {
				position = new Position( this.container.getPosX() , this.container.getPosY() , this.container.getPosZ() ) ;
				
				if ( this.block.hasContainerOnPosition( position ) ) {
					super.addFieldError( "container.name" , "J· existe um container na posiÁ„o ( " + position.getPosX() + "," + position.getPosY() + "," + position.getPosZ() + " ) " ) ;
				}
				
				// Verificando se h· um container abaixo do novo caso o mesmo n„o seja no primeiro nÌvel
				if ( ! this.block.hasContainerBellow( position ) ) {
					super.addFieldError( "container.name" , "N„o h· um container abaixo deste" ) ;	
				}
			}
		}
		
		log.trace( "Inserindo containers" ) ;
		
		// Validando a entrada de uma lista de containers via arquivo xml
		if ( this.yard != null && this.block != null && this.xmlUpload != null ) {
			Set<Container> containersSet = new HashSet<Container>() ; 
			this.block.getContainersFromXML( this.xmlUpload , containersSet ) ;
			
			this.log.debug( "Clear block: " + this.clearBlock ) ;
			try {
				Container.checkContainersCode( containersSet ) ;
				
				if ( this.clearBlock == null || ! this.clearBlock.equals( "yes" ) ) {
					this.log.debug( "Testando a lista de containers" ) ;
					this.block.checkContainersListPosition( containersSet ) ;
				}
			} catch ( InsertionContainerException e ) {
				this.log.debug( e.getMessage() ) ;
				super.addFieldError( "xmlUpload", e.getMessage() ) ;
			}
		}
		
	}

	/**
	 * @return Retorna o id do bloco atual
	 */
	public Integer getBlockId() {
		return blockId;
	}

	/**
	 * Seta o id do bloco atual. 
	 * 
	 * @param blockId Id do bloco
	 */
	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	/**
	 * @return Retorna o container populado pelo formul·rio
	 */
	public Container getContainer() {
		return container;
	}

	/**
	 * @param container Seta o container populado pelo formul·rio (Faz as devidas validaÁıes)
	 */
	@VisitorFieldValidator(key="")
	public void setContainer(Container container) {
		this.container = container;
	}

	/**
	 * @return Retorna o bloco atual
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * @return Retorna o p·tio atual
	 */
	public Yard getYard() {
		return yard;
	}

	/**
	 * Retorna um ArrayList em trÍs dimensıes contendo os containers do bloco.<br>
	 * Foi criado assim para facilitar a exibiÁ„o no frontend usando iterators do Struts 2. Ex:<pre>
	 * 
	 * <b>(CÛdigo n„o foi executado, apenas para exemplificaÁ„o)</b>
	 * 
	 * ArrayList&lt;ArrayList&lt;ArrayList&lt;Container&gt;&gt;&gt; camadas = indexActionInstance.getBlockList() ;
	 * for ( linhas : camadas ) {
	 * 	for ( containers : linhas ) {
	 * 		for ( container : containers ) {
	 * 			// ...
	 * 		}
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @return Retorna a representaÁ„o dos containers do bloco em forma de ArrayList 3D
	 */
	public ArrayList<ArrayList<ArrayList<Container>>> getBlockList() {
		return blockList;
	}
	
	/**
	 * Setar um arquivo xml para upload.<br>
	 * … populado via formul·rio
	 * @param xmlUpload Arquivo XML vindo do formul·rio
	 */
	public void setXmlUpload(File xmlUpload) {
		this.xmlUpload = xmlUpload;
	}
	
	/**
	 * Seta o nome do arquivo XMl do upload<br>
	 * … populado via formul·rio
	 * @param xmlUploadFileName Nome do arquivo XML de upload
	 */
	public void setXmlUploadFileName(String xmlUploadFileName) {
		this.xmlUploadFileName = xmlUploadFileName;
	}
	
	/**
	 * ContentType do arquivo XML de upload<br>
	 * … populado via formul·rio
	 * @param xmlUploadContentType
	 */
	public void setXmlUploadContentType(String xmlUploadContentType) {
		this.xmlUploadContentType = xmlUploadContentType;
	}

	/**
	 * @return Retorna o tÌtulo da p·gina
	 */
	public Title getTitle() {
		return title;
	}

	/**
	 * @param clearBlock 'yes' determina que o bloco ser· limpo. Populado via formul·rio
	 */
	public void setClearBlock(String clearBlock) {
		this.clearBlock = clearBlock;
	}

	/**
	 * @return Retorna a escolha de limpeza de bloco populada pelo formul·rio
	 */
	public String getClearBlock() {
		return clearBlock;
	}

}
