import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
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
  private var fecharButton:  Button =_
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



  var tempUser: UserApp= new UserApp("","","",0.0,LazyList[UserList](),LazyList[UserList](),List[String](),List[(String,Double)](),List[categorySavings](),new PlanSoft(10,List[categorySavings]()))

  var listDep: LazyList[UserList] = new LazyList[Deposit]()
  var listExp: LazyList[UserList] = new LazyList[Expense]()

  def setTempUser(tempUser: UserApp): Unit ={
    this.tempUser=tempUser
  }

  /*def getString(i: Int):String={

  }
  */


  def setCategorias(list:List[String]): Unit = {
    list match {
      case Nil =>
      case x :: t => {
        categoriasCB.getItems.add(x)
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
    if(depositoRB.isSelected){
      val list:LazyList[UserList] = tempUser.depositList.filter(x =>{x.date==(mesCB.getSelectionModel.getSelectedItem+"-"+anoCB.getSelectionModel.getSelectedItem)})
      var comeOn: ObservableList[String] = FXCollections.observableArrayList()
      for(aux <- list) {
        var textoDeposito: UserList = showHistory(aux, categoriasCB.getSelectionModel.getSelectedItem)
        if(textoDeposito.category !="" ) {
          comeOn.add(textoDeposito.id+" - "+textoDeposito.value+" - "+textoDeposito.description)
          listDep = textoDeposito#::listDep
        }
      }
      val nova: ListView[String] = new ListView[String](comeOn)
      histTA.setItems(comeOn)
    }else if(compraRB.isSelected){
      val list:LazyList[UserList] = tempUser.expenseList.filter(x => x.date==(mesCB.getSelectionModel.getSelectedItem+"-"+anoCB.getSelectionModel.getSelectedItem))
      var comeOn: ObservableList[String] = FXCollections.observableArrayList()
      for(aux <- list) {
        var textoCompra: UserList = showHistory(aux, categoriasCB.getSelectionModel.getSelectedItem)
        if(textoCompra.category != "") {
          comeOn.add(textoCompra.id+" - "+textoCompra.value+" - "+ textoCompra.description)
          listExp= textoCompra#::listExp
        }
      }
      val nova: ListView[String] = new ListView[String](comeOn)
      histTA.setItems(comeOn)
    }
  }

  def onHistoricoButton:Unit={
    GridPane2.setVisible(false)
    GridPane1.setVisible(true)
    historico.setVisible(false)
    altera.setVisible(false)
    okButton.setVisible(true)
    fecharButton.setVisible(true)
  }

  def onAlterarButtonClicked():Unit={
      GridPane1.setVisible(false)
      GridPane2.setVisible(true)
      historico.setVisible(true)
      altera.setVisible(true)
      okButton.setVisible(false)
      fecharButton.setVisible(false)
  }

  def onAlteraValorClicked():Unit={
    if(alterarValue.isSelected)
      valueText.setText("")
      alterarValue.setSelected(true)
      alterarCat.setSelected(false)
      alterarDesc.setSelected(false)
    //  valueText.setText(histTA.getSelectionModel().getSelectedItem.value.toString)

  }
  def onAlteraCategoriaClicked():Unit={
    if(!alterarCat.isSelected)
      valueText.setText("")
      alterarCat.setSelected(true)
      alterarValue.setSelected(false)
      alterarDesc.setSelected(false)
     // valueText.setText(getString(histTA.getSelectionModel().getSelectedIndex()))

  }
  def onAlteraDescricaoClicked():Unit={
    if(!alterarDesc.isSelected())
      valueText.setText("")
      alterarCat.setSelected(false)
      alterarValue.setSelected(false)
    //  valueText.setText(histTA.getSelectionModel().getSelectedItem.description)
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



 def changeDeposit(userApp: UserApp,list: LazyList[UserList],toChangeList:LazyList[UserList], index:Int, option:String):LazyList[UserList]={
    option match {
      case "1"=>{
        val newDeposit = toChangeList(index).setValue(toChangeList(index), valueText.getText().toDouble)
        val indice = list.indexOf(toChangeList(index))
        println(indice)
        val newlist = list.patch(indice,Seq(newDeposit),1)
        newlist
      } //case 1
      case "2"=>{
        val newDeposit = toChangeList(index).setDescription(toChangeList(index), valueText.getText())
        val indice = list.indexOf(toChangeList(index))
        val newlist =list.patch(indice,Seq(newDeposit),1)
        newlist
      } //case 2
      case "3"=>{
        val newDeposit = toChangeList(index).setCategory(toChangeList(index), valueText.getText())
        val indice = list.indexOf(toChangeList(index))
        val newlist =list.patch(indice,Seq(newDeposit),1)
        newlist
      } //case 2
    }
  }

  def printDeposits(user: UserApp,transactionList:LazyList[UserList],toChangeList:LazyList[UserList],input:Int): LazyList[UserList] ={
    if(alterarValue.isSelected()) {
      val list = changeDeposit(user,transactionList, toChangeList, input - 1,"1")
      list
    }else if(alterarDesc.isSelected()){
      val list = changeDeposit(user,transactionList, toChangeList, input - 1,"2")
      list
    }else{
      val list = changeDeposit(user,transactionList, toChangeList, input - 1,"3")
      list
    }
  }


  def changeThings(user: UserApp, input:String): UserApp ={
    input match {
      case "1"=>{
        val newList =printDeposits(user,user.depositList,listDep,histTA.getSelectionModel().getSelectedIndex())
        val newUser = user.copy(name = user.name,user.email,user.password, balance = user.balance, newList, expenseList = user.expenseList, user.userCategories,user.monthlySavings)
        newUser
      }

      case "2"=>{
        val newList =printDeposits(user,user.expenseList, listExp,histTA.getSelectionModel().getSelectedIndex())
        val newUser = user.copy(name = user.name,user.email,user.password, balance = user.balance, user.depositList, newList, user.userCategories, user.monthlySavings, user.catSavList)
        newUser
      }
    }
  }

  def onFecharClicked: Unit ={
    fecharButton.getScene().getWindow.hide()
  }

}
