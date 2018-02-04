import scala.collection.mutable
import scalafx.beans.property.{BooleanProperty, DoubleProperty, IntegerProperty}
import scalafx.collections.ObservableBuffer


class Model {
    var playing = false
    var seconds = 0.0
    var bpm = 120
    val samples = Array.ofDim[Sample](4, 4)
    var controllers = mutable.Buffer[Controller]()
    
    def run = new Thread(() => {
        while (true) {
            tick
        }
    }).start()
    
    def tick {
        updateControllers
    }
    
    def addController(c: Controller) = controllers += c
    
    def updateControllers = for {
        c: Controller <- controllers
    } c.update
}

case class Sample(name: String)