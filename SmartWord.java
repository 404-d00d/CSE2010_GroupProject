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

   public static ArrayList<tNode> wordNodes = new ArrayList<>();
   public static ArrayList<String> badGuess = new ArrayList<>(); // list of bag guesses
   public static ArrayList<String> goodGuess = new ArrayList<>(); // list of good guesses
	

   public static class tNode implements Comparable<tNode>{ // tree class
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


      @Override
      public int compareTo(tNode t) {
         if (this.getCount() > t.getCount()) {
            return 1;
         } else if (this.getCount() < t.getCount()) {
            return -1;
         } 
         return 0;
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


      /* Using post-order traversal, finds each leaf and adds to guesses if it is used frequently */
      public static void addGuesses(tNode n) {
         for (tNode t : n.getChildren()) {
            addGuesses(t);
         }

         /* if leaf node */
         if (n.getEndOfWord() == true) {
            int i = 0;

            /* adds n into wordNodes in descending order */
            for (tNode tn : wordNodes) {
               if (n.compareTo(tn) == 1) { // if n's count is >= tn's count, adds it in at index i
                  wordNodes.add (i, n);
                  break;
               } else {
                  i++;
               }
            }
            if (!wordNodes.contains(n) || wordNodes.isEmpty()) { // if n has not been added, adds it to the end
               wordNodes.add(n); // adds n to wordNodes
            }
         }

         /* adds the top 3 in wordNodes to guesses */
         for (int i = 0; i < guesses.length; i++) {
            if(i >= wordNodes.size()) {
               break;

            }
            guesses[i] = getWord(wordNodes.get(i));
         }
         /*for (tNode p : wordNodes) {
            System.out.print(getWord(p) + ", " + p.getCount() + "; ");
         }
         System.out.println();
         System.out.println("WordNodes SIZE: " + wordNodes.size());*/
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

      // System.out.println(root.toString());
      Queue<tNode> queue = new LinkedList<>();
      queue.add(root); // adds root to queue
      while (!queue.isEmpty()) {
         int num = queue.size(); // size of queue
         while (num > 0) {
            tNode t = queue.peek(); // t is set to first item in queue
            queue.remove();         // removes an item from queue and adds it to str

            /* if t is right level and right letter, searches subtree for guesses*/
            if (tNode.getRank(t)-1 == letterPosition && t.getLetter() == letter) {
               tNode.addGuesses(t);
            }

            for (tNode c : t.children) { // adds each child of t into the queue
               queue.add(c);
			}

            /* if all at level letterPosition have been searched through, stops the search */
            if (tNode.getRank(t)-1 > letterPosition) {
               queue.clear(); // clears queue
               break;
            }
            num--; 
            wordNodes.clear();
         }
      }
      //System.out.println("LETTER: " + letter + "   letterPOS " + letterPosition + "   wordPOS " + wordPosition);
      //System.out.println("1 " + guesses[0] + " 2 " + guesses[1] + " 3 " + guesses[2]);
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
         return;
      }

      else if((isCorrectGuess == false) && (correctWord != null)) {
         badGuess.add(correctWord);
      }

      else if((isCorrectGuess) && (correctWord != null)) {
         goodGuess.add(correctWord);
      }
  
   }
}
