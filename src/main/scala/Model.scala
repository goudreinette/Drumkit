import scala.collection.mutable
import scalafx.beans.property.{BooleanProperty, DoubleProperty, IntegerProperty}
import scalafx.collections.ObservableBuffer


class Model {
    var playing = false
    var seconds = 0.0
    var bpm = 120
    val samples = Array.ofDim[Sample](4, 4)
    var updaters = mutable.Buffer[Model => Unit]()
    
    def run = new Thread(() => {
        while (true) tick
    }).start()
    
    def tick {
        callUpdaters
    }
    
    
    def callUpdaters = for {
        c <- updaters
    } c.apply(this)
}

case class Sample(name: String)