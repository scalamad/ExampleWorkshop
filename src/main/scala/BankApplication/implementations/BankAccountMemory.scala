package BankApplication.implementations

import BankApplication.models.{BankError, models}
import BankApplication.BankAccountDSL

class BankAccountMemory[F[_]] extends BankAccountDSL[F] {

  def createBankAccount(name: String, pinCode: String, clientId: Long): F[models.BankAccount] = ???

  def getBalance(accountId: Long): F[Either[BankError, Double]] = ???

  def makeDeposit(accountId: Long, ammount: Double): F[Either[BankError, Double]] = ???

  def makeWithdraw(accountId: Long, ammount: Double): F[Either[BankError, Double]] = ???

  def makeTransfer(accountIdSource: Long, accountIdDestination: Long, ammount: Double): F[Either[BankError, Double]] = ???
}
