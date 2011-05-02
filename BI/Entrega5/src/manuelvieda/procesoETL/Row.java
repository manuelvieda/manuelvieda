package manuelvieda.procesoETL;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Representa una fila dentro de un dataset
 * @author Sebastián
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Row  implements Cloneable{
	
	// --------------------------------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------------------------------
	/**
	 * Campos de la fila
	 */
	
	private Map fields;
	
	// --------------------------------------------------------------------------------------------------
	// Constructores
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Constructor por defecto
	 */
	public Row() {
		fields = new Hashtable();
	}
	
	/**
	 * Crea una fila con los nombres de campos y valores dados
	 * @param fieldNames
	 * @param fieldValues
	 */
	public Row(Set<String> fieldNames, Set fieldValues) {
		this();
		Iterator it = fieldValues.iterator();
		System.out.println("Tamanos: "+fieldNames.size()+" - "+fieldValues.size());
		for (String fieldName : fieldNames) {
			Object fieldValue = it.next();
			System.out.println(" "+fieldName+" - "+fieldValue.toString());
			fields.put(fieldName, fieldValue);
		}
	}
	
	/**
	 * Crea una fila con la información de los campos dada
	 * @param fields
	 */
	protected Row(Map fields) {
		this.fields = new Hashtable(fields);
	}
	
	// --------------------------------------------------------------------------------------------------
	// Métodos privados
	// --------------------------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------------------------
	// Métodos públicos
	// --------------------------------------------------------------------------------------------------
	
	/**
	 * Agrega o reemplaza el campo en la fila
	 * @param fieldName
	 * @param fieldValue
	 */
	
	public void setField(String fieldName, Object fieldValue) {
		fields.put(fieldName, fieldValue);
	}
	
	/**
	 * Elimina el campo de la fila
	 * @param fieldName
	 */
	public void removeField(String fieldName) {
		fields.remove(fieldName);
	}
	
	/**
	 * Obtiene el campo dado
	 * @param fieldName
	 * @return fieldValue
	 */
	public Object getField(String fieldName) {
		return  fields.get(fieldName);
	}
	
	/**
	 * Obtiene los nombres de los campos de la fila
	 * @return fieldNames
	 */
	public Set<String> getFieldNames() {
		return fields.keySet();
	}
	
	/**
	 * Obtiene los valores de los campos de la fila
	 * @return
	 */
	public Collection getFieldValues() {
		return fields.values();
	}
	
	/**
	 * Clona el objeto
	 */
	public Row clone() {
		return new Row(fields);
	}
}
