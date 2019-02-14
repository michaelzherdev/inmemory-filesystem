package org.scala.oop.commands

import org.scala.oop.files.Directory
import org.scala.oop.filesystem.State

class Rm(name: String) extends Command {

  override def apply(state: State): State = {
    // get work dir
    val wd = state.workDirectory

    // get absolute path
    val absolutePath =
      if (name.startsWith(Directory.SEPARATOR)) name
      else if (wd.isRoot) wd.path + name
      else wd.path + Directory.SEPARATOR + name

    // do checks
    if (Directory.ROOT_PATH.equals(absolutePath))
      state.setMessage("ROOT cannot be removed!")
    else
      doRm(state, absolutePath)
  }

  def doRm(state: State, path: String): State = {

    def rmHelper(currentDir: Directory, path: List[String]): Directory = {
      if (path.isEmpty) currentDir
      else if (path.tail.isEmpty) currentDir.removeEntry(path.head)
      else {
        val nextDir = currentDir.findEntry(path.head)
        if (!nextDir.isDirectory) currentDir
        else {
          val newNextDirectory = rmHelper(nextDir.asDirectory, path.tail)
          if (newNextDirectory == nextDir) currentDir
          else currentDir.replaceEntry(path.head, newNextDirectory)
        }
      }
    }
    // find the entry to remove
    // update structure

    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val newRoot: Directory = rmHelper(state.root, tokens)

    if (newRoot == state.root)
      state.setMessage(path + ": no such file or directory")
    else
      State(newRoot, newRoot.findDescendant(state.workDirectory.path.substring(1)))
  }
}
