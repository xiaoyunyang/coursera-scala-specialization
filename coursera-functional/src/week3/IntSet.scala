package week3


/** Base Class of Empty and NonEmpty **/
abstract class IntSet {
  //If you have abstract class, then you don't have to have an implementation of class functions
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

/** Subclasses of IntSet implements a binary tree **/
//NonEmpty is a subclass of IntSet. That means the type Empty and NonEmpty conforms to IntSet
//This means Empty and NonEmpty can be used whenever an object of type IntSet is required
//If no explict superclass is given, the standard class Object in the java package java.lang is assumed
//The Empty and NonEmpty classes' contains and incl implements the abstract functions in the base trait IntSet
//You need to use override when you redefine an existing, non-abstract definition of a class


class Empty extends IntSet {
  //contains always false because an empty tree does not contain anything
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
  override def toString = "."
  def union(other: IntSet): IntSet = other
}

//Empty implemented as a singleton (singletons gets created when you first define it)
//We should really be making Empty a singleton because there's only one Empty
object Empty2 extends IntSet {
  def contains(x: Int): Boolean = false
  
  //Singletons don't need the "new" to instantiate. Empty2 is already a value
  def incl(x: Int): IntSet = new NonEmpty(x, Empty2, Empty2)
  
  override def toString = "."
  def union(other: IntSet): IntSet = other
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean = {
    if(x < elem) left.contains(x)
    else if (x > elem) right.contains(x)
    else true
  }
  //creates persistent data structure. If we make changes to the datastructure,
  //we maintain the old copy of the datastructure and make a copy with the changes we want
  def incl(x: Int): IntSet =  {
    if(x < elem) new NonEmpty(elem, left.incl(x), right)
    else if(x > elem) new NonEmpty(elem, left, right.incl(x))
    else this
  }
  override def toString = "{"+left+elem+right+"}"
  
  //combine this IntSet with the other IntSet. Do not repeat elements
  
  def union(other: IntSet):IntSet = {
    ((left union right) union other) incl elem
  }
}
