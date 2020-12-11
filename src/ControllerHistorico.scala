import java.text.SimpleDateFormat
import java.util.Calendar

import IO.{changeThingsPrint, changeTransaction, getUserInput, newCategoryPrint, newDescriptionPrint, newValuePrint}
import MenuUserUtils.{defineCategory, listTotal, showHistory}
import javafx.beans.Observable
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ChoiceBox, RadioButton, TextArea}
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Scene
import javafx.scene.control._
import javafx.scene.control.cell.ComboBoxListCell
import javafx.scene.layout.{GridPane, StackPane}
import javafx.stage.Stage

import scala.collection.immutable
import scala.collection.immutable.Nil.:::
import scala.math.Fractional.Implicits.infixFractionalOps

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

  var listDep: LazyList[Deposit] = new LazyList[Deposit]()
  var listExp: LazyList[Expense] = new LazyList[Expense]()

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
          comeOn.add(textoDeposito.value+" - "+textoDeposito.description)
        }
      }
      val nova: ListView[String] = new ListView[String](comeOn)
      histTA.setItems(comeOn)
    }else if(compraRB.isSelected){
      val list:LazyList[UserList] = tempUser.expenseList.filter(x => x.date==(mesCB.getSelectionModel.getSelectedItem+"-"+anoCB.getSelectionModel.getSelectedItem))
      var comeOn: ObservableList[String] = FXCollections.observableArrayList()
      for(aux <- list) {
        var textoCompra: UserList = showHistory(aux, categoriasCB.getSelectionModel.getSelectedItem)
        if(textoCompra.category != "")
          comeOn.add(textoCompra.value+" - "+ textoCompra.description)
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
  }

  def onAlterarButtonClicked():Unit={
      GridPane1.setVisible(false)
      GridPane2.setVisible(true)
      historico.setVisible(true)
      altera.setVisible(true)
      okButton.setVisible(false)
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
        val newlist = toChangeList.patch(index,Seq(newDeposit),1)
        newlist
      } //case 1
      case "2"=>{
        val newDeposit = toChangeList(index).setDescription(toChangeList(index), valueText.getText())
        val newlist =list.indexOf().patch(index,Seq(newDeposit),1)
        newlist
      } //case 2
      case "3"=>{
        val newDeposit = toChangeList(index).setCategory(toChangeList(index), valueText.getText())
        val newlist =toChangeList.patch(index,Seq(newDeposit),1)
        newlist
      } //case 2
    }
  }

  def printDeposits(user: UserApp,toChangeList:LazyList[UserList]): LazyList[UserList] ={
    println("Qual quer alterar?")
    //showHistory(toChangeList,"0")
    val input = getUserInput().toInt
    val list = changeDeposit(user,toChangeList,input-1)
    val total = listTotal(list,"0")
    println("NEW TOTAL "+ total )
    list
  }


  def changeThings(user: UserApp): UserApp ={
    changeThingsPrint()
    val input = getUserInput()
    input match {
      case "1"=>{
        val format = new SimpleDateFormat("M-y")
        val date: String = format.format(Calendar.getInstance().getTime())
        val list:LazyList[UserList] = user.depositList.filter(x =>{x.date==date})
        val newList =printDeposits(user,list)
        val newUser = user.copy(name = user.name,user.email,user.password, balance = user.balance, newList, expenseList = user.expenseList, user.userCategories,user.monthlySavings)
        newUser
      }

      case "2"=>{
        val format = new SimpleDateFormat("M-y")
        val date: String = format.format(Calendar.getInstance().getTime())
        val list:LazyList[UserList] = user.depositList.filter(x =>{x.date==date})
        val newList =printDeposits(user, list)
        val newUser = user.copy(name = user.name,user.email,user.password, balance = user.balance, user.depositList, newList, user.userCategories, user.monthlySavings, user.catSavList)
        newUser
      }
    }
  }

  def onFecharClicked: Unit ={
    fecharButton.getScene().getWindow.hide()
  }

}
