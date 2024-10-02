package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/



// line 54 "model.ump"
// line 178 "model.ump"
public class LineItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LineItem Attributes
  private int quantity;
  private double price;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LineItem(int aQuantity, double aPrice)
  {
    quantity = aQuantity;
    price = aPrice;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public double getPrice()
  {
    return price;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "," +
            "price" + ":" + getPrice()+ "]";
  }
}