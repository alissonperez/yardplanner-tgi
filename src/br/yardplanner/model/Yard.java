package br.yardplanner.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set ;

import javax.persistence.* ;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * Módel do pátio
 * @author Alisson
 *
 */
@Entity
@Table(name="Yards")
public class Yard {
	
	/**
	 * Id do Pátio
	 */
	@Id
	@GeneratedValue
	@Column(name = "yard_id")
	private Integer yardId ;
	
	/**
	 * Nome do pátio
	 */
	private String name ;
	
	/**
	 * Usuário dono do pátio
	 */
	@ManyToOne
	@JoinColumn(name="user_id")
	private User owner ;
	
	/**
	 * Blocos deste pátio
	 */
	@OneToMany(mappedBy = "yard",fetch = FetchType.LAZY)
	@Cascade(CascadeType.REMOVE)
	@OrderBy("blockId")
	private Set<Block> blocks ;
	
	/**
	 * Lista de blocos
	 */
	@Transient
	private List<Block> block_list = new ArrayList<Block>() ;
	
	/**
	 * Construtor sem parametros
	 */
	public Yard() {}
	
	/**
	 * Construtor apenas com o nome
	 * @param name nome do pátio
	 */
	public Yard( String name ) {
		this.name = name ;
	}

	/**
	 * Construtor com nome e id
	 * @param yardId Id do Patio
	 * @param name Nome do pátio
	 */
	public Yard( Integer yardId , String name ) {
		this.yardId = yardId ;
		this.name = name ;
	}
	
	/**
	 * Construtor com nome, id e dono
	 * 
	 * @param yardId Id do Patio
	 * @param name Nome do pátio
	 * @param owner Dono do pátio
	 */
	public Yard( Integer yardId , String name , User owner ) {
		this.yardId = yardId ;
		this.name = name ;
		this.owner = owner ;
	}
	
	/**
	 * adicionar um bloco passando o id e comprimentos xyz do mesmo
	 * 
	 * @param id Id do bloco
	 * @param x Comprimento
	 * @param y Largura
	 * @param z Altura
	 */
	public void add( int id , int x , int y , int z ) {
		Block b= new Block( this , id , x , y , z ) ;
		block_list.add(b);
	}
	
	/**
	 * Adicionar um bloco passando o mesmo como parametro
	 * @param b bloco desejado
	 */
	public void add( Block b ) {
		block_list.add(b);
	}
	
	/**
	 * @param i Índice do bloco
	 * @return Retorna um bloco da lista dado o índice do mesmo na lista 
	 */
	public Block getBlock(int i) {
		return block_list.get(i);
	}
	
	/**
	 * @return Retorna o id do pátio
	 */
	public Integer getYardId() {
		return yardId;
	}

	/**
	 * Seta o id do pátio
	 * @param yardId Id do pátio
	 */
	public void setYardId(Integer yardId) {
		this.yardId = yardId;
	}

	/**
	 * @return Retorna o nome do pátio
	 */
	public String getName() {
		return name;
	}

	/**
	 * Seta o nome do pátio
	 * @param name Nome do pátio
	 */
	@RequiredStringValidator( message = "O campo 'Nome do pátio' é obrigatório" , shortCircuit = true )
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Retorna o dono do pátio
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * Seta o dono do pátio
	 * @param owner dono do pátio
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return Retorna um set com os blocos do pátio
	 */
	public Set<Block> getBlocks() {
		return blocks;
	}

	/**
	 * Seta os blocos do pátio
	 * @param blocks Set com os blocos do pátio
	 */
	public void setBlocks(Set<Block> blocks) {
		this.blocks = blocks;
	}

}
