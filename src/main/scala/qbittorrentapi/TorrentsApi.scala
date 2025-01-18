package qbittorrentapi

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.headers.HttpCookie
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse, Uri }

import scala.concurrent.{ ExecutionContext, Future }

class TorrentsApi(baseUrl: Uri, sidCookie: Option[HttpCookie])(implicit system: ActorSystem, ec: ExecutionContext):
  private val apiUrl = s"$baseUrl/api/v2/torrents"

  def info(tag: Option[String] = None) =
    shortRequest(
      "info",
      Map(
        "tag" -> tag.getOrElse("")
      )
    )

  private def shortRequest(methodName: String, requestFields: Map[String, String]): Future[HttpResponse] =
    Http()
      .singleRequest(
        HttpRequest(
          uri = Uri(s"${apiUrl}/${methodName}").withQuery(Query(requestFields))
        )
      )

  def files(hash: String): Future[HttpResponse] =
    shortRequest(
      "files",
      Map(
        "hash" -> hash
      )
    )

  def pieceStates(hash: String): Future[HttpResponse] =
    shortRequest(
      "pieceStates",
      Map(
        "hash" -> hash
      )
    )

  /** Sets file priority
    *
    * @param hash     Torrent hash
    * @param id       File ids, separated by '|'
    * @param priority TODO: Document priority
    */
  def filePrio(hash: String, id: String, priority: Int): Future[HttpResponse] =
    shortRequest(
      "filePrio",
      Map(
        "hash"     -> hash,
        "id"       -> id,
        "priority" -> priority.toString
      )
    )
