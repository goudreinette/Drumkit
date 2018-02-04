
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle

object Drumkit extends JFXApp {
    stage = new JFXApp.PrimaryStage {
        title.value = "Drumkit"
        width = 1000
        height = 700
        scene = new Scene {
            fill = LightGreen
            content = new Rectangle {
                x = 25
                y = 40
                width = 100
                height = 100
                fill <== when(hover) choose Green otherwise Red
            }
        }
    }
}
