package org.scala.oop.commands
import org.scala.oop.filesystem.State

class Cat(filename: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.workDirectory
    val dirEntry = wd.findEntry(filename)
    if(dirEntry == null || !dirEntry.isFile)
      state.setMessage(filename + ": no such file")
    else state.setMessage(dirEntry.asFile.contents)
  }
}
