package ExpensesTracker

import scala.io.StdIn.readLine

object MenuUtils {
  def showPrompt(): Unit ={
    print("\n escolha o número:")
    println("\n 1-deposite")
    println("\n 2-compra")
    println("\n 3-balanço")
    println("\n 4-filtro")
  }

  def getUserInput(): String = readLine.trim.toUpperCase

  def filtros(x:UserApp): Unit ={
    showFilters()
    val userFilterInput:String = getUserInput()

    userFilterInput match{
      case "1" =>{
        showElements(x.expenseList)
      }
      case "2" =>{
        showElements(x.depositList)
      }

    }
  }

  def showFilters(): Unit ={
    print("\n escolha o número:")
    println("\n 1-compras")
    println("\n 2-depositos")
    println("\n 3-comida")
    println("\n 4-carro")
  }

  def showElements(list :List[(Double, String)]): Unit ={
    list match{
      case x ::t => {println(x)
        showElements(t)
      }
      case x :: Nil =>{ println(x) }
      case Nil => { println("\n\n\n")}
    }
  }



}
