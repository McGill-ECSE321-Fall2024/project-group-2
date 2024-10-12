package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.*;

import java.sql.Date;

// line 79 "model.ump"
// line 202 "model.ump"
@Entity
public class ChangeRequest
{
   @Id
   @GeneratedValue
  private int id;
  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum RequestStatus { Approved, Declined, InProgress }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ChangeRequest Attributes
  private Date timeRequest;
  private RequestStatus status;

  //ChangeRequest Associations
  @OneToOne
  private Inventory inventory;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ChangeRequest(Date aTimeRequest, RequestStatus aStatus, Inventory aInventory)
  {
    timeRequest = aTimeRequest;
    status = aStatus;
    if (!setInventory(aInventory))
    {
      throw new RuntimeException("Unable to create ChangeRequest due to aInventory. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  public ChangeRequest(){}
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
  public boolean setTimeRequest(Date aTimeRequest)
  {
    boolean wasSet = false;
    timeRequest = aTimeRequest;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(RequestStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public Date getTimeRequest()
  {
    return timeRequest;
  }

  public RequestStatus getStatus()
  {
    return status;
  }
  /* Code from template association_GetOne */
  public Inventory getInventory()
  {
    return inventory;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setInventory(Inventory aNewInventory)
  {
    boolean wasSet = false;
    if (aNewInventory != null)
    {
      inventory = aNewInventory;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    inventory = null;
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "timeRequest" + "=" + (getTimeRequest() != null ? !getTimeRequest().equals(this)  ? getTimeRequest().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "inventory = "+(getInventory()!=null?Integer.toHexString(System.identityHashCode(getInventory())):"null");
  }
}