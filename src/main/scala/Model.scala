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
    val pads = for {c <- 0 until 4} yield for {r <- 0 until 4} yield Pad("kick.wav")
    val updaters = mutable.Buffer[Model => Unit]()
    
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
    
    def addActivation(column: Int, row: Int) = {
        pads(column)(row).activateAtBeat(quantizedBeatsIntoCurrentMeasure)
        println(pads(row)(column), pads(row)(column).activateAt)
    }
    
    def quantizedBeatsIntoCurrentMeasure =
        (beatsIntoCurrentMeasure * 32).round / 32
    
    
    /**
      * Main
      */
    def run = new Thread(() => {
        var lastTime = System.nanoTime()
        while (true) {
            if (playing) {
                tick
                val newTime = System.nanoTime()
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
    
    /**
      * Callbacks
      */
    def onUpdate(u: Model => Unit) = updaters.append(u)
    
    def callUpdaters = for {
        c <- updaters
    } javafx.application.Platform.runLater(() => c.apply(this))
    
    /**
      * Pads
      */
    def forEachPad[A](f: (Int, Int, Pad) => A): IndexedSeq[IndexedSeq[A]] =
        for {c <- 0 until 4} yield
            for {r <- 0 until 4} yield
                f(c, r, pads(c)(r))
    
    /**
      * Audio
      */
    def playSounds = {
        forEachPad((c, r, pad) => {
            pad.tryPlaying(currentWholeMeasure, beatsIntoCurrentMeasure)
        })
        
        //        if (lastPlayedWholeMeasure != currentWholeMeasure) {
        //            lastPlayedWholeMeasure = currentWholeMeasure
        //            Audio.cowbell.play
        //        }
    }
}
