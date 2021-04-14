package br.yardplanner.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;

import br.yardplanner.model.Block;
import br.yardplanner.model.Container;
import br.yardplanner.util.Position;

/**
 * Classe que implementa a interface do algoritmo genético para resolver o problema dos containers
 * @author James Lee
 * @todo Refazer a classe assim que possível
 */
public class Sequence implements GeneticAlgorithmInterface {
	
	/**
	 * Instancia da logger
	 */
	private Logger log ;
	
	// A sequência de retirada
	private List<Integer> genes ;
	
	// custo da sequÃªncia
	private Double cost ;
	
	// Lista de mensagens
	private List<String> msg;
	
	// Velocidade xyz do gancho e velocidade de travamento (vt)
	// Sâ€¹o variáveis usadas para calcular o custo
	private int vx = 1 ;
	private int vy = 1 ;
	private int vz = 1 ;
	private int vt = 1 ;
	
	private Block block ;
	private Integer setup ;
	
	/**
	 * Construtor da classe
	 * @param genes Lista de genes
	 * @param block Bloco para as operações
	 */
	public Sequence( List<Integer> genes , Block block ) {
		this.genes = genes ;
		this.block = block ;
		this.setup = 0 ;
		this.msg = new ArrayList<String>() ;
		this.log = Logger.getLogger( Sequence.class.getName() ) ;
		this.cost = this.calcCost() ;
	}
	
	/**
	 * Clonagem do objeto
	 * @return nova instancia da classe
	 */
	public Sequence clone() {
		return new Sequence( genes , block ) ;
	}
	
	/**
	 * Representaçnao em string do objeto
	 */
	public String toString() {
		StringBuilder out = new StringBuilder() ;
		
		if ( cost < 0 ) {
			out.append( "Seq: " + genes ) ;
			out.append( "Erro: Não há espaço para movimentos de setup" ) ;
		}
		else {
			out.append( "Seq: " + genes ) ;
			out.append( "Custo: " + cost ) ;
			out.append( "Mov Setup: " + setup ) ;
		}
		
		return out.toString() ;
	}
	
	/**
	 * Seta vários parametros de uma única vez
	 * 
	 * @param genes Lista de genes
	 * @param block Bloco
	 * @param setup Valor para setup
	 */
	public void set(List<Integer> genes,Block block,Integer setup){
		this.genes=genes;
		this.block=block;
		this.setup=setup;
		cost=calcCost();
	}
	
	/**
	 * Seta os genes
	 * @param genes Lista de genes
	 */
	public void setGenes( List<Integer> genes ) {
		this.genes = genes ;
		cost=calcCost() ;
	}
	
	/**
	 * Seta o bloco
	 * @param block Bloco
	 */
	public void setBlock(Block block){
		this.block=block;
		cost=calcCost();
	}
	
	/**
	 * @return Retorna uma lista de genes
	 */
	public List<Integer> getGenes() {
		return genes;
	}
	
	/**
	 * @return Retorna o bloco atual
	 */
	public Block getBlock(){
		return block;
	}
	
	/**
	 * @return Retorna o valor do setup
	 */
	public Integer getSetup(){
		return setup;
	}
	
	/**
	 * Retorna o custo para a sequencia dada
	 */
	public double getCost(){
		return cost;
	}
	
	/**
	 * Faz o calculo do custo
	 */
	public double calcCost(){
        // Pilha de containers acima do que desejo remover
        Stack<Container> containersOnTop = new Stack<Container>() ;
        
        //posicao atual do gancho
        Position ganchoAuxPosition = new Position();
        
        //variavel auxiliar para ganhar as posicoes
        Position pos;
        
        //container selecionado
        Container cont;
        
        //interadores
        int i ;
        
        //o numero de movimentos de xyz
        int x=0,y=0,z=0;
        
        //variaveis usadas em movimentos de setup para guardar o xyz atual
        int x1,y1,z1;
        
        //variaveis auxiliares para movimentos de setup
        int k,k2;
        
        //variaveis auxiliares de posicoes xyz
        int posx,posy,posz;
        
        //numero de setups
        int setups=0;
	
		//limpar mensagens
		msg.clear();
	
        // Clonagem do bloco
        Block blockClone = block.clone() ;
        Map<Integer,Container> containersMapId = blockClone.getContainersMapId() ;
        Map<Position,Container> containersMapPos = blockClone.getContainersMap() ;
        
        ganchoAuxPosition.setPosX( block.getHook().getPosX() ) ;
        ganchoAuxPosition.setPosY( block.getHook().getPosY() ) ;
        
        Iterator<Integer> genesIt = genes.iterator() ;
        Integer gene ; 

        while ( genesIt.hasNext() ) {
        	gene = genesIt.next() ;
        	
        	//buscar uma referencia do container atraves do Id
            cont = containersMapId.get( gene ) ;
            
            //verificar quantos containers existem encima do container
            //selecionado e guardar na lista 'acima'
            posx=cont.getPosX();
            posy=cont.getPosY();
            posz=cont.getPosZ()+1;
            pos=new Position(posx,posy,posz);
            while(containersMapPos.get(pos)!=null){
                setups++;
                containersOnTop.push( containersMapPos.get( pos ) ) ;
                posz++;
                pos.setPosZ(posz);
            }

            //mover o gancho ate o primeiro container da sequencia
            //e pegar o container do topo
            x= x + Math.abs( ganchoAuxPosition.getPosX() - posx ) ;
            y = y + Math.abs( ganchoAuxPosition.getPosY() - posy ) ;
            
            posz--;
            pos.setPos(posx, posy, posz);
            z=z+2*(block.getLayers()-posz);

            //caso tenha containers acima
            x1=posx;
            y1=posy;
            while ( ! containersOnTop.empty() ) {
                // retirar container do mapeamento de posicoes
                containersMapPos.remove( pos ) ;
                k=8;
                z1=posz;
                posz=block.getLayers()-1;
                posx++;
                pos.setPos(posx, posy, posz);
                k2=7;
                while ( posx < 0 || posy < 0 || posx>=block.getColumns() || posy>=block.getLines() || containersMapPos.get( pos ) != null ) {
                    if ( k2 >= k - k / 8 ) posy-- ;
                    else if ( k2 >= k / 2 + k / 8 ) posx-- ;
                    else if ( k2 >= k/2-k/8)posy++ ;
                    else if ( k2 >= k/8)posx++ ;
                    else if ( k2 >= 1)posy-- ;
                    else {
                    	k = k + 8 ;
                    	k2 = k ;
                    	posx = x1 + k / 8 ;
                    	posy = y1 ;
                    }
                    k2-- ;
                    pos.setPos(posx, posy, posz);
                    if (posx<=-block.getColumns() || posx>=(block.getColumns()*2)-1){return -1;}
                }
                
                x = x + 2 * Math.abs( posx - x1 ) ;
                y = y + 2 * Math.abs( posy - y1 ) ;
                posz = 0 ;
                pos.setPos( posx , posy , posz ) ;
                while ( containersMapPos.get( pos ) != null ) {
                    posz++;
                    pos.setPos(posx, posy, posz);
                }
                z=z+2*(block.getLayers()-posz) ;
                
                cont=containersOnTop.pop() ;
                
                msg.add( "Mover o container " + cont.getCodeFormmated() + " de " + cont.getPosition() + " para " + pos ) ;
                this.log.debug( "Custo: Mover o container " + cont.getCodeFormmated() + " " + cont.getPosition() + " para " + pos ) ;
                
                cont.setPosX( posx ) ;
                cont.setPosY( posy ) ;
                cont.setPosZ( posz ) ;
                
                // registrar container na nova posicao no mapa de posicoes
                containersMapPos.put(cont.getPosition(),cont);
                posx = x1 ;
                posy = y1 ;
                posz = z1 - 1 ;
                z = z + 2 * ( block.getLayers() - posz ) ;
                pos.setPos( posx , posy , posz ) ;
            }
            
            pos.setPos( posx , posy , posz ) ;
            
            //retirar o container selecionado!
            cont = containersMapPos.get( pos ) ;
            
            msg.add( "Descarregar o container " + cont.getCodeFormmated() ) ;
            this.log.debug( "Descarregar o container " + cont.getCodeFormmated() + " " + cont.getPosition() + " no ponto de descarga " + block.getDischarge() ) ;
            
            containersMapId.remove( containersMapPos.get( pos ).getContainerId() ) ;
            containersMapPos.remove( pos ) ;
            
            x = x + Math.abs(posx-block.getDischarge().getPosX() ) ;
            y = y + Math.abs(posy-block.getDischarge().getPosY() ) ;
            z = z + 2 * ( block.getLayers() ) ;
            
            ganchoAuxPosition.setPosX( block.getDischarge().getPosX() ) ;
            ganchoAuxPosition.setPosY( block.getDischarge().getPosY() ) ;
        }

        if ( setup != null ) {
        	setup=setups;
        }
        return ( x * block.getContainerSize().getWidth() / vx ) + ( y * block.getContainerSize().getHeight() / vy ) + ( z * block.getContainerSize().getLength() / vz ) + ( ( genes.size() + setups ) * vt ) ;
    }
    
    /**
     * Faz a remoção de uma sequencia
     */
    public void remove() {
    	if ( cost >= 0 ) {
	        try {
	        	
	            //lista de containers a serem retirados que estao acima do container selecionado
	            Stack<Container> containersOnTop = new Stack<Container>();
	            
	            //posicao atual do gancho
	            Position ganchoAuxPosition = new Position() ;
	            
	            // Set de containers
	            Set<Container> containers = this.block.getContainers() ;
	            
	            // Retonra uma lista de ids dos containers
	            List<Integer> containersListIds = this.block.getContainerIds() ;
	            
	            // Relacao de ids dos containers
	            Map<Integer, Container> containersMapId = this.block.getContainersMapId() ;
	            
	            // Relação de ids dos containers e posições
	            Map<Position, Container> containersMapPos = this.block.getContainersMap() ;
	            
	            //variavel auxiliar para ganhar as posicoes
	            Position pos;
	            
	            //container selecionado
	            Container cont;
	            
	            //interadores
	            int i ;
	            
	            //variaveis usadas em movimentos de setup para guardar o xyz atual
	            int x1,y1,z1;
	            
	            //variaveis auxiliares para movimentos de setup
	            int k,k2;
	            
	            //variaveis auxiliares de posicoes xyz
	            int posx,posy,posz;
	
	            ganchoAuxPosition.setPosX( block.getHook().getPosX() ) ;
	            ganchoAuxPosition.setPosY( block.getHook().getPosY() ) ;
	            
	            this.log.debug( "Genes: " + this.genes ) ;
	            Iterator<Integer> genesIt = genes.iterator() ;
	            Integer gene ; 

	            while ( genesIt.hasNext() ) {
	            	gene = genesIt.next() ;	            	
	            	
	                //buscar uma referencia do container atraves do Id
	            	cont = containersMapId.get( gene ) ;
	                
	                this.log.debug( "Cont, by id: " + cont ) ;
	                
	                //verificar quantos containers existem encima do container
	                //selecionado e guardar na lista 'acima'
	                posx = cont.getPosX() ;
	                posy = cont.getPosY() ;
	                posz = cont.getPosZ() + 1 ;
	                pos = new Position( posx , posy , posz ) ;
	                
	                this.log.debug( "Posicao atual: " + pos ) ;
	                this.log.debug( "Ver containers acima: " + containersOnTop ) ;
	                while ( containersMapPos.get( pos ) != null ) {
	                    containersOnTop.push( containersMapPos.get( pos ) ) ;
	                    posz++ ;
	                    pos.setPosZ( posz ) ;
	                }
	                
	                this.log.debug( "Posicao atual (apos ver containers acima): " + pos ) ;
	                this.log.debug( "Ver containers acima (apos ver containers acima): " + containersOnTop ) ;
	
	                //mover o gancho ateh o primeiro containers da sequencia
	                //e pegar o container do topo
	                posz--;
	                pos.setPos( posx , posy , posz ) ;
	                
	                this.log.debug( "Posicao atual (apos decrementar a posz): " + pos ) ;
	
	                //caso tenha containers acima
	                x1 = posx ;
	                y1 = posy ;
	                
	                while ( ! containersOnTop.empty() ) {
	                	this.log.debug( "Ver containers acima (dentro do while): " + containersOnTop ) ;
	                	
	                    //retirar container do mapeamento de posicoes
	                	this.log.debug( "Removendo container (dentro do while): " + containersMapPos.get(pos) ) ;
	                    containersMapPos.remove(pos) ;
	                    
	                    k = 8 ;
	                    z1 = posz ;
	                    posz = block.getLayers() - 1 ;
	                    posx++ ;
	                    pos.setPos( posx , posy , posz ) ;
	                    k2 = 7 ;
	                    
	                    while(posx<0 || posy<0 || posx>=block.getColumns() || posy>=block.getLines() || containersMapPos.get(pos)!=null){
	                        if(k2>=k-k/8)posy--;
	                        else if(k2>=k/2+k/8)posx--;
	                        else if(k2>=k/2-k/8)posy++;
	                        else if(k2>=k/8)posx++;
	                        else if(k2>=1)posy--;
	                        else {k=k+8;k2=k;posx=x1+k/8;posy=y1;}
	                        k2--;
	                        pos.setPos(posx, posy, posz);
	                        if (posx<=-block.getColumns() || posx>=(block.getColumns()*2)-1){throw new Exception();}
	                    }
	                    
	                    posz = 0 ;
	                    pos.setPos( posx , posy , posz ) ;
	                    while ( containersMapPos.get( pos ) != null ) {
	                        posz++;
	                        pos.setPos(posx, posy, posz);
	                    }
	                    
	                    cont = containersOnTop.pop() ;
	                    
	                    this.log.debug( "Remoção: Mover o container Id=" + cont.getContainerId() + " " + cont.getPosition() + " para " + pos ) ;
	                    
	                    cont.setPosX(posx);
	                    cont.setPosY(posy);
	                    cont.setPosZ(posz);
	                    
	                    //registrar container na nova posicao no mapa de posicoes
	                    containersMapPos.put(cont.getPosition(),cont);
	                    posx=x1;
	                    posy=y1;
	                    posz=z1-1;
	                    pos.setPos(posx, posy, posz);
	                }
	                
	                pos.setPos(posx, posy, posz);
	                
	                //retirar o container selecionado!
	                containers.remove( containersMapPos.get( pos ) ) ;
	                containersListIds.remove( gene ) ;
	                containersMapId.remove( gene ) ;
	                containersMapPos.remove( pos ) ;
	                
	                // Alterando as posições X,Y do gancho
	                ganchoAuxPosition.setPosX( block.getDischarge().getPosX() ) ;
	                ganchoAuxPosition.setPosY( block.getDischarge().getPosY() ) ;
	            }
	            this.block.setContainers( containers ) ;
	        }
	        catch(Exception e){
	            e.printStackTrace();
	        }
    	}
    }
   
    /**
     * Seta o custo para movimentação nos eixos X, Y e Z
     * 
     * @param x
     * @param y
     * @param z
     * @param t
     */
    public  void setV( int x , int y , int z , int t ) {
        setVx(x) ;
        setVy(y) ;
        setVz(z) ;
        setVt(t) ;
    }

    
    /**
     * Seta o custo para movimentações no eixo X
     * @param vx Custo para o eixo X
     */
    public void setVx(int vx) {
        this.vx = vx;
    }

    /**
     * @return Retorna o custo para movimentações em X
     */
    public int getVx() {
        return vx;
    }

    /**
     * Seta o custo para movimentações no eixo Y
     * @param vy Custo para o eixo Y
     */
    public void setVy(int vy) {
    	this.vy = vy;
    }

    /**
     * @return Retorna o custo para movimentações em Y
     */
    public int getVy() {
        return vy;
    }

    /**
     * Seta o custo para movimentações no eixo Z
     * @param vz Custo para o eixo Z
     */
    public void setVz(int vz) {
    	this.vz = vz;
    }

    /**
     * @return Retorna o custo para movimentações em Z
     */
    public int getVz() {
        return vz;
    }

    /**
     * Seta o custo para em T
     * @param vt Custo para T
     */
    public void setVt(int vt) {
    	this.vt = vt;
    }

    /**
     * @return Retorna o custo para T
     */
    public int getVt() {
        return vt;
    }

	/**
	 * @return Retorna uma lista com as mensagens das movimentações
	 */
	public List<String> getMsg() {
		return msg;
	}
}