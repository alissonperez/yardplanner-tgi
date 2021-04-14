package br.yardplanner.core;

import java.util.ArrayList ;
import java.util.List ;
import java.util.Collections ;

/**
 * Classe popula��o
 * 
 * @author Willyan Abilhoa
 */
public class Population {

    private List<Chromosome> chromosomes ;

    /**
     * Construtor
     */
    public Population(){
        this.chromosomes = new ArrayList<Chromosome>() ;
    }

    /**
     * @return Retorna os Cromossomos da popula��o
     */
    public List<Chromosome> getChromosomes() {
        return chromosomes ;
    }
    
    /**
     * @return Retorna o tamanho dos cromossomos
     */
    public int size() {
        return chromosomes.size() ;
    }

    /**
     * @param index Indice para o cromossomo
     * @return Retorna um cromossomo dado um �ndce
     */
    public Chromosome getChromosome( int index ) {
        return chromosomes.get( index ) ;
    }

    /**
     * Adiciona um cromosso � popula�nao
     * @param chromosome Cromossomo
     */
    public void addChromosome( Chromosome chromosome ) {
        chromosomes.add( chromosome ) ;
    }

    /**
     * Substitui um cromossomo em uma determinada posi��o
     * @param index Posi��p
     * @param newChromosome Novo cromossomo
     */
    public void replaceChromosome( int index , Chromosome newChromosome ) {
        chromosomes.set( index , newChromosome ) ;
    }

    /**
     * Checa se um cromossomo est� contido na lista de cromossomos
     * @param chromosome Cromossomo
     * @return Retorna true se o cromossomo estiver na popula��o
     */
    public boolean contains( Chromosome chromosome ){
        return chromosomes.contains( chromosome ) ;
    }

    /**
     * Embaralha a popula��o de cromossomos
     */
    public void shuffle() {
        Collections.shuffle( chromosomes ) ;
    }

    /**
     * Ordena a lista de cromossomos de forma ascendente
     */
    public void ascendingSort() {
        Collections.sort(chromosomes);
    }

    /**
     * Ordena a lista de cromossomos de forma descendente
     */
    public void descendingSort() {
        Collections.sort( chromosomes ) ;
        Collections.reverse( chromosomes ) ;
    }

    /**
     * @return Retorna o maior cromossomo da lista
     */
    public Chromosome max() {
        return Collections.max( chromosomes ) ;
    }

    /**
     * @return Retorna o menor cromossomo da lista
     */
    public Chromosome min() {
        return Collections.min( chromosomes ) ;
    }

    /**
     * Faz a clonagem da popula��o
     */
    @SuppressWarnings("unchecked")
	@Override
    public Population clone() {
        Population clone = new Population() ;
        
        for ( Chromosome chro : chromosomes ) {
            double clonedFitness = chro.getFitness() ;
            List<Integer> clonedGenes = new ArrayList<Integer>( chro.getGenes() ) ;
            Chromosome replica = new Chromosome() ;
            replica.setFitness( clonedFitness ) ;
            replica.setGenes( clonedGenes ) ;
            clone.addChromosome( replica ) ;
        }
        
        return clone;
    }

    /**
     * Representa��o da popula��o em forma de string
     * @return	Retorna a representa��o da popula��o em formato string
     */
    public String toString() {
        StringBuilder result = new StringBuilder() ;
        
        for ( int i = 0 ; i < chromosomes.size() ; i++ ) {
        	result.append( chromosomes.get(i) + "\n" ) ;
        }
        
        return result.toString() ;
    }
}