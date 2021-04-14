package br.yardplanner.actions.patio;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.*;

import br.yardplanner.dao.YardDAO;
import br.yardplanner.exceptions.YardNotFoundException;
import br.yardplanner.model.Yard;

/**
 * Actions que manipulam a remoÁ„o de p·tios
 * @author Alisson
 */
@ParentPackage("default")
public class RemoverAction {
	
	/**
	 * Instancia da Logger
	 */
	private Logger log ;
	
	/**
	 * Patio para ser gravado
	 */
	private Integer yardId ;
	
	/**
	 * Construtor
	 */
	public RemoverAction() {
		this.log = Logger.getLogger( RemoverAction.class.getName() ) ;
	}
	
	/**
	 * Action que remove um P·tio
	 * @return Retorna o nome do resultado para carregaar a view
	 */
	@Action(
		value = "remover/{yardId}" ,
		results = {
			@Result( name = "ok" , type = "redirectAction" , params = { "actionName" , "index" } ) ,
			@Result( name = "erro" , type = "redirectAction" , params = { "actionName" , "index" , "namespace" , "/" } )
		} ,
		interceptorRefs = @InterceptorRef("autenticate")
	)
	public String execute() {
		log.trace("Entrando na action de remoção") ;
		
		if ( this.yardId != null ) {
			
			log.debug("Id do patio: " + this.yardId ) ;
			
			YardDAO yardDao;
			try {
				yardDao = new YardDAO();
				Yard yard = yardDao.getById( this.yardId ) ;
				if ( yard != null ) {
					log.debug("removendo patio...") ;
					yardDao.delete(yard) ;
				}
			} catch (YardNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "ok" ;
	}

	/**
	 * @return Retorna o id do p·tio atual
	 */
	public Integer getYardId() {
		return yardId;
	}

	/**
	 * Seta o id do p·tio atual.<br>
	 * Preenchido pelo parametro da URL
	 * @param yardId Id do p·tio atual
	 */
	public void setYardId(Integer yardId) {
		this.yardId = yardId;
	}

}
