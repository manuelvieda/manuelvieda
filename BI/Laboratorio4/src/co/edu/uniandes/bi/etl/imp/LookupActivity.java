/**
 * 
 */
package co.edu.uniandes.bi.etl.imp;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import co.edu.uniandes.bi.db.TweetsDAO;
import co.edu.uniandes.bi.etl.Activity;
import co.edu.uniandes.bi.etl.Row;

/**
 * Representa una actividad de búsqueda de un conjunto de campos de una 
 * tabla a partir de un join dado
 * @author Sebastian
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class LookupActivity extends Activity {

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
	 * Nombre de los campos de la tabla de destino por
	 */
	private List<String> targetTableFieldNames;
	
	/**
	 * Nombre de los campos de la tabla que se desean obtener
	 */
	private List<String> fieldsToRetrieve;
	
	/**
	 * Contiene la correspondencia entre campos de entrada (atributo input)
	 * y los campos de la tabla
	 */
	private Map<String, String> fieldMappings;
	
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
	public LookupActivity() {
		super();
		persistence = TweetsDAO.getInstance();
	}
	
	// --------------------------------------------------------------------------------------------------
	// Métodos privados
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Devuelve una colección de valores de campos para buscar en la tabla de destino
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
	 * utilizando los valores establecidos en la llamada del método setJoinCriteria(List<String> leftFields, List<String> rightFields) 
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
	private void addFieldsToOutput(List rowFields) {
		input = output;
		Iterator it = rowFields.iterator();
		Iterator<String> ftrIt = fieldsToRetrieve.iterator();
		
		for(Row row : output) {
			row.setField(ftrIt.next(), it.next());
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
	 * Establece los campos en los que debe existir correspondencia 
	 * @param lefFields deben corresponder con nombres de campos de input
	 * @param rigthFields deben corresponder con columnas de la tabla de destino target table
	 */
	public void setJoinCriteria(List<String> leftFields, List<String> rightFields) {
		this.targetTableFieldNames = rightFields;
		fieldMappings.clear();
		
		Iterator<String> it = rightFields.iterator();
		
		for(String leftFieldName : leftFields) {
			fieldMappings.put(leftFieldName, it.next());
		}
	}
	
	/**
	 * Establece los campos de la tabla que se desean obtener
	 * @param tableFields
	 */
	public void setFieldsToRetrieve(List<String> tableFields) {
		this.fieldsToRetrieve = tableFields;
	}
	
	/* (non-Javadoc)
	 * @see co.edu.uniandes.bi.etl.Activity#execute()
	 */
	@Override
	public void execute() {
		// TODO implementar el método en TweetsDAO
		Collection<List> fieldValues = getFieldValues();
		try {
			List retrievedFieldValues = persistence.retrieveFields(targetTable, targetTableFieldNames, fieldValues, fieldsToRetrieve);
			addFieldsToOutput(retrievedFieldValues);
			executeNext();
		} catch (SQLException e) {
			e.printStackTrace();
			flagError = true;
			e.printStackTrace();
		} finally {
			flagCompleted = true;
		}

	}

}
