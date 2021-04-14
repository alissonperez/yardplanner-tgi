package br.yardplanner.core;

import java.util.List;

/**
 * Interface que define os métodos utilizados pelo algoritmo genético
 * @author James Lee
 */
public interface GeneticAlgorithmInterface {
	/**
	 * Calculo do custo para uma determinada sequência
	 * @return Custo para uma determinada sequência
	 */
	public double calcCost() ;
	
	/**
	 * @return Retorna o custo para uma sequência
	 */
	public double getCost() ;
	
	/**
	 * @return Retorna os genes
	 */
	public List<Integer> getGenes() ;
	
	/**
	 * Seta os genes para o calculo do custo
	 * @param list Genes
	 */
	public void setGenes(List<Integer> list) ;
	
	/**
	 * Método necessário para clonagem do objeto
	 * @return Cópia do objeto
	 */
	public GeneticAlgorithmInterface clone() ;
}
