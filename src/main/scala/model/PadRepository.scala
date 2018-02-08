package model

import java.io.File
import java.net.URL

import com.github.tototoshi.csv.{CSVReader, CSVWriter}

import scala.io.Source
import scala.languageFeature.implicitConversions


object PadRepository {
    type Pads = Seq[Seq[Pad]]
    val file = new File("samples.csv")

    implicit def resourceStringToURL(path: String): URL =
        getClass.getResource(s"../${path}")


    def save(pads: Pads) = {
        val writer: CSVWriter = CSVWriter.open(file)
        writer.writeAll(pads.map(x => x.map((pad: Pad) => pad.sampleUrl)))
        writer.close()
    }

    def loadPads(): Pads =
        if (file exists)
            for {c <- CSVReader.open(file).all()} yield
                for {path <- c} yield Pad(new URL(path))
        else
            defaultPads

    def defaultPads: Pads = {
        val kicks = for {c <- 0 until 2} yield for {r <- 0 until 4} yield Pad("samples/kick.wav")
        val snares = for {c <- 2 until 4} yield for {r <- 0 until 4} yield Pad("/samples/kick.wav")
        kicks ++ snares
    }

}
