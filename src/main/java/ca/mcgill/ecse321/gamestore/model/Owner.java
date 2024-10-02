package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 20 "model.ump"
// line 148 "model.ump"
public class Owner extends Person
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum RequestStatus { Approved, Declined, InProgress }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private Inventory inventory;
  private List<ChangeRequest> changeRequest;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aIsAbstract, String aUserID, String aName, String aEmail, String aPassword, Inventory aInventory)
  {
    super(aIsAbstract, aUserID, aName, aEmail, aPassword);
    if (!setInventory(aInventory))
    {
      throw new RuntimeException("Unable to create Owner due to aInventory. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    changeRequest = new ArrayList<ChangeRequest>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Inventory getInventory()
  {
    return inventory;
  }
  /* Code from template association_GetMany */
  public ChangeRequest getChangeRequest(int index)
  {
    ChangeRequest aChangeRequest = changeRequest.get(index);
    return aChangeRequest;
  }

  public List<ChangeRequest> getChangeRequest()
  {
    List<ChangeRequest> newChangeRequest = Collections.unmodifiableList(changeRequest);
    return newChangeRequest;
  }

  public int numberOfChangeRequest()
  {
    int number = changeRequest.size();
    return number;
  }

  public boolean hasChangeRequest()
  {
    boolean has = changeRequest.size() > 0;
    return has;
  }

  public int indexOfChangeRequest(ChangeRequest aChangeRequest)
  {
    int index = changeRequest.indexOf(aChangeRequest);
    return index;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfChangeRequest()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addChangeRequest(ChangeRequest aChangeRequest)
  {
    boolean wasAdded = false;
    if (changeRequest.contains(aChangeRequest)) { return false; }
    changeRequest.add(aChangeRequest);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeChangeRequest(ChangeRequest aChangeRequest)
  {
    boolean wasRemoved = false;
    if (changeRequest.contains(aChangeRequest))
    {
      changeRequest.remove(aChangeRequest);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addChangeRequestAt(ChangeRequest aChangeRequest, int index)
  {  
    boolean wasAdded = false;
    if(addChangeRequest(aChangeRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChangeRequest()) { index = numberOfChangeRequest() - 1; }
      changeRequest.remove(aChangeRequest);
      changeRequest.add(index, aChangeRequest);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveChangeRequestAt(ChangeRequest aChangeRequest, int index)
  {
    boolean wasAdded = false;
    if(changeRequest.contains(aChangeRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChangeRequest()) { index = numberOfChangeRequest() - 1; }
      changeRequest.remove(aChangeRequest);
      changeRequest.add(index, aChangeRequest);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addChangeRequestAt(aChangeRequest, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    inventory = null;
    changeRequest.clear();
    super.delete();
  }

}