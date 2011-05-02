package co.edu.uniandes.bi.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import twitter4j.Status;
import twitter4j.Tweet;
import co.edu.uniandes.bi.nlp.LangDetector;
import co.edu.uniandes.bi.vo.Channel;

/**
 * Ofrece los servicios básicos de persistencia
 * 
 * @author Sebastian
 * @param <T>
 * 
 */
public class TweetsDAO {

	// --------------------------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------------------------

	/**
	 * Log de la instancia
	 */
	private Logger log;

	/**
	 * Nombre del driver JDBC de la base de datos
	 */
	private static final String DEFAULT_DATABASE_DRIVER = "org.h2.Driver";

	/**
	 * URL de conexión por defecto
	 */
	private static final String DEFAULT_URL = "jdbc:h2:~/";

	/**
	 * Catálogo con el que se trabaja por defecto
	 */
	private static final String DEFAULT_CATALOG = "laboratorio";

	/**
	 * Tabla de tweets
	 */
	private static final String TWEETS_TABLE = "tweets";

	/**
	 * Usuario por defecto
	 */
	private static final String DEFAULT_USER = "sa";

	/**
	 * Contraseña por defecto
	 */
	private static final String DEFAULT_PASSWORD = "";

	// --------------------------------------------------------------------------------------------------
	// Miembros de la clase
	// --------------------------------------------------------------------------------------------------

	/**
	 * Instancia compartida de la clase
	 */
	private static TweetsDAO instance;
	
	/**
	 * Formato de la fecha
	 */
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

	// --------------------------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------------------------

	/**
	 * Conexión con la base de datos
	 */
	private Connection connection;

	// --------------------------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------------------------

	/**
	 * Constructor por defecto
	 */
	private TweetsDAO() {
		log = Logger.getLogger(this.getClass().getCanonicalName());
		try {
			connect();
			setup();
		} catch (SQLException e) {
			log.severe("Error de conexión a la base de datos");
			e.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException e) {
			log.severe("Driver JDBC no encontrado");
			e.printStackTrace();
			System.exit(1);
		}
	}

	// --------------------------------------------------------------------------------------------------
	// Métodos privados
	// --------------------------------------------------------------------------------------------------

	/**
	 * Abre una conexión a la base de datos
	 */
	private void connect() throws SQLException, ClassNotFoundException {
		Class.forName(DEFAULT_DATABASE_DRIVER);
		connection = DriverManager.getConnection(DEFAULT_URL + DEFAULT_CATALOG,
				DEFAULT_USER, DEFAULT_PASSWORD);

	}

	/**
	 * Crea la tabla utilizada si no existe
	 * 
	 * @throws SQLException
	 */
	private void setup() throws SQLException {
		ResultSet rs = connection.getMetaData().getTables(DEFAULT_CATALOG,
				null, TWEETS_TABLE, null);

		if (!rs.next()) {
			Statement stm = connection.createStatement();
			stm.execute("CREATE TABLE IF NOT EXISTS tweets (id bigint NOT NULL,created_at date NOT NULL,"
					+ "from_user varchar(50) NOT NULL, from_user_id bigint NOT NULL, "
					+ "iso_lang_code varchar(5), profile_img_url varchar(200) NOT NULL,"
					+ "message_text varchar(200) NOT NULL)");
			stm.execute("CREATE TABLE IF NOT EXISTS tweet_analytics (id bigint NOT NULL,"
					+ " polarity double NOT NULL, happiness double NOT NULL, "
					+ "sadness double NOT NULL, fear double NOT NULL, anger double NOT NULL,"
					+ " disgust double NOT NULL, surprise double NOT NULL)");
			stm.execute("CREATE TABLE IF NOT EXISTS tweet_sentiments (id bigint NOT NULL,"
					+ " happiness INT NOT NULL, "
					+ "sadness INT NOT NULL, fear INT NOT NULL, anger INT NOT NULL,"
					+ " disgust INT NOT NULL, surprise INT NOT NULL)");
			stm.close();
		}

		rs.close();
	}

	// --------------------------------------------------------------------------------------------------
	// Métodos públicos
	// --------------------------------------------------------------------------------------------------

	/**
	 * Public void almacena la lista de tweets en la base de datos
	 * 
	 * @param statuses
	 * @throws SQLException
	 */
	public void storeStatuses(List<Status> statuses) throws SQLException {
		PreparedStatement pstm = null;
		String insert = "INSERT INTO tweets(id,created_at,from_user,"
				+ "from_user_id,iso_lang_code,profile_img_url,"
				+ "message_text) VALUES (?,?,?,?,?,?,?)";
		pstm = connection.prepareStatement(insert);
		for (Status status : statuses) {
			pstm.setLong(1, status.getId());
			pstm.setDate(2, new Date(status.getCreatedAt().getTime()));
			pstm.setString(3, status.getUser().getScreenName());
			pstm.setLong(4, status.getUser().getId());
			pstm.setString(5, LangDetector.detectLanguage(status.getText()));
			pstm.setString(6, status.getUser().getProfileImageURL().toString());
			pstm.setString(7, status.getText());

			pstm.executeUpdate();
		}
		pstm.close();

	}

	/**
	 * Public void almacena la lista de tweets en la base de datos
	 * 
	 * @param tweets
	 * @throws SQLException
	 */
	public void storeTweets(List<Tweet> tweets) throws SQLException {
		PreparedStatement pstm = null;
		String insert = "INSERT INTO tweets(id,created_at,from_user,"
				+ "from_user_id,iso_lang_code,profile_img_url,"
				+ "message_text) VALUES (?,?,?,?,?,?,?)";
		pstm = connection.prepareStatement(insert);
		for (Tweet tweet : tweets) {
			pstm.setLong(1, tweet.getId());
			pstm.setDate(2, new Date(tweet.getCreatedAt().getTime()));
			pstm.setString(3, tweet.getFromUser());
			pstm.setLong(4, tweet.getFromUserId());
			pstm.setString(
					5,
					tweet.getIsoLanguageCode() != null ? tweet
							.getIsoLanguageCode() : LangDetector
							.detectLanguage(tweet.getText()));
			pstm.setString(6, tweet.getProfileImageUrl());
			pstm.setString(7, tweet.getText());

			pstm.executeUpdate();
		}
		pstm.close();

	}
	
	/**
	 * Busca la información de un canal de social media dado su nombre
	 * @param name
	 * @return channel
	 * @throws SQLException 
	 */
	public Channel findChannelByName(String name) throws SQLException {
		Channel channel = null;
		Statement stm = connection.createStatement();
		
		ResultSet rs = stm
				.executeQuery("SELECT * FROM DIM_CHANNEL WHERE channel_name="+name);

		if (rs.next()) {
			channel = new Channel();
			channel.setKey(rs.getLong(1));
			channel.setName(rs.getString(2));
			channel.setDescription(rs.getString(3));
			rs.close();
		}

		stm.close();
		
		return channel;
	}
	
	/**
	 * Verifica si la fecha dada ya se existe en la dimensión tiempo
	 * y busca su llave
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public int findKeyByDate(java.util.Date date) throws SQLException {
		int dateKey = -1;
		
		Statement stm = connection.createStatement();
		
		ResultSet rs = stm
				.executeQuery("SELECT * FROM DIM_DATE WHERE date_key="+formatter.format(date));
		
		if (rs.next()) {
			dateKey = rs.getInt(1);
		}
		
		rs.close();
		stm.close();
		
		return dateKey;
	}
	
	/**
	 * Guarda las filas en la tabla dada
	 * @param targetTable
	 * @param tableFields nombre de las columnas de la tabla
	 * @param fieldValues valores de las columnas de la tabla (una lista de valores por cada fila)
	 * @return rowKeys lista de llaves asignadas a las filas que se insertaron. Revisar la función IDENTITY del motor H2
	 */
	public List<Long> storeRows(String targetTable, List<String> tableFields, Collection<List> fieldValues) throws SQLException{
		List<Long> rowKeys = null;
		//TODO guardar las filas de acuerdo al contrato	
		//se debe averiguar el tipo de cada campo de la lista de valores de la fila
		
		//TODO para cada fila guardada averiguar la llave sustituta que se le asignó
		return rowKeys;
	}
	
	
	/**
	 * Obtiene los campos requeridos (fieldsToRetrieve) buscando la correspondencia entre tableFields y fieldValues
	 * @param targetTable tabla de la que se extraen los valores de los campos
	 * @param tableFields campos de la derecha por los que se hace la búsqueda (WHERE tableField[i] = fieldValues[i][j]
	 * @param fieldValues campos de la izquierda por los que se hace la búsqueda (WHERE tableField[i] = fieldValues[i][j]
	 * @param fieldsToRetrieve campos de la tabla que se desean obtener
	 * @return rows
	 */
	public List retrieveFields(String targetTable, List<String> tableFields, Collection<List> fieldValues, List<String> fieldsToRetrieve) throws SQLException{
		List rows = null;
		//TODO retornar los campos requeridos haciendo join entre los campos de la izquierda y la derecha
		
		// Seudo-código
		// SELECT fieldsToRetrieve FROM targetTable WHERE tableFields = fieldValues
		
		return rows;
	}
	
	/**
	 * Cierra la conexión a la base de datos
	 * 
	 */
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				log.warning("Ocurrió un error al cerrar la conexión");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Borra todos los registros de las tablas de la base de datos
	 * 
	 * @throws SQLException
	 */
	public void cleanUp() throws SQLException {
		Statement stm = connection.createStatement();
		stm.execute("DELETE FROM tweets");
		stm.execute("DELETE FROM tweet_analytics");
		stm.execute("DELETE FROM tweet_sentiments");
	}

	// --------------------------------------------------------------------------------------------------
	// Métodos estáticos
	// --------------------------------------------------------------------------------------------------

	/**
	 * Obtiene una instancia de la persistencia
	 */
	public static TweetsDAO getInstance() {
		if (instance == null) {
			instance = new TweetsDAO();
		}
		return instance;
	}

}
