import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Button, ButtonType, ChoiceBox, Labeled, RadioButton, TextArea}

class ControllerTotal {

  @FXML
  private var categoriasCB: ChoiceBox[String] = new ChoiceBox[String]()
  @FXML
  private var okTotalButton: Button =_
  @FXML
  private var mesCB: ChoiceBox[String] = new ChoiceBox[String]()
  @FXML
  private var anoCB: ChoiceBox[String] = new ChoiceBox[String]()
  @FXML
  private var depositoRB: RadioButton =_
  @FXML
  private var compraRB: RadioButton =_
  @FXML
  private var voltarButton:  Button =_
  @FXML
  private var errorLabel :Labeled =_

  var tempUser: UserApp= new UserApp("","","",0.0,LazyList[UserList](),LazyList[UserList](),List[String](),List[(String,Double)](),List[categorySavings](),new PlanSoft(10,List[categorySavings]()))

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

  def onOkTotalClicked: Unit ={

    if (!depositoRB.isSelected && !compraRB.isSelected){
      errorLabel.setText("Selecione uma das opções")
      errorLabel.setVisible(true)
    } else if (categoriasCB.getSelectionModel.getSelectedItem == null) {
      errorLabel.setText("Selecione uma categoria")
      errorLabel.setVisible(true)
    } else if(mesCB.getSelectionModel.getSelectedItem==null||anoCB.getSelectionModel.getSelectedItem==null){
      errorLabel.setText("Indique o mês e ano")
      errorLabel.setVisible(true)
    } else if(depositoRB.isSelected){
      errorLabel.setVisible(false)
      val list:LazyList[UserList] = tempUser.depositList.filter(x =>{x.date==(mesCB.getSelectionModel.getSelectedItem+"-"+anoCB.getSelectionModel.getSelectedItem)})
      val textoDeposito:String=listTotal( list,categoriasCB.getSelectionModel.getSelectedItem).toString
      val alert = new Alert(AlertType.NONE, "O valor desejado é: " + textoDeposito, ButtonType.OK)
      alert.showAndWait

    }else if(compraRB.isSelected){
      errorLabel.setVisible(false)
      val list:LazyList[UserList] = tempUser.expenseList.filter(x => x.date==(mesCB.getSelectionModel.getSelectedItem+"-"+anoCB.getSelectionModel.getSelectedItem))
      val textoCompra:String=listTotal( list,categoriasCB.getSelectionModel.getSelectedItem).toString
      val alert = new Alert(AlertType.NONE, "O valor desejado é: " + textoCompra, ButtonType.OK)
      alert.showAndWait
    }
  }

  def listTotal(list : LazyList[UserList], filter :String): Double= {
      val total: Double = (list foldLeft 0.0) ((v1: Double, v2: UserList) => if (v2.category == filter) v1 + v2.value else v1)
      total
  }

  def onVoltarClicked: Unit ={
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerMenu.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerMenu = fxmlLoader.getController
    controller.setUser(tempUser)
    voltarButton.getScene.setRoot(mainViewRoot)
  }

}
