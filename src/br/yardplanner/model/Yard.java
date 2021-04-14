package br.yardplanner.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set ;

import javax.persistence.* ;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * M�del do p�tio
 * @author Alisson
 *
 */
@Entity
@Table(name="Yards")
public class Yard {
	
	/**
	 * Id do P�tio
	 */
	@Id
	@GeneratedValue
	@Column(name = "yard_id")
	private Integer yardId ;
	
	/**
	 * Nome do p�tio
	 */
	private String name ;
	
	/**
	 * Usu�rio dono do p�tio
	 */
	@ManyToOne
	@JoinColumn(name="user_id")
	private User owner ;
	
	/**
	 * Blocos deste p�tio
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
	 * @param name nome do p�tio
	 */
	public Yard( String name ) {
		this.name = name ;
	}

	/**
	 * Construtor com nome e id
	 * @param yardId Id do Patio
	 * @param name Nome do p�tio
	 */
	public Yard( Integer yardId , String name ) {
		this.yardId = yardId ;
		this.name = name ;
	}
	
	/**
	 * Construtor com nome, id e dono
	 * 
	 * @param yardId Id do Patio
	 * @param name Nome do p�tio
	 * @param owner Dono do p�tio
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
	 * @param i �ndice do bloco
	 * @return Retorna um bloco da lista dado o �ndice do mesmo na lista 
	 */
	public Block getBlock(int i) {
		return block_list.get(i);
	}
	
	/**
	 * @return Retorna o id do p�tio
	 */
	public Integer getYardId() {
		return yardId;
	}

	/**
	 * Seta o id do p�tio
	 * @param yardId Id do p�tio
	 */
	public void setYardId(Integer yardId) {
		this.yardId = yardId;
	}

	/**
	 * @return Retorna o nome do p�tio
	 */
	public String getName() {
		return name;
	}

	/**
	 * Seta o nome do p�tio
	 * @param name Nome do p�tio
	 */
	@RequiredStringValidator( message = "O campo 'Nome do p�tio' � obrigat�rio" , shortCircuit = true )
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Retorna o dono do p�tio
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * Seta o dono do p�tio
	 * @param owner dono do p�tio
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return Retorna um set com os blocos do p�tio
	 */
	public Set<Block> getBlocks() {
		return blocks;
	}

	/**
	 * Seta os blocos do p�tio
	 * @param blocks Set com os blocos do p�tio
	 */
	public void setBlocks(Set<Block> blocks) {
		this.blocks = blocks;
	}

}
