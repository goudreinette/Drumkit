import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label, ToggleButton}
import scalafxml.core.macros.sfxml


@sfxml
class MainController(play: ToggleButton,
                     model: Model,
                     secondsLabel: Label,
                     bpmLabel: Label) {
    
    model.onUpdate(model => {
        secondsLabel.text = f"${model.seconds}%2.2fs"
        bpmLabel.text = s"${model.bpm}BPM"
        play.selected = model.playing
    })
    
    /**
      * Event handlers
      */
    def togglePlaying(e: ActionEvent) = model.togglePlaying
}
