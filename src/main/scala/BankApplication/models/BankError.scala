package BankApplication.models

sealed trait BankError

//TODO: modelar los distintos tipos de errores

final case class ErrorType1(message: String) extends BankError