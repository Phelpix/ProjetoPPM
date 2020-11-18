import MenuUtils._

case class UserApp(name: String, balance:Double, depositList: List[(Double,String, String, Any)], expenseList: List[(Double,String, String, Any)])

object Menu extends App{

  val x = new UserApp("Hugo",0,Nil,Nil)

  mainLoop(x)

  def mainLoop(user: UserApp): Unit ={


    showPrompt()

    val userInput = getUserInput()

    userInput match {
      case "1" => {
        val newUser:UserApp = income(user)
        mainLoop(newUser)
      }
      case "2" => {
        val newUser:UserApp =expense(user)
        mainLoop(newUser)
      }
      case "3"=>{
        println("\n\n\n\n **** O VALOR DA SUA BALANCA E " + user.balance +" ****\n")
        Thread.sleep(3000)
        mainLoop(user)
      }
      case "4" =>{
        filters(user)
        Thread.sleep(3000)
        mainLoop(user)
      }
      case "0"=> println("######GOODBYE#######")
      case _ => mainLoop(x)

    }


  }
}
