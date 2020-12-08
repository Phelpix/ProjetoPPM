import IO._
import javax.lang.model.element.NestingKind
import sun.security.util.Password

import scala.io.StdIn.readLine

object  ExpenseTrackerUtils {




 def RegisterUser(username: String, email: String, lines: List[Array[String]]): Boolean = {
  lines match {
   case x :: t => if (x(0) == username) {
    usernameRegistered()
    true
   } else if (x(2) == email) {
    emailRegistered()
    true
   } else RegisterUser(username, email, t)

   case x :: Nil => if (x(0) == username) {
    usernameRegistered()
    true
   } else if (x(2) == email) {
    emailRegistered()
    true
   } else false
   case Nil => false
  }
 }


 def searchUser(email: String, password: String, lines: List[Array[String]]):Array[String]={
  (lines foldRight Array("","",""))(( v:Array[String], lines) => if(v(2) == email && v(1) == password) v else lines)
 }

 def searchUser2(email:String, password: String, lines: List[Array[String ]]): Option[String]={
   lines match{
    case Nil => None
    case x::xs => if( x(1) == password && x(2) == email) Some(x(0)) else searchUser2(email, password,xs)
   }
  }


}