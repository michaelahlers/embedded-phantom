import sbt.Keys._

git.remoteRepo := "git@github.com:michaelahlers/embedded-phantom.git"

/* SBT GitHub Pages is wiping out older versions. See michaelahlers/embedded-phantom#16 for details. */
siteSubdirName in SiteScaladoc := s"${version.value}/api"
