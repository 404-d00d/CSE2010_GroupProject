/* 
  Authors (group members): Garrett Gmeiner, Tyler Ton, and David Tran
  Email addresses of group members: ggmeiner2021@my.fit.edu, tton2021@my.fit.edu, dtran2021@my.fit.edu
  Group name: 34GTD
  Course: CSE 2010
  Section: 34
  Description of the overall algorithm:
*/

import java.io.*;
import java.util.*;
public class SmartWord {

   String[] guesses = new String[3];  // 3 guesses from SmartWord

   public static tNode root; // root of tree

   public static class tNode { // tree class
      char letter;
      tNode parent;
      int count;
      List<tNode> children;
      boolean endOfWord = false;

      public tNode (char letter, int count) { // constructor for tree node
        this.count = count;
        this.letter = letter;
        this.children = new ArrayList<tNode>();
      }
 

       public static tNode appendChild(tNode p, char c, int count) { // adds a child to tree
         tNode t = new tNode(c, count); // creates new tree node
         t.setParent(p); // sets parent of node to p
         t.setEndOfWord(false);
         
         /* alphabetically adds to list of children */
         int index = 0;
         for (tNode n : t.getParent().children) {
            if (n.getLetter() >= t.getLetter()) {
               t.getParent().children.add(index, t); // adds child to children list of parent in front of n
               break;
            } else {
               index++;
            }
         }
         if (!t.getParent().children.contains(t) || t.getParent().children.isEmpty()) { // if t has not been added, adds it to the end
            t.getParent().children.add(t); // adds child to children list of parent
         }

         /* checks if added letter makes a word */
         String possibleWord = "";
         tNode n = t; // temporary tNode n points to tNode t

         // creates backwords word
         while (n != root) {
            possibleWord = possibleWord + n.getLetter();
            n = n.getParent(); // sets n to its parent
         }

         // reverses the characters in the word
         String newStr = "";
         for (int i = 0; i < possibleWord.length(); i++) {
            char s = possibleWord.charAt(i); //extracts each character
            newStr = s + newStr; //adds each character in front of the existing string
         }
         possibleWord = newStr;


         return t;
      }

      public static tNode findNode(tNode n, char s) { // finds node with item equal to s using In-Order traversal
         if ((n.letter == s)) { // base case
            return n;
         } else {
		    for (tNode c : n.children) { // loops through each node in the children list for node n
               tNode vistedNode = findNode(c, s); // makes the problem smaller
               if (vistedNode != null) { // ensures vistedNode isn't null to avoid nullPointerException
                  return vistedNode;
               }
            }
         }
         return null; // returns null if not found
      }


      public static Boolean checkNode(tNode n, char s) { // finds node with item equal to s using In-Order traversal
         if ((n.letter == s)) { // base case
            return true;
         } else {
		    for (tNode c : n.children) { // loops through each node in the children list for node n
               tNode vistedNode = findNode(c, s); // makes the problem smaller
               if (vistedNode != null) { // ensures vistedNode isn't null to avoid nullPointerException
                  return true;
               } 
            }
         }
         return false; // returns false if not found
      }

      public char getLetter() { // returns the letter of a particular tree node
        return this.letter;
      }


      public void setletter(char letter) { // sets the letter of a particular tree node to a string named letter
         this.letter = letter;
      }

      public int getCount() { // returns the count of a particular tree node
        return this.count;
      }

      public void setCount(int count) { // sets the count for a particular node of the tree
        this.count = count;
      }

      public tNode getParent() { // returns the parent of a particular tree node
         return this.parent;
      }


      public void setParent(tNode parent) { // sets the parent of a particular tree node
         this.parent = parent;
      }


      public List<tNode> getchildren() { // returns the children list of a particular tree node
         return this.children;
      }


      public void setEndOfWord(boolean endOfWord) { // sets the character as making the end of a word true or false
         this.endOfWord = endOfWord;
      }


      public boolean getEndOfWord() { // returns true if character forms the end of a word,false otherwise
         return this.endOfWord;
      }


      // checks if there is a child 
      public Boolean hasChild(char c) {
         for(tNode child : children) {
            if(child.getLetter() == c) {
               return true;
            }
         }
         return false;
      }



      // grabs the child 
      public tNode getChild(char c) { 
         for(tNode child : children) {
            if(child.getLetter() == c) {
               return child;
            }
         }
         return null;
      
      }

      @Override
      public String toString() {
         StringBuilder sb = new StringBuilder();

         for(tNode child : children) {
            // recursion that goes down and up the tree
            String childrenStrings = child.toString();
            String tmp = "";
            for (int i = 0; i < childrenStrings.length(); i++) {
               // adds all of the children of the first children
               tmp += childrenStrings.charAt(i);
               
               if (childrenStrings.charAt(i) == '\n' || i == childrenStrings.length() - 1) {
                  // adds parent to the front of string
                  sb.append(child.getLetter() + tmp);
                  // resets temp
                  tmp = "";
               }
         
            }
         }
         if (this.endOfWord) {
            sb.append("\n");
         }
         return sb.toString();
      }
   } /* end of tNode class */


   // initialize SmartWord with a file of English words
   public SmartWord(String wordFile) {

   }

   // process old messages from oldMessageFile
   public void processOldMessages(String oldMessageFile) {
     
   }

   // based on a letter typed in by the user, return 3 word guesses in an array
   // letter: letter typed in by the user
   // letterPosition:  position of the letter in the word, starts from 0
   // wordPosition: position of the word in a message, starts from 0
   public String[] guess(char letter,  int letterPosition, int wordPosition) {

      return guesses;
   }

   // feedback on the 3 guesses from the user
   // isCorrectGuess: true if one of the guesses is correct
   // correctWord: 3 cases:
   // a.  correct word if one of the guesses is correct
   // b.  null if none of the guesses is correct, before the user has typed in
   //            the last letter
   // c.  correct word if none of the guesses is correct, and the user has
   //            typed in the last letter
   // That is:
   // Case       isCorrectGuess      correctWord  
   // a.         true                correct word
   // b.         false               null
   // c.         false               correct word
   public void feedback(boolean isCorrectGuess, String correctWord) {
      if((isCorrectGuess == false) && (correctWord == null)) {
         
      }

      if((isCorrectGuess == false) && (correctWord != null)) {

      }

      if((isCorrectGuess) && (correctWord != null)) {

      }
  
   }
   
   public static void main (String[] args) throws IOException {
 
       File wordF = new File(args[0]);
       File oldF = new File(args[1]);
 
       Scanner sc = new Scanner(wordF);
       Scanner sc2 = new Scanner(oldF);
 
       root = new tNode('*', 0); // creates the root tNode


       /* Move to SmartWord Method */
       while(sc.hasNextLine()) {
         String line = sc.nextLine();
         // sets the parent equal to the root node
         tNode parent = root;
            // go through each character of the word
            for (int i = 0; i < line.length(); i++) {
               char current = line.charAt(i);
               // check if the parent has a child
               Boolean check = parent.hasChild(current);
               // if it does we get the child
               if(check) {
                  parent = parent.getChild(current);
               // if not we add child
               } else {
                  parent = tNode.appendChild(parent, current, 0);  
               } 
           }
           // mark the occurences of word
           parent.count++;
           // mark the ending of the word 
           parent.setEndOfWord(true);
       } 


      /* Move to processOldMessages method */
      while(sc2.hasNextLine()) {
         String line = sc2.nextLine();
         String[] str = line.split(" ");

         tNode parent = root; // sets the parent equal to the root node

         for (String s : str) {

            s = s.replaceAll("[^a-zA-Z]", "").toLowerCase(); // Gets rid of weird characters
            for (int i = 0; i < s.length(); i++) { // go through each character of the word
               char current = s.charAt(i);
            
               /* check if the parent has a child, if it does we get the child */
               if(parent.hasChild(current)) {
                  parent = parent.getChild(current);
               } else { // if not we add child
                  parent = tNode.appendChild(parent, current, 0);  
               } 
		    }
         }     
         parent.count++; // mark the occurences of word      
         parent.setEndOfWord(true); // mark the ending of the word 
	  }

       /* closes scanners */
       sc.close();
       sc2.close();

       System.out.println("output\n" + root.toString());

   }

}
