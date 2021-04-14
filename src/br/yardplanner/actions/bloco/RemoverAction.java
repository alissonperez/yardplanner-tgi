package br.yardplanner.actions.bloco;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.* ;

import br.yardplanner.dao.BlockDAO;
import br.yardplanner.exceptions.BlockNotFoundException;
import br.yardplanner.model.Block;
import br.yardplanner.model.User;
import br.yardplanner.model.Yard;
import br.yardplanner.util.YardPlannerSession;

/**
 * Actions da remoÁ„o de blocos
 * @author Alisson
 */
@ParentPackage("default")
public class RemoverAction {
	
	/**
	 * Instancia da classe de log
	 */
	private Logger log ;
	
	/**
	 * Id do bloco que ser· removido
	 */
	private Integer blockId ;
	
	/**
	 * Id do p·tio atual cujo pertence o bloco
	 */
	private Integer yardId ;
	
	/**
	 * Construtor da classe
	 */
	public RemoverAction() {
		this.log = Logger.getLogger( RemoverAction.class.getName() ) ;
	}
	
	/**
	 * Action principal da remoÁ„o
	 * @return nome do result para carregar a view
	 */
	@Action(
		value = "remover/{blockId}" ,
		results = {
			@Result( name = "ok" , type="redirectAction" , params = { "actionName" , "index/%{yardId}" , "namespace" , "/bloco" } ) ,
			@Result( name = "erro" , type="redirectAction" , params = { "actionName" , "index" , "namespace" , "/patio" } )
		} ,
		interceptorRefs = @InterceptorRef("autenticate")
	)
	public String execute() {	
		BlockDAO blockDAO = new BlockDAO() ;
		
		try {
			log.trace( "Obtendo usuário.." ) ;
			User user = YardPlannerSession.getUser() ;
			
			log.trace("Obtendo bloco: " + this.blockId ) ;
			Block block = blockDAO.getById( this.blockId ) ;
			
			Yard yard = block.getYard() ;
			
			this.yardId = yard.getYardId() ;
			
			log.trace("Comparando os donos dos pátios") ;
			if ( yard.getOwner().getUserId() == user.getUserId() ) {
				log.trace("Removendo bloco..") ;
				blockDAO.delete( block ) ;
			}
			
		} catch (BlockNotFoundException e) {
			log.trace("Bloco não encontrado...") ;
			return "erro" ;
		}
		
		log.trace("Finalizando a remoção..") ;
		
		return "ok" ;		
	}

	/**
	 * Seta o id do bloco por meio da URL
	 * @param blockId Id do bloco
	 */
	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	/**
	 * @return Id do p·tio cujo pertence o bloco
	 */
	public Integer getYardId() {
		return yardId;
	}
}
