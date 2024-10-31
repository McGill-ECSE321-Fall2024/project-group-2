package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

// line 28 "model.ump"
// line 157 "model.ump"
@Entity
public class WishList
{
  @Id
  @GeneratedValue
  private int listId;
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WishList Attributes
  private int numberItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WishList(int aNumberItem)
  {
    numberItem = aNumberItem;
  }

  //------------------------
  // INTERFACE
  //------------------------
  public WishList(){}
  public Integer getId() {
    return listId;
  }

  // Setter for id
  public void setId(Integer id) {
    this.listId = id;
  }
  public boolean setNumberItem(int aNumberItem)
  {
    boolean wasSet = false;
    numberItem = aNumberItem;
    wasSet = true;
    return wasSet;
  }

  public int getNumberItem()
  {
    return numberItem;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "numberItem" + ":" + getNumberItem()+ "]";
  }
}