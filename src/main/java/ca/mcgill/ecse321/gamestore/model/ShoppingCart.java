package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Date;

// line 59 "model.ump"
// line 182 "model.ump"
@Entity
public class ShoppingCart
{
  @Id
  @GeneratedValue
  private int id;
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ShoppingCart Attributes
  private Date creationDate;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public ShoppingCart(){}
  public ShoppingCart(Date aCreationDate)
  {
    creationDate = aCreationDate;
  }

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

  public boolean setCreationDate(Date aCreationDate)
  {
    boolean wasSet = false;
    creationDate = aCreationDate;
    wasSet = true;
    return wasSet;
  }

  public Date getCreationDate()
  {
    return creationDate;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "creationDate" + "=" + (getCreationDate() != null ? !getCreationDate().equals(this)  ? getCreationDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}