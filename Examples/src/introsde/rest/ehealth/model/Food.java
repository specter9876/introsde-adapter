package introsde.rest.ehealth.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceException;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
public class Food {

	private long idFood;
	private String description;
	private String type;
	private double calories;
    private String name;
    

	public Food() {
	}

	public long getIdFood() {
		return idFood;
	}

	public void setIdFood(long idFood) {
		this.idFood = idFood;
	}
    
    public String getName() {
		return name;
	}
    
	public void setName(String name) {
		this.name = name;
    }
    
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {		this.calories = calories;
	}

	
	@Override
	public String toString() {
		return "Food (" + getIdFood() +" name= " + getName() + ", Description=" + getDescription() + ", type=" + getType() + ", calories=" + getCalories() + ")";
	}
}
