package model

import java.io.File

import com.github.tototoshi.csv.{CSVReader, CSVWriter}

import scala.io.Source

object PadRepository {
    type Pads = Seq[Seq[Pad]]
    val file = new File("samples.csv")

    def save(pads: Pads) = {
        val writer: CSVWriter = CSVWriter.open(file)
        writer.writeAll(pads.map(x => x.map((pad: Pad) => pad.samplePath)))
        writer.close()
    }

    def loadPads(): Pads =
        if (file exists)
            for {c <- CSVReader.open(file).all()} yield
                for {path <- c} yield Pad(path)
        else
            defaultPads

    def defaultPads: Pads = {
        val kicks = for {c <- 0 until 2} yield for {r <- 0 until 4} yield Pad(getClass.getResource("../samples/kick.wav").getFile)
        val snares = for {c <- 2 until 4} yield for {r <- 0 until 4} yield Pad(getClass.getResource("../samples/kick.wav").getFile)
        kicks ++ snares
    }

}
