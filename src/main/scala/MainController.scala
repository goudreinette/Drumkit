import Mode._

import scalafx.event.{ActionEvent, Event}
import scalafx.scene.control._
import scalafx.scene.input.DragEvent
import scalafxml.core.macros.sfxml


@sfxml
class MainController(model: Model,
                     play: ToggleButton,
                     record: ToggleButton,
                     mute: ToggleButton,
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
    model.onUpdate { model =>
        secondsLabel.text = f"${model.totalSeconds}%2.2fs"
        beatMeasureLabel.text = f"${model.beatsIntoCurrentMeasure}%2.2f ${model.currentWholeMeasure}"
        bpmLabel.text = s"${model.beatsPerMinute.round}BPM"
        play.selected = model.playing
        progress.progress = (model.beatsIntoCurrentMeasure / model.beatsInAMeasure)

        record.selected = model.isRecording
        mute.selected = model.isMuting
    }

    /**
      * Event handlers
      */
    def togglePlaying(e: ActionEvent) =
        model.togglePlaying

    def toggleRecording(e: ActionEvent) =
        model.toggleMode(Recording)

    def toggleMuting(e: ActionEvent) =
        model.toggleMode(Muting)

    def clear(e: ActionEvent) =
        model.clear

    def bpmSliderChanged(e: Event) =
        model.beatsPerMinute = bpmSlider.getValue

    def sampleDropped(e: DragEvent) = {
        println(e.getDragboard.getString)
    }
}
