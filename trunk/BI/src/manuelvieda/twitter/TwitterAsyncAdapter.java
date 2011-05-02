package manuelvieda.twitter;

import java.util.logging.Logger;

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.Query;
import twitter4j.TwitterListener;


/**
 * Clase que se encarga de gestionar los servicios de Twitter de manera asincronica
 * (Basado en el ejemplo del laboratorio 4 de la clase de Inteligencia de Negocios
 * de la Universidad de los Andes, escrito por Sebastian)
 * 
 * @author Manuel Vieda
 */
public class TwitterAsyncAdapter{
	
	
	//---------------------------------------------------------------------------
	// ATRIBUTOS
	//---------------------------------------------------------------------------
	
	
	/**
	 * Instancia de la clase
	 */
	private static TwitterAsyncAdapter instance;
	
	
	/**
	 * Conexión a los servicios de Twitter
	 */
	private AsyncTwitter asyncTwitter;
	
	/**
	 * Log de la instancia
	 */
	private Logger log;
	
	
	//---------------------------------------------------------------------------
	// CONSTRUCTOR
	//---------------------------------------------------------------------------
	
	/**
	 * Constructor privado
	 */
	private TwitterAsyncAdapter() {
		log = Logger.getLogger(this.getClass().getCanonicalName());
		asyncTwitter = new AsyncTwitterFactory().getInstance();
	}
	
	
	//---------------------------------------------------------------------------
	// METODOS PUBLICOS
	//---------------------------------------------------------------------------
	
	/**
	 * Registra una instancia que va a escuchar los resultados de los servicios llamados  
	   @param listener
	 */
	public void addTwitterListener(TwitterListener listener) {
		asyncTwitter.addListener(listener);
	}
	
	
	public void search(String queryString){
		Query query = new Query(queryString);
		query.setRpp(50);
		query.setPage(2);
		
		log.info("Consulta enviada: "+query);
		
		asyncTwitter.search(query);
	}
	
	
	//---------------------------------------------------------------------------
	// METODOS ESTATICOS
	//---------------------------------------------------------------------------
	
	/**
	 * Se otiene una instancia de la clase
	 */
	public static TwitterAsyncAdapter getInstance() {
		return instance==null? new TwitterAsyncAdapter():instance;
	}




}
