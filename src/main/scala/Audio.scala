import net.beadsproject.beads.core.AudioContext
import net.beadsproject.beads.core.io.JavaSoundAudioIO
import net.beadsproject.beads.data.SampleManager
import net.beadsproject.beads.ugens.{Gain, SamplePlayer}

object Audio {
    val context = new AudioContext
    val gain = new Gain(Audio.context, 1, 1)

    context.out.addInput(gain)
    context.start()

    def makeSamplePlayer(samplePath: String): SamplePlayer = {
        val player = new SamplePlayer(Audio.context, SampleManager.sample(samplePath))
        player.setKillOnEnd(false);
        gain.addInput(player)
        player
    }
}
