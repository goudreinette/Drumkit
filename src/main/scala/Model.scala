import javafx.collections.ObservableArray

import scalafx.beans.property.{BooleanProperty, DoubleProperty, IntegerProperty}
import scalafx.collections.ObservableBuffer


class Model {
    var playing = new BooleanProperty
    var seconds = new DoubleProperty
    var bpm = new IntegerProperty
    val samples = new ObservableBuffer[Sample]()
    
    def run = while (true) {
        if (playing.value)
            println("Playing...")
    }
}

case class Sample(name: String)