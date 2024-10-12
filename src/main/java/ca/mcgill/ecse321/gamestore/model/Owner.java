package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.sql.Date;

// line 20 "model.ump"
// line 147 "model.ump"
@Entity
public class Owner extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  @OneToOne
  private Inventory inventory;
  @OneToOne
  private ChangeRequest changeRequest;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aIsAbstract, String aUserID, String aName, String aEmail, String aPassword, Inventory aInventory, ChangeRequest aChangeRequest)
  {
    super(aIsAbstract, aUserID, aName, aEmail, aPassword);
    if (!setInventory(aInventory))
    {
      throw new RuntimeException("Unable to create Owner due to aInventory. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setChangeRequest(aChangeRequest))
    {
      throw new RuntimeException("Unable to create Owner due to aChangeRequest. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  public Owner (){}

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Inventory getInventory()
  {
    return inventory;
  }
  /* Code from template association_GetOne */
  public ChangeRequest getChangeRequest()
  {
    return changeRequest;
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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setChangeRequest(ChangeRequest aNewChangeRequest)
  {
    boolean wasSet = false;
    if (aNewChangeRequest != null)
    {
      changeRequest = aNewChangeRequest;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    inventory = null;
    changeRequest = null;
    super.delete();
  }

}