package br.yardplanner.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Classe responsável por manipular o código do container. ISO 6346
 * 
 * @author Alisson
 */
public class ContainerCode {
	
	/**
	 * Prefixo default para os códigos gerados aleatóriamente
	 */
	private static final String PREFIX_RANDOM_DEFAULT = "XXXU" ;
	
	/**
	 * Comprimento do código do container sem o dígito
	 */
	private static final int LENGTH_CODE_WITHOUT_DIGIT = 10 ;
	
	/**
	 * Log do sistema
	 */
	private Logger log ;
	
	/**
	 * Mapa de conversão das letras do código
	 */
	private static Map<String,Integer> alphabetNumLetters ;
	
	/**
	 * Código do container
	 */
	private String code ;
	
	/**
	 * Determina se o formato do código é válido, sem levar em conta o dígito
	 */
	private Boolean isValidCode ;
	
	/**
	 * Determina se o dígito é válido em relação à sequência informada
	 */
	private Boolean isValidDigit ;

	/**
	 * Construtor que recebe o código
	 * 
	 * @param code
	 */
	public ContainerCode( String code ) {
		this.instanceLogger() ;
		this.log.debug(code) ;
		this.isValidCode = false ;
		this.isValidDigit = false ;
		this.setCode( code ) ;
	}

	/**
	 * Construtor sem parametros
	 */
	public ContainerCode() {
		this.instanceLogger() ;
		this.isValidCode = false ;
		this.isValidDigit = false ;
	}
	
	/**
	 * Instancia o logger da classe
	 */
	private void instanceLogger() {
		this.log = Logger.getLogger( ContainerCode.class.getName() ) ;
	}
	
	/**
	 * Verifica o código informado pelo usuário
	 */
	private void checkIsValid() {
		this.isValidCode = false ;
		this.isValidDigit = false ;
		
		Pattern patt = Pattern.compile( "^([A-Z]{4}[0-9]{6})([0-9]+)?$" ) ;
		Matcher match = patt.matcher( this.code ) ;
		Integer digit ;
		
		if ( match.matches() ) {
			this.isValidCode = true ;
			
			if ( match.group( 2 ) != null ) {
				digit = Integer.parseInt( match.group( 2 ) ) ;
				Integer checkDigit = this.calcDigit() ;
				
				this.isValidDigit = checkDigit.equals( digit ) ;
			}
		}
	}
	
	/**
	 * Cria o dígito para uma sequência
	 */
	public void createDigit() {
		if ( this.isValidCode ) {
			this.log.debug( this.code.length() + " - " + ContainerCode.LENGTH_CODE_WITHOUT_DIGIT ) ;
			if ( this.code.length() == ContainerCode.LENGTH_CODE_WITHOUT_DIGIT ) {
				this.code += this.calcDigit() ;
				this.log.debug( "Codigo com o digito: " + this.code ) ;
				this.checkIsValid() ;
			}
		}
	}
	
	/**
	 * Calcula o dígito verificador
	 */
	private Integer calcDigit() {
		int letter_i ;
		int[] power_2 = { 1 , 2 , 4 , 8 , 16 , 32 , 64 , 128 , 256 , 512 } ;
		String letter ;
		Integer sum = 0 ;
		Integer num = 0 ;
		
		this.log.debug( "Calculando dígito de " + this.code ) ;
		
		for ( letter_i = 0 ; letter_i < ContainerCode.LENGTH_CODE_WITHOUT_DIGIT ; letter_i++ ) {
			letter = String.valueOf( this.code.charAt( letter_i ) ) ;
			
			if ( letter_i < 4 ) {
				num = ContainerCode.getAlphabetNumLetters().get( letter ) ;
			}
			else {
				num = Integer.parseInt( letter ) ;					
			}
			
			sum += num * power_2[letter_i] ;
		}
		
		this.log.debug( "Digito: " + ( sum % 11 ) ) ;
		
		return sum % 11 ;
	}
	
	private static Map<String,Integer> getAlphabetNumLetters() {
		if ( alphabetNumLetters == null ) {
			alphabetNumLetters = new HashMap<String, Integer>() ;
			alphabetNumLetters.put( "A" , 10 ) ;
			alphabetNumLetters.put( "B" , 12 ) ;
			alphabetNumLetters.put( "C" , 13 ) ;
			alphabetNumLetters.put( "D" , 14 ) ;
			alphabetNumLetters.put( "E" , 15 ) ;
			alphabetNumLetters.put( "F" , 16 ) ;
			alphabetNumLetters.put( "G" , 17 ) ;
			alphabetNumLetters.put( "H" , 18 ) ;
			alphabetNumLetters.put( "I" , 19 ) ;
			alphabetNumLetters.put( "J" , 20 ) ;
			alphabetNumLetters.put( "K" , 21 ) ;
			alphabetNumLetters.put( "L" , 23 ) ;
			alphabetNumLetters.put( "M" , 24 ) ;
			alphabetNumLetters.put( "N" , 25 ) ;
			alphabetNumLetters.put( "O" , 26 ) ;
			alphabetNumLetters.put( "P" , 27 ) ;
			alphabetNumLetters.put( "Q" , 28 ) ;
			alphabetNumLetters.put( "R" , 29 ) ;
			alphabetNumLetters.put( "S" , 30 ) ;
			alphabetNumLetters.put( "T" , 31 ) ;
			alphabetNumLetters.put( "U" , 32 ) ;
			alphabetNumLetters.put( "V" , 34 ) ;
			alphabetNumLetters.put( "W" , 35 ) ;
			alphabetNumLetters.put( "X" , 36 ) ;
			alphabetNumLetters.put( "Y" , 37 ) ;
			alphabetNumLetters.put( "Z" , 38 ) ;
		}
			
		return alphabetNumLetters ;
	}
	
	public String toString() {
		return this.getCode() ;
	}

	/**
	 * @return Se o código é válido ou não
	 */
	public Boolean isValid() {
		return this.isValidCode && this.isValidDigit ;
	}

	/**
	 * @return Retorna o código do container
	 */
	public String getCode() {		
		return this.code ;
	}
	
	/**
	 * @return Retorna o código formatado
	 */
	public String getCodeFormmated() {
		return ContainerCode.getCodeFormmated( this.code ) ;
	}

	/**
	 * @param code Código do container
	 */
	public void setCode( String code ) {
		this.code = code.trim().toUpperCase() ;
		this.checkIsValid() ;
	}

	/**
	 * @param code Código que será formatado
	 * @return Retorna um código formatado
	 */
	public static String getCodeFormmated( String code ) {
		if ( code != null && code.length() > 0 && code.length() >= 10 ) {
			return code.substring( 0 , 4 ) + "-" + code.substring( 4 , 10 ) + "-" + code.substring( 10 ) ;
		}

		return code ;
	}
	
	/**
	 * Gera um código aleatório
	 */
	public static ContainerCode getRandomCode() {
		String randomCodeStr = ContainerCode.PREFIX_RANDOM_DEFAULT ;

		Random random = new Random() ;

		// Gerar com códigos 
		// randomCodeStr = "" ;
		// int chars , num = 3 ;
		// while ( num-- > 0 ) {
		// 	randomCodeStr += (char) ( 65 + random.nextInt( 26 ) ) ;
		// }
		// randomCodeStr += "U" ;

		ContainerCode code ;
		
		randomCodeStr += String.format( "%6d" , random.nextInt( 1000000 ) ).replace( " " , "0" ) ;
		code = new ContainerCode( randomCodeStr ) ;
		code.createDigit() ;
		
		return code ;
	}

	/**
	 * Gera uma determinada quantidade de números randomicos
	 */
	public static void generateRandomCodes() {
		Integer max = 50 ;
		ContainerCode code ;

		Logger log = Logger.getLogger( ContainerCode.class.getName() ) ;

		while ( max-- > 0 ) {
			code = getRandomCode() ;
			log.info( "Codigo: " + code )  ;
		}
	}
}