package util;

import org.hibernate.Hibernate;  
import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.cfg.Configuration;  
    

/**
 * <p>HibernateUtil class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
public class HibernateUtil  
{  
    private static SessionFactory sessionFactory;  
    private static Configuration configuration=new Configuration();
    private static boolean annotationActive = false;  
    private static final ThreadLocal threadSession = new ThreadLocal();  
   
    public static void initialize()  
    {  
        try  
        {   sessionFactory = configuration.configure().buildSessionFactory();
            sessionFactory.openSession();
            System.out.println( ">> HIBERNATE INICIADO COM SUCESSO." );
        }  
        catch ( Throwable e )  
        {  
           System.out.println( ">> FALHA NA INICIA��O DO HIBERNATE." );  
           e.printStackTrace();  
        }  
    }  
  
 
    public static Configuration getConfiguration() {  
          if( configuration == null )  
             configuration =  new Configuration();
          return configuration;  
   }  
  
  
   public static void setConfiguration(Configuration cfg) {
      HibernateUtil.configuration = cfg;  
   }  
  

   public static boolean isAnnotationActive() {  
      return annotationActive;  
   }  
  

   public static void setAnnotationActive(boolean annotationActive) {  
      HibernateUtil.annotationActive = annotationActive;  
   }  
  
   public static void reinitialize(){  
      closeSession();  
      initialize();  
      getSession();  
   }  
  
  
  
    public static void initializeProperties( Object obj )  
    {  
        if ( !Hibernate.isInitialized( obj ) )  
        {  
            Hibernate.initialize( obj );  
        }  
    }  
  
  
    public static void refreshObject( Object obj )  
    {  
        Session s = HibernateUtil.getSession();  
        s.refresh( obj );  
    }  
  
  
    
    public static void clearSession()  
    {  
        Session s = HibernateUtil.getSession();  
        s.clear();  
    }  
  
    public static Session getSession()  
    {  
        if( sessionFactory == null ){
            initialize();
            closeSession();
        }

        Session s = sessionFactory.openSession();
        threadSession.set( s );
        return s;  
    }  
  
  
    public static void closeSession()  
    {  
        Session s = (Session) threadSession.get();  
        threadSession.set( null );  
        if ( s != null && s.isOpen() )  
        {  
            s.close();  
        }  
    }

  
    public static void main(String args[]){
        Session session=HibernateUtil.getSession();
        session.getTransaction().begin();
       
        session.close();
    }
}  
