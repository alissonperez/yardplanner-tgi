package br.yardplanner.dao;

import java.sql.SQLException;
import org.hibernate.Session;

import br.yardplanner.model.*;
import br.yardplanner.util.HibernateUtil;
import br.yardplanner.exceptions.YardNotFoundException;

/**
 * DAO Reponsável por trabalhar com o objeto Pátio
 * @author Alisson
 */
public class YardDAO {
	
	/**
	 * Sess‹o do Hibernate
	 */
	private Session session ;
	
	/**
	 * Construtor da classe
	 */
	public YardDAO() {
		this.session = HibernateUtil.getSession() ;
	}
	
	/**
	 * Grava o Pátio no banco
	 * @param yard pátio que vai ser adicionado
	 */
	public void save( Yard yard ) {
		session.beginTransaction();
		this.session.save( yard ) ;
		session.getTransaction().commit();		
	}
	
	/**
	 * Deleta um pátio do banco
	 * 
	 * @param Yard pátio que será deletado
	 */
	public void delete ( Yard yard ) {
		session.beginTransaction();
		this.session.delete( yard ) ;
		session.getTransaction().commit();		
	}
	
	/**
	 * Retorna um patio dado um ID
	 * 
	 * @param id Id do patio desejado
	 * 
	 * @return Retorna o pátio caso seja encontrado, caso não, dispara uma exception.
	 * 
	 * @throws YardNotFoundException 
	 */
	public Yard getById( Integer id ) throws YardNotFoundException {
		this.session.beginTransaction() ;
		Yard yard = (Yard) this.session.get( Yard.class , id ) ;
		this.session.getTransaction().commit() ;
		
		if ( yard == null ) {
			throw new YardNotFoundException() ;
		}
		
		return yard ;
	}
	
}
