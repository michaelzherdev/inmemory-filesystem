package org.scala.oop.commands

import org.scala.oop.files.{DirEntry, Directory}
import org.scala.oop.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.workDirectory.path, name)
}
