package manuelvieda.procesoETL;

import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Representa una actividad del flujo de ETL
 * @author Sebastian
 *
 */
public abstract class Activity {
	
	// --------------------------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Path to the log file
	 */
	private static final String LOG_FILE_PATH = "./data/log/";
	
	// --------------------------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Filas de entrada de la actividad
	 */
	protected List<Row> input;
	
	/**
	 * Filas de salid de la actividad
	 */
	protected List<Row> output;
	
	/**
	 * La actividad que debe ejecutarse 
	 * al terminar la ejecución de la actividad actual
	 */
	protected Activity nextActivity;
	
	/**
	 * Log de la instancia
	 */
	protected Logger log;
	
	/**
	 * Indica si la tarea ha terminado
	 */
	protected boolean flagCompleted;
	
	/**
	 * Indica si la tarea terminó con error
	 */
	protected boolean flagError;
	
	// --------------------------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------------------------
	/**
	 * Constructor por defecto
	 */
	public Activity() {
		setupLog();
	}
	// --------------------------------------------------------------------------------------------------
	// Métodos privados
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Configura el log de la clase
	 */
	private void setupLog() {
		try {
			log = Logger.getLogger(this.getClass().getCanonicalName());
			FileHandler fh = new FileHandler(LOG_FILE_PATH+this.getClass().getCanonicalName()+".log",
					true);
			fh.setFormatter(new SimpleFormatter());
			log.addHandler(fh);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// --------------------------------------------------------------------------------------------------
	// Métodos protegidos
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Ejecuta la siguiente actividad 
	 */
	protected void executeNext() {
		if (nextActivity != null) {
			log.info("Ejecutando la siguiente actividad...");
			nextActivity.setInput(output);
			nextActivity.execute();	
		}
	}
	
	// --------------------------------------------------------------------------------------------------
	// Métodos públicos
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Establece las filas de entrada de la actividad
	 * @param input
	 */
	public void setInput(List<Row> input) {
		this.input = input;
	}
	
	/**
	 * Obtiene las filas de la salida de la actividad
	 * @return
	 */
	public List<Row> getOutput() {
		return output;
	}
	
	/**
	 * Establece la actividad que debe ejecutarse al finalizar 
	 * la ejecución de esta actividad
	 * @param nextActivity
	 */
	public void setNextActivity(Activity nextActivity) {
		this.nextActivity = nextActivity;
	}
	
	/**
	 * ejecuta la tarea actual
	 * <b>pre:</b> se ha llamado el método setInput(List)
	 * @see Activity#setInput(List)
	 */
	public abstract void execute();
	
	
}
