import scala.collection.mutable


class Model {
    /**
      * Fields
      */
    @volatile
    var playing = false
    
    val beatsInMaat = 4;
    
    var nanos = 0.0
    
    var bpm = 120
    val samples = Array.ofDim[Sample](4, 4)
    var updaters = mutable.Buffer[Model => Unit]()
    
    val tickPause = 100
    
    /**
      * Getters
      */
    def beats {
    
    }
    
    def seconds: Double = {
        val s = nanos / 1000000000
        return if (s > beatsInMaat)
            s % 4
        else
            s
    }
    
    /**
      * Actions
      */
    def togglePlaying = {
        playing = !playing
    }
    
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
            } else {
                lastTime = System.nanoTime()
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