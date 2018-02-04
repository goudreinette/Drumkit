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
    
    var lastPlayedBeat: Int = 0
    
    /**
      * Getters
      */
    def totalSeconds =
        nanos / 1000000000
    
    def secondsIntoCurrentMeasure =
        totalSeconds % secondsInAMeasure
    
    def currentMeasure =
        totalSeconds / secondsInAMeasure
    
    def currentBeat: Int = {
        val remainingSeconds = totalSeconds - secondsIntoCurrentMeasure
        (remainingSeconds / secondsInABeat).toInt
    }
    
    def secondsIntoCurrentBeat: Double =
        secondsIntoCurrentMeasure % secondsInABeat
    
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
        if (lastPlayedBeat != currentBeat) {
            Audio.playFile("kick.wav")
            lastPlayedBeat = currentBeat.toInt
        }
    }
}


case class Sample(name: String)