package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;
import java.util.*;

// line 45 "model.ump"
// line 173 "model.ump"
public class Order
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum OrderStatus { Pending, Shipped, Delivered, Cancelled }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private int number;
  private Date orderedDate;
  private Date shippedDate;
  private String shipTo;
  private double total;
  private OrderStatus status;

  //Order Associations
  private Payment paymentOfOrder;
  private List<LineItem> lineItemsOfOrder;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(int aNumber, Date aOrderedDate, Date aShippedDate, String aShipTo, double aTotal, OrderStatus aStatus, Payment aPaymentOfOrder)
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
    lineItemsOfOrder = new ArrayList<LineItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  /* Code from template association_GetMany */
  public LineItem getLineItemsOfOrder(int index)
  {
    LineItem aLineItemsOfOrder = lineItemsOfOrder.get(index);
    return aLineItemsOfOrder;
  }

  public List<LineItem> getLineItemsOfOrder()
  {
    List<LineItem> newLineItemsOfOrder = Collections.unmodifiableList(lineItemsOfOrder);
    return newLineItemsOfOrder;
  }

  public int numberOfLineItemsOfOrder()
  {
    int number = lineItemsOfOrder.size();
    return number;
  }

  public boolean hasLineItemsOfOrder()
  {
    boolean has = lineItemsOfOrder.size() > 0;
    return has;
  }

  public int indexOfLineItemsOfOrder(LineItem aLineItemsOfOrder)
  {
    int index = lineItemsOfOrder.indexOf(aLineItemsOfOrder);
    return index;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLineItemsOfOrder()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addLineItemsOfOrder(LineItem aLineItemsOfOrder)
  {
    boolean wasAdded = false;
    if (lineItemsOfOrder.contains(aLineItemsOfOrder)) { return false; }
    lineItemsOfOrder.add(aLineItemsOfOrder);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLineItemsOfOrder(LineItem aLineItemsOfOrder)
  {
    boolean wasRemoved = false;
    if (lineItemsOfOrder.contains(aLineItemsOfOrder))
    {
      lineItemsOfOrder.remove(aLineItemsOfOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLineItemsOfOrderAt(LineItem aLineItemsOfOrder, int index)
  {  
    boolean wasAdded = false;
    if(addLineItemsOfOrder(aLineItemsOfOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLineItemsOfOrder()) { index = numberOfLineItemsOfOrder() - 1; }
      lineItemsOfOrder.remove(aLineItemsOfOrder);
      lineItemsOfOrder.add(index, aLineItemsOfOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLineItemsOfOrderAt(LineItem aLineItemsOfOrder, int index)
  {
    boolean wasAdded = false;
    if(lineItemsOfOrder.contains(aLineItemsOfOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLineItemsOfOrder()) { index = numberOfLineItemsOfOrder() - 1; }
      lineItemsOfOrder.remove(aLineItemsOfOrder);
      lineItemsOfOrder.add(index, aLineItemsOfOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLineItemsOfOrderAt(aLineItemsOfOrder, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    paymentOfOrder = null;
    lineItemsOfOrder.clear();
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