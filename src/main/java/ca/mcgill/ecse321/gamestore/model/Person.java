package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.*;

// line 4 "model.ump"
// line 132 "model.ump"
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)

public class Person
{
  @Id
  private String email;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String isAbstract;
  private String userID;
  private String name;
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person( String aUserID, String aName, String aEmail, String aPassword)
  {
    userID = aUserID;
    name = aName;
    email = aEmail;
    password = aPassword;
  }
  public Person(){}
  //------------------------
  // INTERFACE
  //------------------------


  // Setter for id



  public boolean setUserID(String aUserID)
  {
    boolean wasSet = false;
    userID = aUserID;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }


  public String getUserID()
  {
    return userID;
  }

  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "userID" + ":" + getUserID()+ "," +
            "name" + ":" + getName()+ "," +
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}