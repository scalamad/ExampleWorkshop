package org.scalamad.techparty.videoclub7

import java.util.UUID

import cats.{Applicative, ApplicativeError}

case class Client(id: UUID, name: String)
case class Movie(id: UUID, name: String, client: Option[Client])
case class Library(id: UUID, movies: List[Movie])

trait Operations[F[_]] {
  def addMovie(name: String, library: Library): F[Library]
  def rentMovie(name: String, client: Client, library: Library): F[Library]
  def deleteMovie(movie: Movie, library: Library): F[Library]
  def findMovie(name: String, library: Library): F[Option[Movie]]
}


object OpsInst {

  def impl[F[_] : Applicative]: Operations[F] = new Operations[F] {
    override def addMovie(name: String, library: Library): F[Library] =
      Applicative[F].pure(library.copy(movies = Movie(id = UUID.randomUUID(), name, None) :: library.movies))

    override def deleteMovie(movie: Movie, library: Library): F[Library] =
      Applicative[F].pure(library.copy(movies = library.movies.filterNot(_.id == movie.id)))

    override def findMovie(name: String, library: Library): F[Option[Movie]] =
      Applicative[F].pure(
        library.movies.find(movie => movie.name.equalsIgnoreCase(name) && movie.client.isEmpty))

    override def rentMovie(name: String, client: Client, library: Library): F[Library] = ???
  }

  def implError[F[_]](implicit A: ApplicativeError[F, Throwable]): Operations[F] = new Operations[F] {
    override def addMovie(name: String, library: Library): F[Library] =
      A.pure(library.copy(movies = Movie(id = UUID.randomUUID(), name, None) :: library.movies))

    override def deleteMovie(movie: Movie, library: Library): F[Library] =
      A.pure(library.copy(movies = library.movies.filterNot(_.id == movie.id)))

    override def findMovie(name: String, library: Library): F[Option[Movie]] =
      A.catchNonFatal(
        library.movies.find(movie => movie.name.equalsIgnoreCase(name) && movie.client.isEmpty))

    override def rentMovie(name: String, client: Client, library: Library): F[Library] = ???
  }
}
