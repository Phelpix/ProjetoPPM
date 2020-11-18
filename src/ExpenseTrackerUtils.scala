import javax.lang.model.element.NestingKind
import sun.security.util.Password

import scala.io.StdIn.readLine

object  ExpenseTrackerUtils {

 def showMenu(): Unit = {
  println("1-Login")
  println("2-Registo")
 }

 def getUserInput(): String = readLine.trim

 def RegisterUser(username: String, email: String, lines: List[Array[String]]): Boolean = {
  lines match {
   case x :: t => if (x(0) == username) {
    println("Username ja registado")
    true
   } else if (x(2) == email) {
    println("Email ja registado")
    true
   } else RegisterUser(username, email, t)

   case x :: Nil => if (x(0) == username) {
    println("Username ja registado")
    true
   } else if (x(2) == email) {
    println("Email ja registado")
    true
   } else false
   case Nil => false
  }
 }

 def loginUser(email: String, password: String, lines: List[Array[String]]): String = {
  lines match {
   case x :: t => if (x(2) == email && x(1) == password) x(0) else loginUser(email, password, t)
   case x:: Nil => if (x(2) == email && x(1) == password) x(0) else "Voce nao se econtra registado"
   case Nil=> ""
  }
 }

}