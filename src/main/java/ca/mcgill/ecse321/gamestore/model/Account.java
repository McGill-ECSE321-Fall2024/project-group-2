package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.*;

import java.sql.Date;

// line 32 "model.ump"
// line 162 "model.ump"
@Entity
public class Account
{ @Id

  private String email;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  private String billingAddress;
  private Date openDate;
  private Date closedDate;
  private boolean isClosed;

  //Account Associations

 @OneToOne
  private Customer accountOwner;
  @OneToOne
  private Payment payment;
  @OneToOne
  private ShoppingCart cart;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Account(String emailad,String aBillingAddress, Date aOpenDate, Date aClosedDate, boolean aIsClosed, Customer aAccountOwner, Payment aPayment, ShoppingCart aCart)
  {
      email=emailad;
    billingAddress = aBillingAddress;
    openDate = aOpenDate;
    closedDate = aClosedDate;
    isClosed = aIsClosed;
    if (!setAccountOwner(aAccountOwner))
    {
      throw new RuntimeException("Unable to create Account due to aAccountOwner. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setPayment(aPayment))
    {
      throw new RuntimeException("Unable to create Account due to aPayment. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCart(aCart))
    {
      throw new RuntimeException("Unable to create Account due to aCart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  public Account(){}
  //------------------------
  // INTERFACE
  //------------------------
  public void setEmail(String email) {
    this.email = email;
  }

  // Getter for email
  public String getEmail() {
    return email;
  }

  // Setter for id

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
  /* Code from template association_GetOne */
  public Payment getPayment()
  {
    return payment;
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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setPayment(Payment aNewPayment)
  {
    boolean wasSet = false;
    if (aNewPayment != null)
    {
      payment = aNewPayment;
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

  public void delete()
  {
    accountOwner = null;
    payment = null;
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
            "  " + "payment = "+(getPayment()!=null?Integer.toHexString(System.identityHashCode(getPayment())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null");
  }
}