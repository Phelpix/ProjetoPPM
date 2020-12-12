import java.text.SimpleDateFormat
import java.util
import java.util.Calendar

import MenuUserUtils.roundAt
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, ChoiceBox, ComboBox, Labeled, TextField}
import javafx.stage.Stage
import javax.swing.ButtonModel


class ControllerTransaction {
  @FXML
  private var valorTF: TextField = _
  @FXML
  private var descricaoTF: TextField = _
  @FXML
  private var categoriasCB: ChoiceBox[String] = new ChoiceBox[String]()
  @FXML
  private var confirmarButton: Button = _
  @FXML
  private var voltarButton:Button =_
  @FXML
  private var titleLable: Labeled =_
  @FXML
  private var confirmLabel: Labeled=_
  @FXML
  private var errorLabel: Labeled=_


  var tipo :Int = 0
  var tempUser: UserApp= new UserApp("","","",0.0,LazyList[UserList](),LazyList[UserList](),List[String](),List[(String,Double)](),List[categorySavings](),new PlanSoft(10,List[categorySavings]()))
  var parent:ControllerMenu=new ControllerMenu
  //setter
  def setTipo(tipo:Int): Unit ={
    this.tipo=tipo
  }
  def getUser():UserApp={
    tempUser
  }

  def setParent(parent: ControllerMenu): Unit ={
    this.parent=parent
  }
  def setTempUser(tempUser: UserApp): Unit ={
    this.tempUser=tempUser
  }
  def setCategorias(list:List[String]): Unit = {
    list match {
      case Nil =>
      case x :: t => {
        categoriasCB.getItems.add(x)
        setCategorias(t)
      }
    }

  }

  def setTitle(): Unit ={
    tipo.toString match{
      case "1" => {
        titleLable.setText("Deposito")
      }
      case "2" => {
        titleLable.setText("Compra")
      }
    }

  }

  def onconfirmarButtonClicked():Unit={
    tipo.toString match{
      case "1" => {
        println("ENTREI")
         tempUser = transaction(tempUser,tempUser.depositList,tipo)
      }
      case "2" => {
        println("ENTREI 2")
        tempUser = transaction(tempUser,tempUser.expenseList,tipo)
      }
    }

  }



  //transaçao
  def transaction(user:UserApp, userList: LazyList[UserList], tipo:Int) :UserApp = {
    try {

      val newTransactionValue: Double = roundAt(valorTF.getText().toDouble)
      val newDescription: String = descricaoTF.getText()
      val format = new SimpleDateFormat("M-y")
      val sameDate:String = format.format(Calendar.getInstance().getTime())
      val newFormat = new SimpleDateFormat("d-M-y h-m-s")
      val newID:String = newFormat.format(Calendar.getInstance().getTime())
      if(categoriasCB.getSelectionModel.getSelectedItem==null){
        confirmLabel.setVisible(false)
        errorLabel.setVisible(true)
        throw new Exception
      }
      val tempCategory =categoriasCB.getSelectionModel.getSelectedItem
      val newTransaction: UserList = new UserList {
        override val id: String =newID
        override val value: Double = newTransactionValue
        override val category: String = tempCategory
        override val description: String = newDescription
        override val date: String = sameDate
      }
      val newTransactionList : LazyList[UserList]= newTransaction#::userList
      if (tipo == 1) {
        val newUserApp = {
          user.copy(name = user.name, balance = user.balance + newTransactionValue, depositList = newTransactionList, expenseList = user.expenseList, userCategories = user.userCategories)
        }
        confirmLabel.setText("Deposito efetuado")
        errorLabel.setVisible(false)
        confirmLabel.setVisible(true)
        newUserApp
      }
      else {
        val newUserApp = {
          user.copy(name = user.name, balance = user.balance - newTransactionValue, depositList = user.depositList, expenseList = newTransactionList, userCategories = user.userCategories)
        }
        confirmLabel.setText("Compra efetuada")
        errorLabel.setVisible(false)
        confirmLabel.setVisible(true)
        newUserApp
      }

    }catch {
      case ex: Exception => {
        confirmLabel.setVisible(false)
        errorLabel.setVisible(true)
        user
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