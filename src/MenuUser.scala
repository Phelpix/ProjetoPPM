import java.text.SimpleDateFormat
import java.io.IOException

import MenuUserUtils._
import CSVFileReader._
import IO._

import scala.io.StdIn.readLine
import java.util.Calendar


import scala.::
case class UserApp(name: String,email:String,password: String, balance:Double, depositList:LazyList[UserList], expenseList: LazyList[UserList], userCategories: List[String],monthlySavings: List[(String,Double)])

object MenuUser extends App{


  def userLoop(user: UserApp): Unit ={


    showPrompt(user)

    val userInput = getUserInput()
    val function = transaction(user,_:LazyList[UserList],_:Int)
    userInput match {
      case "1" => {
        val newUserApp:UserApp = function(user.depositList,1)
        actionFinished(newUserApp.balance)
        Thread.sleep(3000)
        userLoop(newUserApp)
      }
      case "2" => {
        val newUserApp:UserApp = function(user.expenseList,2)
        actionFinished(newUserApp.balance)
        Thread.sleep(3000)
        userLoop(newUserApp)
      }
        //interessa
      case "3"=>{
        balance(user.balance)
        Thread.sleep(3000)
        userLoop(user)
      }
        //interessa feito
      case "4" =>{
        higherfunction(user, showHistory)
        Thread.sleep(3000)
        userLoop(user)
     }

      case "5" =>{
        category()
        val categoria = getUserInput()
        val newUserApp = addCategory(user,categoria)
        userLoop(newUserApp)
      }
        //interessa feito
      case "6"=>{
        higherfunction(user, listTotal)
        Thread.sleep(3000)
        userLoop(user)
      }
      case "7"=>{
        val newUser = profile(user)
        userLoop(newUser)
      }

      case "8"=>{
       val newUser=changeThings(user)
       userLoop(newUser)
      }
      case "9"=>{
       for (element <- user.monthlySavings){
        println("TUPLO: "+ element._1 + ", " + element._2)
       }
       userLoop(user)
      }

      case "0" => saveUser(user)
    }

  }
}
