import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label}
import scalafxml.core.macros.sfxml


@sfxml
class MainController(playButton: Button,
                     model: Model,
                     secondsLabel: Label) {
    
    model.onUpdate(model => {
        secondsLabel.text = f"${model.seconds}%2.2fs"
        playButton.text = if (model.playing) "Playing..." else "Play"
    })
    
    /**
      * Event handlers
      */
    def togglePlaying(e: ActionEvent) = model.togglePlaying
}
