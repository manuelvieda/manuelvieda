package co.edu.uniandes.bi.etl.imp;

import co.edu.uniandes.bi.etl.Activity;
import co.edu.uniandes.bi.etl.Row;
import co.edu.uniandes.bi.nlp.AnalysisResult;

/**
 * Representa una actividad de columna derivada
 * @author Sebastian
 *
 */
public class DerivedColumnActivity extends Activity{

	//--------------------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------------------
	
	/**
	 * Nombre de la columna que se va a utilizar para calcular la polaridad
	 */
	private String sourceColumnField;
	
	//--------------------------------------------------------------------------------------------------
	// Constructores
	//--------------------------------------------------------------------------------------------------
	
	/**
	 * Constructor por defecto
	 */
	public DerivedColumnActivity() {
		super();
	}
	
	//--------------------------------------------------------------------------------------------------
	// Métodos privados
	//--------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------
	// Métodos públicos
	//--------------------------------------------------------------------------------------------------
	
	
	/**
	 * Establece la columna para extraer la polaridad
	 */
	public void setSourceColumnField(String sourceColumnField) {
		this.sourceColumnField = sourceColumnField;
	}
	
	/**
	 * Calcula la polaridad de las reacciones encontradas
	 */
	@Override
	public void execute() {
		boolean positive = false;
		boolean negative = false;
		boolean neutral = false;
		int valence = 0;
		AnalysisResult analysis = null;
		try {
			for (Row row : input) {
				//TODO utilizar EmotionsAnalyzer para encontrar la polaridad de la columna sourceColumnField
				 row.setField("positive", positive);
				 row.setField("negative", negative);
				 row.setField("neutral", neutral);
			}
		} catch (Exception e) {
			flagError = true;
			e.printStackTrace();
		} finally {
			flagCompleted = true;
			output = input;
		}
	}
	
}
