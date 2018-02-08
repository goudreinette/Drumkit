package model

import model.PadRepository.getClass
import model.Implicits._

class Metronome(beatsInAMeasure: Int) {
    val measurePad = makeMeasureMetronome
    val beatPad = makeBeatMetronome
    val pads = List(measurePad, beatPad)


    /**
      * Getters
      */
    def muted = pads.forall(_ muted)

    /**
      * model.Pad constructors
      */

    def makeMeasureMetronome: Pad = {
        val metronome = Pad("samples/metronome_measure.wav")
        metronome.muted = true
        metronome.activateAtBeat(0, -1)
        metronome
    }

    def makeBeatMetronome: Pad = {
        val metronome = Pad("samples/metronome_beat.wav")
        metronome.muted = true
        for {b <- 1 until beatsInAMeasure}
            metronome.activateAtBeat(b, -1)
        metronome
    }

    /**
      * Actions
      */
    def toggleMuted() =
        pads.foreach(_ toggleMuted())

    def tryPlaying(currentWholeMeasure: Int, beatsIntoCurrentMeasure: Double) =
        pads.foreach(_ tryPlaying(currentWholeMeasure, beatsIntoCurrentMeasure))
}
