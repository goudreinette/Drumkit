import java.io.File

import com.github.tototoshi.csv.{CSVReader, CSVWriter}

object SampleStorage {
    val file = new File("samples.csv")
    
    def save(pads: Seq[Seq[Pad]]) = {
        val writer: CSVWriter = CSVWriter.open(file)
        writer.writeAll(pads.map(x => x.map((pad: Pad) => pad.samplePath)))
        writer.close()
    }
    
    def loadPads(): List[List[Pad]] =
        for {c <- CSVReader.open(file).all()} yield
            for {path <- c} yield Pad(path)
}
