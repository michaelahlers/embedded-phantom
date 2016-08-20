javacOptions in(Compile, compile) ++=
  "-source" :: "1.7" ::
    "-target" :: "1.7" ::
    Nil

compileOrder in Compile := CompileOrder.JavaThenScala

compileOrder in Test := CompileOrder.Mixed

scalaVersion := "2.11.8"

/**
 * Strict settings to avoid common bugs. Class files are limited in length to support builds on Windows.
 */
scalacOptions ++=
  "-feature" ::
    "-unchecked" ::
    "-deprecation" ::
    "-target:jvm-1.7" ::
    "-Xfatal-warnings" ::
    "-Xmax-classfile-name" :: "150" ::
    Nil
