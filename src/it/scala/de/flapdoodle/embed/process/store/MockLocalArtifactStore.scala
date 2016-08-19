package de.flapdoodle.embed.process.store

import java.io.File

import de.flapdoodle.embed.process.config.store.IDownloadConfig
import de.flapdoodle.embed.process.distribution.Distribution

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
object MockLocalArtifactStore
  extends LocalArtifactStore {

  def getArtifact(downloadConfig: IDownloadConfig, distribution: Distribution): File =
    LocalArtifactStore.getArtifact(downloadConfig, distribution)

}
