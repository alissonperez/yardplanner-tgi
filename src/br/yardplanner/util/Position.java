package br.yardplanner.util;

/**
 * Objeto para marcar uma posi��o X,Y,Z.<br>
 * Criado com a finalidade de indexar a posi��o de um container em um Map<br>
 * Exemplo: <pre>
 * // Objeto position com a posi��o (10, 20, 1), respectivamente (x, y, z).
 * Position pos = new Position( 10 , 20 , 1 ) ;
 * </pre>
 * @author Alisson
 */
public class Position {
	
	/**
	 * Posi��o X
	 */
	private Integer posX ;
	
	/**
	 * Posi��o Y
	 */
	private Integer posY ;
	
	/**
	 * Posi��o Z
	 */
	private Integer posZ ;
	
	/**
	 * Construtor vazio.
	 */
	public Position() {}
	
	/**
	 * Construtor que recebe os as posi��es X, Y e Z como parametros
	 * 
	 * @param posX Pos. X
	 * @param posY Pos. Y
	 * @param posZ Pos. Z
	 */
	public Position( Integer posX , Integer posY , Integer posZ ) {
		this.posX = posX ;
		this.posY = posY ;
		this.posZ = posZ ;
	}
	
	/**
	 * Construtor que recebe apenas as posi��es X e Y.<br>
	 * <b><span style="color: red">Neste caso, assume-se automaticamente Z com O</span></b>
	 * 
	 * @param posX
	 * @param posY
	 */
	public Position( Integer posX , Integer posY ) {
		this.posX = posX ;
		this.posY = posY ;
		this.posZ = 0 ;
	}

	/**
	 * Retorna o HashCode deste objeto.<br>
	 * Usa um n�mero primo para calcular o resultado final
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((posX == null) ? 0 : posX.hashCode()) ;
		result = prime * result + ((posY == null) ? 0 : posY.hashCode()) ;
		result = prime * result + ((posZ == null) ? 0 : posZ.hashCode()) ;
		
		return result;
	}

	/**
	 * Faz a compara��o deste objeto com outro.<br>
	 * Gerado automaticamente pelo eclipse.
	 */
	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		
		if ( obj == null ) {
			return false;
		}
		
		if ( getClass() != obj.getClass() ) {
			return false;
		}
		
		Position other = (Position) obj;
		
		if (posX == null) {
			if (other.posX != null) {
				return false;
			}
		}
		else if ( ! posX.equals( other.posX ) ) {
			return false;
		}
		
		if ( posY == null ) {
			if ( other.posY != null ) {
				return false;
			}
		}
		else if ( ! posY.equals( other.posY ) ) {
			return false;
		}
		
		if ( posZ == null ) {
			if ( other.posZ != null ) {
				return false;
			}
		}
		else if ( ! posZ.equals( other.posZ ) ) {
			return false;
		}
		
		return true;
	}

	/**
	 * @return Retorna a posi��o X
	 */
	public Integer getPosX() {
		return posX;
	}
	
	/**
	 * @param posX Posi��o X
	 */
	public void setPosX(Integer posX) {
		this.posX = posX;
	}
	
	/**
	 * @return Retorna a posi��o Y
	 */
	public Integer getPosY() {
		return posY;
	}
	
	/**
	 * @param posY Posi��o Y
	 */
	public void setPosY(Integer posY) {
		this.posY = posY;
	}
	
	/**
	 * @return Retorna a posi��o Z
	 */
	public Integer getPosZ() {
		return posZ;
	}
	
	/**
	 * @param posZ Posi��o Z
	 */
	public void setPosZ(Integer posZ) {
		this.posZ = posZ;
	}
	
	/**
	 * Faz o clone do objeto atual
	 */
	public Position clone() {
		return new Position( this.posX , this.posY , this.posZ ) ;		
	}
	
	/**
	 * Seta 3 posi��es de uma �nica vez.
	 * 
	 * @param posX Pos. X
	 * @param posY Pos. Y
	 * @param posZ Pos. Z
	 * 
	 * @author James Lee
	 */
    public void setPos(Integer posX,Integer posY,Integer posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

	/**
	 * @return Retorna uma representa��o do objeto em string.
	 */
	@Override
	public String toString() {
		return "(" + posX + ", " + posY + ", " + posZ + ")" ;
	}

}