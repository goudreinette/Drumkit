package model

import model.PadRepository.getClass
import model.Implicits._

class Metronome(beatsInAMeasure: Int) {
    val measurePad =
        Pad("samples/metronome_measure.wav",
            muted = true,
            activations = Set(Activation(0, -1)))

    val beatPad =
        Pad("samples/metronome_beat.wav",
            muted = true,
            activations =
              (for {b <- 1 until beatsInAMeasure}
                  yield Activation(b, -1)).toSet)


    val pads = List(measurePad, beatPad)

    /**
      * Getters
      */
    def muted = pads.forall(_ muted)

    /**
      * Actions
      */
    def toggleMuted() =
        pads.foreach(_ toggleMuted())

    def tryPlaying(currentWholeMeasure: Int, beatsIntoCurrentMeasure: Double) =
        pads.foreach(_ tryPlaying(currentWholeMeasure, beatsIntoCurrentMeasure))
}
