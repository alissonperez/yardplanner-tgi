/**
 * Documentação do JUnit
 * 
 * http://junit.sourceforge.net/
 */
package br.yardplanner.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.yardplanner.core.*;
import br.yardplanner.exceptions.ContainerIdConflictException;
import br.yardplanner.exceptions.FullStackException;
import br.yardplanner.exceptions.PositionOnOutsideException;
import br.yardplanner.model.Block;
import br.yardplanner.model.Container;
import br.yardplanner.model.Yard;
import br.yardplanner.util.Position;

/**
 * Classe de teste para a classe {@see Sequence}
 * @author jameslee
 */
public class Sequence_test{
	
	/**
	 * Executado sempre antes de cada teste
	 */
	@Before
	public void setUp() {
		System.out.println( "Inicializando o teste" ) ;    	
	}
	
	/**
	 * Testa o calculo do custo
	 * @author jameslee
	 */
	@Test
	public void testaCusto() {
		System.out.print("->Testando o Custo...");
		//Iniciar patio (id=0 do patio, nome do patio)
    	Yard yard=new Yard(0,"Patio zero");
    	//bloco selecionado
    	Block block;
    	
		//contador de id
		int id;
		
		//ArrayList que guarda o número de movimentos de setup
    	ArrayList<Integer> movsetup=new ArrayList<Integer>();
    	movsetup.add(0);
		
    	//Lista de containers a serem removidos do bloco através do id do container
    	ArrayList<Integer> removelist=new ArrayList<Integer>();
		
		//-----bloco id=0-----
		
		//adicionar bloco (id e comprimentos x y z do bloco)
    	yard.add(0,5,5,5);
    	
		//Pegar referência do bloco
    	block = yard.getBlock(0);
    	
    	//Adicionar containers ao bloco atraves do id e posicoes x,y do container
    	try {
	    	id=1;
	    	for ( int i=0 ; i < 5 ; i++ ) { 
	    		for (int j=0;j<5;j++){
	    			for (int k=0;k<5;k++){
							block.add(id++, i, j);
	    			}
	    		}
	    	}
    	} catch (PositionOnOutsideException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (FullStackException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (ContainerIdConflictException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	//id de containers que serão removidos
    	removelist.clear();
    	removelist.add(5);
    	removelist.add(4);
    	removelist.add(3);
    	removelist.add(2);
    	removelist.add(1);
    	
    	Sequence seq=new Sequence(removelist,block);
    	//verificar o custo dessa sequência de retirada
		assertEquals( (Double)105.0 ,  (Double)seq.getCost() ) ;
		//verificar o número de movimentos de setup
		assertEquals( (Integer)0 ,  (Integer)seq.getSetup() ) ;
		//-----bloco id=1-----
		
		//adicionar bloco (id e comprimentos x y z do bloco)
    	yard.add(1,5,5,5);
    	
		//Pegar referência do bloco
    	block=yard.getBlock(1);
    	
    	//Adicionar containers ao bloco atraves do id e posicoes x,y do container
    	try {
			block.add(1, 0, 0) ;
	    	block.add(3, 2, 2) ;
	    	block.add(2, 2, 2) ;
		} catch (PositionOnOutsideException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FullStackException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ContainerIdConflictException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	//id de containers que serão removidos
    	removelist.clear();
    	removelist.add(3);
    	removelist.add(2);
    	removelist.add(1);
    	
    	
    	Sequence seq2=new Sequence(removelist,block);
    	//verificar o custo dessa sequência de retirada
		assertEquals( (Double)114.0 , (Double) seq2.getCost());
		//verificar o número de movimentos de setup
		assertEquals( (Integer)1 ,  (Integer)seq2.getSetup() ) ;
		
		//-----bloco id=2-----
		
		//adicionar bloco (id e comprimentos x y z do bloco)
    	yard.add(2,5,5,5);
    	
		//Pegar referência do bloco
    	block=yard.getBlock(2);
    	
    	//Adicionar containers ao bloco atraves do id e posicoes x,y do container
    	try {
			block.add( 1 , 2 , 2 ) ;
			block.add( 2 , 2 , 2 ) ;
			block.add( 3 , 2 , 2 ) ;
			block.add( 4 , 2 , 2 ) ;
			block.add( 5 , 2 , 2 ) ;
			block.add( 6 , 3 , 2 ) ;
			block.add( 7 , 3 , 2 ) ;
			block.add( 8 , 3 , 2 ) ;
			block.add( 9 , 3 , 2 ) ;
			block.add( 10 , 3 , 2 ) ;
		} catch (PositionOnOutsideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FullStackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContainerIdConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
    	
    	//id de containers que serão removidos
    	removelist.clear();
    	removelist.add(3);
    	removelist.add(5);
    	
    	Sequence seq3=new Sequence(removelist,block);
    	//verificar o custo dessa sequência de retirada
		assertEquals( (Double)117.0 ,  (Double)seq3.getCost()) ;
		//verificar o número de movimentos de setup
		assertEquals( (Integer)3 ,  (Integer)seq3.getSetup() ) ;
	}
	
	/**
	 * Testa a remoção de containers em um bloco
	 * @author jameslee
	 */
	@Test
	public void testaRemove() {
		System.out.print("->Testando a Remocao...");
		//Iniciar patio (id=0 do patio, nome do patio)
    	Yard y=new Yard(0,"Patio zero");
    	//bloco selecionado
    	Block b;
    	//iterador para a verificacao do hashset
    	Iterator<Container> it;
    	
		//contador de id
		int id;
		
    	//Lista de containers a serem removidos do bloco atraves do id do container
    	ArrayList<Integer> removelist=new ArrayList<Integer>();
		
		//-----bloco id=0-----
		
		//adicionar bloco (id e comprimentos x y z do bloco)
    	y.add(0,5,5,5);
    	
		//Pegar referencia do bloco
    	b=y.getBlock(0);
    	
    	//Adicionar containers ao bloco atraves do id e posicoes x,y do container
    	try {
    		id=1;
    		for ( int i = 0 ; i < 5 ; i++ ) {
    			for ( int j = 0 ; j < 5 ; j++ ) {
    				for ( int k = 0 ; k < 5 ; k++ ) { 
    					b.add( id++ , i , j ) ;
    				}
    			}
    		}
    	} catch (PositionOnOutsideException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (FullStackException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (ContainerIdConflictException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	//id de containers que serao removidos
    	removelist.clear();
    	removelist.add(5);
    	removelist.add(4);
    	removelist.add(3);
    	removelist.add(2);
    	removelist.add(1);
    	
    	//realizar a retirada de containers
    	Sequence seq=new Sequence(removelist,b);
    	seq.remove();
    	
    	//verificar se os containers foram efetivamente retirados
    	assertNull(b.getContainersMapId().get(5));
    	assertNull(b.getContainersMapId().get(4));
    	assertNull(b.getContainersMapId().get(3));
    	assertNull(b.getContainersMapId().get(2));
    	assertNull(b.getContainersMapId().get(1));
    	//verificar no HashSet
    	it=b.getContainers().iterator();
    	while (it.hasNext()){
    		assertTrue(b.getContainersMapId().containsKey(it.next().getContainerId()));
    	}
    	
    	//verificar as posicoes de todos os containers restantes no bloco
    	id=6;
    	for (int i=0;i<5;i++){
    		for (int j=0;j<5;j++){
    			for (int k=0;k<5;k++){
    				if (i!=0||j!=0)assertEquals( new Position (i,j,k) ,  b.getContainersMapId().get(id++).getPosition() ) ;
    			}
    		}
    	}
    	
		//-----bloco id=1-----
		//adicionar bloco (id e comprimentos x y z do bloco)
    	y.add(1,5,5,5);
    	
		//Pegar referencia do bloco
    	b=y.getBlock(1);
    	
    	//Adicionar containers ao bloco atraves do id e posicoes x,y do container
    	try {
			b.add(1, 0, 0);
	    	b.add(3, 2, 2);
	    	b.add(2, 2, 2);
	    	b.add(4, 3, 3);
		} catch (PositionOnOutsideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FullStackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContainerIdConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	//id de containers que serao removidos
    	removelist.clear();
    	removelist.add(3);
    	removelist.add(2);
    	removelist.add(1);
    	
    	//realizar a retirada de containers
    	Sequence seq2=new Sequence(removelist,b);
    	seq2.remove();
    	
    	//verificar se os containers foram efetivamente retirados
    	assertNull(b.getContainersMapId().get(3));
    	assertNull(b.getContainersMapId().get(2));
    	assertNull(b.getContainersMapId().get(1));
    	
    	//verificar no HashSet
    	it=b.getContainers().iterator();
    	while (it.hasNext()){
    		assertTrue(b.getContainersMapId().containsKey(it.next().getContainerId()));
    	}
    	
    	//verificar as posicoes de todos os containers restantes no bloco
    	assertEquals( new Position(3,3,0) ,  b.getContainersMapId().get(4).getPosition() ) ;
    	
		//-----bloco id=2-----
		
		//adicionar bloco (id e comprimentos x y z do bloco)
    	y.add(2,5,5,5);
    	
		//Pegar referencia do bloco
    	b=y.getBlock(2);
    	
    	//Adicionar containers ao bloco atraves do id e posicoes x,y do container
    	try {
			b.add(1 , 2, 2) ;
	    	b.add(2 , 2, 2) ;
	    	b.add(3 , 2, 2) ;
	    	b.add(4 , 2, 2) ;
	    	b.add(5 , 2, 2) ;
	    	b.add(6 , 3, 2) ;
	    	b.add(7 , 3, 2) ;
	    	b.add(8 , 3, 2) ;
	    	b.add(9 , 3, 2) ;
	    	b.add(10, 3, 2) ;			
		} catch (PositionOnOutsideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FullStackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContainerIdConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//id de containers que serao removidos
    	removelist.clear();
    	removelist.add(3);
    	removelist.add(5);
    	
    	//realizar a retirada de containers
    	Sequence seq3=new Sequence(removelist,b);
    	seq3.remove();
    	
    	//verificar se os containers foram efetivamente retirados
    	assertNull(b.getContainersMapId().get(3));
    	assertNull(b.getContainersMapId().get(5));
    	
    	//verificar no HashSet
    	it=b.getContainers().iterator();
    	while (it.hasNext()){
    		assertTrue(b.getContainersMapId().containsKey(it.next().getContainerId()));
    	}
    	
    	//verificar as posicoes de todos os containers restantes no bloco
    	assertEquals( new Position (3,2,0),  b.getContainersMapId().get(6).getPosition() ) ;
    	assertEquals( new Position (3,2,1),  b.getContainersMapId().get(7).getPosition() ) ;
    	assertEquals( new Position (3,2,2),  b.getContainersMapId().get(8).getPosition() ) ;
    	assertEquals( new Position (3,2,3),  b.getContainersMapId().get(9).getPosition() ) ;
    	assertEquals( new Position (3,2,4),  b.getContainersMapId().get(10).getPosition() ) ;
    	assertEquals( new Position (2,2,0),  b.getContainersMapId().get(1).getPosition() ) ;
    	assertEquals( new Position (2,2,1),  b.getContainersMapId().get(2).getPosition() ) ;
    	assertEquals( new Position (4,1,0),  b.getContainersMapId().get(4).getPosition() ) ;
    	//-----bloco id=3-----
		
    	//adicionar bloco (id e comprimentos x y z do bloco)
    	y.add(3,5,5,5);
    	    	
    	//Pegar referencia do bloco
    	b=y.getBlock(3);
    	
    	//Adicionar containers ao bloco atraves do id e posicoes x,y do container
    	try {
			b.add(1 , 0, 0) ;
	    	b.add(2 , 1, 0) ;
	    	b.add(3 , 2, 0) ;
	    	b.add(4 , 0, 0) ;
	    	b.add(5 , 1, 0) ;
	    	b.add(6 , 2, 0) ;	
		} catch (PositionOnOutsideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FullStackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContainerIdConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//id de containers que serao removidos
    	removelist.clear();
    	removelist.add(1);
    	removelist.add(2);
    	removelist.add(3);
    	
    	//realizar a retirada de containers
    	Sequence seq4=new Sequence(removelist,b);
    	
    	GeneticAlgorithm ga = new GeneticAlgorithm(seq4) ;
    	ga.run() ;
    	
    	seq4.remove();
    	
    	//verificar se os containers foram efetivamente retirados
    	assertNull(b.getContainersMapId().get(1));
    	assertNull(b.getContainersMapId().get(2));
    	assertNull(b.getContainersMapId().get(3));
    	
    	//verificar no HashSet
    	it=b.getContainers().iterator();
    	while (it.hasNext()){
    		assertTrue(b.getContainersMapId().containsKey(it.next().getContainerId()));
    	}
    	
	}
	
	/**
	 * Testa a operação de mutação
	 * @author jameslee
	 */
	@Test
	public void testaMutation() {
		System.out.print("->Testando a Mutação...");
		ArrayList<Integer> a=new ArrayList<Integer>();
		a.add(11);
		a.add(23);
		a.add(45);
		a.add(78);
		a.add(122);
		Chromosome c=new Chromosome(a);
		GeneticAlgorithm GA=new GeneticAlgorithm();
		Chromosome c2=GA.mutation(c);
		for (int i=0;i<c2.getGenes().size();i++){
			assertTrue(c2.contains(a.get(i)));
		}
		assertEquals((Integer)c.getGenes().size(),(Integer)c2.getGenes().size());
	}
	
	/**
	 * Testa a operação de OrderCrossover
	 * @author jameslee
	 */
	@Test
	public void testaOrderCrossover() {
		System.out.print("->Testando o OrderCrossover...");
		ArrayList<Integer> p1=new ArrayList<Integer>();
		ArrayList<Integer> p2=new ArrayList<Integer>();
		p1.add(11);p1.add(23);p1.add(45);p1.add(78);p1.add(122);
		p2.add(78);p2.add(11);p2.add(122);p2.add(23);p2.add(45);
		
		Chromosome c1=new Chromosome(p1);
		Chromosome c2=new Chromosome(p2);
		GeneticAlgorithm GA=new GeneticAlgorithm();
		Chromosome c3=GA.orderCrossover(c1,c2);
		for (int i=0;i<c3.getGenes().size();i++){
			assertTrue(c3.contains(c1.getGenes().get(i)));
		}
		assertEquals((Integer)c1.getGenes().size(),(Integer)c3.getGenes().size());
	}
	
	
	/**
	 * Testa o calculo do Fitness
	 * @author jameslee
	 */
	@Test
	public void testaCalcFitness() {
		System.out.println("->Testando o Calculo do Fitness...");
		//Iniciar patio (id=0 do patio, nome do patio)
	    Yard y=new Yard(0,"Patio zero");
	    
	    //bloco selecionado
	    Block b;
	    
	    //Lista de containers a serem removidos do bloco através do id do container
	    ArrayList<Integer> removelist=new ArrayList<Integer>();
		
	    //-----bloco id=0-----
	    //adicionar bloco (id e comprimentos x y z do bloco)
		y.add(0,5,5,5);
		
		//Pegar referência do bloco
		b=y.getBlock(0);
		
		//Adicionar containers ao bloco atraves do id e posicoes x,y do container
		try {
			b.add(1, 0, 0);
			b.add(3, 2, 2);
			b.add(2, 2, 2);
		} catch (PositionOnOutsideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FullStackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContainerIdConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//id de containers que serão removidos
		removelist.clear();
		removelist.add(3);
		removelist.add(2);
		removelist.add(1);
		Chromosome c=new Chromosome(removelist);
		//verificar o custo dessa sequência de retirada
		Sequence seq=new Sequence(removelist,b);
		
		GeneticAlgorithm GA=new GeneticAlgorithm(seq);
		//verificar o número de movimentos de setup
		assertEquals( (Double)0.5 ,  (Double)GA.calcFitness(c,228.0) ) ;
		
	}
	
	/**
	 * Executado ap—s cada teste
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("Ok!");
		System.out.println("Finalizando...");
	}

}
