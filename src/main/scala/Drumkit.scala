import kuusisto.tinysound.TinySound

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafxml.core.{DependenciesByType, FXMLView}
import scala.reflect.runtime.universe._


object Drumkit extends JFXApp {

    val model = new Model

    stage = new JFXApp.PrimaryStage() {
        title = "Drumkit"
        onCloseRequest = _ => exit
        scene = new Scene(
            FXMLView(getClass.getResource("layout.fxml"),
                new DependenciesByType(Map(
                    typeOf[Model] -> model))))
    }

    model.run

    def exit {
        PadRepository.save(model.pads)
        System.exit(0)
    }
}
