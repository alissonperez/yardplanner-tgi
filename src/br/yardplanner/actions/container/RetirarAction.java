package br.yardplanner.actions.container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.* ;

import br.yardplanner.core.GeneticAlgorithm;
import br.yardplanner.core.Sequence;
import br.yardplanner.dao.BlockDAO;
import br.yardplanner.exceptions.BlockNotFoundException;
import br.yardplanner.model.Block;
import br.yardplanner.model.Container;
import br.yardplanner.model.User;
import br.yardplanner.model.Yard;
import br.yardplanner.util.Title;
import br.yardplanner.util.Util;
import br.yardplanner.util.YardPlannerSession;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Actions da remoÁ„o de containers
 * 
 * @todo Alterar a forma de remoÁ„o para checkbox`s ao invÈs de listar os ids em um campo text
 * @author Alisson
 */
@ParentPackage("default")
@Validations
public class RetirarAction extends ActionSupport {
	
	/**
	 * Instancia da Logger
	 */
	private Logger log ;
	
	/**
	 * Id do bloco atual
	 */
	private Integer blockId ;
	
	/**
	 * Bloco do container
	 */
	private Block block ;
	
	/**
	 * P·tio do bloco
	 */
	private Yard yard ;
	
	/**
	 * TÌtulo da p·gina
	 */
	private Title title ;
	
	/**
	 * String com os ids dos containers para remoÁ„o separados por virgula
	 * Populada do formulá·rio da pá·gina
	 */
	private String containersStrIds ;
	
	/**
	 * Bot„o de confirmaÁ„o de remoÁ„o
	 */
	private String btnConfirmation ;
	
	/**
	 * Bot„o de cancelar
	 */
	private String btnCancel ;
	
	/**
	 * Lista com os ids para remoÁ„o do bloco
	 */
	private List<Integer> containerIds ;
	
	/**
	 * Lista com a melhor sequÍncia de remoÁ„o<br>
	 * Gerado pelo algoritmo genÈtico {@see GeneticAlgorithm}
	 */
	private String bestSequenceIds ;
	
	/**
	 * Sequencia de cÛdigos para remoÁ„o
	 */
	private List<String> bestSequenceCodes ;
	
	/**
	 * Custo para remoÁ„o de uma determinada sequÍncia de ids
	 */
	private Double costToRemove ;
	
	/**
	 * Mensagens de movimentaÁ„o
	 */
	private List<String> msgs ;
	
	/**
	 * DAO do Bloco
	 */
	private BlockDAO blockDAO ;
	
	/**
	 * Representação do p·tio via arrayList
	 */
	private ArrayList<ArrayList<ArrayList<Container>>> blockList ;

	/**
	 * Construtor
	 */
	public RetirarAction() {
		this.title = new Title() ;
		this.log = Logger.getLogger( RetirarAction.class.getName() ) ;
	}
	
	/**
	 * Instancia o bloco atual
	 * @throws BlockNotFoundException
	 */
	private void instanceBlock() throws BlockNotFoundException {
		this.yard = null ;
		
		if ( this.blockId != null ) {
			User user = YardPlannerSession.getUser() ;
			
			this.blockDAO = new BlockDAO() ;
			
			Block block = this.blockDAO.getById( this.blockId ) ;
							
			Yard yard = block.getYard() ;
			
			// Verifica o dono do pátio
			if ( yard.getOwner().getUserId() == user.getUserId() ) {
				
				// Setando o bloco
				this.block = block ;
				
				// Obtem a represetanção do bloco em ArrayList
				this.blockList = block.getListRepresentation() ;
				
				// Finalmente setando pátio
				this.yard = yard ;
			}
		}
	}
	
	/**
	 * Action da remoÁ„o de containers
	 * @return Retorna o nome do resultado para carregar a view
	 */
	@Action(
		value = "retirar/{blockId}" , 
		results = {
			@Result( name = "ok" , location = "remove.jsp" ) ,
			@Result( name = "input" , location = "remove.jsp" ) ,
			@Result( name = "erro" , type = "redirectAction" , params = { "actionName" , "index" , "namespace" , "/patio" } ) ,
			@Result( name = "cancel" , type = "redirectAction" , params = { "actionName" , "retirar/{blockId}" , "namespace" , "/container" } )
		} ,
		interceptorRefs = @InterceptorRef( "autenticate" )
	)
	public String execute() {		
		this.log.debug( "Action de remoÁ„o" ) ;
		try {
			this.instanceBlock() ;
		} catch ( BlockNotFoundException e ) {
			return "erro" ;
		}
			
		// Acertando o título das páginas
		this.title.add( this.yard.getName() ) ;
		this.title.add( this.block.getName() ) ;
		this.title.add( "Remover containers" ) ;
		
		if ( this.btnCancel != null ) {
			return "cancel" ;
		}
		
		// Caso n„o tenha nenhum id, o usu·rio est· visualizando a p·gina de remoÁ„o apenas
		if ( this.containerIds == null ) {
			return "ok" ;
		}

		this.log.debug( "Instanciando a sequence" ) ;
		
		Sequence sequence = new Sequence( this.containerIds , this.block ) ;
		if ( this.btnConfirmation == null ) {
			this.log.debug( "Rodando o GA" ) ;
			GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm( sequence ) ;
			geneticAlgorithm.run() ;
			this.log.debug( "GA executado. Obtendo a melhor sequÍncia." ) ;
			this.containerIds = geneticAlgorithm.getBestSequence() ;
			this.log.debug( "SequÍncia obtida: " + this.containerIds ) ;
		}
		
		this.log.debug( "Implode na sequÍncia obtida" ) ;
		this.bestSequenceIds = Util.implode( this.containerIds , ", " ) ;

		this.log.debug( "Obtendo sequencia de cÛdigos baseado nos ids" ) ;
		this.bestSequenceCodes = this.getCodesFromIds( this.containerIds ) ;

		this.log.debug( "Obtendo o custo" ) ;
		this.costToRemove = sequence.getCost() ;

		this.log.debug( "Obtendo as mensagens" ) ;
		this.msgs = sequence.getMsg() ;
		
		this.log.debug( "Removendo containers" ) ;
		sequence.remove() ;
		
		this.log.debug( "Obtendo o bloco resultante" ) ;
		this.block = sequence.getBlock() ;

		this.log.debug( "Obtendo a representaÁ„o em um array de 3 dimensoes" ) ;
		this.blockList = this.block.getListRepresentation() ;

		this.log.debug( "Finalizando..." ) ;
		
		// Caso tenha clicado no bot„o confirmar
		if ( this.btnConfirmation != null ) {
			this.blockDAO.update( this.block ) ;
			this.bestSequenceIds = null ;
			this.bestSequenceCodes = null ;
		}
		
		return "ok" ;
	}
	
	/**
	 * Retorna uma lista com os cÛdigos da melhor remoÁ„o formatados
	 * 
	 * @param containerIds Ids dos containers para remoÁ„o
	 * 
	 * @return
	 */
	private List<String> getCodesFromIds( List<Integer> containerIds ) {
		List<String> codes = new ArrayList<String>() ;
		
		if ( this.block == null ) {
			return codes ;
		}
		
		Map<Integer, Container> containersMapId = this.block.getContainersMapId() ;
		
		Iterator<Integer> it = containerIds.iterator() ;
		Container container ;
		
		while ( it.hasNext() ) {
			container = containersMapId.get( it.next() ) ;
			if ( container != null ) {
				codes.add( container.getCodeFormmated() ) ;
			}
		}
		
		return codes ;
	}
	
	/**
	 * ValidaÁıes da sequÍncia de Ids para remoÁ„o
	 */
	public void validate() {		
		log.debug( "ValidaÁ„o da retirada" ) ;
		log.debug( "Ids: " + this.containersStrIds ) ;

		if ( this.containersStrIds == null ) {
			return ;
		}

		try {
			this.instanceBlock() ;
		} catch (BlockNotFoundException e) {
			this.log.debug( "Container n„o encontrado" ) ;
			return ;
		}
			
		Map<Integer, Container> containersMapIds = this.block.getContainersMapId() ;
		
		String containerIds ;
		containerIds = Pattern.compile( "\\s+" , Pattern.DOTALL ).matcher( this.containersStrIds ).replaceAll( "" ) ;
		containerIds = Pattern.compile( "[,;]+" , Pattern.DOTALL ).matcher( containerIds ).replaceAll( "," ) ;
		log.debug( containerIds ) ;
		
		Pattern pattNum = Pattern.compile( "[0-9]+" ) ;
		Integer containerIntId ;
		
		this.containerIds = new ArrayList<Integer>() ;
		for ( String containerId : containerIds.split( "," ) ) {
			log.debug( containerId ) ;
			
			// Verificando se h· apenas n˙meros
			if ( ! pattNum.matcher( containerId ).matches() ) {
				super.addFieldError( "containerIds" , "Os id's dos containers deve conter apenas n˙meros" ) ;
				break ;
			}
			
			containerIntId = Integer.parseInt( containerId ) ;
			
			if ( ! containersMapIds.containsKey( containerIntId ) ) {
				super.addFieldError( "containerIds" , "Id '" + containerIntId + "' não encontrado neste bloco" ) ;
				break ;
			}
			
			if ( this.containerIds.contains( containerIntId ) ) {
				continue ;
			}
			
			this.containerIds.add( containerIntId ) ;
		}
		
		log.debug( "Ids dos container: " + this.containerIds ) ;
	}

	/**
	 * @return Retorna o id do bloco atual
	 */
	public Integer getBlockId() {
		return blockId;
	}

	/**
	 * @param blockId Seta o id do bloco Atual
	 */
	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	/**
	 * @return Retorna o bloco atual
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * @return retorna o p·tio atual
	 */
	public Yard getYard() {
		return yard;
	}

	/**
	 * @return retorna o tÌtulo da p·gina
	 */
	public Title getTitle() {
		return title;
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
	 * @return Retorna os ids dos containers para remoÁ„o
	 */
	public String getContainersStrIds() {
		return containersStrIds;
	}

	/**
	 * Seta uma string com os ids dos containers desejados para remoÁ„o separados por virgula<br>
	 * Ex: <pre>"14, 15, 16, 17, 29, 30"<pre>
	 * @param containerIds String com ids dos containers para remoÁ„o separados por virgula
	 */
	public void setContainersStrIds(String containerIds) {
		this.containersStrIds = containerIds;
	}

	/**
	 * Retorna a melhor sequencia de retirada em format String com separaÁıes por virgula
	 * Ex: <pre>"14, 15, 16, 17, 29, 30"<pre>
	 * @return Retorna uma string com a melhor sequÍncia de retirada
	 */
	public String getBestSequenceIds() {
		return bestSequenceIds;
	}

	/**
	 * @return Retorna o custo para remoÁ„o de uma determinada sequÍncia
	 */
	public Double getCostToRemove() {
		return costToRemove;
	}

	/**
	 * Seta o valor do bot„o de confirmaÁ„o, quando for clicado.
	 * @param btnConfirmation Valor do bot„o de confirmaÁ„o
	 */
	public void setBtnConfirmation(String btnConfirmation) {
		this.btnConfirmation = btnConfirmation;
	}

	/**
	 * Seta o valod do bot„o de cancelar, quando for clicado.
	 * @param btnCancel Valor do bot„o de cancelar
	 */
	public void setBtnCancel(String btnCancel) {
		this.btnCancel = btnCancel;
	}

	/**
	 * @return the msgs
	 */
	public List<String> getMsgs() {
		return msgs;
	}

	/**
	 * @return the bestSequenceCodes
	 */
	public List<String> getBestSequenceCodes() {
		return bestSequenceCodes;
	}
}
