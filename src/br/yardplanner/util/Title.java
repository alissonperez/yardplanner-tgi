package br.yardplanner.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Classe gerenciadora do título das páginas
 * Exemplo: <pre>
 * Title title = new Title( "MeuSite" ) ;
 * title.add( "Usuarios" ) ;
 * title.add( "Usuário fulano" ) ;
 * String tituloPronto = title.toString() ; // tituloPronto = "MeuSite - Usuários - Usuário fulano"
 * 
 * title.reset() ;
 * title.add( "Home" ) ;
 * String outroTituloPronto = title.toString() ; // tituloPronto = "MeuSite - Home"
 * </pre>
 * @author Alisson
 */
public class Title {
	
	/**
	 * Instancia da classe de log
	 */
	private Logger log ;
	
	/**
	 * Nome do site
	 */
	private String appName ;
	
	/**
	 * Lista com os ítens para navegação do sistema
	 */
	private List<String> navigation ;
	
	/**
	 * Construtor que não recebe o nome do sistema
	 */
	public Title() {
		this.navigation = new ArrayList<String>() ;
		this.appName = "YardPlanner" ;
		this.log = Logger.getLogger( Title.class.getName() ) ;
		this.reset() ;
	}
	
	/**
	 * Construtor que recebe o nome do aplicativo do sistema.
	 * @param appName Nome do aplicativo para servir de base para o título da página
	 */
	public Title( String appName ) {
		this.navigation = new ArrayList<String>() ;
		this.appName = appName ;
		this.log = Logger.getLogger( Title.class.getName() ) ;
		this.reset() ;
	}
	
	/**
	 * Incluir um novo ítem na navegação para ser exibido no título
	 * @param location
	 */
	public void add( String location ) {
		if ( ! this.navigation.contains( location ) ) {
			this.navigation.add( location ) ;			
		}
	}
	
	/**
	 * Retorna o título concatenado.
	 * Ex: <i>Sistema - Usuários - Usuário Fulano</i>
	 * 
	 * @return Retorna o título pronto
	 */
	public String toString() {
		Iterator<String> titleIt = this.navigation.iterator() ;
		StringBuilder sb = new StringBuilder() ;
		
		while ( titleIt.hasNext() ) {
			sb.append( titleIt.next().trim() ) ;
			
			if ( titleIt.hasNext() ) {
				sb.append( " - " ) ;
			}
		}
		
		return sb.toString() ;
	}
	
	/**
	 * Reseta o título da página, mantendo apenas o nome do sistema
	 */
	public void reset() {
		this.navigation.clear() ;
		
		this.navigation.add( this.appName ) ;
	}

}