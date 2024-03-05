package qbittorrentapi.Folder

/**
 * Torrent folder API. Access a torrent like a folder.
 */
case class TorrentFolder(name: String, items: Seq[TorrentFileItem]) extends TorrentFileItem(name)
