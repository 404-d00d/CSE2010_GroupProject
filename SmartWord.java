/*
  Authors (group members): Garrett Gmeiner, Tyler Ton, and David Tran
  Email addresses of group members: ggmeiner2021@my.fit.edu, tton2021@my.fit.edu, dtran2021@my.fit.edu
  Group name: 34GTD
  Course: CSE 2010
  Section: 34
  Description of the overall algorithm:
  Build trie with words and old text file.
  Trie is made character by Character.
  Eval simulates user typing and guess will
  return the top 3 best guesses. Feedback 
  is used to improve accurracy.
*/

import java.io.*;
import java.util.*;

public class SmartWord {

   public static String[] guesses = new String[3];  // 3 guesses from SmartWord
   public static tNode root = new tNode(' ', 0); // creates the root tNode; // root of tree
   public static tNode current; // current value in the tree
   public static ArrayList<tNode> wordNodes = new ArrayList<>(); // list containing end of word

   public static class tNode implements Comparable<tNode> { // tree class
      char letter;
      tNode parent;
      int count;
      List<tNode> children;
      boolean endOfWord = false;

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

      
      public static void addGuesses(tNode c, int max) {
         // grabs entire children of node and runs recursive function
         for (tNode e : c.getChildren()) {
            // base case, if word is true, go up to root node and get characters
            if (e.getEndOfWord() == true && getRank(e) < max) {
               wordNodes.add(e); 

               Collections.sort(wordNodes, Collections.reverseOrder());

            } else {
               addGuesses(e, max);
            }
			
         }
         
		  // adds the top three choices to the guess array
         for(int i = 0; i < wordNodes.size(); i++) {
            if (i >= 3) {
               break;
		      } else {
               guesses[i] = getWord(wordNodes.get(i));
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

      /* grabs the child */
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
      // increments the count if we get a good guess
      public static void incremCount(tNode parent, String word) {
         for (int a = 0; a < word.length(); a++) {
            parent = parent.getChild(word.charAt(a));
         }
         if ((parent.getEndOfWord() == true) && (parent.getLetter() == word.charAt(word.length() - 1))) {
            parent.count++;
         }
      }

      // adds characters to the trie
      public static void addTrie(String word) {
         tNode parent = root; // sets the parent equal to the root node
        
         for (int i = 0; i < word.length(); i++) { // go through each character of the word
            char current = word.charAt(i);
            if(tNode.hasChild(parent, current)) { // check if the parent has a child, if it does we get the child
               parent = parent.getChild(current);
            } else { // if not we add child
               parent = tNode.appendChild(parent, current, 0);  
            }
         }
         parent.count++; // mark the occurences of word
         parent.setEndOfWord(true); // mark the ending of the word

      }
   } /* end of tNode class */

   // initialize SmartWord with a file of English words
   public SmartWord(String wordFile) throws IOException {
      File wordF = new File("swords.txt");
      Scanner sc = new Scanner(wordF);

      /* loops until end of the file */
      while(sc.hasNextLine()) {
         String line = sc.nextLine();
         tNode.addTrie(line);
         
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
         tNode.addTrie(w);
      }
      sc2.close(); // closes scanner
   }

   public String[] guess(char letter,  int letterPosition, int wordPosition) {
      // reset guesses each time 
      guesses = new String[3];
      if(letterPosition == 0) {
         current = root;
      }
      if(tNode.hasChild(current, letter)){
         current = current.getChild(letter);
         int maxdepth = tNode.getRank(current) + 8;

         tNode.addGuesses(current, maxdepth);

         wordNodes.clear();
      }

      return guesses;
   }



   public void feedback(boolean isCorrectGuess, String correctWord) {
      if((isCorrectGuess == false) && (correctWord == null)) {
         return;
      }

      else if((isCorrectGuess == false) && (correctWord != null)) {
         tNode.addTrie(correctWord);
      }

      else if((isCorrectGuess) && (correctWord != null)) {
         tNode.incremCount(root, correctWord);
      }
   }
}
