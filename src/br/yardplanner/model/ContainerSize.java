package br.yardplanner.model;

import javax.persistence.* ;

/**
 * Model do ContainerSize
 * @author Alisson
 */
@Entity
@Table( name = "Container_sizes" )
public class ContainerSize {
	
	/**
	 * Largura default
	 */
	public final static Double WIDTH_DEFAULT = 1.0 ;
	
	/**
	 * Altura default
	 */
	public final static Double HEIGHT_DEFAULT = 1.0 ;
	
	/**
	 * Comprimento default
	 */
	public final static Double LENGTH_DEFAULT = 1.0 ;
	
	/**
	 * Chave primária do tamanho do container
	 */
	@Id
	@GeneratedValue
	@Column( name = "size_id" )
	private Integer sizeId ;
	
	/**
	 * Unidade TEU do container
	 */
	private Integer teu ;
	
	/**
	 * Comprimento
	 */
	private Double width ;
	
	/**
	 * Altura
	 */
	private Double height ;
	
	/**
	 * Largura
	 */
	private Double length ;

	/**
	 * @return Retorna o id do tamanho no banco
	 */
	public Integer getSizeId() {
		return sizeId;
	}

	/**
	 * Seta o id do tamanho no banco
	 * @param sizeId Id desejado
	 */
	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	/**
	 * @return Retorna o TEU
	 */
	public Integer getTeu() {
		return teu;
	}
	
	/**
	 * @return Retorna TEU formatado com plural ou singular
	 */
	public String getTeuFormated() {
		String formated ;
		if ( this.teu > 1 ) {
			formated = this.teu + " TEUs" ;			
		}
		else {
			formated = this.teu + " TEU" ;			
		}
		//formated +=  " [A: " + this.height + ", L: " + this.width + ", C: " + this.length + "]" ;
		return formated ;
	}

	/**
	 * Seta o TEU do tamanho
	 * @param teu Teu do tamanho
	 */
	public void setTeu(Integer teu) {
		this.teu = teu;
	}

	/**
	 * @return Retorna a largura
	 */
	public Double getWidth() {
		return width;
	}

	/**
	 * Setando a largura
	 * @param width Largura
	 */
	public void setWidth(Double width) {
		this.width = width;
	}

	/**
	 * @return Retorna a altura
	 */
	public Double getHeight() {
		return height;
	}

	/**
	 * Setando a altura
	 * @param height Altura do tamanho
	 */
	public void setHeight(Double height) {
		this.height = height;
	}

	/**
	 * @return Retorna o comprimento
	 */
	public Double getLength() {
		return length;
	}

	/**
	 * Seta o comprimento
	 * @param length Comprimento
	 */
	public void setLength(Double length) {
		this.length = length;
	}
	
}