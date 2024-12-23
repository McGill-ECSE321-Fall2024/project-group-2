package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

// line 68 "model.ump"
// line 192 "model.ump"
@Entity
public class Category
{
  @Id
  @GeneratedValue
  private int categoryId;
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Category Attributes
  private String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Category(String aName)
  {
    name = aName;
  }
  public Category(){}

  //------------------------
  // INTERFACE
  //------------------------
  public Integer getId() {
    return categoryId;
  }

  // Setter for id
  public void setId(Integer id) {
    this.categoryId = id;
  }
  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }


  public String getName()
  {
    return name;
  }


  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," ;
  }
}