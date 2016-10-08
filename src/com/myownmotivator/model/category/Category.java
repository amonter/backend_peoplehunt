package com.myownmotivator.model.category;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Category")
public class Category implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "CATEGORY_ID")
	private long id;
	
	@Column(name = "categoryName")
	private String categoryName;
	
	@Column(name = "categoryDescription")
	private String categoryDescription;

	public Category(){}
	
	public Category(long categoryId) {
		id = categoryId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "Category ( "
	        + super.toString() + TAB
	        + "id = " + this.id + TAB
	        + "categoryName = " + this.categoryName + TAB
	        + "categoryDescription = " + this.categoryDescription + TAB
	        + " )";
	
	    return retValue;
	}

}
