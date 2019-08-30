package BankApplication

import BankApplication.implementations.{BankAccountDB, BankAccountMemory}
import BankApplication.models.BankError
import BankApplication.models.models.BankAccount

trait BankAccountDSL[F[_]] {

  def createBankAccount(name: String,
                        pinCode: String,
                        clientId: Long): F[BankAccount]

  def getBalance(accountId: Long): F[Either[BankError, Double]]

  def makeDeposit(accountId: Long, ammount: Double): F[Either[BankError, Double]]

  def makeWithdraw(accountId: Long, ammount: Double):F[Either[BankError, Double]]

  def makeTransfer(accountIdSource: Long,
                   accountIdDestination: Long,
                   ammount: Double): F[Either[BankError, Double]]

}

object BankAccountDSL {

  def apply[F[_]](implicit ev: BankAccountDSL[F]): BankAccountDSL[F] = ev

  def memoryImp[F[_]]: BankAccountDSL[F] = new BankAccountMemory[F]

  def dbImp[F[_]]: BankAccountDSL[F] = BankAccountDB[F]

}
