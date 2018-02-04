import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafxml.core.{DependenciesByType, FXMLView}
import scala.reflect.runtime.universe._


object Drumkit extends JFXApp {
    val model = new Model
    
    stage = new JFXApp.PrimaryStage() {
        title = "Test window"
        scene = new Scene(
            FXMLView(getClass.getResource("layout.fxml"),
                     new DependenciesByType(Map(
                         typeOf[Model] -> model))))
    }
}
