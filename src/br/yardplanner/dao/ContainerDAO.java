package br.yardplanner.dao;

import org.hibernate.Session;

import br.yardplanner.exceptions.ContainerNotFoundException;
import br.yardplanner.model.Container;
import br.yardplanner.util.HibernateUtil;

/**
 * DAO Repons�vel por trabalhar com o objeto Container
 * @author Alisson
 */
public class ContainerDAO {
	
	/**
	 * Sess�o do Hibernate
	 */
	private Session session ;
	
	/**
	 * Construtor da classe
	 */
	public ContainerDAO() {
		this.session = HibernateUtil.getSession() ;
	}
	
	/**
	 * Grava o Container no banco
	 * @param Container container que vai ser adicionado
	 */
	public void save( Container container ) {
		this.session.beginTransaction();
		this.session.save( container ) ;
		this.session.getTransaction().commit();
	}
	
	/**
	 * Deleta um container
	 * 
	 * @param Container container que ser� removido
	 * 
	 * @throws SQLException 
	 * @throws containerNotFoundException 
	 */
	public void delete ( Container container ) {
		this.session.beginTransaction();
		this.session.save( container ) ;
		this.session.getTransaction().commit();
	}
	
	/**
	 * Retorna um container dado um ID
	 * 
	 * @param id
	 * @return Container encontrado, caso n�o, � disparada uma exception.
	 * @throws ContainerNotFoundException
	 */
	public Container getById( Integer id ) throws ContainerNotFoundException {
		this.session.beginTransaction();
		Container container = (Container) this.session.get( Container.class , id ) ;
		this.session.getTransaction().commit();
		
		if ( container == null ) {
			throw new ContainerNotFoundException() ;
		}
		
		return container ;
	}
	
}
