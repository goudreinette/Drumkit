import scalafx.Includes._
import scalafx.scene.control.Button
import scalafx.scene.input.{DragEvent, MouseButton, MouseEvent, TransferMode}
import scalafx.scene.layout.GridPane
import scalafxml.core.macros.sfxml
import Mode._

@sfxml
class PadController(model: Model, padsGrid: GridPane) {
    var padButtons = initializePads


    model.onUpdate(_ => updatePads)


    /**
      * Init
      */
    def initializePads = model.forEachPad { (column, row, pad: Pad) =>
        val padButton = makeButton(column, row, pad)
        padsGrid.add(padButton, column, row)
        padButton
    }


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
                        model.mode match {
                            case Muting => pad.toggleMuted()
                            case Normal => pad.play()
                            case Recording => {
                                model.addActivation(column, row)
                                pad.play()
                            }
                        }
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
    def updatePads = model.forEachPad { (column, row, pad) =>
        val padButton = padButtons(column)(row)

        if (pad.activations.isEmpty)
            toggleClass(padButton, false, "highlighted")
        toggleClass(padButton, pad.muted, "muted")

        pad.activations.foreach { case Activation(beat, _) => {
            val beatsIntoCurrentMeasure = model.beatsIntoCurrentMeasure
            val highlighted = beat <= beatsIntoCurrentMeasure + 0.2
            padButton.text = pad.sampleName
            toggleClass(padButton, highlighted, "highlighted")
        }
        }
    }

    def toggleClass(padButton: Button, toggle: Boolean, classname: String) =
        if (toggle)
            padButton.getStyleClass().add(classname)
        else
            padButton.getStyleClass().removeAll(classname)
}
