package br.yardplanner.core;

import java.util.List;

/**
 * Interface que define os m�todos utilizados pelo algoritmo gen�tico
 * @author James Lee
 */
public interface GeneticAlgorithmInterface {
	/**
	 * Calculo do custo para uma determinada sequ�ncia
	 * @return Custo para uma determinada sequ�ncia
	 */
	public double calcCost() ;
	
	/**
	 * @return Retorna o custo para uma sequ�ncia
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
	 * M�todo necess�rio para clonagem do objeto
	 * @return C�pia do objeto
	 */
	public GeneticAlgorithmInterface clone() ;
}
