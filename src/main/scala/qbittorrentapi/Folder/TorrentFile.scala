package qbittorrentapi.Folder

import scala.concurrent.Future

case class TorrentFile(name: String, file: Future[java.io.File], size: Int) extends TorrentFileItem(name)
