package org.scala.oop.files

import org.scala.oop.filesystem.FileSystemException

class File(override val parentPath: String, override val name: String, val contents: String)
  extends DirEntry(parentPath, name) {

  override def asDirectory: Directory =
    throw new FileSystemException("A file cannot be converted to a directory!")

  override def asFile: File = this

  override def getType: String = "File"

  override def isDirectory: Boolean = false

  override def isFile: Boolean = true

  def setContents(newContents: String): DirEntry = new File(parentPath, name, newContents)

  def appendContents(newContents: String): DirEntry = setContents(contents + "\n" + newContents)
}

object File {
  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, "")
}