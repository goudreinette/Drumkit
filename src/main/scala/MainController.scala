import javafx.beans.value.{ChangeListener, ObservableValue}

import scalafx.event.ActionEvent
import scalafx.scene.control._
import scalafx.scene.input.MouseDragEvent
import scalafx.scene.layout.GridPane
import scalafxml.core.macros.sfxml


@sfxml
class MainController(model: Model,
                     play: ToggleButton,
                     secondsLabel: Label,
                     beatMeasureLabel: Label,
                     bpmLabel: Label,
                     bpmSlider: Slider,
                     progress: ProgressBar,
                     padsGrid: GridPane) {
    
    initializePads
    initializeSlider
    
    model.onUpdate(model => {
        secondsLabel.text = f"${model.totalSeconds}%2.2fs"
        beatMeasureLabel.text = f"${model.beatsIntoCurrentMeasure}%2.2f ${model.currentWholeMeasure}"
        bpmLabel.text = s"${model.beatsPerMinute.round}BPM"
        play.selected = model.playing
        progress.progress = (model.beatsIntoCurrentMeasure / model.beatsInAMeasure)
    })
    
    
    /**
      * Init
      */
    def initializePads =
        for {row <- 0 until 4; column <- 0 until 4}
            padsGrid.add(new Button {
                maxWidth = Double.MaxValue
                maxHeight = Double.MaxValue
                onMouseClicked = (_: Event) => model.addActivation(row, column)
            }, row, column)
    
    
    def initializeSlider =
        bpmSlider.value = model.beatsPerMinute
    
    /**
      * Event handlers
      */
    def togglePlaying(e: ActionEvent) =
        model.togglePlaying
    
    def bpmSliderChanged(e: scalafx.event.Event) =
        model.beatsPerMinute = bpmSlider.getValue
}
