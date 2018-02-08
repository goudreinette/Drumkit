package controller

import model.Model

import scalafx.event.{ActionEvent, Event}
import scalafx.scene.control._
import scalafx.scene.input.DragEvent
import scalafxml.core.macros.sfxml
import model.Mode._

import scalafx.scene.image.ImageView


@sfxml
class MainController(model: Model,
                     play: ToggleButton,
                     record: ToggleButton,
                     mute: ToggleButton,
                     metronome: ToggleButton,
                     clear: Button,
                     secondsLabel: Label,
                     beatMeasureLabel: Label,
                     bpmLabel: Label,
                     bpmSlider: Slider) {

    initializeSlider
    setGraphics

    /**
      * Init
      */
    def initializeSlider =
        bpmSlider.value = model.beatsPerMinute


    def setGraphics = for {button <- List(play, record, mute, metronome, clear)} {
        val path = getClass.getResource(s"../icons/${button.getId}.png").toString
        val imageView = new ImageView(path) {
            fitHeight = 18
            fitWidth = 18
        }
        button.setGraphic(imageView)
    }

    /**
      * Update
      */
    model.onUpdate { model =>
        secondsLabel.text = f"${model.totalSeconds}%2.2fs"
        beatMeasureLabel.text = f"${model.beatsIntoCurrentMeasure}%2.2f ${model.currentWholeMeasure}"
        bpmLabel.text = s"${model.beatsPerMinute.round}BPM"
        play.selected = model.playing

        record.selected = model.isRecording
        mute.selected = model.isMuting
        metronome.selected = !model.metronome.muted
    }

    /**
      * Event handlers
      * TODO: reduce duplication
      */
    def togglePlaying(e: ActionEvent) =
        model.togglePlaying

    def toggleRecording(e: ActionEvent) =
        model.toggleMode(Recording)

    def toggleMuting(e: ActionEvent) =
        model.toggleMode(Muting)

    def toggleMetronome(e: ActionEvent) =
        model.toggleMetronome

    def clear(e: ActionEvent) =
        model.clear

    def bpmSliderChanged(e: Event) =
        model.beatsPerMinute = bpmSlider.getValue

    def sampleDropped(e: DragEvent) = {
        println(e.getDragboard.getString)
    }
}
