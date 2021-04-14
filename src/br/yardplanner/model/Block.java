package br.yardplanner.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.* ;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import br.yardplanner.dao.BlockDAO;
import br.yardplanner.exceptions.BlockNotFoundException;
import br.yardplanner.exceptions.ContainerCodeInvalidException;
import br.yardplanner.exceptions.ContainerFloatingException;
import br.yardplanner.exceptions.ContainerIdConflictException;
import br.yardplanner.exceptions.ContainerPositionColisionException;
import br.yardplanner.exceptions.FullStackException;
import br.yardplanner.exceptions.PositionOnOutsideException;
import br.yardplanner.util.Position;
import br.yardplanner.util.YardPlannerSession;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

/**
 * Model do Bloco
 * @author Alisson
 */
@Entity
@Table(name="Blocks")
public class Block {
	
	/**
	 * Id do bloco no banco
	 */
	@Id
	@GeneratedValue
	@Column(name="block_id")
	private Integer blockId ;
	
	/**
	 * Nome do bloco
	 */
	private String name ;
	
	/**
	 * Num de linhas do bloco
	 */
	@Column( name = "bl_lines" )
	private Integer lines ;
	
	/**
	 * Colunas do bloco
	 */
	@Column( name = "bl_columns" )
	private Integer columns ;
	
	/**
	 * Camadas do bloco
	 */
	@Column( name = "bl_layers" )
	private Integer layers ;
	
	/**
	 * Pátio do qual o bloco pertence
	 */
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "yard_id" )
	private Yard yard ;
	
	/**
	 * Tamanho aceito para os containers
	 */
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "container_size" )
	private ContainerSize containerSize ;
	
	/**
	 * Constainers do bloco
	 */
	@OneToMany( mappedBy = "block" , fetch = FetchType.LAZY , orphanRemoval = true )
	@Cascade( CascadeType.ALL )
	private Set<Container> containers ;
	
	/**
	 * Map dos containers para busca em O(1) usando tabelas HashMap
	 */
	@Transient
	private Map<Position, Container> containersMap ;
	
	/**
	 * Map dos ids dos containers
	 */
	@Transient
	private Map<Integer, Container> containersMapId ;
	
	/**
	 * Lista com os ids de todos os containers
	 */
	@Transient
	private List<Integer> containerIds ;
	
	/**
	 * Posição do Gancho
	 */
	@Transient
	private Position hook ;
	
	/**
	 * Posição do ponto de descarga
	 */
	@Transient
	private Position dischargePosition ;
	
	/**
	 * Lista de mensagens de erro ocorridas ao tentar inserir containers em lote (Arquivo XML)
	 */
	@Transient
	private List<String> errorMessages ;
	
	/**
	 * Instancia da logger
	 */
	@Transient
	private Logger log ;
	
	/**
	 * Construtor vazio
	 */
	public Block() {
		this.hook = new Position( -1 , -1 ) ;
		this.dischargePosition = new Position( -1 , -1 ) ;
		this.log = Logger.getLogger(Block.class.getName()) ;
	}
	
	/**
	 * Construtor
	 * 
	 * @param yard Pátio
	 * @param id Id do bloco
	 * @param columns Num de colunas do bloco
	 * @param lines Num de linhas do bloco
	 * @param layers Num de camadas do bloco
	 */
	public Block( Yard yard , int id , int columns , int lines , int layers ) {
		this.yard = yard ;
		this.blockId = id ;
		this.columns = columns ;
		this.lines = lines ;
		this.layers = layers ;
		this.containerIds = new ArrayList<Integer>() ;
		this.containersMapId = new HashMap<Integer,Container>() ;
		this.containersMap = new HashMap<Position,Container>() ;
				
		this.hook = new Position( -1 , -1 ) ;
		this.dischargePosition = new Position( -1 , -1 ) ;
		this.containers = new HashSet<Container>() ;
		this.containerSize = new ContainerSize() ;
		this.containerSize.setHeight( ContainerSize.HEIGHT_DEFAULT ) ;
		this.containerSize.setWidth( ContainerSize.WIDTH_DEFAULT ) ;
		this.containerSize.setLength( ContainerSize.LENGTH_DEFAULT ) ;
		this.log = Logger.getLogger(Block.class.getName()) ;
	}
	
	/**
	 * Faz a clonagem do objeto
	 */
	public Block clone() {
		Block b = new Block( this.yard , this.blockId , this.columns , this.lines , this.layers ) ;
		Container cont;
		for(int i=0;i<containerIds.size();i++){
    		cont=containersMapId.get(containerIds.get(i));
    		cont=cont.clone();
    		b.getContainers().add(cont);
    		b.getContainersMapId().put(cont.getContainerId(), cont);
    		b.getContainersMap().put(cont.getPosition(), cont);
    		b.getContainerIds().add(cont.getContainerId());
    	}
		
		// Ponto de descarga e gancho
		b.setHook( this.hook.clone() ) ;
		b.setDischarge( this.dischargePosition.clone() ) ;
        return b;
	}
	
	/**
	 * Popula os mapas de containers indexando pelas posições e id's dos mesmos
	 */
	private void populateContainers() {
		Set<Container> containers = this.getContainers() ;
		
		this.containersMap = new HashMap<Position, Container>() ;
		this.containersMapId = new HashMap<Integer, Container>() ;
		this.containerIds = new ArrayList<Integer>() ;
		
		Iterator<Container> iterator = (Iterator<Container>) containers.iterator() ;
		Container container ;
		while ( iterator.hasNext() ) {
			container = (Container) iterator.next() ;
			
			this.log.trace( "Populando container.." + container.getCode() ) ;
			
			this.containersMap.put( new Position( container.getPosX() , container.getPosY() , container.getPosZ() ) , container ) ;
			this.containersMapId.put( container.getContainerId() , container ) ;
			this.containerIds.add( container.getContainerId() ) ;
		}
	}
	
	
	/**
	 * Retorna a representação do bloco em uma lista de 3 dimensões
	 * Foi criado assim para facilitar a exibição no frontend usando iterators do Struts 2. Ex:<pre>
	 * 
	 * <b>(Código não foi executado, apenas para exemplificação)</b>
	 * 
	 * ArrayList&lt;ArrayList&lt;ArrayList&lt;Container&gt;&gt;&gt; camadas = indexActionInstance.getBlockList() ;
	 * for ( linhas : camadas ) {
	 * 	for ( containers : linhas ) {
	 * 		for ( container : containers ) {
	 * 			// ...
	 * 		}
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @author alissonperez
	 */
	public ArrayList<ArrayList<ArrayList<Container>>> getListRepresentation() {
		
		// Camadas do bloco
		ArrayList<ArrayList<ArrayList<Container>>> levels ;
		
		// Linhas do bloco
		ArrayList<ArrayList<Container>> lines ;
		
		// Colunas de cada linha com os seus containers
		ArrayList<Container> columns ;
		
		// Indices usados para percorrer as linhas e colunas
		Integer lin_i = 0 , col_i = 0 , lay_i = 0 ;		
		Container container ;		
		Position position = new Position() ;		
		levels = new ArrayList<ArrayList<ArrayList<Container>>>() ;
		
		this.log.debug("Retornando Lista do bloco") ;
		
		// Percorrendo os camadas do bloco 
		while( lay_i < this.getLayers() ) {
			lin_i = 0 ; // Resetando a linha
			
			this.log.debug("Camada: " + lay_i ) ;
			
			lines = new ArrayList<ArrayList<Container>>() ;
			
			// Percorrendo as linhas
			while ( lin_i < this.getLines() ) {
				col_i = 0 ; // Resetando a coluna
				
				this.log.debug("Linha: " + lin_i ) ;
				
				columns = new ArrayList<Container>() ;
				
				// Percorrendo as colunas e obtendo os containers
				while( col_i < this.getColumns() ) {
					this.log.debug("Coluna: " + col_i ) ;
					
					position.setPosX( col_i ) ;
					position.setPosY( lin_i ) ;
					position.setPosZ( lay_i ) ;
					
					this.log.debug( position ) ;
					
					container = this.getContainerOnPosition( position ) ;
					
					this.log.debug( "CONTAINER: " + container ) ;
					
					columns.add( container ) ;
					
					col_i++ ;
				}
				
				lines.add( columns ) ;
				
				lin_i++ ;
			}
			
			levels.add( lines ) ;
			
			lay_i++ ;
		}
		
		return levels ;
	}
	
	/**
	 * Retorna um Set de containers baseado em um arquivo XML.<br>
	 * Utilizado no upload de XMLS para popular o pátio
	 * 
	 * @param xmlStr
	 * @param containerSet
	 * @author Alisson (baseado em código de Josué Salvador)
	 * @return Set de containers
	 */
	public Set<Container> getContainersFromXML( File xmlFile , Set<Container> containerSet ) {		
		this.log.trace( "Obtendo containers.." ) ;
		
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance() ;
			DocumentBuilder docBuilder;
			docBuilder = docBuilderFactory.newDocumentBuilder();
			
			// Lendo o documento
			Document document = docBuilder.parse( xmlFile ) ;
			
			Element blockElem = document.getDocumentElement() ;
			
			this.log.trace( "Raiz: " + blockElem.getNodeName() ) ;
			
			NodeList containersList = blockElem.getElementsByTagName( "container" ) ;
			
			Element containerElem ;
			Container container ;
			Integer posX, posY, posZ ;
			String code ;
			
			// Percorrendo a lista de containers
			for ( int i = 0 ; i < containersList.getLength() ; i++ ) {
				containerElem = ( Element ) containersList.item( i ) ;
				this.log.debug( "Container: " + containerElem.getAttribute( "code" ) ) ;
				
				posX = Integer.parseInt( containerElem.getAttribute( "posX" ).trim() ) ;
				posY = Integer.parseInt( containerElem.getAttribute( "posY" ).trim() ) ;
				posZ = Integer.parseInt( containerElem.getAttribute( "posZ" ).trim() ) ;
				code = containerElem.getAttribute( "code" ).trim() ;
				
				container = new Container( this.yard , this , null , code , posX , posY , posZ ) ;
				this.log.debug( container ) ;
				container.setSize( this.containerSize ) ;
				
				if ( container.isEmptyCode() ) {
					container.generateRandomCode() ;
				}
				
				containerSet.add( container ) ;
			}
		
		} catch ( ParserConfigurationException e ) {
			this.log.trace(e.getMessage()) ;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( SAXException e ) {
			this.log.trace(e.getMessage()) ;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( IOException e ) {
			this.log.trace(e.getMessage()) ;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return containerSet ;
	}
	
	/**
	 * Verifica se uma lista de containers (Set de containers) é válida. Caso não, dispara uma série de Exceptions.
	 * 
	 * @param containersSet Set de cotainers para verificação
	 * 
	 * @throws ContainerPositionColisionException
	 * @throws ContainerFloatingException
	 * @throws PositionOnOutsideException 
	 */
	public Boolean checkContainersListPosition( Set<Container> containersSet ) throws ContainerPositionColisionException, ContainerFloatingException, PositionOnOutsideException {
		Map<Position, Container> containersMap ;
		
		// Obtendo o Map de um Set
		containersMap = Container.getContainersMapOfSet( containersSet ) ;
		
		if ( this.containersMap == null ) {
			this.populateContainers() ;
		}
		
		Position position ;
		for ( Map.Entry<Position, Container> mapEntry: containersMap.entrySet() ) {
			position = mapEntry.getKey() ;
			
			// Verifica se a posição esta dentro das extremidades do pátio			
			if ( this.isPositionOnInside( position ) ) {
				
				// Conflito de containers nas posições já existentes
				if ( this.containersMap.containsKey(mapEntry.getKey()) ) {
					throw new ContainerPositionColisionException( "Conflito de containers na coluna " + position.getPosX() + ", linha " + position.getPosY() + " e camada " + position.getPosZ() ) ;
				}
				
				// Caso este container não esteja na primeira camada e não tenha nenhum container abaixo do mesmo
				if ( ! ( this.hasContainerBellow( position , containersMap ) || this.hasContainerBellow( position ) ) ) {
					throw new ContainerFloatingException( "Não há um container na coluna " + position.getPosX() + ", linha " + position.getPosY() + " e camada " + ( position.getPosZ() - 1 ) ) ;
				}
				
			}
			
		}
		
		return true ;
	}
	
	/**
	 * Checa se uma posição está dentro das extremidades do bloco
	 * 
	 * @param position Posição que será verificada
	 * @return True caso a posição esteja dentro das extremidades.
	 * @throws PositionOnOutsideException
	 */
	public Boolean isPositionOnInside( Position position ) throws PositionOnOutsideException {
		
		if ( position == null ) {
			return false ;
		}
		
		if ( position.getPosX() != null && ( position.getPosX() < 0 || position.getPosX() >= this.columns ) ) {
			throw new PositionOnOutsideException( "Coluna " + position.getPosX() + " fora das extremidades do pátio" ) ;
		}
		
		if ( position.getPosY() != null && ( position.getPosY() < 0 || position.getPosY() >= this.lines ) ) {
			throw new PositionOnOutsideException( "Linha " + position.getPosY() + " fora das extremidades do pátio" ) ;
		}
		
		if ( position.getPosZ() != null && ( position.getPosZ() < 0 || position.getPosZ() >= this.layers ) ) {
			throw new PositionOnOutsideException( "Camada " + position.getPosZ() + " fora das extremidades do pátio" ) ;
		}
		
		return true ;
	}
	
	/**
	 * @param Position
	 * @return Retorna um container dada uma posição
	 */
	private Container getContainerOnPosition( Position position ) {
		Container container = new Container() ;
		
		if ( this.containersMap == null ) {
			this.populateContainers() ;
		}
		
		this.log.trace( "Localizando containers na posição: " + position ) ;
		if ( this.containersMap.containsKey( position ) ) {
			container = this.containersMap.get( position ) ;
			this.log.trace( "Container encontrado.." + container ) ;
		}
		
		return container ;
	}
	
	/**
	 * @param position Posição que será verificada
	 * @return Retorna true se há um container em uma determinada posição
	 */
	public Boolean hasContainerOnPosition( Position position ) {
		if ( this.containersMap == null ) {
			this.populateContainers() ;
		}
		
		return this.containersMap.containsKey( position ) ;
	}
	
	/**
	 * @param position Posição que será verificada
	 * @return Retorna true se houver um container abaixo da posição informada.
	 */
	public Boolean hasContainerBellow( Position position ) {
		if ( position.getPosZ() > 0 ) {
			Position positionBellow = new Position( position.getPosX() , position.getPosY() , position.getPosZ() - 1 ) ;
			return this.hasContainerOnPosition( positionBellow ) ;
		}
		
		return true ;
	}
	
	/**
	 * @param position Posição desejada
	 * @param containersMap Mapa de containers que a checagem será feita
	 * @return Retorna true se houver algum container abaixo da posição passada no map de containers informado 
	 */
	public Boolean hasContainerBellow( Position position , Map<Position, Container> containersMap ) {
		if ( position.getPosZ() > 0 ) {
			Position positionBellow = new Position( position.getPosX() , position.getPosY() , position.getPosZ() - 1 ) ;
			return containersMap.containsKey( positionBellow ) ;
		}
		
		return true ;
	}
	
	/**
	 * Dada uma lista de ids, checa se todos estão entre os containers do bloco
	 * @param listIds Lista de ids
	 * @return True se todos os ids estiverem no bloco
	 */
	public Boolean hasContainerIds( List<Integer> listIds ) {
		if ( this.containersMapId == null ) {
			this.populateContainers() ;
		}
		
		Iterator<Integer> it = listIds.iterator() ;
		while ( it.hasNext() ) {
			if ( ! this.containersMapId.containsKey( it.next() ) ) {
				return false ;
			}
		}
		
		return true ;
	}
	
	/**
     * Adiciona um container em uma posiçõo X e Y, a posição Z é localizada automaticamente
     * 
     * @param containerID
     * @param x Posição X
     * @param y Posição Y
     * 
	 * @throws PositionOnOutsideException 
	 * @throws FullStackException 
	 * @throws ContainerIdConflictException 
     */
    public void add( int containerID , int x , int y ) throws PositionOnOutsideException, FullStackException, ContainerIdConflictException {
    	if ( this.containerIds == null ) {
    		this.populateContainers() ;
    	}
    	
    	// Verifica se o id do container já está sendo usado
        if ( this.containerIds.contains( containerID ) ) {
            throw new ContainerIdConflictException() ;
        }
        else {
    		int z = 0 ;
    		Position pos = new Position( x , y , z ) ;
    		
    		if ( this.isPositionOnInside( pos ) ) {
    			while ( containersMap.get( pos ) != null ) {
    				z++ ;
    				pos.setPos( x , y , z ) ;
    				
    				// Caso a pilha esteja cheia
    				if ( z > this.layers ) {
    					throw new FullStackException() ;
    				}
    			}
    			
    			Container c = new Container( this.yard , this , containerID , null , x , y , z ) ;
    			c.generateRandomCode() ;
    			
    			this.containersMapId.put(containerID,c);
    			this.containersMap.put(new Position(x,y,z),c);
    			this.containerIds.add(containerID);
    			this.containers.add(c) ;    			
    		}        		
        }
    }
    
	/**
	 * @return Retorna o bloco do Id
	 */
	public Integer getBlockId() {
		return blockId;
	}

	/**
	 * @param blockId Seta o id do bloco
	 */
	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	/**
	 * @return Retorna o número de linhas do bloco
	 */
	public Integer getLines() {
		return lines;
	}

	/**
	 * Seta as linhas de um bloco
	 * @param lines Linhas do container
	 */
	@RequiredFieldValidator( message = "Campo 'Linhas' obrigatório" , shortCircuit = true )
	@IntRangeFieldValidator( message = "Campo 'Linhas' deve estar entre ${min} e ${max}" , min = "2" , max = "20" )
	public void setLines(Integer lines) {
		this.lines = lines;
	}

	/**
	 * @return Retorna o número de colunas do bloco
	 */
	public Integer getColumns() {
		return columns;
	}

	/**
	 * Seta o número de colunas do bloco
	 * @param width Número de colunas do bloco
	 */
	@RequiredFieldValidator( message = "Campo 'Colunas' obrigatório" , shortCircuit = true )
	@IntRangeFieldValidator( message = "Campo 'Colunas' deve estar entre ${min} e ${max}" , min = "2" , max = "20" )
	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	/**
	 * @return Retorna o pátio
	 */
	public Yard getYard() {
		return yard;
	}

	/**
	 * @param yard Seta o pátio do qual o bloco pertence
	 */
	public void setYard(Yard yard) {
		this.yard = yard;
	}

	/**
	 * @return Retorna o nome do bloco
	 */
	public String getName() {
		return name;
	}

	/**
	 * Seta o nome do bloco
	 * @param name Nome do bloco
	 */
	@RequiredStringValidator( message = "Campo 'Nome' obrigatório" , shortCircuit = true )
	@StringLengthFieldValidator( message = "Campo 'Nome' deve ter no mí­nimo ${minLength} caracteres" , minLength = "4" )
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Retorna o número de camadas do bloco
	 */
	public Integer getLayers() {
		return layers;
	}

	/**
	 * Seta o número de linhas do bloco
	 * @param height Linhas do bloco
	 */
	@RequiredFieldValidator( message = "Campo 'Camadas' obrigatório" , shortCircuit = true )
	@IntRangeFieldValidator( message = "Campo 'Camadas' deve estar entre ${min} e ${max}" , min = "2" , max = "5" )
	public void setLayers(Integer layers) {
		this.layers = layers;
	}

	/**
	 * @return Retorna os containers do bloco
	 */
	public Set<Container> getContainers() {
		return containers;
	}

	/**
	 * @param containers Seta os containers do bloco
	 */
	public void setContainers(Set<Container> containers) {
		this.containers = containers;
		this.populateContainers() ;
	}

	/**
	 * @return Retorna um map de containers indexados pela posição
	 */
	public Map<Position, Container> getContainersMap() {
		return containersMap;
	}

	/**
	 * Seta os containers mapeados pela posição
	 * @param containersMap Map de containers indexados pela posição (X,Y,Z) dos mesmos
	 */
	public void setContainersMap( Map<Position, Container> containersMap ) {
		this.containersMap = containersMap ;
	}

	/**
	 * @return Retorna um mapa de containers indexados pelo dos mesmsos
	 */
	public Map<Integer, Container> getContainersMapId() {
		return containersMapId;
	}

	/**
	 * @param containersMapId Seta um map de containers indexados pelo id dos mesmos
	 */
	public void setContainersMapId(Map<Integer, Container> containersMapId) {
		this.containersMapId = containersMapId;
	}

	/**
	 * @return Retorna uma lista de ids dos containers
	 */
	public List<Integer> getContainerIds() {
		return containerIds;
	}

	/**
	 * Seta a lista de ids dos containers
	 * @param containerIds Lista de ids dos containers
	 */
	public void setContainerIds( List<Integer> containerIds ) {
		this.containerIds = containerIds ;
	}

	/**
	 * @return Retorna a posição do gancho
	 */
	public Position getHook() {
		return hook;
	}

	/**
	 * Seta a posicão do gancho
	 * @param hook Posição do gancho
	 */
	public void setHook(Position hook) {
		this.hook = hook;
	}

	/**
	 * @return Retorna o ponto de descarga
	 */
	public Position getDischarge() {
		return dischargePosition;
	}

	/**
	 * @param discharge Ponto de descarga
	 */
	public void setDischarge(Position discharge) {
		this.dischargePosition = discharge;
	}

	/**
	 * @return Retorna o tamanho de container aceito
	 */
	public ContainerSize getContainerSize() {
		return containerSize;
	}

	/**
	 * Seta o tamanho de container aceito pelo bloco
	 * @param containerSize Tamanho de container aceito pelo bloco
	 */
	public void setContainerSize(ContainerSize containerSize) {
		this.containerSize = containerSize;
	}

	/**
	 * @return Retorna a lista de mensagens de erro ao tentar inserir os containers 
	 */
	public List<String> getErrorMessages() {
		return errorMessages;
	}

	/**
	 * Seta a lista de mensagens de erro da inserção de containers
	 * @param errorMessages Lista de mensagens de erro
	 */
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	/**
	 * Representação em string do bloco
	 */
	@Override
	public String toString() {
		return "Block [blockId=" + blockId + ", name=" + name + "]";
	}
	
	/**
	 * "Printa" os dados do bloco
	 */
	public void print(){
		for (int i=0;i<getContainerIds().size();i++)
			System.out.println(getContainerIds().get(i)+" "+getContainersMapId().get(getContainerIds().get(i)).getPosition());
		
	}
}
