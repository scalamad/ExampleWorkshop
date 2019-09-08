package org.scalamad.techparty.videoclub6

import java.util.UUID

import cats.{Applicative, Functor}

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

/*
object OpsInst {

  def impl[F[_]]: Operations[F] = new Operations[F] {
    override def addMovie(name: String, library: Library): F[Library] = ???

    override def rentMovie(name: String, client: Client, library: Library): F[Library] = ???

    override def deleteMovie(movie: Movie, library: Library): F[Library] = ???

    override def findMovie(name: String, library: Library): F[Movie] = ???
  }
}
*/

object OpsInst {

  def impl[F[_] : Applicative]: Operations[F] = new Operations[F] {
    override def addMovie(name: String, library: Library): F[Library] =
      Applicative[F].pure(library.copy(movies = Movie(id = UUID.randomUUID(), name, None) :: library.movies))

    override def deleteMovie(movie: Movie, library: Library): F[Library] =
      Applicative[F].pure(library.copy(movies = library.movies.filterNot(_.id == movie.id)))

    override def findMovie(name: String, library: Library): F[Movie] = ???

    override def rentMovie(name: String, client: Client, library: Library): F[Library] = ???
  }
}
