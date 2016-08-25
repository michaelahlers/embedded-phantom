package ahlers.phantom.embedded

import java.util

import com.google.common.base.Optional
import de.flapdoodle.embed.process.config.store.FileSet
import de.flapdoodle.embed.process.config.store.FileSet.Entry
import de.flapdoodle.embed.process.config.store.FileType.Executable
import de.flapdoodle.embed.process.distribution.ArchiveType._
import de.flapdoodle.embed.process.distribution.BitSize._
import de.flapdoodle.embed.process.distribution.Platform._
import de.flapdoodle.embed.process.distribution._
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.collection.convert.WrapAsScala._
import scala.language.postfixOps

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
class PhantomPackageResolverSpec
  extends WordSpec
          with Matchers
          with MockFactory {

  val resolver = PhantomPackageResolver.getInstance()

  import resolver._
  import PhantomPackageResolver._

  /** Exercises all permutations contributing to [[Distribution]] objects, matching supported cases. */
  for (version <- PhantomVersion.values; platform <- Platform.values; bitsize <- BitSize.values) {
    val distribution = new Distribution(version, platform, bitsize)

    s"""Distribution "$distribution"""" must {

      def haveArchiveType(archiveType: ArchiveType) = {
        s"""have archive type "$archiveType"""" in {
          getArchiveType(distribution) should be(archiveType)
        }
      }

      platform match {
        case Linux =>
          haveArchiveType(TBZ2)
        case OS_X | Windows =>
          haveArchiveType(ZIP)
        case _ =>
          an[UnsupportedPlatformException] should be thrownBy getArchiveType(distribution)
      }

      def havePlatformClassifier(classifier: String) = {
        s"""have platform classifier "$classifier"""" in {
          platformClassifierFor(distribution) should be(classifier)
        }
      }

      platform match {
        case Linux =>
          havePlatformClassifier("linux")
        case OS_X =>
          havePlatformClassifier("macosx")
        case Windows =>
          havePlatformClassifier("windows")
        case _ =>
          "have no platform classifier" in {
            an[UnsupportedPlatformException] should be thrownBy platformClassifierFor(distribution)
          }
      }

      def haveBitsizeClassifier(classifier: Optional[String]) = {
        s"""have bitsize classifier "$classifier"""" in {
          bitsizeClassifierFor(distribution) should be(classifier)
        }
      }

      (platform, bitsize) match {
        case (Linux, B32) =>
          haveBitsizeClassifier(Optional.of("i686"))
        case (Linux, B64) =>
          haveBitsizeClassifier(Optional.of("x86_64"))
        case (Linux, _) =>
          "have no bitsize classifier" in {
            an[UnsupportedBitsizeException] should be thrownBy bitsizeClassifierFor(distribution)
          }
        case _ =>
          haveBitsizeClassifier(Optional.absent[String])
      }

      def haveExtractedFileSet(files: FileSet) = {
        val executables =
          files
            .entries()
            .filter(Executable == _.`type`)
            .map(_.destination)
            .mkString("\"", "\";\"", "\"")

        val entryCount: Int = files.entries.size

        s"""have file set with executables $executables and $entryCount total entries""" in {
          /* Until flapdoodle-oss/de.flapdoodle.embed.process#44 is merged, manually extract and compare material values. */
          def tupled(entry: Entry) = (entry.`type`, entry.destination, entry.matchingPattern.pattern)
          getFileSet(distribution).entries.map(tupled) should contain theSameElementsAs files.entries.map(tupled)
        }
      }

      platform match {
        case Linux | OS_X =>
          haveExtractedFileSet {
            FileSet.builder()
              .addEntry(Executable, "phantomjs")
              .build()
          }
        case Windows =>
          haveExtractedFileSet {
            FileSet.builder()
              .addEntry(Executable, "phantomjs.exe")
              .build()
          }
        case _ =>
          "have no file set" in {
            an[UnsupportedPlatformException] should be thrownBy getFileSet(distribution)
          }
      }

      /** While apparently redundant, verify *only* the archive type participates in the mapping. */
      ArchiveType.values foreach { archiveType =>

        def haveArchiveExtension(extension: String) = {
          s"""have for archive type "$archiveType" extension "$extension"""" in {
            archiveExtensionFor(distribution, archiveType) should be(extension)
          }
        }

        archiveType match {
          case TBZ2 =>
            haveArchiveExtension("tar.bz2")
          case ZIP =>
            haveArchiveExtension("zip")
          case _ =>
            s"""have no extension for archive type "$archiveType"""" in {
              an[UnsupportedArchiveException] should be thrownBy archiveExtensionFor(distribution, archiveType)
            }
        }

      }

    }
  }

  "Distribution path" must {

    "represent distribution" when {
      util.EnumSet.of(Linux, OS_X, Windows) foreach { platform =>
        s"""platform is "$platform"""" in {
          val version = mock[IVersion]
          (version.asInDownloadPath _).expects().returns("VERSION").anyNumberOfTimes()

          val distribution = new Distribution(version, platform, B32)

          val platformClassifier = platformClassifierFor(distribution)

          getPath(distribution) should be {
            platform match {
              case Linux =>
                s"phantomjs-${version.asInDownloadPath}-$platformClassifier-i686.tar.bz2"
              case _ =>
                s"phantomjs-${version.asInDownloadPath}-$platformClassifier.zip"
            }
          }
        }
      }

    }

  }
}
