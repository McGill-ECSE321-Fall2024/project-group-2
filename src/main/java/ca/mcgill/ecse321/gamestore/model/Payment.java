package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;

// line 39 "model.ump"
// line 168 "model.ump"
public class Payment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Payment Attributes
  private Date paidDate;
  private double total;
  private String details;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Payment(Date aPaidDate, double aTotal, String aDetails)
  {
    paidDate = aPaidDate;
    total = aTotal;
    details = aDetails;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPaidDate(Date aPaidDate)
  {
    boolean wasSet = false;
    paidDate = aPaidDate;
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

  public boolean setDetails(String aDetails)
  {
    boolean wasSet = false;
    details = aDetails;
    wasSet = true;
    return wasSet;
  }

  public Date getPaidDate()
  {
    return paidDate;
  }

  public double getTotal()
  {
    return total;
  }

  public String getDetails()
  {
    return details;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "total" + ":" + getTotal()+ "," +
            "details" + ":" + getDetails()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "paidDate" + "=" + (getPaidDate() != null ? !getPaidDate().equals(this)  ? getPaidDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}