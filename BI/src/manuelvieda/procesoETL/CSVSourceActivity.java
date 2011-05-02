package manuelvieda.procesoETL;

import java.util.List;


/**
 * Carga los datos de un archivo delimitados por comas (CSV) a la base de datos
 * @author Manuel Vieda
 */

public class CSVSourceActivity extends Activity {
	
	// -------------------------------------------------------------------
	// ATRIBUTOS
	// -------------------------------------------------------------------
	
	/**
	 * Ruta del archivo CSV que se va a cargar
	 */
	private String filePath;
	
	// -------------------------------------------------------------------
	// CONSTRUCTOR
	// -------------------------------------------------------------------
	/**
	 * Constructor por defecto
	 */
	public CSVSourceActivity() {
		super();
	}
	
	// -------------------------------------------------------------------
	// METODOS
	// -------------------------------------------------------------------
	
	/**
	 * Carga un archivo CSV especificado en los stributos y convierte el contenido
	 * en datos dentro de la base de datos. Cada fila se convierte en objeto Row
	 * @PRE Se ha llamado al método setCSVFilePath(String)
	 * @return rows
	 */
	private List<Row> loadCSVFile() {
		log.info("Cargando archivo:"+filePath+" ...");
		List<Row> rows = null;
		return rows;
	}
	
	

}
