/* 

  Authors (group members): Garrett Gmeiner, Tyler Ton, and David Tran
  Email addresses of group members: ggmeiner2021@my.fit.edu, tton2021@my.fit.edu, dtran2021@my.fit.edu
  Group name: 34GTD

  Course: CSE 2010
  Section: 34

  Description of the overall algorithm:


*/
import java.nio.file.*;
import java.io.*;
import java.util.*;
public class SmartWord {

   String[] guesses = new String[3];  // 3 guesses from SmartWord

   public static tNode root; // root of tree

   public static WordList w;

   public static class tNode { // tree class
      char letter;
      tNode parent;
      int count;
      List<tNode> children;
      boolean endOfWord;

      public tNode (char letter, int count) { // constructor for tree node
        this.count = count;
        this.letter = letter;
        this.children = new ArrayList<tNode>();
      }
 

       public static void appendChild(tNode p, char c, int count) { // adds a child to tree
         tNode t = new tNode(c, count); // creates new tree node
         t.setParent(p); // sets parent of node to p
         
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

         if (w.contains(possibleWord)) {
            t.setEndOfWord(true);
         } else {
            t.setEndOfWord(false);
         }
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

   } /* end of tNode class */



   public static class WordList {

      /* initializes hashSet of Strings named wordSet */
      private Set<String> wordSet = new HashSet<>();;

      public WordList() throws IOException { // creates list of words

         /* creates Path p to access words.txt */
         Path p = Paths.get("words.txt");

         byte[] b = Files.readAllBytes(p); // reads the bytes in an makes them into an array of bytes
         String contents = new String(b, "UTF-8"); // string contentes is a big string with all the words
         String[] words = contents.split("\n"); // splits contentes by new line and puts each string into an array words

         /* adds each word in words to the hashSet */
         Collections.addAll(wordSet, words);
      }

      /* if word is in wordSet, returns true. If not, returns false */
      public boolean contains(String word) {
         return wordSet.contains(word);
      }

   } /* end of WordList class */




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

      /* initializes hashSet of Strings named oldWords */
      Set<String> oldWords = new HashSet<>();

      /* creates Path p to access old words */
      Path path = Paths.get(args[1]);

      byte[] by = Files.readAllBytes(path); // reads the bytes in an makes them into an array of bytes
      String contents = new String(by, "UTF-8"); // string contentes is a big string with all the words
      String[] wor = contents.split("\n"); // splits contentes by new line and puts each string into an array wor

      /* adds each word in words to the hashSet */
      Collections.addAll(oldWords, wor);


      File wordF = new File(args[0]);
      File oldF = new File(args[1]);

      Scanner sc = new Scanner(wordF);
      Scanner sc2 = new Scanner(oldF);

      w = new WordList(); // creates & stores the wordList
      root = new tNode('*', 0); // creates the root tNode

      while(sc.hasNextLine()) {
        String line = sc.nextLine();
        char [] spiltChar = new char[line.length()];

        for(int i = 0; i < line.length(); i++) {
            spiltChar[i] = line.charAt(i);
        }

      }

   }

}
