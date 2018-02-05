import scala.collection.mutable


class Model {
    /**
      * Fields
      */
    @volatile
    var playing = false
    
    val beatsInAMeasure = 4;
    
    var nanos = 0.0
    
    var beatsPerMinute = 120.0
    val samples = Array.ofDim[Sample](4, 4)
    var updaters = mutable.Buffer[Model => Unit]()
    
    val tickPause = 1
    
    var lastPlayedWholeBeat: Int = 0
    var lastPlayedWholeMeasure: Int = 0
    
    
    /**
      * Total
      */
    def totalSeconds =
        nanos / 1000000000
    
    def currentWholeMeasure: Int =
        (totalSeconds / secondsInAMeasure).toInt
    
    def currentBeat: Double =
        totalSeconds / secondsInABeat
    
    def currentWholeBeat =
        currentBeat.toInt
    
    /**
      * Relative
      */
    def secondsIntoCurrentBeat: Double =
        secondsIntoCurrentMeasure % secondsInABeat
    
    def beatsIntoCurrentMeasure =
        currentBeat % beatsInAMeasure
    
    def secondsIntoCurrentMeasure =
        totalSeconds % secondsInAMeasure
    
    /**
      * Conversions
      */
    def secondsInABeat =
        60 / beatsPerMinute
    
    def secondsInAMeasure =
        secondsInABeat * beatsInAMeasure
    
    
    /**
      * Actions
      */
    def togglePlaying =
        playing = !playing
    
    
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
                //                println(nanos)
                Thread.sleep(tickPause)
            } else {
                lastTime = System.nanoTime()
            }
        }
    }).start()
    
    def tick {
        callUpdaters
        playSounds
    }
    
    def onUpdate(u: Model => Unit) = updaters.append(u)
    
    def callUpdaters = for {
        c <- updaters
    } javafx.application.Platform.runLater(() => c.apply(this))
    
    
    /**
      * Audio
      */
    def playSounds = {
        if (lastPlayedWholeBeat != currentWholeBeat) {
            lastPlayedWholeBeat = currentWholeBeat
            Audio.kick.play
        }
        if (lastPlayedWholeMeasure != currentWholeMeasure) {
            lastPlayedWholeMeasure = currentWholeMeasure
            Audio.cowbell.play
        }
    }
}


case class Sample(name: String)