import org.scalatest.FunSuite

import BeatUtils._

class Tests extends FunSuite {
    val quantizations = Map(
        (3.1234, 2) -> 3,
        (3.125, 4) -> 3.25,
        (3.1234, 8) -> 3.125,

        (3.9, 2) -> 4,
        (3.6, 2) -> 3.5
    )

    test("quantize should round to the nearest beat-part") {
        for {((beat, quantizeBy), result) <- quantizations}
            assert(quantize(beat, quantizeBy) == result)
    }
}