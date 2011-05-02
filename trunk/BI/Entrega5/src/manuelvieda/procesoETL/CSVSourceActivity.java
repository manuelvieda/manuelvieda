package manuelvieda.procesoETL;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;


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
		
		log.info("Cargando archivo CSV:"+filePath);
		
		// Creamos una lista con todos las filas del archivo
		List<Row> rows = new ArrayList<Row>();
				
		try {
			CSVReader reader = new CSVReader(new FileReader(filePath));
			String [] nextLine;
			
			nextLine = reader.readNext();
			
			// Creamos el set con el nombre de los campos
			Set<String> fieldNames = new LinkedHashSet<String>();
			if(nextLine!=null){
				for(int i=0; i<nextLine.length; i++){
					fieldNames.add(nextLine[i]);
					System.out.print(nextLine[i]+" - ");
				}				
			}
			
			System.out.println("");
			// Las siguientes lineas corresponden a los valores
			while ((nextLine = reader.readNext()) != null) {
				Set<String> fieldValues = new LinkedHashSet<String>();
				for(int i=0; i<nextLine.length; i++){
						fieldValues.add(nextLine[i]);
				}
				System.out.println("  ");
				Row row = new Row(fieldNames, fieldValues);
				rows.add(row);
			}
	
			
		} catch (FileNotFoundException e) {
			log.severe(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.severe(e.getMessage());
			e.printStackTrace();
		}
		
		return rows;
	}
	
	
	
	
	/**
	 * Establece la ubicación del archivo que va a cargar la actividad
	 * @param filePath archivo CSV
	 */
	public void setCSVFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Carga los campos del archivo CSV que se indicó
	 * <b>pre:</b> se ha llamado el método setCSVFilePath(String)
	 * @see CSVSourceActivity#setCSVFilePath(String)
	 */
	@Override
	public void execute() {
		this.output = loadCSVFile();
		flagCompleted = true;
		executeNext();
	}
	
	

}
