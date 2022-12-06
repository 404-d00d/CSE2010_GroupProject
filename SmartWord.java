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

   public static tNode root = new tNode("*", 0); // creates the root tNode; // root of tree

   public static tNode current;

   public static ArrayList<tNode> wordNodes = new ArrayList<>();

 
   public static class tNode implements Comparable<tNode> { // tree class
      String letter;
      tNode parent;
      int count;
      List<tNode> children;
      boolean endOfWord;

         // Need to fix this
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

      public tNode (String letter, int count) { // constructor for tree node
        this.count = count;
        this.letter = letter;
        this.children = new ArrayList<tNode>();
        this.endOfWord = false;
      }

       public static tNode appendChild(tNode p, String c, int count) { // adds a child to tree
         tNode t = new tNode(c, count); // creates new tree node
         t.setParent(p); // sets parent of node to p
         t.setEndOfWord(false);
         
         /* alphabetically adds to list of children */
         int index = 0;
         for (tNode n : t.getParent().children) {
            if (n.getLetter().charAt(0) >= t.getLetter().charAt(0)) {
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
      public String getLetter() { // returns the letter of a particular tree node
        return this.letter;
      }
      public void setLetter(String letter) { // sets the letter of a particular tree node to a string named letter
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
      public void setChildren (List<tNode> children) {
         this.children = children;  
      }
      public void setEndOfWord(boolean endOfWord) { // sets the character as making the end of a word true or false
         this.endOfWord = endOfWord;
      }
      public boolean getEndOfWord() { // returns true if character forms the end of a word,false otherwise
         return this.endOfWord;
      }


      /* using PreOrder Traversal, compresses the tree */
      public static void compressTree(tNode n) {
         if (n.getChildren().size() == 1) {
            n.setLetter(n.getLetter() + n.getChildren().get(0).getLetter()); // combines letter Strings Strings
            n.setChildren(n.getChildren().get(0).getChildren()); // sets n's children to n's child's children
         }
         
         for (tNode tn : n.getChildren()) {
            compressTree(tn);
         }
      }

      
      public static void addGuesses(tNode c, int max) {
         // grabs entire children of node and runs recursive function
         for (tNode e : c.getChildren()) {
            // base case, if word is true, go up to root node and get characters
            if (e.getEndOfWord() == true && getRank(e) < max) {
//System.out.println("WORD ADDED: " + getWord(e) + "  " + e.getCount());
               wordNodes.add(e); 

               Collections.sort(wordNodes, Collections.reverseOrder());

               
//System.out.println("SIZE: " +wordNodes.size());
            } else {
               addGuesses(e, max);
            }
			
         }

		 int index = 0;
         for(tNode tn : wordNodes) {
            if (index >= 3) {
               break;
		    } else {
               guesses[index] = getWord(wordNodes.get(index));
			}
			index++;
		 }
		 
		 //guesses[1] = getWord(wordNodes.get(1));
		 //guesses[2] = getWord(wordNodes.get(2));
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
      public static Boolean hasChild(tNode p, String c) {
         
         for(tNode child : p.getChildren()) {
            if(child.letter.equals(c)) {
               return true;
            }
         }
         return false;
      }



      /* grabs the child */
      public tNode getChild(String c) {
         for(tNode child : children) {
            if(child.getLetter().equals(c)) {
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

      /*public static void incremCount(tNode parent, String word) {
         for (int a = 0; a < word.length(); a++) {
            parent = parent.getChild(word.charAt(a));
         }
         if ((parent.getEndOfWord() == true) && (parent.getLetter().charAt(0) == word.charAt(word.length() - 1))) {
            parent.count++;
         }
      }*/
   } /* end of tNode class */



   // initialize SmartWord with a file of English words
   public SmartWord(String wordFile) throws IOException {
      File wordF = new File("words1.txt");
      Scanner sc = new Scanner(wordF);

      /* loops until end of the file */
      while(sc.hasNextLine()) {
         String line = sc.nextLine();
         
         tNode parent = root; // sets the parent equal to the root node
           
            for (int i = 0; i < line.length(); i++) { // go through each character of the word
               String current = Character.toString(line.charAt(i));
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
            String current = Character.toString(w.charAt(i));

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
      tNode.compressTree(root);
      sc2.close(); // closes scanner
   }



   // based on a letter typed in by the user, return 3 word guesses in an array
   // letter: letter typed in by the user
   // letterPosition:  position of the letter in the word, starts from 0
   // wordPosition: position of the word in a message, starts from 0
   public String[] guess(char letter,  int letterPosition, int wordPosition) {
      String newLetter = Character.toString(letter);
      guesses = new String[3];
      if(letterPosition == 0) {
         current = root;
      }
      if(tNode.hasChild(current, newLetter)){
         current = current.getChild(newLetter);
         int maxdepth = tNode.getRank(current) + 7;
//System.out.println("LETTER: " + letter + ",  letterPosition: " + letterPosition + ",  wordPosition: " + wordPosition);
//System.out.println("PATH: " + tNode.getWord(current));
         tNode.addGuesses(current, maxdepth);


/*for (tNode ghf : wordNodes) {
	System.out.print(tNode.getWord(ghf) + ", " + ghf.getCount() + ";  ");
}
System.out.println();*/

         wordNodes.clear();
//System.out.println("CLEARED!");
         //tNode.createGuesses();
      }
//System.out.println("GUESSES: " + guesses[0] + ", " +  guesses[1] + ", " + guesses[2]);
      return guesses;
   }



   public void feedback(boolean isCorrectGuess, String correctWord) {
      if((isCorrectGuess == false) && (correctWord == null)) {
         return;
      }

      else if((isCorrectGuess == false) && (correctWord != null)) {
         //badGuess.add(correctWord);
         tNode parent = root; // sets the parent equal to the root node
        
         for (int i = 0; i < correctWord.length(); i++) { // go through each character of the word
            String current = Character.toString(correctWord.charAt(i));
            if(tNode.hasChild(parent, current)) { // check if the parent has a child, if it does we get the child
               parent = parent.getChild(current);
            } else { // if not we add child
               parent = tNode.appendChild(parent, current, 0);  
            }
         }
         parent.count++; // mark the occurences of word
         parent.setEndOfWord(true); // mark the ending of the word
      }

      else if((isCorrectGuess) && (correctWord != null)) {
         //tNode.incremCount(root, correctWord);
      }
 
   }
}
