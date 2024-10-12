package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.*;

import java.sql.Date;

// line 45 "model.ump"
// line 172 "model.ump"
@Entity
@Table(name = "\"order\"")
public class Order
{

  //------------------------
  // ENUMERATIONS
  //------------------------


  public enum OrderStatus { Pending, Shipped, Delivered, Cancelled }

  //------------------------
  // MEMBER VARIABLES
  //------------------------
  @Id
  @GeneratedValue
  private int id;


  //Order Attributes
  private int number;
  private Date orderedDate;
  private Date shippedDate;
  private String shipTo;
  private double total;
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  //Order Associations
  @OneToOne
  private Payment paymentOfOrder;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(int aNumber, Date aOrderedDate, Date aShippedDate, String aShipTo, double aTotal, OrderStatus aStatus,Payment aPaymentOfOrder)
  {

    number = aNumber;
    orderedDate = aOrderedDate;
    shippedDate = aShippedDate;
    shipTo = aShipTo;
    total = aTotal;
    status = aStatus;
    if (!setPaymentOfOrder(aPaymentOfOrder))
    {
      throw new RuntimeException("Unable to create Order due to aPaymentOfOrder. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  public Order (){}
  //------------------------
  // INTERFACE
  //------------------------
  public Integer getId() {
    return id;
  }

  // Setter for id
  public void setId(Integer id) {
    this.id = id;
  }
  public boolean setNumber(int aNumber)
  {
    boolean wasSet = false;
    number = aNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrderedDate(Date aOrderedDate)
  {
    boolean wasSet = false;
    orderedDate = aOrderedDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setShippedDate(Date aShippedDate)
  {
    boolean wasSet = false;
    shippedDate = aShippedDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setShipTo(String aShipTo)
  {
    boolean wasSet = false;
    shipTo = aShipTo;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotal(double aTotal)
  {
    boolean wasSet = false;
    total = aTotal;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(OrderStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public int getNumber()
  {
    return number;
  }

  public Date getOrderedDate()
  {
    return orderedDate;
  }

  public Date getShippedDate()
  {
    return shippedDate;
  }

  public String getShipTo()
  {
    return shipTo;
  }

  public double getTotal()
  {
    return total;
  }

  public OrderStatus getStatus()
  {
    return status;
  }
  /* Code from template association_GetOne */
  public Payment getPaymentOfOrder()
  {
    return paymentOfOrder;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setPaymentOfOrder(Payment aNewPaymentOfOrder)
  {
    boolean wasSet = false;
    if (aNewPaymentOfOrder != null)
    {
      paymentOfOrder = aNewPaymentOfOrder;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    paymentOfOrder = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "," +
            "shipTo" + ":" + getShipTo()+ "," +
            "total" + ":" + getTotal()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orderedDate" + "=" + (getOrderedDate() != null ? !getOrderedDate().equals(this)  ? getOrderedDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "shippedDate" + "=" + (getShippedDate() != null ? !getShippedDate().equals(this)  ? getShippedDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "paymentOfOrder = "+(getPaymentOfOrder()!=null?Integer.toHexString(System.identityHashCode(getPaymentOfOrder())):"null");
  }
}