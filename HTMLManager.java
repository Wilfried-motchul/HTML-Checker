// HW Core Topics: queues, stacks
//
// This program will be creating an HTML Checker program that will check the syntax of 
//HTML tags. It is similar, but different, from the Syntax Checker which checked matching 
//opening and closing symbol placement.


import java.util.*;

public class HTMLManager {
   private Queue<HTMLTag> tags;
   public HTMLManager(Queue<HTMLTag>html){
      tags = new LinkedList<>();
      if(tags == null){
         throw new IllegalArgumentException();
      }
      this.tags = html;
   }
   public Queue<HTMLTag> getTags(){
      return this.tags;
   }
   public String toString(){
      //creating a string builder where we can store our tags
      StringBuilder whateverName = new StringBuilder();
      // go to all the tags in the queue and store them in the  string builder 
      // but still leaving them in the queue.
      for(int i = 0 ;i < this.tags.size(); i++){
         HTMLTag youNameIt = this.tags.remove();
         // to put all the tag in one line 
         whateverName.append(youNameIt.toString().trim());
         this.tags.add(youNameIt);
      } 
      return whateverName.toString();
        
   }
   public void fixHTML(){
      Stack <HTMLTag> stack = new Stack<>();
      // remeber the size of the queue
      int size = this.tags.size();
      // going through the whole elemet of the queue
      for(int i = 0 ; i < size; i++){
         HTMLTag current = this.tags.remove();
         // checking if the element from the queue is an openTag
         if(current.isOpening()){
         // add that tag in the stack
            stack.push(current);
         // add that tag back in the queue
            this.tags.add(current);
         }
         // checking if the elemenet from the queue is an self closing tag
         else if(current.isSelfClosing()){
         // add that tag back in the queue
            this.tags.add(current);   
         }
         // checking if it's a closing tag
         else{
         // checking if the stack is empty
            if(!stack.isEmpty()){
            // get the opening tag from the stack
               HTMLTag kickOut = stack.pop();
           //check if that opening tag from the stack match with  the colsing tag from the queue   
               if(current.matches(kickOut)){
               // add that closing tag back in the queue
                  this.tags.add(current);
               }
               // if that closing tag does not match with the opening tag from the stack
               else{
               //creat a new closing that that will match with the opening tag that we pop out of the stack
                  HTMLTag val = kickOut.getMatching();
               // adding that new closing that in the queue
                  this.tags.add(val);
               }
            }      
         }
      }
      // in case the stack is still not empty, after going through the first original size of th equeue
      while(!stack.isEmpty()){
      // get the opening tag from the stack
         HTMLTag kickOut = stack.pop();
        //creat a new closing that that will match with the opening tag that we pop out of the stack 
         HTMLTag val = kickOut.getMatching();
         // adding that new closing that in the queue
         this.tags.add(val);
      }
   }
}


/*  ===============================
 Processing tests/test3.html...
 ===============================
 HTML: <br /></p></p>
 Checking HTML for errors...
 HTML after fix: <br />
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test2.html...
 ===============================
 HTML: <a><a><a></a>
 Checking HTML for errors...
 HTML after fix: <a><a><a></a></a></a>
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test5.html...
 ===============================
 HTML: <div><h1></h1><div><img /><p><br /><br /><br /></div></div></table>
 Checking HTML for errors...
 HTML after fix: <div><h1></h1><div><img /><p><br /><br /><br /></p></div></div>
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test4.html...
 ===============================
 HTML: <div><div><ul><li></li><li></li><li></ul></div>
 Checking HTML for errors...
 HTML after fix: <div><div><ul><li></li><li></li><li></li></ul></div></div>
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test1.html...
 ===============================
 HTML: <b><i><br /></b></i>
 Checking HTML for errors...
 HTML after fix: <b><i><br /></i></b>
 ----> Result matches Expected Output!
 
 ===============================
         All tests passed!
 ===============================
 */
