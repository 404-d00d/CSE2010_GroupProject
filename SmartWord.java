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

   public static String[] guesses = new String[3];  // 3 guesses from SmartWord

   public static tNode root = new tNode('*', 0); // creates the root tNode; // root of tree

   public static tNode current;

   public static ArrayList<tNode> wordNodes = new ArrayList<>();
   public static ArrayList<String> badGuess = new ArrayList<>(); // list of bag guesses
   public static ArrayList<String> goodGuess = new ArrayList<>(); // list of good guesses

 
   public static class tNode implements Comparable<tNode> { // tree class
      char letter;
      tNode parent;
      int count;
      List<tNode> children;
      boolean endOfWord = false;
      String fullWord; // only used for wordNodes, NEVER for the main tree

         /* compares nodes bases on occurences */
         @Override
         public int compareTo(tNode t) {
            if (this.count > t.count) {
               return 1;
            }
            else if (this.count < t.count) {
               return -1;
            }
            else {
               return 0;
            }
         }

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
         return t;
      }


      /* get and set methods */
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
      public List<tNode> getChildren() { // returns the children list of a particular tree node
         return this.children;
      }
      public void setEndOfWord(boolean endOfWord) { // sets the character as making the end of a word true or false
         this.endOfWord = endOfWord;
      }
      public boolean getEndOfWord() { // returns true if character forms the end of a word,false otherwise
         return this.endOfWord;
      }

      
      public static void addGuesses(tNode c) {
         Queue<tNode> queue = new LinkedList<>();
         int maxDepth = getRank(c) + 5; // we only want to search for next 5 letters, average word is 5 letters
         queue.add(c); // adds root to queue
         while (!queue.isEmpty()) {
            int num = queue.size(); // size of queue
            while (num > 0) {
               tNode t = queue.poll(); // Retrieves and removes the head of this queue, or returns null if this queue is empty.

               /* base case */
               if (getRank(t) > maxDepth) { // if t is out of bounds, stops searching
                  queue.clear();
                  break;
               }

               /* if t is end of word, adds word to wordNodes */
			   if (t.getEndOfWord()) {
                  int i = 0;

                  /* adds t into wordNodes in descending order based on number of occurences*/
                  for (tNode tn : wordNodes) {
                     if (t.getCount() >= tn.getCount()) { // if t's count is >= tn's count, adds it in at index i
                        wordNodes.add (i, t);
                        break;
                     } else {
                        i++; // increments index
                     }
                  }

                  /* if t has not been added into wordNodes, adds it to the end*/
                  if (!wordNodes.contains(t) || wordNodes.isEmpty()) { 
                     wordNodes.add(t); // adds t to wordNodes
                  }
               }

               /* adds children of t to the queue */
               for (tNode child : t.children) { 
                  queue.add(child);
			   }
               num--; 
            }
         }

         /* adds the top 3 in wordNodes to guesses */
         int j = 0;
         for (tNode n : wordNodes) {

            /* break case */ 
            if(guesses[2] != null) { // if guesses is full, stops filling
               break;
            }

            /* adds word to guesses if it was not a bad guess before */
            String word = getWord(wordNodes.get(j));
            if (!(badGuess.contains(word))) { // if word was not a bad guess before, adds it to guesses
               guesses[j] = word;
            }
         }
      }


      public static String getWord (tNode t) {
         String s = "";

         /* gets word */
         while (t != root) {
            s = t.getLetter() + s;
            t = t.getParent();
         }
         return s;
      }


      /* checks if there is a child */
      public static Boolean hasChild(tNode p, char c) {
         
         for(tNode child : p.getChildren()) {
            if(child.letter == c) {
               return true;
            }
         }
         return false;
      }



      /* returns the child with letter c */
      public tNode getChild(char c) {
         for(tNode child : children) {
            if(child.getLetter() == c) {
               return child;
            }
         }
         return null;
      }


      /* returns the rank of the tree node with item equal to s */
      public static int getRank (tNode n) {
         int rank = 0;
         while (n != root) { // loops from n to root
            n = n.getParent(); // sets n to its parent
            rank++;
         }
         return rank;
      }


      /* prints out tree's words */
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


      /* increments occurences of word when guessed correctly */
      public static void incremCount(tNode parent, String word) {
         // for(tNode child : parent.children) {
         //    for(int i = 0; i < word.length(); i++) {
         //       char currentChild = word.charAt(i);
         //       if(child.getLetter() == currentChild) {
         //          incremCount(child, word);
         //          if((child.getEndOfWord() == true) && (child.getLetter() == word.charAt(word.length() - 1))) {
         //             child.count++;
         //          }
         //       }
         //    }
         // }
         for (int a = 0; a < word.length(); a++) {
            parent = parent.getChild(word.charAt(a));
         }
         if (parent.getEndOfWord() == true) {
            parent.count++;
         }
      }
   } /* end of tNode class */



   // initialize SmartWord with a file of English words
   public SmartWord(String wordFile) throws IOException {
      File wordF = new File(wordFile);
      Scanner sc = new Scanner(wordF);

      /* loops until end of the file */
      while(sc.hasNextLine()) {
         String line = sc.nextLine();
         
         tNode parent = root; // sets the parent equal to the root node
           
            for (int i = 0; i < line.length(); i++) { // go through each character of the word
               char current = line.charAt(i);
               if(tNode.hasChild(parent, current)) { // check if the parent has a child, if it does we get the child
                  parent = parent.getChild(current);
               } else { // if not we add child
                  parent = tNode.appendChild(parent, current, 0);  
               }
           }
           parent.count++; // mark the occurences of word
           parent.setEndOfWord(true); // mark the ending of the word
      }
      sc.close(); // closes scanner
   }



   // process old messages from oldMessageFile
   public void processOldMessages(String oldMessageFile) throws IOException {
      File oldF = new File(oldMessageFile);
      Scanner sc2 = new Scanner(oldF);

      /* loops until end of the file */
      while(sc2.hasNext()) {
         String w = sc2.next();

         tNode parent = root; // sets the parent equal to the root node

         w = w.replaceAll("[^a-zA-Z]", "").toLowerCase(); // Gets rid of weird characters
         for (int i = 0; i < w.length(); i++) { // go through each character of the word
            char current = w.charAt(i);
           
            /* check if the parent has a child, if it does we get the child */
            if(tNode.hasChild(parent, current)) {
               parent = parent.getChild(current);
            } else { // if not we add child
               parent = tNode.appendChild(parent, current, 0);  
            }
         }
         parent.count++; // mark the occurences of word      
         parent.setEndOfWord(true); // mark the ending of the word
      }
      sc2.close(); // closes scanner
   }


   // based on a letter typed in by the user, return 3 word guesses in an array
   // letter: letter typed in by the user
   // letterPosition:  position of the letter in the word, starts from 0
   // wordPosition: position of the word in a message, starts from 0
   public String[] guess(char letter,  int letterPosition, int wordPosition) {
      if(letterPosition == 0) {
         current = root;
      } 
      if(SmartWord.tNode.hasChild(current, letter)){
            current = current.getChild(letter);
            tNode.addGuesses(current);
      }

      return guesses;
      
   }

   public void feedback(boolean isCorrectGuess, String correctWord) {
      if((isCorrectGuess == false) && (correctWord == null)) {
         for (String word : guesses) {
            badGuess.add(word);
         }
      }

      else if((isCorrectGuess == false) && (correctWord != null)) {
         badGuess.add(correctWord);
      }

      else if((isCorrectGuess) && (correctWord != null)) {
         goodGuess.add(correctWord);
      }
 
   }
}
