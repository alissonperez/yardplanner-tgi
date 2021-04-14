package br.yardplanner.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.* ;

import org.apache.log4j.Logger;

import br.yardplanner.exceptions.ContainerCodeInvalidException;
import br.yardplanner.exceptions.ContainerPositionColisionException;
import br.yardplanner.util.Position;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

/**
 * Classe Container
 * @author Alisson
 */
@Entity
@Table(name="Containers")
public class Container {
	/**
	 * Instancia da logger
	 */
	@Transient
	private Logger log ;
	
	/**
	 * Chave primária do container
	 */
	@Id
	@GeneratedValue
	@Column(name="container_id")
	private Integer containerId ;
	
	/**
	 * Posição X
	 */
	@Column(name="pos_x")
	private Integer posX ;

	/**
	 * Posição Y
	 */
	@Column(name="pos_y")
	private Integer posY ;

	/**
	 * Posição Z
	 */
	@Column(name="pos_z")
	private Integer posZ ;
	
	/**
	 * Objeto que referencia a posição do container
	 */
	@Transient
	private Position position ;
	
	/**
	 * Código do container
	 */
	private String code ;
	
	/**
	 * Bloco que pertence o container
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "block_id" )
	private Block block ;
	
	/**
	 * Patio que o container pertence
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "yard_id" )
	private Yard yard ;
	
	/**
	 * Tamanho do container
	 */
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "size_id" )
	private ContainerSize size ;
	
	/**
	 * Construtor sem parametros
	 */
	public Container() {
		this.log = Logger.getLogger( Container.class.getName() ) ;
	}
	
	/**
	 * Construtor com parametros
	 * 
	 * @param yard
	 * @param block
	 * @param containerId
	 * @param x
	 * @param y
	 * @param z
	 */
	public Container( Yard yard , Block block , Integer containerId , String code , Integer x , Integer y , Integer z ) {
		this.yard = yard ;
		this.block = block ;
		this.containerId = containerId ;
		this.code = code ;
		this.posX = x ;
		this.posY = y ;
		this.posZ = z ;
		this.position = new Position( x , y , z ) ;
		this.log = Logger.getLogger( Container.class.getName() ) ;
	}
	
	/**
	 * Instância o position caso não tenha sido feita
	 */
	private void instancePosition() {
		if ( this.position == null ) {
			this.position = new Position( this.posX , this.posY , this.posZ ) ;
		}
	}
	
	/**
	 * @return Retorna o id do container no banco
	 */
	public Integer getContainerId() {
		return containerId;
	}

	/**
	 * Seta o id do container
	 * @param containerId Id do container
	 */
	public void setContainerId(Integer containerId) {
		this.containerId = containerId;
	}

	/**
	 * @return Retorna a posição X do container
	 */
	public Integer getPosX() {
		return posX ;
	}

	/**
	 * Seta a posiçnao X do container
	 * @param posX Posição X do container
	 */
	@RequiredFieldValidator( message = "O campo 'Coluna' é obrigatório" , shortCircuit = true )
	@RegexFieldValidator( message = "O campo 'Coluna' deve conter apenas números inteiros positivos" , expression = "[0-9]+" )
	public void setPosX(Integer posX) {
		this.instancePosition() ;
		this.posX=posX;
		this.position.setPosX(posX);
	}

	/**
	 * @return Retorna a posição Y do container
	 */
	public Integer getPosY() {
		return posY;
	}

	/**
	 * Seta a posição Y do container
	 * @param posY Posição Y do container
	 */
	@RequiredFieldValidator( message = "O campo 'Linha' é obrigatório" , shortCircuit = true )
	@RegexFieldValidator( message = "O campo 'Linha' deve conter apenas números inteiros positivos" , expression = "[0-9]+" )
	public void setPosY(Integer posY) {
		this.instancePosition() ;
		this.posY=posY;
		this.position.setPosY(posY);
	}

	/**
	 * @return Retorna a posição Z do container
	 */
	public Integer getPosZ() {
		return posZ;
	}

	/**
	 * Seta a posição Z do container
	 * @param posZ Posição Z do container
	 */
	@RequiredFieldValidator( message = "O campo 'Camada' é obrigatório" , shortCircuit = true )
	@RegexFieldValidator( message = "O campo 'Camada' deve conter apenas números inteiros positivos" , expression = "[0-9]+" )
	public void setPosZ(Integer posZ) {
		this.instancePosition() ;
		this.posZ=posZ;
		this.position.setPosZ( posZ);
	}
	
	/**
	 * @return Retorna a posição do container
	 */
	public Position getPosition() {
		this.instancePosition() ;
		this.log.debug( this.position ) ;
		if ( this.position == null ) {
			this.log.debug( "Position null:" ) ;
			this.position = new Position( this.posX , this.posY , this.posZ ) ;
		}
		
		return this.position ;
	}

	/**
	 * @return Retorna o bloco ao qual pertence o container
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * Seta o bloco do qual o container pertence
	 * @param block Bloco do qual o container pertence
	 */
	public void setBlock(Block block) {
		this.block = block;
	}

	/**
	 * @return Retorna o pátio que o container pertence
	 */
	public Yard getYard() {
		return yard;
	}

	/**
	 * Seta o pátio que o container pertence
	 * @param yard Pátio que o container pertence
	 */
	public void setYard(Yard yard) {
		this.yard = yard;
	}

	/**
	 * @return Retorna o tamanho do container
	 */
	public ContainerSize getSize() {
		return size;
	}

	/**
	 * Seta o tamanho do container
	 * @param size Tamanho do container
	 */
	public void setSize(ContainerSize size) {
		this.size = size;
	}

	/**
	 * @return Código do container
	 */
	public String getCode() {
		return code ;
	}
	
	/**
	 * Retorna o código formatado
	 */
	public String getCodeFormmated() {
		return ContainerCode.getCodeFormmated( this.code ) ;
	}

	/**
	 * @param code Código do container
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Retorna a parte numérica do código do container
	 */
	public String getCodeNumber() {
		if ( this.code != null ) {
			return this.code.substring(6) ;			
		}
		
		return "" ;
	}
	
	/**
	 * Valida o código do container
	 * @throws ContainerCodeInvalidException 
	 */
	public void checkCode() throws ContainerCodeInvalidException {
		ContainerCode code = new ContainerCode( this.code ) ;
		if ( ! code.isValid() ) {
			throw new ContainerCodeInvalidException( "Código " + code.getCode() + " do container inválido" ) ;
		}
	}
	
	/**
	 * Gera um código aleatório
	 */
	public void generateRandomCode() {
		this.code = ContainerCode.getRandomCode().getCode() ;
	}
	
	/** 
	 * @return Checa se o código do container está vazio (=="")
	 */
	public Boolean isEmptyCode() {
		return this.code != null && this.code.equals( "" ) ;
	}

	/**
	 * Faz um clone do container
	 */
	public Container clone(){
		return new Container( yard , block , containerId , code , posX , posY , posZ ) ;
	}
	
	/**
	 * Representação em string do container
	 */
	public String toString() {
		return "Code: " + this.code + ", id: " + this.containerId ;
	}
	
	/**
	 * Retorna um map de containers baseado em uma lista
	 * 
	 * @param containersSet
	 * @return
	 * @throws ContainerPositionColisionException
	 */
	public static Map<Position, Container> getContainersMapOfSet( Set<Container> containersSet ) throws ContainerPositionColisionException {
		Map<Position, Container> containersMap = new HashMap<Position, Container>() ;

		Iterator<Container> it = containersSet.iterator() ;
		Container container ;
		Position position ;
		while ( it.hasNext() ) {
			container = it.next() ;
			position = container.getPosition() ;
			if ( containersMap.containsKey( position ) ) {
				throw new ContainerPositionColisionException( "Já existe um container na coluna " + position.getPosX() + ", linha " + position.getPosY() + " e camada " + position.getPosZ() ) ;
			}
			
			containersMap.put( position , container ) ;
		}

		return containersMap ;
	}
	
	/**
	 * Verifica se os códigos dos containers estão corretos
	 * @param containersSet Set de containers para verificação
	 * @return
	 * @throws ContainerCodeInvalidException 
	 */
	public static boolean checkContainersCode( Set<Container> containersSet ) throws ContainerCodeInvalidException {
		Iterator<Container> it = containersSet.iterator() ;
		Container container ;
		
		while ( it.hasNext() ) {
			container = it.next() ;
			if ( ! container.isEmptyCode() ) {
				container.checkCode() ;
			}
		}
		
		return true ;
	}
}
