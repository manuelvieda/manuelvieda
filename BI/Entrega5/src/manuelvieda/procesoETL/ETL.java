/**
 * 
 */
package manuelvieda.procesoETL;

import java.util.logging.Logger;

/**
 * Proceso ETL de Social Media para General Motors 
 * Entrega 5 Proyecto clase Inteligencia de Negocios
 * @author Manuel Vieda
 */
public class ETL {
	
	

	
	// ----------------------------------------------------------------
	// ATRIBUTOS
	// ----------------------------------------------------------------
	
	/**
	 * Log de la instancia
	 */
	private static Logger log;
	
	/**
	 * Actividad Fuente CSV
	 */
	private CSVSourceActivity campanasLoad;
	
	
	
	// ----------------------------------------------------------------
	// CONSTRUCTOR
	// ----------------------------------------------------------------
	public ETL(){
		log = Logger.getLogger(this.getClass().getCanonicalName());
		
		campanasLoad = new CSVSourceActivity();
	}
	
	
	// ----------------------------------------------------------------
	// METODOS
	// ----------------------------------------------------------------
	
	private void setupCSVSourceActivity() {
		campanasLoad.setCSVFilePath("./data/campanas.csv");
		
	}
	
	
	/**
	 * Este metodo se encarga de crear el flujo de trabajo para el proceso de ETL
	 */
	private void setupWorkFlow(){
		setupCSVSourceActivity();
	}
	
	/**
	 * Configura el flujo de trabajo y lo ejecuta
	 */
	public void executeWorkFlow() {
		setupWorkFlow();
		campanasLoad.execute();
	}
	
	
	// ----------------------------------------------------------------
	// MAIN DE LA APLICACION
	// ----------------------------------------------------------------
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ETL etl = new ETL();
		etl.setupWorkFlow();
		etl.executeWorkFlow();
	}

}
