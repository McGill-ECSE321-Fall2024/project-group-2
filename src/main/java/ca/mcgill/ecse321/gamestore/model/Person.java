package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/




import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// line 4 "model.ump"
// line 133 "model.ump"
@MappedSuperclass
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private int id;
  //Person Attributes
  private String isAbstract;

  private String userID;
  private String name;
  private String email;
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(){

  }


  public Person(String aIsAbstract, String aUserID, String aName, String aEmail, String aPassword)
  {
    isAbstract = aIsAbstract;
    userID = aUserID;
    name = aName;
    email = aEmail;
    password = aPassword;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsAbstract(String aIsAbstract)
  {
    boolean wasSet = false;
    isAbstract = aIsAbstract;
    wasSet = true;
    return wasSet;
  }

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

  public String getIsAbstract()
  {
    return isAbstract;
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

  public int getId() {
    // TODO Auto-generated method stub
    return id;
  }


  public String toString()
  {
    return super.toString() + "["+
            "isAbstract" + ":" + getIsAbstract()+ "," +
            "userID" + ":" + getUserID()+ "," +
            "name" + ":" + getName()+ "," +
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}