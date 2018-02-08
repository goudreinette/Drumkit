package main

import kuusisto.tinysound.TinySound
import model.{Model, PadRepository}

import scala.reflect.runtime.universe._
import scalafx.Includes._
import model.Implicits._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafxml.core.{DependenciesByType, FXMLView}


object FXDrumkit extends JFXApp {
    TinySound.init
    val model = new Model

    stage = new JFXApp.PrimaryStage() {
        title = "Drumkit"
        onCloseRequest = _ => exit
        scene = new Scene(
            FXMLView("layout.fxml",
                new DependenciesByType(Map(
                    typeOf[Model] -> model))))
    }

    model.run

    def exit {
        TinySound.shutdown()
        PadRepository.save(model.pads)
        System.exit(0)
    }
}
