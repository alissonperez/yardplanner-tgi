package br.yardplanner.core;
import java.util.ArrayList;
import java.util.List;

/**
 * Cromossomo
 * @author Willyan Abilhoa
 */
public class Chromosome implements Comparable<Chromosome> {

    private double fitness;
    private List<Integer> genes;

    /**
     * Construtor
     */
    public Chromosome(){
        this.fitness = 0;
        this.genes = new ArrayList<Integer>();
    }
    
    /**
     * Construtor que recebe uma lista de Genes
     * @param genes
     */
    public Chromosome(ArrayList<Integer> genes){
        this.fitness = 0;
        this.genes = genes;
    }
    
    /**
     * @return Retorna o fitness do cromossomo
     */
    public double getFitness(){
        return fitness;
    }

    /**
     * Seta o fitness do cromossomo
     * @param fitness
     */
    public void setFitness(double fitness){
        this.fitness = fitness;
    }

    /**
     * @return Retorna uma lista de genes do cromossomo
     */
    public List<Integer> getGenes(){
        return genes;
    }

    /**
     * Seta uma lista de genes do cromossomo
     * @param genes2 Lista de genes do cromossomo
     */
    public void setGenes(List<Integer> genes ){
        this.genes = genes ;
    }

    /**
     * @param locus Locos para o gene desejado
     * @return Retorna um gene dado um Locus
     */
    public int getGene( int locus ) {
        return genes.get( locus ) ;
    }
    
    /**
     * Seta um gene no locus desejado
     * @param locus Locus
     * @param gene Gene
     */
    public void setGene( int locus , int gene ) {
        this.genes.add( locus , gene ) ;
    }

    /**
     * Retorna o tamanho do cromossomo
     * @return
     */
    public int getLenght() {
        return genes.size() ;
    }
    
    /**
     * Verifica se o gene existe no cromossomo
     * @param gene Gene
     * @return Retorna true se o gene já existir
     */
    public boolean contains( int gene ) {
        return genes.contains( gene ) ;
    }

    /**
     * Método para comparação entre dois cromossomos.<br>
     * Faz as verificações pelo fitness dos cromossomos
     * @param chromossome Cromossomo para comparação
     */
    @Override
    public int compareTo( Chromosome chromosome ) {
        if ( this.fitness < chromosome.getFitness() ) {
        	return -1 ;
        }
        
        if ( this.fitness > chromosome.getFitness() ) {
        	return 1 ;
        }
        
        return 0 ;
    }

    /**
     * Representação em string do cromossomo
     */
    public String toString() {
        StringBuilder result = new StringBuilder( "{Fitness: " + fitness + " | Chromosome:" ) ;
        for( int i = 0 ; i < genes.size() ; i++ ) {
        	result.append( " " + genes.get(i).intValue() ) ;        	
        }
        
        result.append( "}" ) ;
        return result.toString() ;
    }
}