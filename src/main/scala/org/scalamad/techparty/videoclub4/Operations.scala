package org.scalamad.techparty.videoclub4

import java.util.UUID

import scala.util.Try

case class Client(id: UUID, name: String)
case class Movie(id: UUID, name: String, client: Option[Client])
case class Library(id: UUID, movies: List[Movie])

trait Operations {
  def addMovie(name: String, library: Library): Library
  def rentMovie(name: String, client: Client, library: Library): Try[Library]
  def deleteMovie(movie: Movie, library: Library): Library
  def findMovie(name: String, library: Library): Option[Movie]
}

class OperationsImpl extends Operations {
  def addMovie(name: String, library: Library): Library =
    library.copy(movies = Movie(id = UUID.randomUUID(), name, None) :: library.movies)

  def rentMovie(name: String, client: Client, library: Library): Try[Library] =
    findMovie(name, library) match {
      case Some(movie) =>
        val newLibrary = deleteMovie(movie, library)
        Try(newLibrary.copy(movies = movie.copy(client = Some(client)) :: newLibrary.movies))
      //Library(newLibrary.id, movie.copy(client = Some(client)) :: newLibrary.movies)
      case None => Try(throw new Exception("No movie founded"))
    }

  def findMovie(name: String, library: Library): Option[Movie] =
    library.movies.find(movie => movie.name.equalsIgnoreCase(name) && movie.client.isEmpty)

  def deleteMovie(movie: Movie, library: Library): Library =
    library.copy(movies = library.movies.filterNot(_.id == movie.id))
}