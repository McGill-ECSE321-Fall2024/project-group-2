package ca.mcgill.ecse321.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

import jakarta.persistence.*;
import java.util.*;

// line 28 "model.ump"
// line 158 "model.ump"
@Entity
public class WishList {

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WishList Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int numberItem;

  //WishList Associations
  @OneToMany
  private List<LineItem> lineItemInWishList;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WishList(int aNumberItem) {
    numberItem = aNumberItem;
    lineItemInWishList = new ArrayList<LineItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumberItem(int aNumberItem) {
    boolean wasSet = false;
    numberItem = aNumberItem;
    wasSet = true;
    return wasSet;
  }

  public int getNumberItem() {
    return numberItem;
  }

  /* Code from template association_GetMany */
  public LineItem getLineItemInWishList(int index) {
    LineItem aLineItemInWishList = lineItemInWishList.get(index);
    return aLineItemInWishList;
  }

  public List<LineItem> getLineItemInWishList() {
    List<LineItem> newLineItemInWishList = Collections.unmodifiableList(lineItemInWishList);
    return newLineItemInWishList;
  }

  public int numberOfLineItemInWishList() {
    int number = lineItemInWishList.size();
    return number;
  }

  public boolean hasLineItemInWishList() {
    boolean has = lineItemInWishList.size() > 0;
    return has;
  }

  public int indexOfLineItemInWishList(LineItem aLineItemInWishList) {
    int index = lineItemInWishList.indexOf(aLineItemInWishList);
    return index;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLineItemInWishList() {
    return 0;
  }

  /* Code from template association_AddUnidirectionalMany */
  public boolean addLineItemInWishList(LineItem aLineItemInWishList) {
    boolean wasAdded = false;
    if (lineItemInWishList.contains(aLineItemInWishList)) {
      return false;
    }
    lineItemInWishList.add(aLineItemInWishList);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLineItemInWishList(LineItem aLineItemInWishList) {
    boolean wasRemoved = false;
    if (lineItemInWishList.contains(aLineItemInWishList)) {
      lineItemInWishList.remove(aLineItemInWishList);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addLineItemInWishListAt(LineItem aLineItemInWishList, int index) {
    boolean wasAdded = false;
    if (addLineItemInWishList(aLineItemInWishList)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfLineItemInWishList()) {
        index = numberOfLineItemInWishList() - 1;
      }
      lineItemInWishList.remove(aLineItemInWishList);
      lineItemInWishList.add(index, aLineItemInWishList);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLineItemInWishListAt(LineItem aLineItemInWishList, int index) {
    boolean wasAdded = false;
    if (lineItemInWishList.contains(aLineItemInWishList)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfLineItemInWishList()) {
        index = numberOfLineItemInWishList() - 1;
      }
      lineItemInWishList.remove(aLineItemInWishList);
      lineItemInWishList.add(index, aLineItemInWishList);
      wasAdded = true;
    } else {
      wasAdded = addLineItemInWishListAt(aLineItemInWishList, index);
    }
    return wasAdded;
  }

  public void delete() {
    lineItemInWishList.clear();
  }

  public String toString() {
    return super.toString() + "[" +
            "numberItem" + ":" + getNumberItem() + "]";
  }
}
