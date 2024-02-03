package qbittorrentapi

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.HttpCookie

import scala.concurrent.{ExecutionContext, Future}

object qBitTorrentApi:
  def connect(baseUrl: String, username: String, password: String)(implicit system: ActorSystem, ec: ExecutionContext): Future[Option[HttpCookie]] =
    AuthenticationApi(baseUrl, None)(system, ec)
      .login(username, password)
      .map(res => res.sidCookie)


class qBitTorrentApi(baseUrl: String, sidCookie: Option[HttpCookie])(implicit system: ActorSystem, ec: ExecutionContext):
  def authentication: AuthenticationApi =
    AuthenticationApi(baseUrl, sidCookie)

  def torrents: TorrentsApi =
    TorrentsApi(baseUrl, sidCookie)