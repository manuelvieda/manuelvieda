package co.edu.uniandes.bi.vo;

/**
 * Canal de social media
 * @author Sebastian
 *
 */
public class Channel {

	//--------------------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------------------
	
	private long key;
	
	private String name;
	
	private String description;

	//--------------------------------------------------------------------------------------------------
	// Getters y Setters
	//--------------------------------------------------------------------------------------------------
	
	/**
	 * @return the key
	 */
	public long getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(long key) {
		this.key = key;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
