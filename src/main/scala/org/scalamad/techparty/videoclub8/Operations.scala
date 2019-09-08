package org.scalamad.techparty.videoclub8

import java.util.UUID

import cats.{Applicative, ApplicativeError}
import cats._
import cats.implicits._

case class Client(id: UUID, name: String)
case class Movie(id: UUID, name: String, client: Option[Client])
case class Library(id: UUID, movies: List[Movie])

trait Operations[F[_]] {
  def addMovie(name: String, library: Library): F[Library]
  def rentMovie(name: String, client: Client, library: Library): F[Library]
  def deleteMovie(movie: Movie, library: Library): F[Library]
  def findMovie(name: String, library: Library): F[Option[Movie]]
}


object OpsInstNormal {

  def impl[F[_]: Monad]: Operations[F] = new Operations[F] {
    override def addMovie(name: String, library: Library): F[Library] =
      Monad[F].pure(library.copy(movies = Movie(id = UUID.randomUUID(), name, None) :: library.movies))

    override def deleteMovie(movie: Movie, library: Library): F[Library] =
      Applicative[F].pure(library.copy(movies = library.movies.filterNot(_.id == movie.id)))

    override def findMovie(name: String, library: Library): F[Option[Movie]] =
      Applicative[F].pure(
        library.movies.find(movie => movie.name.equalsIgnoreCase(name) && movie.client.isEmpty))

    override def rentMovie(name: String, client: Client, library: Library): F[Library] =
      findMovie(name, library).flatMap { movie =>
        movie match {
          case Some(m) =>
            deleteMovie(m, library).map( nl =>
              nl.copy(movies = m.copy(client = Some(client)) :: nl.movies)
            )
          case None => Applicative[F].pure(library)
        }
      }
  }

}

object OpsInstError {

  def impl[F[_]](implicit A: MonadError[F, Throwable]): Operations[F] = new Operations[F] {
    override def addMovie(name: String, library: Library): F[Library] =
      Monad[F].pure(library.copy(movies = Movie(id = UUID.randomUUID(), name, None) :: library.movies))

    override def deleteMovie(movie: Movie, library: Library): F[Library] =
      Applicative[F].pure(library.copy(movies = library.movies.filterNot(_.id == movie.id)))

    override def findMovie(name: String, library: Library): F[Option[Movie]] =
      Applicative[F].pure(
        library.movies.find(movie => movie.name.equalsIgnoreCase(name) && movie.client.isEmpty))

    override def rentMovie(name: String, client: Client, library: Library): F[Library] =
      findMovie(name, library).flatMap { movie =>
        movie match {
          case Some(m) =>
            deleteMovie(m, library).map( nl =>
              nl.copy(movies = m.copy(client = Some(client)) :: nl.movies)
            )
          case None => A.raiseError(new Exception("Movie cannot be rented"))
        }
      }
  }

}
