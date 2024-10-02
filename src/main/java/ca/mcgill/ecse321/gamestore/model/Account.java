package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;
import java.util.*;

// line 32 "model.ump"
// line 163 "model.ump"
public class Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  private String billingAddress;
  private Date openDate;
  private Date closedDate;
  private boolean isClosed;

  //Account Associations
  private Customer accountOwner;
  private List<Payment> payment;
  private ShoppingCart cart;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Account(String aBillingAddress, Date aOpenDate, Date aClosedDate, boolean aIsClosed, Customer aAccountOwner, ShoppingCart aCart)
  {
    billingAddress = aBillingAddress;
    openDate = aOpenDate;
    closedDate = aClosedDate;
    isClosed = aIsClosed;
    if (!setAccountOwner(aAccountOwner))
    {
      throw new RuntimeException("Unable to create Account due to aAccountOwner. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    payment = new ArrayList<Payment>();
    if (!setCart(aCart))
    {
      throw new RuntimeException("Unable to create Account due to aCart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBillingAddress(String aBillingAddress)
  {
    boolean wasSet = false;
    billingAddress = aBillingAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setOpenDate(Date aOpenDate)
  {
    boolean wasSet = false;
    openDate = aOpenDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosedDate(Date aClosedDate)
  {
    boolean wasSet = false;
    closedDate = aClosedDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsClosed(boolean aIsClosed)
  {
    boolean wasSet = false;
    isClosed = aIsClosed;
    wasSet = true;
    return wasSet;
  }

  public String getBillingAddress()
  {
    return billingAddress;
  }

  public Date getOpenDate()
  {
    return openDate;
  }

  public Date getClosedDate()
  {
    return closedDate;
  }

  public boolean getIsClosed()
  {
    return isClosed;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsClosed()
  {
    return isClosed;
  }
  /* Code from template association_GetOne */
  public Customer getAccountOwner()
  {
    return accountOwner;
  }
  /* Code from template association_GetMany */
  public Payment getPayment(int index)
  {
    Payment aPayment = payment.get(index);
    return aPayment;
  }

  public List<Payment> getPayment()
  {
    List<Payment> newPayment = Collections.unmodifiableList(payment);
    return newPayment;
  }

  public int numberOfPayment()
  {
    int number = payment.size();
    return number;
  }

  public boolean hasPayment()
  {
    boolean has = payment.size() > 0;
    return has;
  }

  public int indexOfPayment(Payment aPayment)
  {
    int index = payment.indexOf(aPayment);
    return index;
  }
  /* Code from template association_GetOne */
  public ShoppingCart getCart()
  {
    return cart;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setAccountOwner(Customer aNewAccountOwner)
  {
    boolean wasSet = false;
    if (aNewAccountOwner != null)
    {
      accountOwner = aNewAccountOwner;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPayment()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addPayment(Payment aPayment)
  {
    boolean wasAdded = false;
    if (payment.contains(aPayment)) { return false; }
    payment.add(aPayment);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePayment(Payment aPayment)
  {
    boolean wasRemoved = false;
    if (payment.contains(aPayment))
    {
      payment.remove(aPayment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPaymentAt(Payment aPayment, int index)
  {  
    boolean wasAdded = false;
    if(addPayment(aPayment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPayment()) { index = numberOfPayment() - 1; }
      payment.remove(aPayment);
      payment.add(index, aPayment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePaymentAt(Payment aPayment, int index)
  {
    boolean wasAdded = false;
    if(payment.contains(aPayment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPayment()) { index = numberOfPayment() - 1; }
      payment.remove(aPayment);
      payment.add(index, aPayment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPaymentAt(aPayment, index);
    }
    return wasAdded;
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

  public void delete()
  {
    accountOwner = null;
    payment.clear();
    cart = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "billingAddress" + ":" + getBillingAddress()+ "," +
            "isClosed" + ":" + getIsClosed()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "openDate" + "=" + (getOpenDate() != null ? !getOpenDate().equals(this)  ? getOpenDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closedDate" + "=" + (getClosedDate() != null ? !getClosedDate().equals(this)  ? getClosedDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "accountOwner = "+(getAccountOwner()!=null?Integer.toHexString(System.identityHashCode(getAccountOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null");
  }
}