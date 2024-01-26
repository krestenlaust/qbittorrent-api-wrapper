package qbittorrentapi

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.{Path, Query}
import akka.http.scaladsl.model.headers.HttpCookie
import akka.http.scaladsl.model.{HttpEntity, HttpRequest, Uri}

import scala.concurrent.Future

// https://github.com/qbittorrent/qBittorrent/wiki/WebUI-API-(qBittorrent-4.1)#login
// https://doc.akka.io/docs/akka-http/current/client-side/request-and-response.html#creating-requests
// https://doc.akka.io/docs/akka-http/current/index.html

case class LoginResult(statusCode: Int, sidCookie: Option[HttpCookie])

class AuthenticationApi(baseUrl: Uri, var sidCookie: Option[HttpCookie]):
  def login(username: String, password: String): Future[LoginResult] =
    val req = HttpRequest(
      uri = baseUrl
        .withPath(Path("/api/v2/auth/login"))
        .withQuery(Query(Map(
          "username" -> username, "password" -> password
        ))),
    )

    Http()
      .singleRequest(req)
      .flatMap { res =>
        Future.successful {
          sidCookie = response.headers.collect {
            case c: `Set-Cookie` if c.cookie.name == "SID" => c.cookie
          }.headOption

          LoginResult(res.status.intValue, sidCookie)
        }
      }

  def logout: Future[Unit] =
    val cookieHeader = akka.http.scaladsl.model.headers.Cookie(sidCookie.get)

    val req = HttpRequest(
      uri = s"${baseUrl}/api/v2/auth/logout",
      headers = List(cookieHeader)
    )

    Http()
      .singleRequest(req)
      .flatMap(_ => Future.successful)
