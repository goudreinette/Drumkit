import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml


@sfxml
class MainController(play: Button, model: Model) {
    
    model.updaters.append(model => {
        println(model)
    })
    
    
    def onCreate(event: ActionEvent) {
    
    }
}
