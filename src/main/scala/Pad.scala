import java.io.File

import kuusisto.tinysound.TinySound
import org.apache.commons.io.{FileUtils, FilenameUtils}

import scala.reflect.io.Path


case class Pad(var samplePath: String) {
    var activateAt: Option[Double] = None
    var sample = TinySound.loadSound(new File(samplePath))
    
    var lastPlayedMeasure = 0
    
    def sampleName =
        FilenameUtils.getBaseName(samplePath)
    
    def activateAtBeat(beat: Double) =
        activateAt = Some(beat)
    
    def changeSample(f: File) = {
        sample = TinySound.loadSound(f)
        samplePath = f.getAbsolutePath
    }
    
    def tryPlaying(currentWholeMeasure: Int, beatsIntoCurrentMeasure: Double) {
        activateAt.map(beat => {
            println(activateAt)
            if (lastPlayedMeasure != currentWholeMeasure &&
                beatsIntoCurrentMeasure >= beat) {
                lastPlayedMeasure = currentWholeMeasure
                sample.play
            }
        })
    }
    
}


