package br.yardplanner.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Class útil do hibernate.<br>
 * Instancia a SessionFactory fazendo uso de um singleton.
 * 
 * @author Alisson
 */
public final class HibernateUtil {

	/**
	 * SessionFactory do hibernate
	 */
	private static SessionFactory factory ;
	
	/**
	 * Retorna a session do hibernate.
	 * @return
	 */
    public static Session getSession() {
    	
    	if ( factory == null ) {
    		Configuration configuration = new Configuration();
    	    configuration.configure();
    	    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
    	    factory = configuration.buildSessionFactory(serviceRegistry);
    	}
    	
        return factory.openSession();
    }
	
}