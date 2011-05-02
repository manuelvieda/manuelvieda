package co.edu.uniandes.bi.etl.imp;

import java.util.Collection;

import twitter4j.Tweet;
import twitter4j.User;

import co.edu.uniandes.bi.etl.Activity;
import co.edu.uniandes.bi.etl.Row;
import co.edu.uniandes.bi.exception.DataAccessException;
import co.edu.uniandes.bi.twitter.TwitterService;

/**
 * Extrae la información de las reacciones de Twitter
 * @author Sebastian
 *
 */
public class TwitterExtractionActivity extends Activity {

	// --------------------------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Termino de búsqueda en Twitter
	 */
	private String searchTermField;
	
	/**
	 * Instancia del servicio de Twitter
	 */
	private TwitterService twitter;
	
	// --------------------------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Constructor por defecto
	 */
	public TwitterExtractionActivity() {
		super();
		twitter = TwitterService.getInstance();
	}
	
	// --------------------------------------------------------------------------------------------------
	// Métodos privados
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Agrega las reacciones a los eventos
	 * @param tweets
	 * @param row
	 */
	private void addTweetFieldsToRow(Collection<Tweet> tweets, Row row) {
		boolean firstFlag = true;
		Row newRow = null;
		User user = null;
		for (Tweet tweet : tweets) {
			if (firstFlag) {
				newRow = row;
			} else {
				newRow = row.clone();
			}
			newRow.setField("response_type", "Tweet");
			newRow.setField("response_content", tweet.getText());
			newRow.setField("user_login", tweet.getFromUser());
			
			//TODO implementar método en TwitterService
			user = twitter.lookupUser(tweet.getFromUser());
			newRow.setField("real_name", user.getName());
			
			output.add(newRow);
			firstFlag = false;
		}
	}
	
	// --------------------------------------------------------------------------------------------------
	// Métodos públicos
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Establece el nombre del campo de la entrada que corresponde con el 
	 * término de búsqueda en Twitter
	 * @param searchTermField
	 */
	public void setSearchTermField(String searchTermField) {
		this.searchTermField = searchTermField;
	}
	
	/**
	 * Encuentra las reacciones a los eventos monitoreados
	 */
	@Override
	public void execute() {
		try {
			String searchTerm = null;
			Collection<Tweet> tweets = null;
			for (Row row: input) {
				searchTerm = (String) row.getField(searchTermField);
				//TODO arreglar método TwitterService#search
				tweets = twitter.search(searchTerm);
				addTweetFieldsToRow(tweets,row);
			}
			executeNext();
		} catch (DataAccessException e) {
			flagCompleted = true;
			flagError = true;
			e.printStackTrace();
		}
	}

}
