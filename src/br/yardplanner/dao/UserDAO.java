package br.yardplanner.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import br.yardplanner.exceptions.UserBlockedException;
import br.yardplanner.exceptions.userNotFoundException;
import br.yardplanner.model.*;
import br.yardplanner.util.HibernateUtil;

/**
 * DAO Repons�vel por trabalhar com o objeto Usuario
 * @author Alisson
 *
 */
public class UserDAO {
	
	/**
	 * Instancia da Logger
	 */
	private Logger log ;
	
	/**
	 * Sess�o do Hibernate
	 */
	private Session session ;
	
	/**
	 * Construtor
	 */
	public UserDAO() {
		this.session = HibernateUtil.getSession() ;
		this.log = Logger.getLogger( UserDAO.class.getName() ) ;
	}
	
	/**
	 * Destroy do objeto, � necess�rio fechar a sess�o.
	 */
	public void destroy() {
		this.session.disconnect() ;
	}
	
	/**
	 * Grava o usu�rio no banco
	 * @param user Usu�rio que ser� gravado no banco.
	 */
	public void save( User user ) {
		this.session.beginTransaction();
		this.session.save( user ) ;
		this.session.getTransaction().commit();
	}
	
	/**
	 * Atualiza��o de um usu�rio no banco.
	 * @author Alisson
	 */
	public void update( User user ) {
		this.session.beginTransaction() ;
		this.session.update( user ) ;
		this.session.getTransaction().commit() ;
	}
	
	/**
	 * Atualiza o conte�do de um usu�rio com o que tiver no banco.
	 * @param user Usu�rio que deseja-se atualizar seu conte�do
	 */
	public void refresh( User user ) {
		this.session.refresh( user ) ;
	}
	
	/**
	 * Retorna um usu�rio dado um Id
	 * @param id Id do usu�rio desejado
	 * @return Usu�rio, caso encontrado. Caso n�o, ser� retornado null.
	 * @todo Disparar uma exception caso o usu�rio n�o seja encontrado.
	 */
	public User getById( Integer id ) {
		this.session.beginTransaction() ;
		User user = (User) this.session.get( User.class , id ) ;
		this.session.getTransaction().commit() ;
		
		return user ;
	}
	
	/**
	 * Retorna uma lista com todos os usu�rios do banco
	 * @return Lista com todos os usu�rios do banco
	 */
	public List<User> getAll() {
		Query query = this.session.createQuery( "select u from User u" ) ;
		
		@SuppressWarnings("unchecked")
		List<User> result = (List<User>) query.list() ;
		
		return result ;
	}
	
	/**
	 * Verifica se o e-mail de um dado usu�rio existe no banco.
	 * @param user Usu�rio que deseja-se veficar se seu e-mail existe.
	 * @return Caso e-mail exista, retorna true, caso n�o, false.
	 */
	public boolean emailExists( User user ) {
		
		if ( user == null ) {
			return false ;
		}
		
		if ( user.getEmail() == null ) {
			return true ;
		}
		
		Query query ;
		if ( user.getUserId() == null ) {
			this.log.trace("Usuario nao existe") ;
			
			query = this.session.createQuery( "select count(*) from User where email = :emailParam" ) ;			
		}
		else {
			this.log.trace("Usuario existe") ;
			
			query = this.session.createQuery( "select count(*) from User where email = :emailParam and user_id != :userIdParam" ) ;
			query.setInteger("userIdParam", user.getUserId() ) ;
		}
		
		query.setString( "emailParam" , user.getEmail() ) ;
		
		Long count = (Long) query.iterate().next() ;
		
		this.log.trace("Resultado da query" + count ) ;
		
		if ( count > 0 ) {
			return true ;
		}
		
		return false ;
	}
	
	/**
	 * Procura o usu�rio por seu e-mail e senha no banco.<br>
	 * Se usu�rio n�o existir, � disparada uma exception, caso o mesmo exista mas esteja bloqueado, � disparada uma outra exception.
	 * 
	 * @param user Usu�rio que deseja-se buscar seu e-mail e senha no banco.
	 * 
	 * @return Retorna o usu�rio caso o e-mail e senha existam no banco. Caso n�o, � disparada uma exception.
	 * 
	 * @throws userNotFoundException 
	 * @throws UserBlockedException 
	 */
	public User login( User user ) throws userNotFoundException, UserBlockedException {
		
		if ( user == null ) {
			throw new userNotFoundException() ;
		}
		
		this.session.beginTransaction() ;
		
		this.log.trace( "Senha: " + user.getPassword() + "\n" +
				"email: " + user.getEmail() ) ;
		
		Query query = this.session.createQuery( "select u from User u where email = :email and ( password = :password or password_temp = :password )" ) ;
		
		query.setParameter( "password", user.getPassword() ) ;
		query.setParameter( "email" , user.getEmail() ) ;
		
		User userLogin = (User) query.uniqueResult();
		
		this.log.debug(userLogin) ;
		
		if ( userLogin == null ) {
			throw new userNotFoundException() ;
		}
		else if ( userLogin.isBlocked() ) {
			throw new UserBlockedException() ;
		}
		
		return userLogin ;
	}
}
