import java.io.File

import kuusisto.tinysound.TinySound
import org.apache.commons.io.FilenameUtils


case class Activation(atBeat: Double, var lastPlayedMeasure: Int = 0)


case class Pad(var samplePath: String) {
    var activations = Set[Activation]()
    var sample = TinySound.loadSound(new File(samplePath))
    // Beads.makeSamplePlayer(samplePath)
    var muted = false


    var lastPlayedMeasure = 0

    /**
      * Sample
      */
    def sampleName =
        FilenameUtils.getBaseName(samplePath)


    def changeSample(f: File) = {
        sample = TinySound.loadSound(f) //Beads.make
        samplePath = f.getAbsolutePath
    }

    /**
      * Activations
      */
    def activateAtBeat(beat: Double, currentWholeMeasure: Int) =
        activations += Activation(beat, lastPlayedMeasure = currentWholeMeasure)


    def removeActivations =
        activations = Set[Activation]()


    /**
      * Main
      */
    def reset = removeActivations

    def tryPlaying(currentWholeMeasure: Int, beatsIntoCurrentMeasure: Double) {
        for {a <- activations} {
            if (a.lastPlayedMeasure != currentWholeMeasure && beatsIntoCurrentMeasure >= a.atBeat) {
                a.lastPlayedMeasure = currentWholeMeasure
                play()
            }
        }
    }

    def play() =
        if (!muted)
            sample.play()

    def toggleMuted() = muted = !muted
}
