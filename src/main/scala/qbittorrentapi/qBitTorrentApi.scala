package qbittorrentapi

import akka.http.scaladsl.model.headers.HttpCookie

class qBitTorrentApi(baseUrl: String):
  var sidCookie: HttpCookie

  def connect(username: String, password: String) =
    AuthenticationApi(baseUrl, None)
      .login(LoginParameters(username, password))

  def authentication =
    AuthenticationApi(baseUrl)
