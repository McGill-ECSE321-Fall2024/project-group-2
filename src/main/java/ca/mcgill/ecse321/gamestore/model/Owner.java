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



  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner( String aUserID, String aName, String aEmail, String aPassword)
  {
    super( aUserID, aName, aEmail, aPassword);


  }
  public Owner (){}

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */

  /* Code from template association_GetOne */
  /* Code from template association_SetUnidirectionalOne */


  /* Code from template association_SetUnidirectionalOne */


  public void delete()
  {
    super.delete();
  }

}