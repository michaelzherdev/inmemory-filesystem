package org.scala.oop.commands
import org.scala.oop.files.DirEntry
import org.scala.oop.filesystem.State

class Ls extends Command {

  override def apply(state: State): State = {
    val contents = state.workDirectory.contents
    val niceOutput = createNiceOutput(contents)
    state.setMessage(niceOutput)
  }

  def createNiceOutput(contents: List[DirEntry]): String = {
    if(contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + "[" + entry.getType + "]\n" + createNiceOutput(contents.tail)
    }
  }
}
