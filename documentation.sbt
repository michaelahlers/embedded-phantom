import sbt.Keys._

git.remoteRepo := "git@github.com:michaelahlers/embedded-phantom.git"

siteSubdirName in SiteScaladoc := version.value + "/api"
