package org.scalamad.techparty.videoclub5

import java.util.UUID

object MainOption extends App {

  val ops = new OperationsImplOption()

  val lib = Library(UUID.randomUUID(), Nil)
  val client = Client(UUID.randomUUID(), "Jose")

  val lib1: Option[Library] = ops.addMovie("LordOfTheRings", lib)
  val lib2: Option[Library] = lib1.flatMap( nl => ops.addMovie("LordOfTheRings", nl))
  val lib3: Option[Library] = lib2.flatMap( nl => ops.rentMovie("LordOfTheRings", client, nl))
  val lib4: Option[Library] = lib3.map(_.copy(id = UUID.randomUUID()))

  val lib3Bis: Option[Library] = ops.addMovie("LordOfTheRings", lib).flatMap{ lib1 =>
    ops.addMovie("LordOfTheRings", lib1).flatMap { lib2 =>
      ops.rentMovie("LordOfTheRings", client, lib2).map( lib3 =>
        lib3.copy(id = UUID.randomUUID())
      )
    }
  }

  val lib3For = for {
   lib1 <- ops.addMovie("LordOfTheRings", lib)
   lib2 <- ops.addMovie("LordOfTheRings", lib1)
   lib3 <- ops.rentMovie("LordOfTheRings", client, lib2)
  } yield lib3.copy(id = UUID.randomUUID())

}
