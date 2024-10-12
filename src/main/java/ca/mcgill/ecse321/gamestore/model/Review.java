package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.sql.Date;

// line 73 "model.ump"
// line 197 "model.ump"
@Entity
public class Review
{
  @Id
  @GeneratedValue
  private int id;
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  private int rating;
  private String comments;
  private Date reviewDate;

  //Review Associations
  @OneToOne
  private Customer reviewWriter;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(int aRating, String aComments, Date aReviewDate, Customer aReviewWriter)
  {
    rating = aRating;
    comments = aComments;
    reviewDate = aReviewDate;
    if (!setReviewWriter(aReviewWriter))
    {
      throw new RuntimeException("Unable to create Review due to aReviewWriter. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
 public Review(){};
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
  /* Code from template association_GetOne */
  public Customer getReviewWriter()
  {
    return reviewWriter;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setReviewWriter(Customer aNewReviewWriter)
  {
    boolean wasSet = false;
    if (aNewReviewWriter != null)
    {
      reviewWriter = aNewReviewWriter;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    reviewWriter = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "rating" + ":" + getRating()+ "," +
            "comments" + ":" + getComments()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "reviewDate" + "=" + (getReviewDate() != null ? !getReviewDate().equals(this)  ? getReviewDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "reviewWriter = "+(getReviewWriter()!=null?Integer.toHexString(System.identityHashCode(getReviewWriter())):"null");
  }
}