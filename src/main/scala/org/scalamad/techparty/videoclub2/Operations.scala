package org.scalamad.techparty.videoclub2

case class Movie(name: String, rented: Boolean)
case class Library(movies: List[Movie])

trait Operations {
  def addMovie(name: String, library: Library): Library =
    Library(Movie(name, true) :: library.movies)

  def existsMovie(name: String, library: Library): Boolean =
    library.movies match {
      case Movie(nm, _) :: ht => nm.equalsIgnoreCase(name) || existsMovie(name, Library(ht))
      case Movie(nm, _) :: Nil => nm.equalsIgnoreCase(name)
      case Nil => false
    }

}
