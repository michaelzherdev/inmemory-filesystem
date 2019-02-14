package org.scala.oop.commands
import org.scala.oop.filesystem.State

class UnknownCommand extends Command {
  override def apply(state: State): State = state.setMessage("Command not found!")
}
