package org.scala.oop.commands

import org.scala.oop.files.{DirEntry, File}
import org.scala.oop.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.workDirectory.path, name)
}
