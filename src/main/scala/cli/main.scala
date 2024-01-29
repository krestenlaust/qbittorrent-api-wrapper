package cli

import qbittorrentapi.qBitTorrentApi

@main def main() =
  val torrentApi = qBitTorrentApi("http://127.0.0.1:8080/")
  println(torrentApi.torrents.info())
