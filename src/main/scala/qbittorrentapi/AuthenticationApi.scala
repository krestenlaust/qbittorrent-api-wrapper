package qbittorrentapi

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.{ Path, Query }
import akka.http.scaladsl.model.headers.{ `Set-Cookie`, Cookie, HttpCookie }
import akka.http.scaladsl.model.{ HttpEntity, HttpRequest, Uri }

import scala.concurrent.{ ExecutionContext, Future }

// https://github.com/qbittorrent/qBittorrent/wiki/WebUI-API-(qBittorrent-4.1)#login
// https://doc.akka.io/docs/akka-http/current/client-side/request-and-response.html#creating-requests
// https://doc.akka.io/docs/akka-http/current/index.html

class AuthenticationApi(baseUrl: Uri, var sidCookie: Option[HttpCookie])(implicit
    system: ActorSystem,
    ec: ExecutionContext
):
  def login(username: String, password: String): Future[LoginResult] =
    val req = HttpRequest(
      uri = baseUrl
        .withPath(Path("/api/v2/auth/login"))
        .withQuery(
          Query(
            Map(
              "username" -> username,
              "password" -> password
            )
          )
        )
    )

    Http()
      .singleRequest(req)
      .flatMap { res =>
        Future.successful {
          sidCookie = res.headers.collectFirst {
            case c: `Set-Cookie` if c.cookie.name == "SID" => c.cookie
          }

          LoginResult(res.status.intValue, sidCookie)
        }
      }

  def logout: Future[Unit] =
    sidCookie match {
      case Some(cookie) =>
        Http()
          .singleRequest(
            HttpRequest(
              uri = s"${baseUrl}/api/v2/auth/logout",
              headers = List(Cookie(cookie.name, cookie.value))
            )
          )
          .flatMap { response =>
            response.discardEntityBytes().future.map(_ => ())
          }
      case None => Future.successful[Unit](())
    }

  case class LoginResult(statusCode: Int, sidCookie: Option[HttpCookie])
