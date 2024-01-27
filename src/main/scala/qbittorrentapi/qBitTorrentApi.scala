package qbittorrentapi

import akka.http.scaladsl.model.headers.HttpCookie
import scala.util.Try

class qBitTorrentApi(baseUrl: String):
  var sidCookie: HttpCookie

  def connect(username: String, password: String): Try[Unit] =
    Try(sidCookie =
      AuthenticationApi(baseUrl, None)
      .login(LoginParameters(username, password))
      .get)

  def authentication =
    AuthenticationApi(baseUrl, None)

  def torrents =
    TorrentsApi(baseUrl, None)