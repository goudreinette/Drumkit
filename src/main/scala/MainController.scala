import scala.collection.mutable
import scalafx.Includes._
import scalafx.event
import scalafx.event.{ActionEvent, Event}
import scalafx.scene.control._
import scalafx.scene.input.{DragEvent, TransferMode}
import scalafx.scene.layout.{Background, GridPane}
import scalafx.scene.paint.Color._
import scalafxml.core.macros.sfxml
import scala.collection.JavaConversions._


@sfxml
class MainController(model: Model,
                     play: ToggleButton,
                     record: ToggleButton,
                     clear: Button,
                     secondsLabel: Label,
                     beatMeasureLabel: Label,
                     bpmLabel: Label,
                     bpmSlider: Slider,
                     progress: ProgressBar) {

    initializeSlider


    /**
      * Init
      */
    def initializeSlider =
        bpmSlider.value = model.beatsPerMinute


    /**
      * Update
      */
    model.onUpdate(model => {
        secondsLabel.text = f"${model.totalSeconds}%2.2fs"
        beatMeasureLabel.text = f"${model.beatsIntoCurrentMeasure}%2.2f ${model.currentWholeMeasure}"
        bpmLabel.text = s"${model.beatsPerMinute.round}BPM"
        play.selected = model.playing
        progress.progress = (model.beatsIntoCurrentMeasure / model.beatsInAMeasure)
    })

    /**
      * Event handlers
      */
    def togglePlaying(e: ActionEvent) =
        model.togglePlaying

    def toggleRecording(e: ActionEvent) =
        model.toggleRecording

    def clear(e: ActionEvent) =
        model.clear

    def bpmSliderChanged(e: Event) =
        model.beatsPerMinute = bpmSlider.getValue

    def sampleDropped(e: DragEvent) = {
        println(e.getDragboard.getString)
    }
}
