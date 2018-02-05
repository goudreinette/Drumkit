import kuusisto.tinysound.TinySound

import scala.collection.mutable

class Pad(samplePath: String) {
    val activations = mutable.Buffer[Double]
    val sample = TinySound.loadSound(samplePath)
}
