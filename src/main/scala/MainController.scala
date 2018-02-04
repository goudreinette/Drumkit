import javafx.beans.value.{ChangeListener, ObservableValue}

import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label, Slider, ToggleButton}
import scalafxml.core.macros.sfxml


@sfxml
class MainController(play: ToggleButton,
                     model: Model,
                     secondsLabel: Label,
                     beatMeasureLabel: Label,
                     bpmLabel: Label,
                     bpmSlider: Slider) {
    
    model.onUpdate(model => {
        secondsLabel.text = f"${model.totalSeconds}%2.2fs"
        beatMeasureLabel.text = f"${model.currentBeat} ${model.currentMeasure}"
        bpmLabel.text = s"${model.beatsPerMinute}BPM"
        play.selected = model.playing
        
        bpmSlider.valueProperty.addListener((observable, oldValue, newValue) =>
                                                model.beatsPerMinute = newValue.doubleValue())
    })
    
    /**
      * Event handlers
      */
    def togglePlaying(e: ActionEvent) = model.togglePlaying
}
