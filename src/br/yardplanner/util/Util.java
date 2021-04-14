package br.yardplanner.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Iterator;


/**
 * Classe com método úteis ao sistema
 * @author Alisson
 */
public class Util {
	
	/**
	 * Obtem o MD5 de uma string.<br>
	 * Exemplo:
	 * <pre>
	 * String teste = "Alisson" ;
	 * String result = Util.MD5( teste ) ; // result = "48ca192d5394397a37e28e5b913dcdb5"
	 * </pre>
	 * 
	 * @param str String que deseja-se converterm em MD5
	 * 
	 * @return MD5 da String passada
	 */
	public static String MD5( String str ) {
        MessageDigest md = null;  
        
        try {
            md = MessageDigest.getInstance( "MD5" ) ;
        }
        catch ( NoSuchAlgorithmException e ) {
            e.printStackTrace();  
        }
        
        BigInteger hash = new BigInteger( 1 , md.digest( str.getBytes() ) ) ;
        
        return hash.toString(16);
	}
	
	/**
	 * Junta uma lista com uma string de "cola".
	 * 
	 * <pre>
	 * List<Integer> listaTeste = new ArrayList<Integer>( Arrays.asList( 1 , 2 , 3 , 5 , 8 , 13 , 21 ) ) ;
	 * String result = Util.implode( listaTeste , "@" ) ; // result = "1@2@3@5@8@13@21"
	 * </pre>
	 * 
	 * @param list Lista que se deseja unir
	 * @param glue String para junção dos elementos
	 * @return String com os elementos colados
	 */
	public static String implode( List<Integer> list , String glue ) {
		StringBuilder implode ;
		
		implode = new StringBuilder() ;
		Iterator<Integer> it = list.iterator() ;
		Integer obj ;
		while ( it.hasNext() ) {
			obj = it.next() ;
			implode.append( obj.toString() ) ;
			if ( it.hasNext() ) {
				implode.append( glue ) ;
			}
		}
		
		return implode.toString() ;
	}

}