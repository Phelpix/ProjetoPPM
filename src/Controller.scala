import CSVFileReader.{readFile, readUser}
import ExpenseTrackerUtils.{searchUser, searchUser2}
import IO.loginError
import MenuUser.userLoop
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.{Parent, Scene}
import javafx.scene.control.{Button, TextField}
import javafx.stage.{Modality, Stage}

class Controller {
  @FXML
  private var registerButton: Button = _
  @FXML
  private var loginButton: Button = _
  @FXML
  private var emailTF: TextField = _
  @FXML
  private var passwordTF: TextField = _

  def onLoginClicked(): Unit = {
    val file = "CSVFiles/UserCredentials.csv"
    val list = readFile(file)
    val username:Option[String] = searchUser2(emailTF.getText(),passwordTF.getText(),list)
    username match{
      case Some(b) => {
        println(b)
        val userToApp: UserApp = readUser("CSVFiles/" + b + ".csv", b, emailTF.getText(),passwordTF.getText())
        val secondStage: Stage = new Stage()
        secondStage.initModality(Modality.APPLICATION_MODAL)
        secondStage.initOwner(loginButton.getScene().getWindow)
        val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerMenu.fxml"))
        val mainViewRoot: Parent = fxmlLoader.load()
        val c:ControllerMenu=fxmlLoader.getController
        c.setUser(userToApp)
        val scene = new Scene(mainViewRoot)
        secondStage.setScene(scene)
        secondStage.show()
      }
      case None =>{
        loginError()
      }
    }
    /*
    if(username == ""){
      println("Login nao dado")
    } else {
      val userToApp: UserApp = readUser("CSVFiles/" + username + ".csv", username, emailTF.getText(),passwordTF.getText())

      val secondStage: Stage = new Stage()
      secondStage.initModality(Modality.APPLICATION_MODAL)
      secondStage.initOwner(loginButton.getScene().getWindow)
      val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerMenu.fxml"))
      val mainViewRoot: Parent = fxmlLoader.load()
      val c:ControllerMenu=fxmlLoader.getController
      c.setUser(userToApp)
      val scene = new Scene(mainViewRoot)
      secondStage.setScene(scene)
      secondStage.show()
    }
    */

  }

  def onRegisterClicked(): Unit = {
    val secondStage: Stage = new Stage()
    secondStage.initModality(Modality.APPLICATION_MODAL)
    secondStage.initOwner(registerButton.getScene().getWindow)
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerRegisto.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)
    secondStage.setScene(scene)
    secondStage.show()
  }
}
