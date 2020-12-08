import scala.io.StdIn.readLine

object IO {

  //User IO
  def showPrompt(user:UserApp): Unit ={println("########" +user.name+"##########");println("\n escolha o número:");println("\n1-depositar");println("2-compra");println("3-balanço");println("4-historico");println("5-adicionar categoria");println("6-mostrar totais");println("7-mostrar Perfil");println(" 0-Exit")}

  def getUserInput(): String = readLine

  def muchTransaction(userList: LazyList[UserList]): Unit=
    print("\n\n\n\n **** QUAL É O VALOR DA TRANSAÇÃO? ****\nValor:")



  def description() : Unit={print("\n DESCRIÇAO:")}

  def actionFinished(newBalance :Double): Unit={println("\n\n\n\n DEPOSITO REALIZADO COM SUCESSO \n\n TEM AGORA " + newBalance + " NA SUA CONTA")}

  def error() :Unit={println("Insira um valor válido")}

  def muchExpense() :Unit={println("\n\n\n\n **** QUAL FOI O VALOR DA SUA COMPRA? ****\n")}


  //ExpenseTracker IO
  def email():Unit={print("email:")}

  def password():Unit={print("Password:")}

  def loginError():Unit={println("Login nao dado")}

  def username():Unit={print("Username:")}

  def closeApp():Unit={println("######GOODBYE#######")}

  //MenuUser

  def category():Unit={println("Qual a categoria que quer adicionar?")}

  def balance(balance:Double): Unit={println("\n\n\n\n **** O VALOR DA SUA BALANCA E " + balance +" ****\n")}

  //ExpenseTracekrUtils
  def showMenu(): Unit = {println("1-Login");println("2-Registo")}

  def usernameRegistered():Unit ={ println("Username ja registado")}

  def emailRegistered() :Unit = {println("Email ja registado")}

  //MenuUserUtils
  def showOptions(): Unit ={println("\n escolha o número:");println(" 1-depositos");println(" 2-compras")}

  def semFiltro():Unit=println("0-Nao aplicar filtro!")

  def whatCategory(): Unit =  println("########### INDIQUE A CATEGORIA ###########")



  def printValue(any: Any):Unit= println(any)


}
