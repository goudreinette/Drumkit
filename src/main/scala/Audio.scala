import java.io.File
import javax.sound.sampled.AudioSystem

object Audio {
    def playFile(file: String) {
        val url = new File(s"samples/$file")
        val audioIn = AudioSystem.getAudioInputStream(url)
        val clip = AudioSystem.getClip
        clip.open(audioIn)
        clip.start
    }
}
