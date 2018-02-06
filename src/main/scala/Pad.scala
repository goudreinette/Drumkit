import java.io.File

import kuusisto.tinysound.TinySound
import net.beadsproject.beads.data.SampleManager
import net.beadsproject.beads.ugens.SamplePlayer
import org.apache.commons.io.{FileUtils, FilenameUtils}
import net.beadsproject.beads.ugens.Gain


case class Activation(atBeat: Double, var lastPlayedMeasure: Int = 0)

case class Pad(var samplePath: String) {
    var activations = Set[Activation]()
    var sample = Audio.makeSamplePlayer(samplePath)

    var lastPlayedMeasure = 0

    /**
      * Sample
      */
    def sampleName =
        FilenameUtils.getBaseName(samplePath)


    def changeSample(f: File) = {
        sample = new SamplePlayer(Audio.context, SampleManager.sample(f.getAbsolutePath))
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

    def play() = {
        sample.setToLoopStart()
        sample.start()
    }
}
