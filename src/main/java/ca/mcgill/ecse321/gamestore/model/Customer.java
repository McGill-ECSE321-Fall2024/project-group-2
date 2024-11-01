package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.*;

// line 12 "model.ump"
// line 137 "model.ump"
@Entity
@DiscriminatorValue("Customer")

public class Customer extends Person
{


  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Associations
  @OneToOne
  private WishList wishList;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer( String aUserID, String aName, String aEmail, String aPassword)
  {
    super( aUserID, aName, aEmail, aPassword);
  }
  public Customer (){}

  // Setter for id
  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public WishList getWishList()
  {
    return wishList;
  }

  public boolean hasWishList()
  {
    boolean has = wishList != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setWishList(WishList aNewWishList)
  {
    boolean wasSet = false;
    wishList = aNewWishList;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    wishList = null;
    super.delete();
  }

}