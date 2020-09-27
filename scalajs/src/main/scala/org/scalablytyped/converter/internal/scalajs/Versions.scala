package org.scalablytyped.converter.internal
package scalajs

object Versions {
  val sbtVersion = "1.4.0-RC2"

  val runtime      = Dep.ScalaJs("com.olvind", "scalablytyped-runtime", "2.2.0")
  val scalaJsDom   = Dep.ScalaJs("org.scala-js", "scalajs-dom", "1.0.0")
  val slinkyWeb    = Dep.ScalaJs("me.shadaj", "slinky-web", "0.6.5")
  val slinkyNative = Dep.ScalaJs("me.shadaj", "slinky-native", "0.6.5")
  val scalajsReact = Dep.ScalaJs("com.github.japgolly.scalajs-react", "core", "1.7.0")

  private val StableVersion = "(\\d+).(\\d+).(\\d+)".r

  case class Scala(scalaVersion: String) {
    val isDotty = scalaVersion.startsWith("0.")

    def scalaOrganization: String =
      if (isDotty) "ch.epfl.lamp" else "org.scala-lang"

    def compiler: Dep.Java =
      if (isDotty) Dep.Java(scalaOrganization, "dotty-compiler_0.28", scalaVersion)
      else Dep.Java(scalaOrganization, "scala-compiler", scalaVersion)

    def library: Dep.Java =
      if (isDotty) Scala213.library
      else Dep.Java(scalaOrganization, "scala-library", scalaVersion)

    def dottyLibrary: Option[Dep.Java] =
      if (isDotty) Some(Dep.Java(scalaOrganization, "dotty-library", scalaVersion))
      else None

    val binVersion: String = scalaVersion match {
      case v if isDotty                   => v.split("\\.").take(2).mkString(".")
      case StableVersion(major, minor, _) => s"$major.$minor"
      case other                          => other
    }

    val sbtDotty: Option[Dep.Scala] = {
      if (isDotty) Some(Dep.Scala("ch.epfl.lamp", "sbt-dotty", "0.4.2")) else None
    }
  }

  val Scala212 = Scala("2.12.11")
  val Scala213 = Scala("2.13.3")
  val Dotty    = Scala("0.28.0-bin-20200925-f4528ce-NIGHTLY")

  case class ScalaJs(scalaJsVersion: String) {
    val scalaJsBinVersion: String =
      scalaJsVersion match {
        case StableVersion("1", _, _)   => "1"
        case StableVersion("0", "6", _) => "0.6"
        case other                      => other
      }

    val scalaJsOrganization = "org.scala-js"
    val sbtPlugin           = Dep.Scala(scalaJsOrganization, "sbt-scalajs", scalaJsVersion)
  }

  val ScalaJs1  = ScalaJs("1.1.0")
  val ScalaJs06 = ScalaJs("0.6.33")
}

case class Versions(scala: Versions.Scala, scalaJs: Versions.ScalaJs) {
  val scalacOptions: List[String] = {
    val base = List(
      "-encoding",
      "utf-8",
      "-feature",
      "-g:notailcalls",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-language:existentials",
    ).filter {
      case str if scala.isDotty => str =/= "-g:notailcalls" // keep original order
      case _                    => true
    }

    val scalaJsDefined =
      if (scalaJs.scalaJsVersion.startsWith("0.6")) List("-P:scalajs:sjsDefinedByDefault")
      else Nil

    val dottyFlag = if (scala.isDotty) List("-scalajs") else Nil

    base ++ scalaJsDefined ++ dottyFlag
  }

  val scalaJsLibrary: Dep =
    Dep
      .Scala(scalaJs.scalaJsOrganization, "scalajs-library", scalaJs.scalaJsVersion)
      .withDottyCompat(scala.isDotty)

  val scalaJsTestInterface: Dep =
    Dep
      .Scala(scalaJs.scalaJsOrganization, "scalajs-test-interface", scalaJs.scalaJsVersion)
      .withDottyCompat(scala.isDotty)

  val scalaJsCompiler: Option[Dep.ScalaFullVersion] =
    if (scala.isDotty) None
    else Some(Dep.ScalaFullVersion(scalaJs.scalaJsOrganization, "scalajs-compiler", scalaJs.scalaJsVersion))

}
