package co.edu.uniandes.bi.etl.imp;

import java.util.List;

import co.edu.uniandes.bi.etl.Activity;
import co.edu.uniandes.bi.etl.Row;
/**
 * Carga un archivo CSV a partir de un 
 * @author Sebastian
 *
 */
public class CSVSourceActivity extends Activity {
	
	// --------------------------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Ruta al archivo CSV que
	 * va a cargar la actividad
	 */
	private String filePath;
	
	// --------------------------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * Constructor por defecto
	 */
	public CSVSourceActivity() {
		super(); // importante, esta llamada configura el log
	}
	
	// --------------------------------------------------------------------------------------------------
	// M�todos privados
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Carga el archivo CSV y convierte sus filas en objetos Row
	 * <b>pre:</b> se ha llamado el m�todo setCSVFilePath(String)
	 * @return rows
	 */
	private List<Row> loadCSVFile() {
		log.info("Cargando archivo:"+filePath+" ...");
		List<Row> rows = null;
		//TODO cargar el archivo y poner los campos en el objeto output
		
		//puede utilizar OpenCSV: http://opencsv.sourceforge.net/
		
		return rows;
	}
	// --------------------------------------------------------------------------------------------------
	// M�todos p�blicos
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Establece la ubicaci�n del archivo que va a cargar la actividad
	 * @param filePath archivo CSV
	 */
	public void setCSVFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Carga los campos del archivo CSV que se indic�
	 * <b>pre:</b> se ha llamado el m�todo setCSVFilePath(String)
	 * @see CSVSourceActivity#setCSVFilePath(String)
	 */
	@Override
	public void execute() {
		this.output = loadCSVFile();
		flagCompleted = true;
		executeNext();
	}
	
	// --------------------------------------------------------------------------------------------------
	// M�todos est�ticos
	// --------------------------------------------------------------------------------------------------

}
