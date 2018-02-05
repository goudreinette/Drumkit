import scala.collection.mutable
import scalafx.Includes._
import scalafx.event.{ActionEvent, Event}
import scalafx.scene.control._
import scalafx.scene.layout.{Background, GridPane}
import scalafx.scene.paint.Color._
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
    
    var padButtons = initializePads
    initializeSlider
    
    model.onUpdate(model => {
        secondsLabel.text = f"${model.totalSeconds}%2.2fs"
        beatMeasureLabel.text = f"${model.beatsIntoCurrentMeasure}%2.2f ${model.currentWholeMeasure}"
        bpmLabel.text = s"${model.beatsPerMinute.round}BPM"
        play.selected = model.playing
        progress.progress = (model.beatsIntoCurrentMeasure / model.beatsInAMeasure)
        
        updatePads
    })
    
    
    /**
      * Init
      */
    def initializePads = model.forEachPad((column, row, _) => {
        val padButton = new Button {
            maxWidth = Double.MaxValue
            maxHeight = Double.MaxValue
            onMouseClicked = (_: Event) => model.addActivation(column, row)
        }
        padsGrid.add(padButton, column, row)
        padButton
    })
    
    
    def initializeSlider =
        bpmSlider.value = model.beatsPerMinute
    
    
    /**
      * Update
      */
    def updatePads = model.forEachPad((column, row, pad) => {
        pad.activateAt.map(beat => {
            val highlighted = beat >= model.beatsIntoCurrentMeasure - 0.1 && beat < model.beatsIntoCurrentMeasure + 1
            if (highlighted) padButtons(column)(row).getStyleClass().add("highlight")
            else padButtons(column)(row).getStyleClass().removeAll("highlight")
        })
    })
    
    
    /**
      * Event handlers
      */
    def togglePlaying(e: ActionEvent) =
        model.togglePlaying
    
    def bpmSliderChanged(e: scalafx.event.Event) =
        model.beatsPerMinute = bpmSlider.getValue
}
