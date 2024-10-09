package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.util.*;
import java.sql.Date;

// line 12 "model.ump"
// line 138 "model.ump"
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer extends Person {

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Associations
  @OneToOne
  private WishList wishList;

  @OneToMany
  private List<Review> review;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aIsAbstract, String aUserID, String aName, String aEmail, String aPassword) {
    super(aIsAbstract, aUserID, aName, aEmail, aPassword);
    review = new ArrayList<Review>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public WishList getWishList() {
    return wishList;
  }

  public boolean hasWishList() {
    boolean has = wishList != null;
    return has;
  }

  /* Code from template association_GetMany */
  public Review getReview(int index) {
    Review aReview = review.get(index);
    return aReview;
  }

  public List<Review> getReview() {
    List<Review> newReview = Collections.unmodifiableList(review);
    return newReview;
  }

  public int numberOfReview() {
    int number = review.size();
    return number;
  }

  public boolean hasReview() {
    boolean has = review.size() > 0;
    return has;
  }

  public int indexOfReview(Review aReview) {
    int index = review.indexOf(aReview);
    return index;
  }

  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setWishList(WishList aNewWishList) {
    boolean wasSet = false;
    wishList = aNewWishList;
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReview() {
    return 0;
  }

  /* Code from template association_AddUnidirectionalMany */
  public boolean addReview(Review aReview) {
    boolean wasAdded = false;
    if (review.contains(aReview)) { return false; }
    review.add(aReview);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview) {
    boolean wasRemoved = false;
    if (review.contains(aReview)) {
      review.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Review aReview, int index) {
    boolean wasAdded = false;
    if(addReview(aReview)) {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReview()) { index = numberOfReview() - 1; }
      review.remove(aReview);
      review.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Review aReview, int index) {
    boolean wasAdded = false;
    if(review.contains(aReview)) {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReview()) { index = numberOfReview() - 1; }
      review.remove(aReview);
      review.add(index, aReview);
      wasAdded = true;
    }
    else {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }

  public void delete() {
    wishList = null;
    review.clear();
    super.delete();
  }

}
