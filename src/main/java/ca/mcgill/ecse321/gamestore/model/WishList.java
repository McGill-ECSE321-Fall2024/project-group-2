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
  private String wishName;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WishList(String wishName)
  {
    wishName = wishName;
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
  public boolean setWishName(String aWishName)
  {
    boolean wasSet = false;
    wishName = aWishName;
    wasSet = true;
    return wasSet;
  }
  public String getWishName()
  {

    return wishName;
  }





  public String toString()
  {
    return super.toString() + "["+
            "wishName" + ":" + wishName+ "]";
  }
}