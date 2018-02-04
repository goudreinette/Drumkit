import scala.collection.mutable
import scala.compat.Platform
import scalafx.beans.property.{BooleanProperty, DoubleProperty, IntegerProperty}
import scalafx.collections.ObservableBuffer


class Model {
    /**
      * Fields
      */
    var playing = false
    var nanos = 0.0
    var bpm = 120
    val samples = Array.ofDim[Sample](4, 4)
    var updaters = mutable.Buffer[Model => Unit]()
    
    val tickPause = 100
    
    /**
      * Getters
      */
    def seconds = nanos / 1000000000
    
    /**
      * Main
      */
    def run = new Thread(() => {
        var lastTime = System.nanoTime()
        while (true) {
            if (playing) {
                tick
                var newTime = System.nanoTime()
                nanos += (newTime - lastTime)
                lastTime = newTime
                println(nanos)
                Thread.sleep(tickPause)
            }
        }
    }).start()
    
    def tick {
        callUpdaters
    }
    
    def onUpdate(u: Model => Unit) = updaters.append(u)
    
    def callUpdaters = for {
        c <- updaters
    } javafx.application.Platform.runLater(() => c.apply(this))
}

case class Sample(name: String)