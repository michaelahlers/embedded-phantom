javacOptions in Compile ++=
  "-source" :: "1.7" ::
    "-target" :: "1.7" ::
    "-g:lines" ::
    Nil

crossPaths in Compile := false

autoScalaLibrary in Compile := false

scalaVersion in Test := "2.11.8"

/**
 * Strict settings to avoid common bugs. Class files are limited in length to support builds on Windows.
 */
scalacOptions in Test ++=
  "-feature" ::
    "-unchecked" ::
    "-deprecation" ::
    // "-target:jvm-1.8" ::
    "-Xfatal-warnings" ::
    "-Xmax-classfile-name" :: "150" ::
    Nil
