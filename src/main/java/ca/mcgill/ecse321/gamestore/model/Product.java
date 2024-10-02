package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 63 "model.ump"
// line 188 "model.ump"
public class Product
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Product Attributes
  private String name;
  private String description;

  //Product Associations
  private List<LineItem> lineItemOfProduct;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Product(String aName, String aDescription)
  {
    name = aName;
    description = aDescription;
    lineItemOfProduct = new ArrayList<LineItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  /* Code from template association_GetMany */
  public LineItem getLineItemOfProduct(int index)
  {
    LineItem aLineItemOfProduct = lineItemOfProduct.get(index);
    return aLineItemOfProduct;
  }

  public List<LineItem> getLineItemOfProduct()
  {
    List<LineItem> newLineItemOfProduct = Collections.unmodifiableList(lineItemOfProduct);
    return newLineItemOfProduct;
  }

  public int numberOfLineItemOfProduct()
  {
    int number = lineItemOfProduct.size();
    return number;
  }

  public boolean hasLineItemOfProduct()
  {
    boolean has = lineItemOfProduct.size() > 0;
    return has;
  }

  public int indexOfLineItemOfProduct(LineItem aLineItemOfProduct)
  {
    int index = lineItemOfProduct.indexOf(aLineItemOfProduct);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLineItemOfProduct()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addLineItemOfProduct(LineItem aLineItemOfProduct)
  {
    boolean wasAdded = false;
    if (lineItemOfProduct.contains(aLineItemOfProduct)) { return false; }
    lineItemOfProduct.add(aLineItemOfProduct);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLineItemOfProduct(LineItem aLineItemOfProduct)
  {
    boolean wasRemoved = false;
    if (lineItemOfProduct.contains(aLineItemOfProduct))
    {
      lineItemOfProduct.remove(aLineItemOfProduct);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLineItemOfProductAt(LineItem aLineItemOfProduct, int index)
  {  
    boolean wasAdded = false;
    if(addLineItemOfProduct(aLineItemOfProduct))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLineItemOfProduct()) { index = numberOfLineItemOfProduct() - 1; }
      lineItemOfProduct.remove(aLineItemOfProduct);
      lineItemOfProduct.add(index, aLineItemOfProduct);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLineItemOfProductAt(LineItem aLineItemOfProduct, int index)
  {
    boolean wasAdded = false;
    if(lineItemOfProduct.contains(aLineItemOfProduct))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLineItemOfProduct()) { index = numberOfLineItemOfProduct() - 1; }
      lineItemOfProduct.remove(aLineItemOfProduct);
      lineItemOfProduct.add(index, aLineItemOfProduct);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLineItemOfProductAt(aLineItemOfProduct, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    lineItemOfProduct.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "]";
  }
}