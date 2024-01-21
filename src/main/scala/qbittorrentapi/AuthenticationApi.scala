package qbittorrentapi

import akka.http.scaladsl.model.Uri.{Path, Query}
import akka.http.scaladsl.model.{HttpEntity, HttpRequest, Uri}

import scala.concurrent.Future

case class LoginParameters(username: String, password: String)

case class LoginResult(statusCode: Int)

class AuthenticationApi(baseUrl: Uri):
  def login(loginParameters: LoginParameters): Future[LoginResult] =
    HttpRequest(
      uri = baseUrl
        .withPath(Path("/api/v2/auth/login"))
        .withQuery(Query(Map(
          "username" -> loginParameters.username, "password" -> loginParameters.password
        ))),
    )

  def logout: Future[Unit] =
    HttpRequest(uri = s"${baseUrl}/api/v2/auth/logout")
