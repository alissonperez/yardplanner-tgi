package br.yardplanner.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Classe principal que implementa os métodos do algoritmo genético 
 * 
 * @author Willyan Abilhoa
 */
public class GeneticAlgorithm {
	
	/**
	 * Instancia da logger
	 */
	private Logger log ;

    /**
     * População usada no algoritmo
     */
    private int populationSize = 100 ;
    
    /**
     * Taxa de recombinação
     */
    private double recombinationRate = 0.18 ;
    
    /**
     * Taxa de mutação
     */
    private double mutationRate = 0.67 ;
    
    /**
     * Número máximo de gerações
     */
    private int maxGeneration = 20 ;
    
    /**
     * Objeto usado para calcular o Fitness do algoritmo genético
     */
    private GeneticAlgorithmInterface gaObject ;
    
    /**
     * População com os melhores indivíduos
     */
    private Population bestSolutions ;
    
    /**
     * Construtor que recebe um objeto que implementa os métodos necessários para o algoritmo genético
     * 
     * @param gaObject Objeto com os métodos necessários para a execução do algoritmo genético
     */
    public GeneticAlgorithm(GeneticAlgorithmInterface gaObject) {
    	this.bestSolutions = new Population() ;
        this.gaObject = gaObject ;
        this.log = Logger.getLogger( GeneticAlgorithm.class.getName() ) ;
    }
    
    /**
     * Construtor vazio
     */
    public GeneticAlgorithm(){
    	this.bestSolutions = new Population() ;
    }
    
    /**
     * Retorna um número randômico
     * @param n
     * @return
     */
    private int intRandom( int n ) {
        Random rnd = new Random() ;
        return rnd.nextInt( n ) ;
    }

	/**
	 * Retorna um número double randômico
	 * @return
	 */
    private double doubleRandom(){
        return Math.random();
    }

    /**
     * Execução do algoritmo
     */
    public void run() {
    	if ( this.gaObject != null ) {
    		if ( this.gaObject.getGenes().size() > 1 ) {
    			double cost = -1 ;
    			cost = this.gaObject.getCost() ;
    			
    			// Solução inicial
    			GeneticAlgorithmInterface initialSolution = this.gaObject.clone() ;
    			Population population = this.initialization( this.gaObject.getGenes() ) ;
    			
    			evaluation( population , cost ) ;
    			
    			Population matingPool , offspring ;
    			
    			for ( int g = 0 ; g < this.maxGeneration ; g++ ) {
    				matingPool = this.parentSelection( population ) ;
    				offspring = this.recombination( matingPool ) ;
    				
    				// Mutação e avaliação
    				this.mutation( offspring ) ;
    				this.evaluation( offspring , cost ) ;
    				
    				population = this.survivorSelection( population , offspring ) ;
    				this.log.debug( "Melhor da geração " + g + ":" + population.max() ) ;
    				
    				this.bestSolutions.addChromosome( population.max() ) ;
    				this.gaObject.setGenes( this.bestSolutions.max().getGenes() ) ;
    				if ( cost <= 0 ) {
    					cost = this.gaObject.getCost() ;
    				}
    			}
    			
    			log.debug( "População Final:\n" + this.bestSolutions ) ;
    			log.debug( "Melhor individuo:\n" + this.bestSolutions.max() ) ;
    			
    			log.debug( "-- Comparação --" ) ;
    			log.debug( "===Inicial===" ) ;
    			
    			log.debug( initialSolution ) ;
    			
    			log.debug( "===Final===" ) ;
    			log.debug( this.gaObject ) ;
    		}
    		else if ( this.gaObject.getGenes().size() == 1 ) {
    			log.trace( "Apenas um gene" ) ;
    			Chromosome chromosome = this.createChromosome( this.gaObject.getGenes() ) ;
    			
    			Population population = new Population() ;
    			population.addChromosome(chromosome) ;
    			
    			this.bestSolutions = population ;
    			
    			log.debug( this.gaObject ) ;
    		}
    	}
    	else {
    		log.debug( "Objeto para manupulação do algoritmo genético não inicializado" ) ;
    	}
    }
    
    /**
     * @return Retorna a melhor sequência obtida pelo algoritmo
     */
    public List<Integer> getBestSequence() {
    	this.log.debug( this.bestSolutions ) ;
    	
    	return this.bestSolutions.max().getGenes() ;
    }

    /**
     * Inicialização
     * 
     * @param list Lista para a criação da população
     * @return Retorna a primeira população gerada
     */
    public Population initialization( List<Integer> list ) {
        Population firstPopulation = new Population();
        
        for ( int i = 0 ; i < this.populationSize ; i++ ) {
            List<Integer> genes = new ArrayList<Integer>( list ) ;
            Collections.shuffle( genes ) ;
            firstPopulation.addChromosome( this.createChromosome( genes ) ) ;
        }
        
        return firstPopulation ;
    }

    /**
     * Cria um cromossomo
     * @param genes Genes para a criação de um cromossomo
     * @return Cromossomo
     */
    private Chromosome createChromosome( List<Integer> genes ) {
        Chromosome newChromosome = new Chromosome() ;
        newChromosome.setGenes( genes ) ;
        return newChromosome ;
    }

    /**
     * Avaliação
     * @param population
     * @param cost
     */
    public void evaluation( Population population , double cost ) {
        for( Chromosome c : population.getChromosomes() ) {
            c.setFitness( this.calcFitness( c , cost ) ) ;
        }
    }

    /**
     * Seleção de pais
     * @param population
     * @return
     */
    public Population parentSelection( Population population ) {
        Population matingPool = new Population() ;
        int mpSize = this.intRandom( population.size() ) ;
        
        if ( mpSize == 0 ) {
            mpSize = 2 ;
        }
        
        for ( int i = 0 ; i < mpSize ; i++ ) {
            matingPool.addChromosome( selectParent( population ) ) ;
        }
        
        return matingPool ;
    }

    /**
     * Seleção de pais
     * @param population
     * @return
     */
    private Chromosome selectParent( Population population ) {
        Chromosome parent = null ;
        int flag = intRandom( 2 ) ;
        
        switch ( flag ) {
	        case 0:
	        	parent = roulette(population);
	        	break;
	        case 1:
	        	parent = tournament(population);
	        	break ;
        }
        
        return parent;
    }
    
    /**
     * Roleta
     * @param population
     * @return
     */
    private Chromosome roulette( Population population ) {
        double rankingSum = 0 , auxSum = 0 , cut ;
        int idx ;
        
        for ( Chromosome c : population.getChromosomes() ) {
            rankingSum += c.getFitness() ;
        }
        
        cut = Math.random() * rankingSum ;
        
        for ( idx = 0 ; idx < population.size() ; idx++ ) {
            auxSum += population.getChromosome( idx ).getFitness() ;
            if ( auxSum >= cut ) {
                break ;
            }
        }
        
        if ( idx == population.size() ) {
        	idx-- ;
        }
        
        return population.getChromosome( idx ) ;
    }

    /**
     * Competição
     * @param population
     * @return
     */
    private Chromosome tournament( Population population ) {
        int k = intRandom( population.size() ) + 1 ;
        ArrayList<Chromosome> competitors = new ArrayList<Chromosome>();
        Chromosome winner ;
        
        for( int i = 0 ; i < k ; i++ ) {
            competitors.add( population.getChromosome( intRandom( population.size() ) ) ) ;
        }
        
        winner = competitors.get( 0 ) ;
        
        for ( int i = 1 ; i < k - 1 ; i++ ) {
            if ( winner.getFitness() < competitors.get( i ).getFitness() ) {
                winner = competitors.get( i ) ;
            }
        }
        
        return winner ;
    }

    /**
     * Recombinação
     * @param matingPool
     * @return
     */
    public Population recombination( Population matingPool ) {
        Population offspring = new Population() ;
        int count = 0 ;
        
        while ( count < populationSize ) {
            if( doubleRandom() < recombinationRate ) {
                Chromosome parent1 = matingPool.getChromosome( intRandom( matingPool.size() ) ) ;
                Chromosome parent2 = matingPool.getChromosome( intRandom( matingPool.size() ) ) ;
                Chromosome son = crossover( parent1 , parent2 ) ;
                offspring.addChromosome( son ) ;
                count++ ;
            }
        }
        
        return offspring;
    }

    /**
     * Operação crossover
     * 
     * @param parent1
     * @param parent2
     * @return
     */
    private Chromosome crossover( Chromosome parent1 , Chromosome parent2 ) {
        Chromosome offspring = orderCrossover( parent1 , parent2 ) ;
        return offspring ;
    }

    /**
     * Operação de crossover
     * @param parent1
     * @param parent2
     * @return
     */
    public Chromosome orderCrossover( Chromosome parent1 , Chromosome parent2 ) {
        int length = parent1.getLenght() ;
        int end = intRandom( length ) + 1 ;
        int start = intRandom( end ) ;
        int[] searchGenes = this.adapt( end , length , parent2.getGenes() ) ;
        int[] offGenes = new int[ length ] ;
        
        this.add( 0, start , end , offGenes , parent1 , null ) ;
        this.add( 1, end , length , offGenes , null , searchGenes ) ;
        this.add( 1, 0 , start , offGenes , null , searchGenes ) ;
        
        Chromosome offspring = new Chromosome() ;
        
        for ( int i = 0 ; i < length ; i++ ) {
            offspring.setGene( i , offGenes[i] ) ;
        }
        
        return offspring ;
    }

    /**
     * Adaptação
     * @param e
     * @param l
     * @param list
     * @return
     */
    private int[] adapt( int e , int l , List<Integer> list ) {
    	int[] s ;
        ArrayList<Integer> t = new ArrayList<Integer>() ;
        t.addAll( list.subList( e - 1 , l ) ) ;
        t.addAll( list.subList( 0 , e ) ) ;
        s = toVector( t ) ;
        return s ;
    }

    /**
     * Converte um arraylist para vetor
     * @param c
     * @return
     */
    private int[] toVector(ArrayList<Integer> c){
        int[] v = new int[c.size()] ;
        
        for ( int i = 0 ; i < v.length ; i++ ) {
            v[i] = c.get( i ) ;
        }
        
        return v ;
    }

    /**
     * Adiciona
     * 
     * @param flag
     * @param s
     * @param e
     * @param o
     * @param p1
     * @param p2
     */
    private  void add( int flag , int s , int e , int[] o , Chromosome p1 , int[] p2 ) {
    	switch ( flag ) {
	    	case 0:
	    		for(int i = s; i < e; i++){
	    			o[i] = p1.getGene(i);
	    		}
	    		
	    		break ;
	    	case 1:
	    		for( int i = s ; i < e ; i++ ) {
	    			for ( int j = 0 ; j < p2.length ; j++ ) {
	    				if ( ! contains( p2[j] , o ) ) {
	    					o[i] = p2[j] ;
	    					break ;
	    				}
	    			}
	    		}
	    		
	    		break;
    	}
    }

    /**
     * Verifica se um vetor possui um elemento
     * 
     * @param x
     * @param v
     * @return
     */
    private boolean contains( int x , int[] v ) {
        for ( int i = 0 ; i < v.length ; i++ ) {
            if ( x == v[i] ) {
                return true ;
            }
        }
        
        return false ;
    }

    /**
     * Processo de mutação de uma população inteira
     * @param offspring
     */
    public void mutation( Population offspring ) {
        for ( Chromosome c : offspring.getChromosomes() ) {
            if ( this.doubleRandom() < mutationRate ) {
            	this.mutation( c ) ;
            }
        }
    }

    /**
     * Processo de mutação de um cromossomo
     * @param chromosome
     * @return
     */
    @SuppressWarnings("unchecked")
    public Chromosome mutation( Chromosome chromosome ) {
    	List<Integer> genes ;
        int point1 = 0 ;
        int point2 = this.intRandom( chromosome.getLenght() ) ;
        if ( point2 == 0 ) {
            point2++ ;
        }
        
        point1 = this.intRandom( point2 ) ;
        if ( point1 == ( chromosome.getLenght() - 1 ) ) {
            point1-- ;
        }
        
        genes = new ArrayList<Integer>( chromosome.getGenes() ) ;
        
        int flag = this.intRandom( 4 ) ;
        switch( flag ) {
	        case 0:
	        	this.swap( point1 , point2 , genes ) ;
	        	break ;
	        case 1:
	        	this.invertion( point1 , point2 , genes ) ;
	        	break ;
	        case 2:
	        	this.scramble( point1 , point2 , genes ) ;
	        	break ;
	        case 3:
	        	this.insertion( point1 , point2 , genes ) ;
	        	break ;
        }
        
        Chromosome mutation = new Chromosome() ;
        mutation.setGenes( genes ) ;
        return mutation ;
    }
    
    /**
     * Procedimento de troca
     * @param point1
     * @param point2
     * @param genes
     */
    private  void swap(int point1, int point2, List<Integer> genes){
        int aux = genes.get( point2 ) ;
        genes.set( point2 , genes.get( point1 ) ) ;
        genes.set( point1 , aux ) ;
    }

    /**
     * Inverção
     * @param point1
     * @param point2
     * @param genes
     */
    private  void invertion( int point1 , int point2 , List<Integer> genes ) {
        List<Integer> subGenes = new ArrayList<Integer>() ;
        int i , j ;
        
        subGenes.addAll( genes.subList( point1 , point2 ) ) ;
        Collections.reverse( subGenes ) ;
        
        j = 0 ;
        for( i = point1 ; i < point2 ; i++ ) {
            genes.set( i , subGenes.get( j ) ) ;
            j++ ;
        }
    }
    
    /**
     * Embaralhamento
     * @param point1
     * @param point2
     * @param genes
     */
    private void scramble( int point1 , int point2 , List<Integer> genes ) {
        ArrayList<Integer> subGenes = new ArrayList<Integer>() ;
        int i , j ;
        
        subGenes.addAll( genes.subList( point1 , point2 ) ) ;
        Collections.shuffle( subGenes ) ;
        
        j = 0 ;
        for ( i = point1 ; i < point2 ; i++ ) {
            genes.set( i , subGenes.get( j ) ) ;
            j++ ;
        }
    }

    /**
     * Inserção
     * @param point1
     * @param point2
     * @param genes
     */
    private void insertion( int point1 , int point2 , List<Integer> genes ) {
        genes.add( point1 + 1 , genes.get( point2 ) ) ;
        genes.remove( point2 + 1 ) ;
    }

    /**
     * Selecao de Sobreviventes
     * @param population
     * @param offspring
     * @return
     */
    public Population survivorSelection( Population population , Population offspring ) {
        Population nextIndividuals = null ;
        
        int flag = intRandom( 2 ) ;        
        switch( flag ) {
	        case 0:
	        	nextIndividuals = this.fitnessBased(population, offspring) ;
	        	break ;
	        case 1:
	        	nextIndividuals = this.elitism(population, offspring) ;
	        	break ;
        }
        
        return nextIndividuals ;
    }
    
    /**
     * 
     * @param population
     * @param offspring
     * @return
     */
    private  Population fitnessBased( Population population , Population offspring ) {
        Population nextIndividuals = population.clone() ;
        int pSize , i ;
        
        pSize = population.size() ;
        for ( i = 0 ; i < pSize ; i++ ) {
            if ( offspring.getChromosome( i ).getFitness() > population.getChromosome( i ).getFitness() ) {
            	nextIndividuals.replaceChromosome( i , offspring.getChromosome( i ) ) ;            	
            }
        }
        
        return nextIndividuals;
    }
    
    /**
     * 
     * @param parents
     * @param offspring
     * @return
     */
    private Population elitism( Population parents , Population offspring ) {
        Population nextIndividuals = new Population() ;
        parents.descendingSort() ;
        offspring.descendingSort() ;
        
        for ( int i = 0 ; i < parents.size() / 2 ; i++ ) {
            nextIndividuals.addChromosome( parents.getChromosome( i ) ) ;
            nextIndividuals.addChromosome( offspring.getChromosome( i ) ) ;
        }
        
        return nextIndividuals;
    }

    /**
     * Calcula o Fitness
     * @param CRi
     * @param cost
     * @return
     */
    public double calcFitness( Chromosome CRi , double cost ) {
    	this.gaObject.setGenes( CRi.getGenes() ) ;
        double thecost = this.gaObject.getCost() ;
        if ( thecost < 0 ) {
        	return -1 ;
        }
        else if ( cost < 0 ) {
        	return 0;
        }
        
        return 1 - thecost / cost ;
    }
}