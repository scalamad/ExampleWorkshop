package org.scalamad.techparty.videoclub5

import java.util.UUID

import scala.util.Try

case class Client(id: UUID, name: String)
case class Movie(id: UUID, name: String, client: Option[Client])
case class Library(id: UUID, movies: List[Movie])

trait Operations[F[_]] {
  def addMovie(name: String, library: Library): F[Library]
  def rentMovie(name: String, client: Client, library: Library): F[Library]
  def deleteMovie(movie: Movie, library: Library): F[Library]
  def findMovie(name: String, library: Library): F[Movie]
}

class OperationsImplTry extends Operations[Try] {
  def addMovie(name: String, library: Library): Try[Library] =
    Try(library.copy(movies = Movie(id = UUID.randomUUID(), name, None) :: library.movies))

  def rentMovie(name: String, client: Client, library: Library): Try[Library] =
    findMovie(name, library).flatMap { movie =>
      val newLibrary = deleteMovie(movie, library)
      newLibrary.map(nl => nl.copy(movies = movie.copy(client = Some(client)) :: nl.movies))
    }

  def findMovie(name: String, library: Library): Try[Movie] =
    library.movies.find(movie => movie.name.equalsIgnoreCase(name) && movie.client.isEmpty) match {
      case Some(lib) => Try(lib)
      case None => Try(throw new Exception("No movie founded"))
    }

  def deleteMovie(movie: Movie, library: Library): Try[Library] =
    Try(library.copy(movies = library.movies.filterNot(_.id == movie.id)))
}

class OperationsImplOption extends Operations[Option] {
  def addMovie(name: String, library: Library): Option[Library] =
    Some(library.copy(movies = Movie(id = UUID.randomUUID(), name, None) :: library.movies))

  def rentMovie(name: String, client: Client, library: Library): Option[Library] =
    findMovie(name, library).flatMap { movie =>
      val newLibrary = deleteMovie(movie, library)
      newLibrary.map(nl => nl.copy(movies = movie.copy(client = Some(client)) :: nl.movies))
    }

  def findMovie(name: String, library: Library): Option[Movie] =
    library.movies.find(movie => movie.name.equalsIgnoreCase(name) && movie.client.isEmpty)

  def deleteMovie(movie: Movie, library: Library): Option[Library] =
    Some(library.copy(movies = library.movies.filterNot(_.id == movie.id)))
}