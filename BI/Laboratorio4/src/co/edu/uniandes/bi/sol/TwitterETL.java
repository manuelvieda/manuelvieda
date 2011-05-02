package co.edu.uniandes.bi.sol;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import twitter4j.QueryResult;
import twitter4j.Tweet;
import co.edu.uniandes.bi.data.TweetsDAO;
import co.edu.uniandes.bi.data.TwitterStats;
import co.edu.uniandes.bi.exception.ChartBuilderException;
import co.edu.uniandes.bi.graph.ChartBuilder;
import co.edu.uniandes.bi.nlp.AnalysisResult;
import co.edu.uniandes.bi.nlp.EmotionsAnalyzer;
import co.edu.uniandes.bi.twitter.TwitterAsyncAdapter;
import co.edu.uniandes.bi.twitter.TwitterAsyncListener;

/**
 * Solución del laboratorio
 *
 */
public class TwitterETL extends TwitterAsyncListener implements Runnable{
	
	// --------------------------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------------------------
	/**
	 * Persistencia de la aplicación
	 */
	private TweetsDAO persistence;
	
	/**
	 * Servicios de Twitter
	 */
	private TwitterAsyncAdapter twitter;
	
	/**
	 * Página actual de la consulta
	 */
	private int currentPage;
	
	/**
	 * Flag que indica si se está desarrollando el caso de uso 1
	 */
	private boolean useCase1;
	
	/**
	 * Log de la instancia
	 */
	private static Logger log;
	
	/**
	 * Usuarios que se van a consultar en el primer caso de uso
	 */
	private String[] userNames;
	
	/**
	 * Tema que se va a monitorear
	 */
	private String topic;
	
	// --------------------------------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Constructor por defecto
	 */
	public TwitterETL() {
		currentPage = 1;
		persistence = TweetsDAO.getInstance();
		
		try {
			persistence.cleanUp();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		twitter = TwitterAsyncAdapter.getInstance();
		twitter.addTwitterListener(this);
		log = Logger.getLogger(this.getClass().getCanonicalName());
		
	}
	// --------------------------------------------------------------------------------------------------
	// Métodos privados
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Busca los mensajes de los usuarios que hablan de un tema dado
	 * @param users
	 * @param topic
	 */
	private void searchTweetsFromUsers(String[] users, String topic) {
		StringBuffer query = new StringBuffer();
		
		//TODO: construir la cadena de caracteres de la consulta
		
		twitter.search(query.toString(),currentPage);
	}
	
	/**
	 * Busca los mensajes que hablan de un tema dado
	 * @param topic
	 */
	private void searchTweetsOfTopic(String topic) {
		twitter.search(topic,currentPage);
	}
	
	/**
	 * Obtiene las estadísticas para los mensajes recolectados
	 */
	private void getStats() {
		
		try {
			TwitterStats stats = persistence.calculateStats();
			log.info("Stats:");
			log.info("total positive: "+stats.getPositiveCount());
			log.info("total negative: "+stats.getNegativeCount());
			log.info("total neutral: "+stats.getNeutralCount());
			log.info("total tweets: "+stats.getTotalTweets());
			log.info("emotions: "+Arrays.toString(stats.getEmotions()));
			log.info("emotions frecuency: "+Arrays.toString(stats.getEmotionsFirstFrequency()));
			
			ChartBuilder.buildEmotionsChart(stats.getEmotions());
			ChartBuilder.buildValenceChart(stats.getPositiveCount(), stats.getNeutralCount(), stats.getNegativeCount(),(useCase1 ? 10: 1500));
			ChartBuilder.buildEmotionsOrderChart(stats.getEmotionsFirstFrequency(),(useCase1 ? 10: 1500));
			
			persistence.cleanUp();
			persistence.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ChartBuilderException e) {
			e.printStackTrace();
		}
	}
	
	// --------------------------------------------------------------------------------------------------
	// Getters y Setters
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * @return the useCase1
	 */
	public boolean isUseCase1() {
		return useCase1;
	}

	/**
	 * @param useCase1 the useCase1 to set
	 */
	public void setUseCase1(boolean useCase1) {
		this.useCase1 = useCase1;
	}
	
	/**
	 * @param userNames the userNames to set
	 */
	public void setUserNames(String[] userNames) {
		this.userNames = userNames;
	}

	/**
	 * @return the userNames
	 */
	public String[] getUserNames() {
		return userNames;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}
	
	// --------------------------------------------------------------------------------------------------
	// TwitterAsyncListener
	// --------------------------------------------------------------------------------------------------
	
	@Override
	public void searched(QueryResult result) {
		List<Tweet> tweets = result.getTweets();
		Collection<AnalysisResult> results = new LinkedList<AnalysisResult>();
		
		AnalysisResult analysis = null;
		
		log.info("page: "+currentPage+"since_id:"+result.getSinceId()+" max_id:"+result.getMaxId());
		
		try {
			persistence.storeTweets(tweets);
			
			for (Tweet tweet : tweets) {
				analysis = EmotionsAnalyzer.analyze(tweet.getText());
				analysis.setMsgId(tweet.getId());
				results.add(analysis);
			}
			
			persistence.storeSentiments(results);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if ( currentPage < 15 && tweets.size() > 0) {
			currentPage++;
			log.info("Pasando a página: "+currentPage);
			if ( useCase1 ) {
				executeUseCase1();
			} else {
				executeUseCase2();
			}
		} else {
			log.info("Consulta terminada. Página: "+currentPage);
			currentPage = 0;
			getStats();
		}
	}
	
	// --------------------------------------------------------------------------------------------------
	// Runnable
	// --------------------------------------------------------------------------------------------------
	
	@Override
	public void run() {
		if (isUseCase1()) {
			executeUseCase1();
		} else {
			executeUseCase2();
		}
		
	}
	
	// --------------------------------------------------------------------------------------------------
	// Casos de uso
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Ejecuta el caso de uso 1:
	 * Búsqueda de temas en un número predefinido de usuarios
	 */
	public void executeUseCase1(){
		searchTweetsFromUsers(userNames, topic);
	}
	
	/**
	 * Ejecuta el caso de uso 2:
	 * Búsqueda de temas en Twitter
	 */
	public void executeUseCase2() {
		//TODO Construir la consulta
		String query = "";
		searchTweetsOfTopic(query);
	}
	
	// --------------------------------------------------------------------------------------------------
	// Main
	// --------------------------------------------------------------------------------------------------
	
	public static void main(String... args) {
		TwitterETL analysis = new TwitterETL();
		String useCase1 ="Caso de uso 1";
		String useCase2 ="Caso de uso 2";
		String[] possibleValues = new String[]{useCase1,useCase2};
		String selectedValue = (String)JOptionPane.showInputDialog(null,
	            "Elija un caso de uso", "Laboratorio Twitter",
	            JOptionPane.INFORMATION_MESSAGE, null,
	            possibleValues, possibleValues[0]);
		if (selectedValue != null && selectedValue.equals(useCase1)) {
			analysis.setUseCase1(true);
		} else if (selectedValue != null && selectedValue.equals(useCase2)){
			analysis.setUseCase1(false);
		} else {
			log.severe("Caso de uso no implementado");
			System.exit(0);
		}
		
		new Thread(analysis).start();
		JOptionPane.showMessageDialog(null, "Oprima aceptar cuando haya recibido las estadísticas");
		
	}
	
}
