import java.io.File

import kuusisto.tinysound.TinySound
import org.apache.commons.io.{FileUtils, FilenameUtils}


case class Activation(atBeat: Double, var lastPlayedMeasure: Int = 0)

case class Pad(var samplePath: String) {
    var activations = Set[Activation]()
    var sample = TinySound.loadSound(new File(samplePath))
    
    var lastPlayedMeasure = 0
    
    def sampleName =
        FilenameUtils.getBaseName(samplePath)
    
    def activateAtBeat(beat: Double) =
        activations += Activation(beat)
    
    def changeSample(f: File) = {
        sample = TinySound.loadSound(f)
        samplePath = f.getAbsolutePath
    }
    
    def tryPlaying(currentWholeMeasure: Int, beatsIntoCurrentMeasure: Double) {
        for {a <- activations} {
            if (a.lastPlayedMeasure != currentWholeMeasure && beatsIntoCurrentMeasure >= a.atBeat) {
                
                a.lastPlayedMeasure = currentWholeMeasure
                sample.play
            }
        }
    }
    
}
