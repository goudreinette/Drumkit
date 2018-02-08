package model

import java.net.URL

object Implicits {
    implicit def resourceStringToURL(path: String): URL =
        ClassLoader.getSystemResource(s"${path}")
}
