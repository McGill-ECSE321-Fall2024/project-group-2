package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.*;

import java.sql.Date;

// line 16 "model.ump"
// line 142 "model.ump"
@Entity
@DiscriminatorValue("Employee") // Add this line
public class Employee extends Person
{


  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Associations
  @OneToOne
  private ChangeRequest changeRequest;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aIsAbstract, String aUserID, String aName, String aEmail, String aPassword, ChangeRequest aChangeRequest)
  {
    super(aIsAbstract, aUserID, aName, aEmail, aPassword);
    if (!setChangeRequest(aChangeRequest))
    {
      throw new RuntimeException("Unable to create Employee due to aChangeRequest. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  public Employee(){}
  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public ChangeRequest getChangeRequest()
  {
    return changeRequest;
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
    changeRequest = null;
    super.delete();
  }

}