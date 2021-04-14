package br.yardplanner.model;

import java.util.Set;

import javax.persistence.*;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.validator.annotations.*;

import br.yardplanner.util.Util;

/**
 * Model do Usu·rio
 * 
 * @author alissonperez
 */
@Entity
@Table(name="Users")
public class User {
	
	/**
	 * Instancia da classe de log
	 */
	@Transient
	private Logger log ;
	
	/**
	 * Id do usu·rio no banco
	 */
	@Id
	@GeneratedValue
	@Column(name="user_id")
	private Integer userId ;
	
	/**
	 * Primeiro nome do usu·rio
	 */
	@Column(name="first_name")
	private String firstName ;
	
	/**
	 * ⁄ltimo nome do usu·rio
	 */
	@Column(name="last_name")
	private String lastName ;
	
	/**
	 * E-mail do usu·rio
	 */
	private String email ;
	
	/**
	 * Senha do usu·rio
	 * Codificada em MD5
	 */
	private String password ;
	
	/**
	 * Senha tempor‚Ä°ria do usu·rio
	 * caso o mesmo esque¬ça a senha, esta senha ser‚Ä° preenchida com a que ser‚Ä° enviada para o mesmo
	 */
	@Column(name="password_temp")
	private String passwordTemporary ;
	
	/**
	 * Determina se o usu·rio È administrador do sistema
	 */
	@Column(columnDefinition = "BIT")
	@Type( type = "org.hibernate.type.BooleanType" )
	private Boolean root ;
	
	/**
	 * Determina se o usu·rio esta bloqueado ou n„o para acesso
	 */
	@Column(columnDefinition = "BIT")
	@Type(type = "org.hibernate.type.BooleanType")
	private Boolean blocked ;
	
	/**
	 * Patios do usu·rio
	 */
	@OneToMany(mappedBy="owner" , fetch = FetchType.LAZY)
	@OrderBy("yardId")
	private Set<Yard> yards ;
	
	/**
	 * Construtor
	 */
	public User() {
		this.log = Logger.getLogger( User.class.getName() ) ;
	}

	/**
	 * @return Retorna true se o usu·rio for administrador
	 */
	public Boolean isRoot() {
		return root;
	}

	/**
	 * Seta se o usu·rio È administrador
	 * @param root True se o usu·rio for administrador
	 */
	public void setRoot(Boolean root) {
		this.root = root;
	}

	/**
	 * @return Retorna se o usu·rio est· bloqueado ou n„o
	 */
	public boolean isBlocked() {
		this.log.debug( "Blocked: " + this.blocked ) ;
		return blocked;
	}

	/**
	 * Seta se o usu·rio est· bloqueado ou n„o
	 * @param blocked true se o usu·rio estiver bloqueado
	 */
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	/**
	 * @return Retorna o Id do usu·rio
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Seta o id do usu·rio no banco
	 * @param userId Id do usu·rio no banco
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return Retorna o primeiro nome do usu·rio
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Seta o primeiro nome do usu·rio
	 * @param firstName Primeiro nome do usu·rio
	 */
	@RequiredStringValidator(message = "Campo 'Primeiro nome' obrigatÛrio" , shortCircuit = true )
	@StringLengthFieldValidator(message = "Campo 'Primeiro nome' deve ter no mÌnimo 4 caracteres" , minLength = "4" )
	public void setFirstName(String firstName) {
		firstName = firstName.trim() ;
		this.firstName = firstName ;
	}

	/**
	 * @return Retorna o ˙ìltimo nome do usu·rio
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Seta o ˙ìltimo nome do usu·rio
	 * @param lastNam ⁄ltimo nome do usu·rio
	 */
	@RequiredStringValidator(message = "Campo '⁄ltimo nome' obrigatÛrio" , shortCircuit = true )
	@StringLengthFieldValidator(message = "Campo '⁄ltimo nome' deve ter no mÌnimo 3 caracteres" , minLength = "3" )
	public void setLastName(String lastName) {
		lastName = lastName.trim() ;
		this.lastName = lastName;
	}

	/**
	 * @return Retorna o e-mail do usu·rio
	 */
	@RequiredStringValidator( message = "Campo 'E-mail' obrigatÛrio" , shortCircuit = true )
	@EmailValidator( message = "Campo 'E-mail' inv·lido" )
	public String getEmail() {
		return email;
	}

	/**
	 * Seta o e-mail do usu·rio
	 * @param email E-mail do usu·rio
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Retorna a senha
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Seta a senha do usu·rio
	 * @param password Senha do usu·rio
	 */
	@StringLengthFieldValidator(message = "Campo 'Senha' deve ter no mÌnimo 6 caracteres" , minLength = "6" )
	public void setPassword( String password ) {
		this.password = password;
	}
	
	/**
	 * Seta a senha codificando a mesma em MD5
	 * @param password senha do usu·rio
	 */
	public void setPasswordMD5( String password ) {
		password = Util.MD5(password) ;
		this.password = password;
	}

	/**
	 * @return Retorna a senha tempor·ria do usu·rio
	 */
	public String getPasswordTemporary() {
		return passwordTemporary;
	}

	/**
	 * Seta a senha tempor·ria do usu·rio
	 * @param passwordTemporary Senha tempor·ria do usu·rio
	 */
	public void setPasswordTemporary(String passwordTemporary) {
		this.passwordTemporary = passwordTemporary;
	}
	
	/**
	 * Seta a senha temporaria codificando em MD5
	 * 
	 * @param passwordTemporary senha tempor·ria do usu·rio
	 */
	public void setPasswordTemporaryMD5(String passwordTemporary) {
		passwordTemporary = Util.MD5(passwordTemporary) ;
		this.passwordTemporary = passwordTemporary;
	}
	
	/**
	 * @return Retorna os p·tios do usu·rio
	 */
	public Set<Yard> getYards() {
		return yards;
	}

	/**
	 * Seta os p·tios do usu·rio
	 * @param yards P·tios do usu·rio
	 */
	public void setYards(Set<Yard> yards) {
		this.yards = yards;
	}

	/**
	 * RepresentaÁ„o em string do usu·rio
	 * @return
	 */
	public String toString() {
		return "Nome: " + this.getFirstName() + " " + this.getLastName() +
				"\nE-mail: " + this.getEmail() +
				"\nPassword: " + this.getPassword() + 
				"\nUser Id: " + this.getUserId() + 
				"\nblocked: " + this.blocked + 
				"\nroot: " + this.root ;			
	}
}
