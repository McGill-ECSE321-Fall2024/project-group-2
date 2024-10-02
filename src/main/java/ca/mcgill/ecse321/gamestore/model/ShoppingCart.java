package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;
import java.util.*;

// line 59 "model.ump"
// line 183 "model.ump"
public class ShoppingCart
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ShoppingCart Attributes
  private Date creationDate;

  //ShoppingCart Associations
  private List<LineItem> lineItemsInCart;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ShoppingCart(Date aCreationDate)
  {
    creationDate = aCreationDate;
    lineItemsInCart = new ArrayList<LineItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCreationDate(Date aCreationDate)
  {
    boolean wasSet = false;
    creationDate = aCreationDate;
    wasSet = true;
    return wasSet;
  }

  public Date getCreationDate()
  {
    return creationDate;
  }
  /* Code from template association_GetMany */
  public LineItem getLineItemsInCart(int index)
  {
    LineItem aLineItemsInCart = lineItemsInCart.get(index);
    return aLineItemsInCart;
  }

  public List<LineItem> getLineItemsInCart()
  {
    List<LineItem> newLineItemsInCart = Collections.unmodifiableList(lineItemsInCart);
    return newLineItemsInCart;
  }

  public int numberOfLineItemsInCart()
  {
    int number = lineItemsInCart.size();
    return number;
  }

  public boolean hasLineItemsInCart()
  {
    boolean has = lineItemsInCart.size() > 0;
    return has;
  }

  public int indexOfLineItemsInCart(LineItem aLineItemsInCart)
  {
    int index = lineItemsInCart.indexOf(aLineItemsInCart);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLineItemsInCart()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addLineItemsInCart(LineItem aLineItemsInCart)
  {
    boolean wasAdded = false;
    if (lineItemsInCart.contains(aLineItemsInCart)) { return false; }
    lineItemsInCart.add(aLineItemsInCart);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLineItemsInCart(LineItem aLineItemsInCart)
  {
    boolean wasRemoved = false;
    if (lineItemsInCart.contains(aLineItemsInCart))
    {
      lineItemsInCart.remove(aLineItemsInCart);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLineItemsInCartAt(LineItem aLineItemsInCart, int index)
  {  
    boolean wasAdded = false;
    if(addLineItemsInCart(aLineItemsInCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLineItemsInCart()) { index = numberOfLineItemsInCart() - 1; }
      lineItemsInCart.remove(aLineItemsInCart);
      lineItemsInCart.add(index, aLineItemsInCart);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLineItemsInCartAt(LineItem aLineItemsInCart, int index)
  {
    boolean wasAdded = false;
    if(lineItemsInCart.contains(aLineItemsInCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLineItemsInCart()) { index = numberOfLineItemsInCart() - 1; }
      lineItemsInCart.remove(aLineItemsInCart);
      lineItemsInCart.add(index, aLineItemsInCart);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLineItemsInCartAt(aLineItemsInCart, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    lineItemsInCart.clear();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "creationDate" + "=" + (getCreationDate() != null ? !getCreationDate().equals(this)  ? getCreationDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}