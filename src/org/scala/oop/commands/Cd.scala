package org.scala.oop.commands

import org.scala.oop.files.{DirEntry, Directory}
import org.scala.oop.filesystem.State

import scala.annotation.tailrec

class Cd(dir: String) extends Command {

  override def apply(state: State): State = {
    //1. find root
    val root = state.root
    val wd = state.workDirectory

    //2. find absolute path of the directory I want to cd to
    val absolutePath = if (dir.startsWith(Directory.SEPARATOR)) dir
    else if (wd.isRoot) wd.path + dir
    else wd.path + Directory.SEPARATOR + dir

    //3. find the dor to cd to, given the path
    val destDir = doFindDEntry(root, absolutePath)
    //4. change state
    if (destDir == null || !destDir.isDirectory)
      state.setMessage(dir + ": no such directory!")
    else
      State(root, destDir.asDirectory)
  }

  def doFindDEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDir: Directory, path: List[String]): DirEntry = {
      if (path.isEmpty || path.head.isEmpty) currentDir
      else if (path.tail.isEmpty) currentDir.findEntry(path.head)
      else {
        val nextDir = currentDir.findEntry(path.head)
        if (nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }

    @tailrec
    def collapseRelativeTokens(path: List[String], result: List[String]): List[String] = {
      if (path.isEmpty) result
      else if (".".equals(path.head)) collapseRelativeTokens(path.tail, result)
      else if ("..".equals(path.head)) {
        if (result.isEmpty) null
        else collapseRelativeTokens(path.tail, result.init)
      } else collapseRelativeTokens(path.tail, result :+ path.head)
    }

    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList

    val newTokens = collapseRelativeTokens(tokens, List())

    if(newTokens == null) null
    else findEntryHelper(root, newTokens)
  }

}


