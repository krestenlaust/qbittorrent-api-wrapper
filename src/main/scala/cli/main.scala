package cli

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route.seal
import qbittorrentapi.qBitTorrentApi

import scala.concurrent.ExecutionContext

@main def main() =
  implicit val system = ActorSystem("qbittorrent-api-system")
  implicit val executionContext: ExecutionContext = system.dispatcher

  val torrentApi = qBitTorrentApi("http://127.0.0.1:8080/", None)(system, executionContext)
  println(torrentApi.torrents.info())
