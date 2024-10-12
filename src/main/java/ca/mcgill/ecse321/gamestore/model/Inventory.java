package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

// line 24 "model.ump"
// line 152 "model.ump"
@Entity
public class Inventory
{
  @Id
  @GeneratedValue
  private int id;
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Inventory Attributes
  private int numberOfItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Inventory(int aNumberOfItems)
  {
    numberOfItems = aNumberOfItems;
  }
  public Inventory(){}

  //------------------------
  // INTERFACE
  //------------------------
  public Integer getId() {
    return id;
  }

  // Setter for id
  public void setId(Integer id) {
    this.id = id;
  }
  public boolean setNumberOfItems(int aNumberOfItems)
  {
    boolean wasSet = false;
    numberOfItems = aNumberOfItems;
    wasSet = true;
    return wasSet;
  }

  public int getNumberOfItems()
  {
    return numberOfItems;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "numberOfItems" + ":" + getNumberOfItems()+ "]";
  }
}