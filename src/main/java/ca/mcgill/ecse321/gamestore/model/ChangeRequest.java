package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// line 79 "model.ump"
// line 203 "model.ump"
@Entity
public class ChangeRequest
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  //------------------------
  // MEMBER VARIABLES
  //------------------------
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private int id;
  //ChangeRequest Attributes
  private Date timeRequest;

  @Enumerated(EnumType.STRING)
  @Column(name = "requestStatus")
  private RequestStatus status;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ChangeRequest() {};


  public ChangeRequest(Date aTimeRequest, RequestStatus aStatus)
  {
    timeRequest = aTimeRequest;
    status = aStatus;
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "timeRequest" + "=" + (getTimeRequest() != null ? !getTimeRequest().equals(this)  ? getTimeRequest().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null");
  }
}