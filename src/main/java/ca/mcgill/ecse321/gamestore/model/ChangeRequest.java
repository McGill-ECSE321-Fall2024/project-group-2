package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;

// line 79 "model.ump"
// line 203 "model.ump"
public class ChangeRequest
{

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

  //------------------------
  // CONSTRUCTOR
  //------------------------

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