javacOptions ++=
  "-source" :: "1.7" ::
    Nil

scalaVersion := "2.11.8"

/**
 * Strict settings to avoid common bugs. Class files are limited in length to support builds on Windows.
 */
scalacOptions ++=
  "-feature" ::
    "-unchecked" ::
    "-deprecation" ::
    // "-target:jvm-1.8" ::
    "-Xfatal-warnings" ::
    "-Xmax-classfile-name" :: "150" ::
    Nil
