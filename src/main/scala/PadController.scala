import scalafx.event.Event
import scalafx.scene.control.Button
import scalafx.scene.input.{DragEvent, TransferMode}
import scalafx.scene.layout.GridPane
import scalafxml.core.macros.sfxml
import scalafx.Includes._


@sfxml
class PadController(model: Model, padsGrid: GridPane) {
    var padButtons = initializePads


    model.onUpdate(model => {
        updatePads
    })


    /**
      * Init
      */
    def initializePads = model.forEachPad((column, row, pad: Pad) => {
        val padButton = new Button {
            maxWidth = Double.MaxValue
            maxHeight = Double.MaxValue
            text = pad.sampleName
            onMousePressed = (_: Event) => {
                if (model.recording)
                    model.addActivation(column, row);
                pad.sample.play()
            }
            onDragOver = (event: DragEvent) => {
                val db = event.getDragboard
                if (db.hasFiles) event.acceptTransferModes(TransferMode.Copy)
                else event.consume
            }
            onDragDropped = (event: DragEvent) => {
                val db = event.getDragboard
                if (db.hasFiles) {
                    val file = db.getFiles.get(0)
                    pad.changeSample(file)
                    println(pad)
                }
            }
        }
        padsGrid.add(padButton, column, row)

        padButton
    }

    )


    /**
      * Update
      */
    def updatePads = model.forEachPad((column, row, pad) => {
        pad.activations.map({ case Activation(beat, _) => {
            val highlighted = beat >= model.beatsIntoCurrentMeasure && beat < model.beatsIntoCurrentMeasure + 1
            val padButton = padButtons(column)(row)
            padButton.text = pad.sampleName
            if (highlighted) padButton.getStyleClass().add("highlight")
            else padButton.getStyleClass().removeAll("highlight")
        }
        })
    })

}
