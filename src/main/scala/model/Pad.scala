package model

import java.io.{File, InputStream}
import java.net.URL

import kuusisto.tinysound.TinySound
import org.apache.commons.io.FilenameUtils


case class Pad(var sampleUrl: URL,
               var muted: Boolean = false,
               var activations: Set[Activation] = Set[Activation]()) {

    var sample = TinySound.loadSound(sampleUrl)
    var lastPlayedMeasure = 0


    /**
      * Sample
      */

    assert(sample != null)

    def sampleName = FilenameUtils.getBaseName(sampleUrl.getFile)

    def changeSample(f: File) = {
        sample = TinySound.loadSound(f) //Beads.make
        sampleUrl = f.toURL
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
