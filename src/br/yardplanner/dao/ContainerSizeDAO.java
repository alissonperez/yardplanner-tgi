package br.yardplanner.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import br.yardplanner.exceptions.ContainerNotFoundException;
import br.yardplanner.model.ContainerSize;
import br.yardplanner.util.HibernateUtil;

/**
 * DAO responsável por trabalhar a entidade container no banco.
 * @author Alisson
 */
public class ContainerSizeDAO {
	
	/**
	 * Sessão do Hibernate
	 */
	private Session session ;
	
	/**
	 * Construtor
	 */
	public ContainerSizeDAO() {
		this.session = HibernateUtil.getSession() ;
	}
	
	/**
	 * Retorna um ContainerSize dado um ID
	 * @param sizeId Id do ContainerSize desejado 
	 * @return Container encontrado, caso não, é disparada uma exception.
	 * @throws ContainerNotFoundException
	 */
	public ContainerSize getById( Integer sizeId ) throws ContainerNotFoundException {
		this.session.beginTransaction() ;
		ContainerSize containerSize = (ContainerSize) this.session.get( ContainerSize.class, sizeId ) ;
		this.session.getTransaction().commit() ;
		
		if ( containerSize == null ) {
			throw new ContainerNotFoundException() ;
		}
		
		return containerSize ;
	}
	
	/**
	 * Determina se um tamanho de container existe.
	 * @param sizeId
	 * @return True se o id de um tamanho de container existir.
	 */
	public Boolean ContainerSizeIdExists( Integer sizeId ) {
		
		this.session.beginTransaction() ;
		ContainerSize containerSize = (ContainerSize) this.session.get( ContainerSize.class , sizeId ) ;
		this.session.getTransaction().commit() ;
		
		return containerSize != null ;
	}
	
	/**
	 * Todos os containers do banco
	 * @return Retorna todos os containers do banco.
	 */
	public ArrayList<ContainerSize> getAll() {
		ArrayList<ContainerSize> containerSizes = new ArrayList<ContainerSize>() ;
		
		containerSizes = (ArrayList<ContainerSize>) this.session.createCriteria(ContainerSize.class).list() ;
		
		return containerSizes ;
	}
}
