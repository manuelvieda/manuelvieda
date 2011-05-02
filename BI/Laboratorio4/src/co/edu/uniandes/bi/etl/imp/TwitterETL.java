package co.edu.uniandes.bi.etl.imp;

import java.util.logging.Logger;

/**
 * Solución del laboratorio
 *
 */
public class TwitterETL {
	
	// --------------------------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Log de la instancia
	 */
	private static Logger log;
	
	private CSVSourceActivity eventsLoad;
	
	private JDBCDestinationActivity dimEvent;
	
	private LookupActivity dimChannel;
	
	private TwitterExtractionActivity reactionExtraction;
	
	private JDBCDestinationActivity dimUserResponse;
	
	private JDBCDestinationActivity dimUser;
	
	private DerivedColumnActivity polarityCalc;
	
	private LookupActivity dimDate;
	
	private JDBCDestinationActivity factUserResponse;
	
	// --------------------------------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Constructor por defecto
	 */
	public TwitterETL() {
		log = Logger.getLogger(this.getClass().getCanonicalName());
		
		factUserResponse = new JDBCDestinationActivity();
		dimDate = new LookupActivity();
		dimUser = new JDBCDestinationActivity();
		dimUserResponse = new JDBCDestinationActivity();
		polarityCalc = new DerivedColumnActivity();
		reactionExtraction = new TwitterExtractionActivity();
		dimChannel = new LookupActivity();
		dimEvent = new JDBCDestinationActivity();
		eventsLoad = new CSVSourceActivity();
	}
	
	
	// --------------------------------------------------------------------------------------------------
	// Métodos privados
	// --------------------------------------------------------------------------------------------------
	
	
	private void setupCSVSourceActivity() {
		eventsLoad.setCSVFilePath("./data");
		eventsLoad.setNextActivity(dimEvent);
	}
	
	private void setupDimEvent() {
		dimEvent.setNextActivity(dimChannel);
		//TODO configurar la tabla de destino y la correpondencia entre campos
	}
	
	private void setupDimChannel() {
		dimChannel.setNextActivity(reactionExtraction);
		//TODO configurar la tabla de destino, los criterios de join y los campos a retornar
	}
	
	private void setupReactionExtraction() {
		reactionExtraction.setNextActivity(polarityCalc);
		//TODO configurar el nombre de la columna que contiene el término de consulta
	}
	
	private void setupPolarityCalc() {
		polarityCalc.setNextActivity(dimUserResponse);
		//TODO configurar el nombre de la columna que contiene el texto al que se le va a medir la polaridad
	}
	
	private void setupDimUserResponse(){
		dimUserResponse.setNextActivity(dimUser);
		//TODO configurar la tabla de destino y la correpondencia entre campos
	}
	
	private void setupDimUser() {
		dimUser.setNextActivity(dimDate);
		//TODO configurar la tabla de destino y la correpondencia entre campos
	}
	
	private void setupDimDate() {
		dimDate.setNextActivity(factUserResponse);
		//TODO configurar la tabla de destino, los criterios de join y los campos a retornar
	}
	
	private void setupFactUserResponse() {
		//TODO configurar la tabla de destino y la correpondencia entre campos
	}
	
	/**
	 * Arma el flujo de trabajo para el proceso de ETL
	 */
	private void setupWorkFlow() {
		setupCSVSourceActivity();
		setupDimEvent();
		setupDimChannel();
		setupReactionExtraction();
		setupPolarityCalc();
		setupDimUserResponse();
		setupDimUser();
		setupDimDate();
		setupFactUserResponse();
	}
	
	// --------------------------------------------------------------------------------------------------
	// Métodos públicos
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Configura el flujo de trabajo y lo ejecuta
	 */
	public void executeWorkFlow() {
		setupWorkFlow();
		eventsLoad.execute();
	}
	
	// --------------------------------------------------------------------------------------------------
	// Main
	// --------------------------------------------------------------------------------------------------
	
	public static void main(String... args) {
		TwitterETL etl = new TwitterETL();
		etl.setupWorkFlow();
		etl.executeWorkFlow();
	}
	
}
