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


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aIsAbstract, String aUserID, String aName, String aEmail, String aPassword)
  {
    super(aIsAbstract, aUserID, aName, aEmail, aPassword);

  }
  public Employee(){}
  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */

  /* Code from template association_SetUnidirectionalOne */


  public void delete()
  {
    super.delete();
  }

}