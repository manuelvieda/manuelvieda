package manuelvieda.procesoETL;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import manuelvieda.data.TweetsDAO;

/**
 * Actividad que carga una lista de filas en una tabla 
 * a través de JDBC
 * @author Sebastian
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JDBCDestinationActivity extends Activity {
	
	// --------------------------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Tabla de destino
	 */
	private String targetTable;
	
	/**
	 * Contiene la correspondencia entre campos de entrada (atributo input)
	 * y los campos de la tabla
	 */
	private Map<String, String> fieldMappings;
	
	/**
	 * Nombre de los campos de la tabla de destino
	 */
	private List<String> targetFieldNames;
	
	/**
	 * Instancia de la persistencia
	 */
	private TweetsDAO persistence;
	
	// --------------------------------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Constructor por defecto
	 */
	public JDBCDestinationActivity() {
		super();
		fieldMappings = new Hashtable<String, String>();
		persistence = TweetsDAO.getInstance();
	}
	
	// --------------------------------------------------------------------------------------------------
	// Métodos privados
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Devuelve una colección de valores de campos para insertar en la tabla de destino
	 * @return fieldValues
	 */
	private Collection<List> getFieldValues() {
		Collection rows = new LinkedList();
		Collection currentRow = null;
		
		Iterator<Row> it = input.iterator();
		
		while (it.hasNext()){
			currentRow = getListFromRow(it.next());
			rows.add(currentRow);
		}
		
		return rows;
	}
	
	/**
	 * Obtiene una lista de valores de campos de la fila
	 * utilizando las correspondencias entre campos que se habían establecido
	 * @param currentRow
	 * @return rowFieldsValues
	 */
	private List getListFromRow(Row currentRow) {
		List valuesList = null;
		
		//TODO utilizar la tabla fieldMappings para devolver los valores
		// de los campos de la fila en el orden dado por targetFieldNames
		
		return valuesList;
	}
	
	/**
	 * Agrega la llave sustituta asignada a cada fila que se insertó
	 * @param rowKeys
	 */
	private void addSurrogateKeyToOutput(List<Long> rowKeys) {
		input = output;
		Iterator<Long> it = rowKeys.iterator();
		for(Row row : output) {
			row.setField(targetTable+"_key", it.next());
		}
	}
	
	// --------------------------------------------------------------------------------------------------
	// Métodos públicos
	// --------------------------------------------------------------------------------------------------

	/**
	 * Establece la tabla de destino de la actividad
	 * @param targetTable tabla en la que se van a guardar las filas
	 */
	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}
	
	/**
	 * Establece la correspondencia entre los campos del flujo del ETL 
	 * y los nombres de los campos de la tabla de destino
	 * @param inputFieldNames deben corresponder a nombres de las filas que se establecieron con el método setInput
	 * @param targetFieldNames deben corresponder a nombres de los campos de la tabla de destino targetTable
	 */
	public void setMappings(List<String> inputFieldNames, List<String> targetFieldNames) {
		fieldMappings.clear();
		this.targetFieldNames = targetFieldNames;
		Iterator<String> it = targetFieldNames.iterator();
		
		for(String inputFieldName : inputFieldNames) {
			fieldMappings.put(inputFieldName, it.next());
		}
	}
	
	/**
	 * Guarda las filas de la entrada teniendo en cuenta la correspondencia entre campos
	 *  de la tabla destino y los campos de las filas
	 *  <b>pre:</b> se ha llamado el método setTargetTable(String)
	 *  <b>pre:</b> se ha llamado el método setMappings(Set<String>,Set<String>)
	 *  @see JDBCDestinationActivity#setTargetTable(String)
	 *  @see JDBCDestinationActivity#setMappings(Set, Set)
	 */
	@Override
	public void execute() {
		Collection<List> fieldValues = getFieldValues();
		try {
			//TODO implementar el método en TweetsDAO
			List<Long> rowKeys = persistence.storeRows(targetTable, targetFieldNames, fieldValues);
			addSurrogateKeyToOutput(rowKeys);
			executeNext();
		} catch (SQLException e) {
			e.printStackTrace();
			flagError = true;
		} finally {
			flagCompleted = true;
		}
	}

}