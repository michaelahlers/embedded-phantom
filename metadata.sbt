organization := "consulting.ahlers"

name := "embedded-phantom"

description := "Automatic installation and process management of PhantomJS for applications on the Java platform."

homepage := Some(url("http://github.com/michaelahlers/embedded-phantom"))

startYear := Some(2016)

developers :=
  Developer("michaelahlers", "Michael Ahlers", "michael@ahlers.consulting", url("http://github.com/michaelahlers")) ::
    Nil

scmInfo :=
  Some(ScmInfo(
    browseUrl = url("http://github.com/michaelahlers/embedded-phantom"),
    connection = "scm:git:https://github.com:michaelahlers/embedded-phantom.git",
    devConnection = Some("scm:git:git@github.com:michaelahlers/embedded-phantom.git")
  ))

licenses :=
  "MIT" -> url("http://opensource.org/licenses/MIT") ::
    Nil
