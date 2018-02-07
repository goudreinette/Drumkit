import scalafx.event.Event
import scalafx.scene.control.Button
import scalafx.scene.input.{DragEvent, MouseButton, MouseEvent, TransferMode}
import scalafx.scene.layout.GridPane
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.scene.input


@sfxml
class PadController(model: Model, padsGrid: GridPane) {
    var padButtons = initializePads


    model.onUpdate(_ => updatePads)


    /**
      * Init
      */
    def initializePads = model.forEachPad((column, row, pad: Pad) => {
        val padButton = makeButton(column, row, pad)
        padsGrid.add(padButton, column, row)
        padButton
    })


    def makeButton(column: Int, row: Int, pad: Pad) =
        new Button {
            maxWidth = Double.MaxValue
            maxHeight = Double.MaxValue
            text = pad.sampleName
            onMousePressed = (e: MouseEvent) => {
                e.button match {
                    case MouseButton.Secondary => {
                        pad.removeActivations
                    }
                    case _ => {
                        if (model.recording)
                            model.addActivation(column, row);
                        pad.play()
                    }

                }
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


    /**
      * Update
      */
    def updatePads = model.forEachPad((column, row, pad) => {
        val padButton = padButtons(column)(row)

        if (pad.activations.isEmpty)
            removeHighlight(padButton)

        pad.activations.map({ case Activation(beat, _) => {
            val beatsIntoCurrentMeasure = model.beatsIntoCurrentMeasure
            val highlighted = beat <= beatsIntoCurrentMeasure + 0.2
            padButton.text = pad.sampleName
            if (highlighted) addHighlight(padButton)
            else removeHighlight(padButton)
        }
        })
    })

    def addHighlight(padButton: Button) = padButton.getStyleClass().add("highlight")

    def removeHighlight(padButton: Button) = padButton.getStyleClass().removeAll("highlight")
}
