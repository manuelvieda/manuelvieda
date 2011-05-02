/**
 * 
 */
package manuelvieda.ETL;


import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import manuelvieda.twitter.TwitterAsyncAdapter;
import manuelvieda.twitter.TwitterAsyncListener;

import twitter4j.QueryResult;
import twitter4j.Tweet;

/**
 * Esta clase contiene todo el proceos ETL de la entrega 5 del proyecto
 * @author Manuel Vieda
 */
public class ProcesoETL extends TwitterAsyncListener implements Runnable{
	
	//---------------------------------------------------------------------------
	// ATRIBUTOS
	//---------------------------------------------------------------------------
	
	/**
	 * Servicios de Twitter
	 */
	private TwitterAsyncAdapter twitter;
	
	/**
	 * Log de la instancia
	 */
	private static Logger log;
	
	//---------------------------------------------------------------------------
	// CONSTRUCTOR
	//---------------------------------------------------------------------------
	
	public ProcesoETL(){
		twitter = TwitterAsyncAdapter.getInstance();
		twitter.addTwitterListener(this);
		log = Logger.getLogger(this.getClass().getCanonicalName());
	}
	
	//---------------------------------------------------------------------------
	// METODOS PRIVADOS
	//---------------------------------------------------------------------------
	
	/**
	 * Busca los mensajes de un topico determinado por el parametro dentro del grupo
	 * de usuario
	 * @param users
	 * @param topic
	 */
	private void searchTweetsFromUsers(String[] users, String topic) {
		StringBuffer query = new StringBuffer();
		query.append("matrimonio");
		twitter.search(query.toString());

	}
	
	//---------------------------------------------------------------------------
	// METODOS PUBLICOS
	//---------------------------------------------------------------------------


	
	
	// --------------------------------------------------------------------------------------------------
	// TwitterAsyncListener
	// --------------------------------------------------------------------------------------------------
	
	@Override
	public void searched(QueryResult result) {
		List<Tweet> tweets = result.getTweets();
		for (Tweet tweet : tweets) {
			System.out.println("ID"+tweet.getId()+" - "+tweet.getText());
		}
		System.out.println("Twitter Async Listener");
	}

	//---------------------------------------------------------------------------
	// RUNNABLE
	//---------------------------------------------------------------------------
	@Override
	public void run() {
		String users[] = {"manuelvieda"};
		System.out.println("Busqueda para usuarios: "+users.toString());
		searchTweetsFromUsers(users, "Ruta");
		System.out.println("Termina busqueda para usuarios (RUN)");
	}
	
	
	//---------------------------------------------------------------------------
	// MAIN
	//---------------------------------------------------------------------------
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Se inicia ETL");
		
		ProcesoETL analysis = new ProcesoETL();
		
		new Thread(analysis).start();
		
		JOptionPane.showMessageDialog(null, "Oprima ACEPTAR cuando haya recibido las estadisticas!");
	}

}
