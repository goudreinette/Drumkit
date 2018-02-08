package model

import model.PadRepository.getClass

class Metronome(beatsInAMeasure: Int) {
    val measurePad = makeMeasureMetronome
    val beatPad = makeBeatMetronome


    /**
      * Getters
      */
    def muted =
        measurePad.muted && beatPad.muted

    /**
      * model.Pad constructors
      */

    def makeMeasureMetronome: Pad = {
        val metronome = Pad(getClass.getResource("../samples/metronome_measure.wav"))
        metronome.muted = true
        metronome.activateAtBeat(0, -1)
        metronome
    }

    def makeBeatMetronome: Pad = {
        val metronome = Pad(getClass.getResource("../samples/metronome_beat.wav"))
        metronome.muted = true
        for {b <- 1 until beatsInAMeasure}
            metronome.activateAtBeat(b, -1)
        metronome
    }

    /**
      * Actions
      */
    def toggleMuted() = {
        measurePad.toggleMuted()
        beatPad.toggleMuted()
    }

    def tryPlaying(currentWholeMeasure: Int, beatsIntoCurrentMeasure: Double) = {
        measurePad.tryPlaying(currentWholeMeasure, beatsIntoCurrentMeasure)
        beatPad.tryPlaying(currentWholeMeasure, beatsIntoCurrentMeasure)
    }

}
