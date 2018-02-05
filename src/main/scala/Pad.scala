import kuusisto.tinysound.TinySound


case class Pad(samplePath: String) {
    var activateAt: Option[Double] = None
    val sample = TinySound.loadSound(samplePath)
    
    var lastPlayedMeasure = 0
    
    def activateAtBeat(beat: Double) =
        activateAt = Some(beat)
    
    def tryPlaying(currentWholeMeasure: Int, beatsIntoCurrentMeasure: Double) {
        activateAt.map(beat => {
            if (lastPlayedMeasure != currentWholeMeasure &&
                beatsIntoCurrentMeasure >= beat) {
                lastPlayedMeasure = currentWholeMeasure
                sample.play
            }
        })
    }
}


