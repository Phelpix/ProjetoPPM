import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, ChoiceBox, RadioButton, _}
import javafx.scene.layout.GridPane

class ControllerHistorico {

  @FXML
  private var categoriasCB: ChoiceBox[String] = new ChoiceBox[String]()
  @FXML
  private var okButton: Button =_
  @FXML
  private var mesCB: ChoiceBox[String] = new ChoiceBox[String]()
  @FXML
  private var anoCB: ChoiceBox[String] = new ChoiceBox[String]()
  @FXML
  private var histTA: ListView[String] = new ListView[String]()
  @FXML
  private var depositoRB: RadioButton =_
  @FXML
  private var compraRB: RadioButton =_
  @FXML
  private var voltarButton:  Button =_
  @FXML
  private var alterarButton: Button=_
  @FXML
  private var alterarCat: ToggleButton =_
  @FXML
  private var alterarValue:ToggleButton=_
  @FXML
  private var alterarDesc:ToggleButton=_
  @FXML
  private var valueText:TextField=_
  @FXML
  private var altera:Button=_
  @FXML
  private var GridPane1:GridPane=_
  @FXML
  private var GridPane2:GridPane=_
  @FXML
  private var historico:Button=_
  @FXML
  private var alterarCategoriaCB:ChoiceBox[String]=new ChoiceBox[String]()


  var tempUser: UserApp= new UserApp("","","",0.0,LazyList[UserList](),LazyList[UserList](),List[String](),List[(String,Double)](),List[categorySavings](),new PlanSoft(10,List[categorySavings]()))
  var parent:ControllerMenu=new ControllerMenu
  var listDep: LazyList[UserList] = LazyList[Deposit]()
  var listExp: LazyList[UserList] = LazyList[Expense]()
  var comeOn: ObservableList[String] = FXCollections.observableArrayList()

  def setTempUser(tempUser: UserApp): Unit ={
    this.tempUser=tempUser
  }

  def setParent(parent: ControllerMenu): Unit ={
    this.parent=parent
  }

  def getString(split:String,i: Int):String={
    val str = split.split(" - ")
    i match{
      case 1 => str(1)
      case 2=>str(2)
    }
  }



  def setCategorias(list:List[String]): Unit = {
    list match {
      case Nil =>
      case x :: t => {
        categoriasCB.getItems.add(x)
        alterarCategoriaCB.getItems.add(x)
        setCategorias(t)
      }
    }
  }

  def startDates(): Unit ={
    anoCB.getItems().add("2020")
    anoCB.getItems().add("2019")
    anoCB.getItems().add("2018")
    mesCB.getItems().add("1")
    mesCB.getItems().add("2")
    mesCB.getItems().add("3")
    mesCB.getItems().add("4")
    mesCB.getItems().add("5")
    mesCB.getItems().add("6")
    mesCB.getItems().add("7")
    mesCB.getItems().add("8")
    mesCB.getItems().add("9")
    mesCB.getItems().add("10")
    mesCB.getItems().add("11")
    mesCB.getItems().add("12")
  }

  def onOkClicked: Unit ={
    comeOn = FXCollections.observableArrayList()
    println(comeOn)
    listDep  = LazyList[UserList]()
    if(depositoRB.isSelected){
      println("DEPOSITO")
      val list:LazyList[UserList] = tempUser.depositList.filter(x =>{x.date==(mesCB.getSelectionModel.getSelectedItem+"-"+anoCB.getSelectionModel.getSelectedItem)})
      for(aux <- list) {
        var textoDeposito: UserList = showHistory(aux, categoriasCB.getSelectionModel.getSelectedItem)
        if(textoDeposito.category !="" ) {
          comeOn.add(textoDeposito.id+" - "+textoDeposito.value+" - "+textoDeposito.description)
          listDep = textoDeposito#::listDep
        }
      }
      histTA.setItems(comeOn)
    }else if(compraRB.isSelected){
      val list:LazyList[UserList] = tempUser.expenseList.filter(x => x.date==(mesCB.getSelectionModel.getSelectedItem+"-"+anoCB.getSelectionModel.getSelectedItem))
      for(aux <- list) {
        var textoCompra: UserList = showHistory(aux, categoriasCB.getSelectionModel.getSelectedItem)
        if(textoCompra.category != "") {
          comeOn.add(textoCompra.id+" - "+textoCompra.value+" - "+ textoCompra.description)
          listExp= textoCompra#::listExp
        }
      }
      histTA.setItems(comeOn)
    }
  }

  def onHistoricoButton:Unit={
    GridPane2.setVisible(false)
    GridPane1.setVisible(true)
    historico.setVisible(false)
    altera.setVisible(false)
    okButton.setVisible(true)
    voltarButton.setVisible(true)
  }

  def onAlterarButtonClicked():Unit={
      GridPane1.setVisible(false)
      GridPane2.setVisible(true)
      historico.setVisible(true)
      altera.setVisible(true)
      okButton.setVisible(false)
      voltarButton.setVisible(false)
      valueText.setVisible(true)
      alterarCategoriaCB.setVisible(false)

  }

  def onAlteraValorClicked():Unit={
    if(alterarValue.isSelected)
      valueText.setText("")
      alterarValue.setSelected(true)
      alterarCat.setSelected(false)
      alterarDesc.setSelected(false)
      valueText.setVisible(true)
      alterarCategoriaCB.setVisible(false)
      valueText.setText(getString(histTA.getSelectionModel().getSelectedItem.toString,1))

  }
  def onAlteraCategoriaClicked():Unit={
    if(!alterarCat.isSelected)

      alterarCat.setSelected(true)
      alterarValue.setSelected(false)
      alterarDesc.setSelected(false)
      valueText.setVisible(false)
      alterarCategoriaCB.setVisible(true)
     // valueText.setText(getString(histTA.getSelectionModel().getSelectedIndex()))

  }
  def onAlteraDescricaoClicked():Unit={
    if(!alterarDesc.isSelected())
      valueText.setText("")
      alterarCat.setSelected(false)
      alterarValue.setSelected(false)
      valueText.setVisible(true)
      alterarCategoriaCB.setVisible(false)
      valueText.setText(getString(histTA.getSelectionModel().getSelectedItem.toString,2))
  }

  def onDepositSelected():Unit={
    depositoRB.setSelected(true)
    compraRB.setSelected(false)
  }

  def onCompraSelected():Unit={
    compraRB.setSelected(true)
    depositoRB.setSelected(false)
  }

  def elementSelected():Unit={
    if(histTA.getSelectionModel().isSelected(histTA.getSelectionModel().getSelectedIndex()))
      alterarButton.setVisible(true)
  }


  def onalteraClicked():Unit={
    val str = histTA.getSelectionModel.getSelectedItem.split(" - ")
    println("STR:  "+str(0))
    if (depositoRB.isSelected) {
      for (x <- tempUser.depositList) {
        if (x.id == str(0)) {
          val n =tempUser.depositList.indexOf(x)
          val user = changeThings(tempUser,"1", n,x)
          println(tempUser.depositList.indexOf())
          //parent.setUser(user)
          tempUser = user
        }
      }
    }
    else {
      for (x <- tempUser.depositList) {
        if (x.id == str(0)) {
          val n = tempUser.depositList.indexOf(x)
          val user = changeThings(tempUser, "2", n, x)
          //parent.setUser(user)
          tempUser = user
        }
      }
    }
    onOkClicked
  }

  def showHistory(item: UserList, filter:String): UserList = {
    if(item.category == filter){
      item
    }
    else new UserList {
      override val id: String = ""
      override val value: Double = 0.0
      override val category: String = ""
      override val description: String = ""
      override val date: String = ""
    }
  }



  def changeDeposit(userApp: UserApp,list: LazyList[UserList],userList: UserList, index:Int, option:String):LazyList[UserList]={
    option match {
      case "1"=>{
        val newDeposit = userList.setValue(userList, valueText.getText().toDouble)
        val newlist = list.patch(index,Seq(newDeposit),1)
        newlist
      } //case 1
      case "2"=>{
        val newDeposit = userList.setDescription(userList, valueText.getText())
        val newlist = list.patch(index,Seq(newDeposit),1)
        newlist
      } //case 2
      case "3"=>{
        val newDeposit = userList.setCategory(userList, alterarCategoriaCB.getSelectionModel.getSelectedItem)
        val newlist = list.patch(index,Seq(newDeposit),1)
        newlist
      } //case 2
    }
  }

  def printDeposits(user: UserApp,transactionList:LazyList[UserList],input:Int,userList: UserList): LazyList[UserList] ={
    if(alterarValue.isSelected()) {
      val list = changeDeposit(user,transactionList,userList, input ,"1")
      list
    }else if(alterarDesc.isSelected()){
      val list = changeDeposit(user,transactionList,userList, input ,"2")
      list
    }else{
      val list = changeDeposit(user,transactionList,userList, input ,"3")
      list
    }
  }


  def changeThings(user: UserApp, input:String, indice:Int, userList: UserList): UserApp ={
    input match {
      case "1"=>{
        val newList =printDeposits(user,user.depositList,indice,userList)
        println("CHANGE THINGS:   "+ histTA.getSelectionModel().getSelectedIndex())
        val newUser = user.copy(name = user.name,user.email,user.password, balance = user.balance, newList, expenseList = user.expenseList, user.userCategories,user.monthlySavings)
        newUser
      }

      case "2"=>{
        val newList =printDeposits(user,user.expenseList, indice,userList)
        val newUser = user.copy(name = user.name,user.email,user.password, balance = user.balance, user.depositList, newList, user.userCategories, user.monthlySavings, user.catSavList)
        newUser
      }
    }
  }

 def onVoltarClicked: Unit ={
  val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerMenu.fxml"))
  val mainViewRoot: Parent = fxmlLoader.load()
   val controller: ControllerMenu = fxmlLoader.getController
   controller.setUser(tempUser)
  voltarButton.getScene.setRoot(mainViewRoot)
 }

}
