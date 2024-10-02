package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Date;

// line 73 "model.ump"
// line 198 "model.ump"

@Entity
public class Review
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  @GeneratedValue
  //Review Attributes
  @Id

  private int rating;
  private String comments;
  private Date reviewDate;

  //------------------------
  // CONSTRUCTOR
  //------------------------


  public Review(int aRating, String aComments, Date aReviewDate)
  {
    rating = aRating;
    comments = aComments;
    reviewDate = aReviewDate;
  }
  public Review(){}

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRating(int aRating)
  {
    boolean wasSet = false;
    rating = aRating;
    wasSet = true;
    return wasSet;
  }

  public boolean setComments(String aComments)
  {
    boolean wasSet = false;
    comments = aComments;
    wasSet = true;
    return wasSet;
  }

  public boolean setReviewDate(Date aReviewDate)
  {
    boolean wasSet = false;
    reviewDate = aReviewDate;
    wasSet = true;
    return wasSet;
  }

  public int getRating()
  {
    return rating;
  }

  public String getComments()
  {
    return comments;
  }

  public Date getReviewDate()
  {
    return reviewDate;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "rating" + ":" + getRating()+ "," +
            "comments" + ":" + getComments()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "reviewDate" + "=" + (getReviewDate() != null ? !getReviewDate().equals(this)  ? getReviewDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}