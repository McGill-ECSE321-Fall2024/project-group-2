package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.*;

// line 54 "model.ump"
// line 177 "model.ump"
@Entity
public class LineItem
{
  @Id
  @GeneratedValue
  private int lineItemId;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LineItem Attributes
  private int quantity;
  private double price;

  //LineItem Associations
  @ManyToOne
  private Order order;
  @ManyToOne
  private ShoppingCart cart;
  @OneToOne
  private WishList wishlist;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LineItem(int aQuantity, double aPrice, Order aOrder, ShoppingCart aCart)
  {
    quantity = aQuantity;
    price = aPrice;
    if (!setOrder(aOrder))
    {
      throw new RuntimeException("Unable to create LineItem due to aOrder. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCart(aCart))
    {
      throw new RuntimeException("Unable to create LineItem due to aCart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }

  }
  public LineItem(){}

  //------------------------
  // INTERFACE
  //------------------------
  public Integer getId() {
    return lineItemId;
  }

  // Setter for id
  public void setId(Integer id) {
    this.lineItemId = id;
  }
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
  /* Code from template association_GetOne */
  public Order getOrder()
  {
    return order;
  }
  /* Code from template association_GetOne */
  public ShoppingCart getCart()
  {
    return cart;
  }
  /* Code from template association_GetOne */
  public WishList getWishlist()
  {
    return wishlist;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setOrder(Order aNewOrder)
  {
    boolean wasSet = false;
    if (aNewOrder != null)
    {
      order = aNewOrder;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCart(ShoppingCart aNewCart)
  {
    boolean wasSet = false;
    if (aNewCart != null)
    {
      cart = aNewCart;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setWishlist(WishList aNewWishlist)
  {
    boolean wasSet = false;
    if (aNewWishlist != null)
    {
      wishlist = aNewWishlist;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    order = null;
    cart = null;
    wishlist = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "," +
            "price" + ":" + getPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "wishlist = "+(getWishlist()!=null?Integer.toHexString(System.identityHashCode(getWishlist())):"null");
  }
}