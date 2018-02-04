import kuusisto.tinysound.Music
import kuusisto.tinysound.Sound
import kuusisto.tinysound.TinySound

object Audio {
    
    TinySound.init
    val kick = TinySound.loadSound("samples/kick.wav")
    
    def playFile(file: String) {
        val url = new File(s"samples/$file")
        val audioIn = AudioSystem.getAudioInputStream(url)
        val clip = AudioSystem.getClip
        clip.open(audioIn)
        clip.start
    }
    
    def playKick(): Unit = {
        val url = new File(s"samples/kick.wav")
        val audioIn = AudioSystem.getAudioInputStream(url)
        val clip = AudioSystem.getClip
        clip.open(audioIn)
        clip.start()
        clip.close()
        audioIn.close
    }
}
