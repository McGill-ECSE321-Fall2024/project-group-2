package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Date;

// line 39 "model.ump"
// line 167 "model.ump"
@Entity

public class Payment
{
  @Id
  @GeneratedValue
  private int paymentId;

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
  public Payment(){}
  //------------------------
  // INTERFACE
  //------------------------
  public Integer getId() {
    return paymentId;
  }

  // Setter for id
  public void setId(Integer id) {
    this.paymentId = id;
  }
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

  public int getId() {
    return id;
  }

  public String toString()
  {
    return super.toString() + "["+
            "total" + ":" + getTotal()+ "," +
            "details" + ":" + getDetails()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "paidDate" + "=" + (getPaidDate() != null ? !getPaidDate().equals(this)  ? getPaidDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}