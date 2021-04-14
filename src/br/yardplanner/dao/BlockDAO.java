package br.yardplanner.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;

import com.mysql.jdbc.PreparedStatement;

import br.yardplanner.exceptions.BlockNotFoundException;
import br.yardplanner.exceptions.userNotFoundException;
import br.yardplanner.model.*;
import br.yardplanner.util.HibernateUtil;

/**
 * DAO Repons�vel por trabalhar com o objeto Bloco
 * @author Alisson
 */
public class BlockDAO {
	
	/**
	 * Sess�o do Hibernate
	 */
	private Session session ;
	
	/**
	 * Construtor
	 */
	public BlockDAO() {
		this.session = HibernateUtil.getSession() ;
	}
	
	/**
	 * Grava o Bloco no banco
	 * @param Block Bloco que vai ser gravado no banco
	 */
	public void save( Block block ) {
		this.session.beginTransaction();
		this.session.save( block ) ;
		this.session.getTransaction().commit();
	}
	
	/**
	 * Atualiza��o do bloco
	 * @param block Bloco que deseja-se atualizar
	 */
	public void update( Block block ) {
		this.session.beginTransaction() ;
		this.session.update( block ) ;
		this.session.getTransaction().commit() ;
	}
	
	/**
	 * Atualiza o conteudo do bloco com os dados do banco.
	 * @param block Bloque de deseja-se atualizar
	 */
	public void refresh( Block block ) {
		this.session.refresh( block ) ;
	}
	
	/**
	 * Deleta um bloco do banco
	 * @param Block bloco a ser retirado
	 * @throws blockNotFoundException 
	 */
	public void delete( Block block ) {
		this.session.beginTransaction();
		this.session.delete( block ) ;
		this.session.getTransaction().commit();
	}
	
	/**
	 * Retorna um bloco dado um Id.<br>
	 * Caso n�o seja encontrado, � disparada uma exception.
	 * @param id
	 * @return Bloco referente ao id.
	 * @throws BlockNotFoundException 
	 */
	public Block getById( Integer id ) throws BlockNotFoundException {
		
		if ( id == null ) {
			throw new BlockNotFoundException() ;
		}
		
		this.session.beginTransaction() ;
		Block block = (Block) this.session.get( Block.class , id ) ;
		this.session.getTransaction().commit() ;
		
		if ( block == null ) {
			throw new BlockNotFoundException() ;
		}
		
		return block ;
	}
	
}
