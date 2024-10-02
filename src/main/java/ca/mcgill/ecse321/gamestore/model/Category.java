package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 68 "model.ump"
// line 193 "model.ump"
public class Category
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Category Attributes
  private String name;
  private int numberItems;

  //Category Associations
  private List<Product> product;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Category(String aName, int aNumberItems)
  {
    name = aName;
    numberItems = aNumberItems;
    product = new ArrayList<Product>();
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

  public boolean setNumberItems(int aNumberItems)
  {
    boolean wasSet = false;
    numberItems = aNumberItems;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getNumberItems()
  {
    return numberItems;
  }
  /* Code from template association_GetMany */
  public Product getProduct(int index)
  {
    Product aProduct = product.get(index);
    return aProduct;
  }

  public List<Product> getProduct()
  {
    List<Product> newProduct = Collections.unmodifiableList(product);
    return newProduct;
  }

  public int numberOfProduct()
  {
    int number = product.size();
    return number;
  }

  public boolean hasProduct()
  {
    boolean has = product.size() > 0;
    return has;
  }

  public int indexOfProduct(Product aProduct)
  {
    int index = product.indexOf(aProduct);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfProduct()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addProduct(Product aProduct)
  {
    boolean wasAdded = false;
    if (product.contains(aProduct)) { return false; }
    product.add(aProduct);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProduct(Product aProduct)
  {
    boolean wasRemoved = false;
    if (product.contains(aProduct))
    {
      product.remove(aProduct);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addProductAt(Product aProduct, int index)
  {  
    boolean wasAdded = false;
    if(addProduct(aProduct))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProduct()) { index = numberOfProduct() - 1; }
      product.remove(aProduct);
      product.add(index, aProduct);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveProductAt(Product aProduct, int index)
  {
    boolean wasAdded = false;
    if(product.contains(aProduct))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProduct()) { index = numberOfProduct() - 1; }
      product.remove(aProduct);
      product.add(index, aProduct);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addProductAt(aProduct, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    product.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "numberItems" + ":" + getNumberItems()+ "]";
  }
}