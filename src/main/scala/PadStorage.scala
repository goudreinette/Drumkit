import java.io.File

import com.github.tototoshi.csv.{CSVReader, CSVWriter}

object PadStorage {
    val file = new File("samples.csv")
    
    def save(pads: Seq[Seq[Pad]]) = {
        val writer: CSVWriter = CSVWriter.open(file)
        writer.writeAll(pads.map(x => x.map((pad: Pad) => pad.samplePath)))
        writer.close()
    }
    
    def loadPads(): Seq[Seq[Pad]] =
        if (file exists)
            for {c <- CSVReader.open(file).all()} yield
                for {path <- c} yield Pad(path)
        else
            defaultPads
    
    def defaultPads: Seq[Seq[Pad]] = {
        val kicks = for {c <- 0 until 2} yield for {r <- 0 until 4} yield Pad("samples/kick.wav")
        val snares = for {c <- 2 until 4} yield for {r <- 0 until 4} yield Pad("samples/snare.wav")
        kicks ++ snares
    }
}
