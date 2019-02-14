package org.scala.oop.filesystem

import org.scala.oop.files.Directory

class State(val root: Directory, val workDirectory: Directory, val output: String) {

  def show: Unit =
    println(output)
    print(State.SHELL_TOKEN)

  def setMessage(message: String): State = State(root, workDirectory, message)
}

object State {
  val SHELL_TOKEN = "$ "

  def apply(root: Directory, workDirectory: Directory, output: String = ""): State =
    new State(root, workDirectory, output)
}
