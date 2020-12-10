import CSVFileReader.saveUser
import IO._
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.{Parent, Scene}
import javafx.scene.control._
import javafx.stage.{Modality, Stage}
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType

class ControllerMenu {
  var user: UserApp= new UserApp("","","",0.0,LazyList[UserList](),LazyList[UserList](),List[String](),List[(String,Double)](),List[categorySavings](),new PlanSoft(10,List[categorySavings]()))
  @FXML
  private var Button1: Button = _
  @FXML
  private var Button2: Button = _
  @FXML
  private var Button3: Button = _
  @FXML
  private var logoutButton: Button = _
  @FXML
  private var Button4: Button = _
  @FXML
  private var Button5: Button=_
  @FXML
  private var Button6: Button=_
  @FXML
  private var Button7: Button=_




  def onButton1Clicked: Unit ={
    //val c:ControllerTransaction=new ControllerTransaction
    val secondStage: Stage = new Stage()
    secondStage.initModality(Modality.APPLICATION_MODAL)
    secondStage.initOwner(Button1.getScene().getWindow)
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerTransaction.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerTransaction = fxmlLoader.getController
    controller.setTipo(1)
    controller.setTitle()
    controller.setTempUser(user)
    println("OI"+ user.name)
    controller.setParent(this)
    controller.setCategorias(user.userCategories)
    val scene = new Scene(mainViewRoot)
    secondStage.setScene(scene)
    secondStage.show()
  }
  def onButton2Clicked: Unit ={
    val secondStage: Stage = new Stage()
    secondStage.initModality(Modality.APPLICATION_MODAL)
    secondStage.initOwner(Button2.getScene().getWindow)
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerTransaction.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerTransaction = fxmlLoader.getController
    controller.setTipo(2)
    controller.setTitle()
    controller.setTempUser(user)
    controller.setParent(this)
    controller.setCategorias(user.userCategories)
    val scene = new Scene(mainViewRoot)
    secondStage.setScene(scene)
    secondStage.show()
  }

  def onButton3Clicked: Unit ={
    val alert = new Alert(AlertType.NONE, "O seu balanço é: " + user.balance, ButtonType.OK)
    alert.showAndWait
  }

  def onButton4Clicked: Unit={
    val secondStage: Stage = new Stage()
    secondStage.initModality(Modality.APPLICATION_MODAL)
    secondStage.initOwner(Button4.getScene().getWindow)
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerHistorico.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerHistorico = fxmlLoader.getController
    controller.setTempUser(user)
    controller.setCategorias(user.userCategories)
    controller.startDates()
    val scene = new Scene(mainViewRoot)
    secondStage.setScene(scene)
    secondStage.show()
  }

  def onButton5Clicked:Unit= {
    val secondStage: Stage = new Stage()
    secondStage.initModality(Modality.APPLICATION_MODAL)
    secondStage.initOwner(Button5.getScene().getWindow)
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerNewCategory.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerNewCategory = fxmlLoader.getController
    controller.setParent(this)
    controller.setTempUser(user)
    val scene = new Scene(mainViewRoot)
    secondStage.setScene(scene)
    secondStage.show()
  }

    def onButton6Clicked(): Unit={
      val secondStage: Stage = new Stage()
      secondStage.initModality(Modality.APPLICATION_MODAL)
      secondStage.initOwner(Button6.getScene().getWindow)
      val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerTotal.fxml"))
      val mainViewRoot: Parent = fxmlLoader.load()
      val controller: ControllerTotal = fxmlLoader.getController
      controller.setTempUser(user)
      controller.setCategorias(user.userCategories)
      controller.startDates()
      val scene = new Scene(mainViewRoot)
      secondStage.setScene(scene)
      secondStage.show()
    }

  def onButton7Clicked():Unit={
    val secondStage: Stage = new Stage()
    secondStage.initModality(Modality.APPLICATION_MODAL)
    secondStage.initOwner(Button7.getScene().getWindow)
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerPerfil2.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerPerfil = fxmlLoader.getController
    controller.setTempUser(user)
    controller.setParent(this)
    val scene = new Scene(mainViewRoot)
    secondStage.setScene(scene)
    secondStage.show()
  }


  def onButtonLogoutClicked:Unit={
    println("LOGOUT:"+user.userCategories)
    saveUser(user)
    logoutButton.getScene().getWindow.hide()

  }
//setter&getter
  def setUser(user: UserApp): Unit ={
    this.user=user
    println(user.userCategories)
  }
  def getUser():UserApp={
    user
  }


}
