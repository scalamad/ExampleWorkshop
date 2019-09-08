package org.scalamad.techparty.videoclub8

import java.util.UUID

import cats._
import cats.effect.IO
import cats.implicits._

import scala.util.Try

object Main extends App {
  val lib = Library(UUID.randomUUID(), Nil)
  val client = Client(UUID.randomUUID(), "Jose")

  val ops = OpsInstNormal.impl[Option]
  val ops2 = OpsInstNormal.impl[Try]

  val opsio = OpsInstError.impl[IO]

  val lib1 = opsio.addMovie("Lord of the rings", lib)

  val z = lib1.map{ lib =>
    println(lib)
  }

  z.unsafeRunSync()
}
