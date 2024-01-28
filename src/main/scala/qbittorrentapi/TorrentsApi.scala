package qbittorrentapi

import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Get
import akka.http.scaladsl.model.Uri.{Path, Query}
import akka.http.scaladsl.model.headers.HttpCookie
import akka.http.scaladsl.model.{HttpEntity, HttpRequest, Uri}

class TorrentsApi(baseUrl: Uri, sidCookie: Option[HttpCookie]):
  private val apiUrl = s"$baseUrl/api/v2/torrents/"

  def info(tag: Option[String] = None) =
    shortRequest("info", Map(
      "tag" -> tag.getOrElse("")
    ))

  private def shortRequest(methodName: String, requestFields: Map[String, String]) =
    Http().singleRequest(Get(baseUrl
      .withPath(apiUrl + methodName)
      .withQuery(Query(requestFields))
    ))

  def files(hash: String) =
    shortRequest("files", Map(
      "hash" -> hash
    ))

  def pieceStates(hash: String) =
    shortRequest("pieceStates", Map(
      "hash" -> hash
    ))

  /**
   * Sets file priority
   *
   * @param hash Torrent hash
   * @param id   File ids, separated by '|'
   * @param priority
   */
  def filePrio(hash: String, id: String, priority: Int) =
    shortRequest("filePrio", Map(
      "hash" -> hash,
      "id" -> id,
      "priority" -> priority
    ))
