package org.scala.oop.commands

import org.scala.oop.filesystem.State

class Pwd extends Command {
  override def apply(state: State): State =
    state.setMessage(state.workDirectory.path)
}
