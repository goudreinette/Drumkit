import javax.sound.sampled.AudioFormat

import kuusisto.tinysound.TinySound
import net.beadsproject.beads.core.{AudioContext, IOAudioFormat, UGen}
import net.beadsproject.beads.core.io
import net.beadsproject.beads.core.io.NonrealtimeIO
import net.beadsproject.beads.data.SampleManager
import net.beadsproject.beads.ugens.{Gain, OnePoleFilter, SamplePlayer}


object Beads {
//
    //    val context = new AudioContext(new NonrealtimeIO()) //frame size 4 bytes (16-bit, 2 channel)
    //
    //    val gain = new Gain(Beads.context, 1, 1)
    //    val filter = new OnePoleFilter(context, 200.0.toFloat)
    //
    //    filter.setFrequency(1000)
    //    gain.addInput(filter)
    //    context.out.addInput(gain)
    //
    //    context.start()
    //
    //    def makeSamplePlayer(samplePath: String): SamplePlayer = {
    //        val player = new SamplePlayer(context, SampleManager.sample(samplePath))
    //        player.setKillOnEnd(false);
    //        filter.addInput(player)
    //        player
    //    }
}
