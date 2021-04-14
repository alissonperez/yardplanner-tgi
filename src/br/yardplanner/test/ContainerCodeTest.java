package br.yardplanner.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import br.yardplanner.model.ContainerCode;

/**
 * Classe de teste para a classe ContainerCode
 * @author Alisson
 *
 */
public class ContainerCodeTest {
	/**
	 * Testa os getters e setters
	 */
	@Test
	public void testGetAndSetCode() {
		ContainerCode code = new ContainerCode( "CSQU3054383" ) ;
		Assert.assertEquals( "CSQU3054383" , code.getCode() ) ;
		
		code.setCode( "TOLU4734787" ) ;
		Assert.assertEquals( "TOLU4734787" , code.getCode() ) ;
		
		code.setCode( "TOLU473478" ) ;
		Assert.assertEquals( "TOLU473478" , code.getCode() ) ;
	}
	
	/**
	 * Testa se um determinado código válido
	 */
	@Test
	public void testIsValid() {
		ContainerCode code = new ContainerCode( "CSQU3054383" ) ;
		Assert.assertTrue( code.isValid() ) ;
		
		code.setCode( "TOLU4734787" ) ;
		Assert.assertTrue( code.isValid() ) ;
	}
	
	/**
	 * Testa se um determinado código Ž inválido
	 */
	@Test
	public void testIsInvalid() {
		ContainerCode code = new ContainerCode( "CSQU3054384" ) ;
		Assert.assertFalse( code.isValid() ) ;
		
		code.setCode( "TOLU4734788" ) ;
		Assert.assertFalse( code.isValid() ) ;
	}
	
	/**
	 * Testa o calculo do dígito verificado
	 */
	@Test
	public void testCalcDigit() {
		ContainerCode code = new ContainerCode( "CSQU305438" ) ;
		code.createDigit() ;
		Assert.assertEquals( "CSQU3054383" , code.getCode() ) ;
		
		code = new ContainerCode( " tolu473478" ) ;
		code.createDigit() ;
		Assert.assertEquals( "TOLU4734787" , code.getCode() ) ;
		
		code = new ContainerCode( "tolu473478 " ) ;
		code.createDigit() ;
		Assert.assertEquals( "TOLU4734787" , code.getCode() ) ;
	}
	
	@Test
	public void testIsValidTwoDigits() {
		ContainerCode code = new ContainerCode( "JHQU028429" ) ;
		code.createDigit() ;
		// Digito é 10
		Assert.assertEquals( "JHQU02842910" , code.getCode() ) ;
		
		code = new ContainerCode( "JHQU02842910" ) ;
		Assert.assertTrue( code.isValid() ) ;
	}
}
