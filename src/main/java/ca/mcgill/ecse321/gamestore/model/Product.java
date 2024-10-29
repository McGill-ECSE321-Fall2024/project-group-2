package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.*;

// line 63 "model.ump"
// line 187 "model.ump"
@Entity
public class Product
{
  @Id
  @GeneratedValue
  private int productId;
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Product Attributes
  private String name;
  private String description;

  //Product Associations
  @OneToOne
  private LineItem lineItemOfProduct;
  @ManyToOne
  private Category category;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Product(String aName, String aDescription, LineItem aLineItemOfProduct, Category aCategory)
  {
    name = aName;
    description = aDescription;
    if (!setLineItemOfProduct(aLineItemOfProduct))
    {
      throw new RuntimeException("Unable to create Product due to aLineItemOfProduct. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCategory(aCategory))
    {
      throw new RuntimeException("Unable to create Product due to aCategory. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  public Product () {}
  //------------------------
  // INTERFACE
  //------------------------
  public Integer getId() {
    return productId;
  }

  // Setter for id
  public void setId(Integer id) {
    this.productId = id;
  }
  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetOne */
  public LineItem getLineItemOfProduct()
  {
    return lineItemOfProduct;
  }
  /* Code from template association_GetOne */
  public Category getCategory()
  {
    return category;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setLineItemOfProduct(LineItem aNewLineItemOfProduct)
  {
    boolean wasSet = false;
    if (aNewLineItemOfProduct != null)
    {
      lineItemOfProduct = aNewLineItemOfProduct;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCategory(Category aNewCategory)
  {
    boolean wasSet = false;
    if (aNewCategory != null)
    {
      category = aNewCategory;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    lineItemOfProduct = null;
    category = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "lineItemOfProduct = "+(getLineItemOfProduct()!=null?Integer.toHexString(System.identityHashCode(getLineItemOfProduct())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "category = "+(getCategory()!=null?Integer.toHexString(System.identityHashCode(getCategory())):"null");
  }
}