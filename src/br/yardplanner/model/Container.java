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
	 * Chave prim�ria do container
	 */
	@Id
	@GeneratedValue
	@Column(name="container_id")
	private Integer containerId ;
	
	/**
	 * Posi��o X
	 */
	@Column(name="pos_x")
	private Integer posX ;

	/**
	 * Posi��o Y
	 */
	@Column(name="pos_y")
	private Integer posY ;

	/**
	 * Posi��o Z
	 */
	@Column(name="pos_z")
	private Integer posZ ;
	
	/**
	 * Objeto que referencia a posi��o do container
	 */
	@Transient
	private Position position ;
	
	/**
	 * C�digo do container
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
	 * Inst�ncia o position caso n�o tenha sido feita
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
	 * @return Retorna a posi��o X do container
	 */
	public Integer getPosX() {
		return posX ;
	}

	/**
	 * Seta a posi�nao X do container
	 * @param posX Posi��o X do container
	 */
	@RequiredFieldValidator( message = "O campo 'Coluna' � obrigat�rio" , shortCircuit = true )
	@RegexFieldValidator( message = "O campo 'Coluna' deve conter apenas n�meros inteiros positivos" , expression = "[0-9]+" )
	public void setPosX(Integer posX) {
		this.instancePosition() ;
		this.posX=posX;
		this.position.setPosX(posX);
	}

	/**
	 * @return Retorna a posi��o Y do container
	 */
	public Integer getPosY() {
		return posY;
	}

	/**
	 * Seta a posi��o Y do container
	 * @param posY Posi��o Y do container
	 */
	@RequiredFieldValidator( message = "O campo 'Linha' � obrigat�rio" , shortCircuit = true )
	@RegexFieldValidator( message = "O campo 'Linha' deve conter apenas n�meros inteiros positivos" , expression = "[0-9]+" )
	public void setPosY(Integer posY) {
		this.instancePosition() ;
		this.posY=posY;
		this.position.setPosY(posY);
	}

	/**
	 * @return Retorna a posi��o Z do container
	 */
	public Integer getPosZ() {
		return posZ;
	}

	/**
	 * Seta a posi��o Z do container
	 * @param posZ Posi��o Z do container
	 */
	@RequiredFieldValidator( message = "O campo 'Camada' � obrigat�rio" , shortCircuit = true )
	@RegexFieldValidator( message = "O campo 'Camada' deve conter apenas n�meros inteiros positivos" , expression = "[0-9]+" )
	public void setPosZ(Integer posZ) {
		this.instancePosition() ;
		this.posZ=posZ;
		this.position.setPosZ( posZ);
	}
	
	/**
	 * @return Retorna a posi��o do container
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
	 * @return Retorna o p�tio que o container pertence
	 */
	public Yard getYard() {
		return yard;
	}

	/**
	 * Seta o p�tio que o container pertence
	 * @param yard P�tio que o container pertence
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
	 * @return C�digo do container
	 */
	public String getCode() {
		return code ;
	}
	
	/**
	 * Retorna o c�digo formatado
	 */
	public String getCodeFormmated() {
		return ContainerCode.getCodeFormmated( this.code ) ;
	}

	/**
	 * @param code C�digo do container
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Retorna a parte num�rica do c�digo do container
	 */
	public String getCodeNumber() {
		if ( this.code != null ) {
			return this.code.substring(6) ;			
		}
		
		return "" ;
	}
	
	/**
	 * Valida o c�digo do container
	 * @throws ContainerCodeInvalidException 
	 */
	public void checkCode() throws ContainerCodeInvalidException {
		ContainerCode code = new ContainerCode( this.code ) ;
		if ( ! code.isValid() ) {
			throw new ContainerCodeInvalidException( "C�digo " + code.getCode() + " do container inv�lido" ) ;
		}
	}
	
	/**
	 * Gera um c�digo aleat�rio
	 */
	public void generateRandomCode() {
		this.code = ContainerCode.getRandomCode().getCode() ;
	}
	
	/** 
	 * @return Checa se o c�digo do container est� vazio (=="")
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
	 * Representa��o em string do container
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
				throw new ContainerPositionColisionException( "J� existe um container na coluna " + position.getPosX() + ", linha " + position.getPosY() + " e camada " + position.getPosZ() ) ;
			}
			
			containersMap.put( position , container ) ;
		}

		return containersMap ;
	}
	
	/**
	 * Verifica se os c�digos dos containers est�o corretos
	 * @param containersSet Set de containers para verifica��o
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
