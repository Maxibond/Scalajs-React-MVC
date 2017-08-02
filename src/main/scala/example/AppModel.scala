package diodeERP

import java.util.UUID

import diode.Action


case class AppModel(employees: Employees)

case class EmployeeId(id: UUID) {
  override def toString: String = this.id.toString
}
object EmployeeId {
    def random = new EmployeeId(UUID.randomUUID)
}

case class Employees(employeeList: Seq[Employee])

case class EmployeeData(firstname: String, lastname: String, phone_number: String, photo: String)

case class Employee(id: EmployeeId, firstname: String, lastname: String, phone_number: String, photo: String)

// routes

sealed trait Pages

case object HomeP extends Pages

case object EmployeeCreateP extends Pages

case class EmployeeEditP(id: String) extends Pages


// actions
case object InitEmployees extends Action

case class AddEmployee(emp: EmployeeData) extends Action

case class Update(emp: Employee) extends Action

case class Delete(id: EmployeeId) extends Action