package de.flapdoodle.embed.process.store

import java.io.{File, PrintWriter}

import org.scalatest.{FlatSpec, Matchers}

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class DigestDownloadVerifierSpec
  extends FlatSpec
          with Matchers {

  it must "verify files" in {
    val signature =
      "7519e1d06d0e0f235540c1a9aa2ddd0e4afa45f3c211a96332f22ae0a97d1ab9"
        .sliding(2, 2).toArray.map(Integer.parseInt(_, 16).toByte)

    val verifier = new DigestDownloadVerifier("SHA-256", signature)

    val sample = File.createTempFile(getClass.getSimpleName, "")

    new PrintWriter(sample.getAbsolutePath) {
      println("The quick red fox jumps over the lazy brown dog.")
      close()
    }

    verifier.isValid(sample) should be(true)
  }

}
