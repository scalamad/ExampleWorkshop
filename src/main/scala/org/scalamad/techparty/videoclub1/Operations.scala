package org.scalamad.techparty.videoclub1

//class Movie(name: String, rented: Boolean = false)
class Movie(val name: String, rented: Boolean)

object Movie {
  def apply(name: String): Movie = new Movie(name, false)  
}

class Library(val movies: List[Movie])

object Library {
  def apply(movies: List[Movie]): Library = new Library(movies)
}

trait Operations {
  def addMovie(name: String, library: Library): Library =
   Library(Movie(name) :: library.movies)

  def existsMovie(name: String, library: Library): Boolean =
    library.movies match {
      case movie :: ht => movie.name.equalsIgnoreCase(name) || existsMovie(name, Library(ht))
      case movie :: Nil => movie.name.equalsIgnoreCase(name)
      case Nil => false
    }

  // library.exists(_.movie.name.equalsIgnoreCase(name))
}