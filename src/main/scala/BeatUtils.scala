object BeatUtils {
    /**
      * Rounds up or down to the nearest beat-part
      * Where 2 = half, 4 = quarter, etc.
      */
    def quantize(beats: Double, quantizeBy: Int) = {
        val multiplied = (beats * quantizeBy).round
        val floored = multiplied.toDouble / quantizeBy.toDouble

        if (floored == 4.0) // FIXME
            0.0
        else
            floored
    }
}
