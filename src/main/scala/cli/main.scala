package cli

import qbittorrentapi.qBitTorrentApi

@main def main() =
  val torrentApi = qBitTorrentApi("http://127.0.0.1:8080/")
  torrentApi.connect("admin", "123123")
