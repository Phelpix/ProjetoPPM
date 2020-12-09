import scala.io.StdIn.readLine

object IO {

  //User IO
  def showPrompt(user:UserApp): Unit ={println("########  " +user.name+"  ##########");println("\n escolha o número:");println("\n1-depositar");println("2-compra");println("3-balanço");println("4-historico");println("5-adicionar categoria");println("6-mostrar totais");println("7-mostrar Perfil");println("8-alterar transacao");println(" 0-Exit")}

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

  def showMonth():Unit = {println("\n1 - Janeiro\n2 - Fevereiro\n3 - Marco\n4 - Abril\n5 - Maio\n6 - Junho \n7 - Julho\n8 - Agosto\n9 - Setembro\n10 - Outubro\n11 - Novembro\n12 - Dezembro")}

  def showYear(): Unit = {println("Indique o ano que quer aceder")}

  def printValue(any: Any):Unit={ println(any)}

  //Alteracoes no perfil
  def changeDepositPrint():Unit={println("Qual quer alterar?")}

  def changeThingsPrint():Unit ={println("O que deseja alterar?");println("1- Depositos");println("2- Despesas")}

  def newCategoryPrint():Unit ={print("Insira nova categoria: ")}

  def newDescriptionPrint():Unit={  print("Insira nova descrição: ")}

  def newValuePrint():Unit={ print("Insira novo valor: ")}

  def changeTransaction():Unit={ println("O que quer alterar?");println("1- Valor");println("2- Descrição");println("3- Categoria")}

  def newPasswordPrint():Unit={print("Nova password: ")}

  def newEmailPrint():Unit = {print("Novo email: ")}

  def newUsernamePrint():Unit={print("Novo username: ")}

  def showSettings(user:UserApp): Unit ={println("#### PERFIL ####");println("Name: "+ user.name);println("Email: "+user.email);println("###############");println("#### DEFINIÇÕES ####");println("1- Mudar username");println("2- Mudar email");println("3- Mudar password")}
}
