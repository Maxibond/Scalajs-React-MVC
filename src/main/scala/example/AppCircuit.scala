package diodeERP

import diode._
import diode.react.ReactConnector


object AppCircuit extends Circuit[AppModel] with ReactConnector[AppModel] {
    
    def initialModel = AppModel(Employees(Seq()))

    override val actionHandler: HandlerFunction = composeHandlers(
        new EmployeeHandler(zoomTo(_.employees.employeeList))
    )
}

class EmployeeHandler[M](modelRW: ModelRW[M, Seq[Employee]]) extends ActionHandler(modelRW) {

    def updateOne(Id: EmployeeId)(f: Employee => Employee): Seq[Employee] = {
        value.map {
            case found@Employee(Id, _, _, _, _) => f(found)
            case other => other
        }
    }

    override def handle = {
        case InitEmployees =>
            println("Initializing employees")
            updated(List(Employee(EmployeeId.random, "Maxim", "Mikheev", "+79655859999", "")))
        case AddEmployee(emp) =>
            println("creating: ", emp)
            updated(value :+ Employee(id = EmployeeId.random,
                                      firstname = emp.firstname,
                                      lastname = emp.lastname,
                                      phone_number = emp.phone_number,
                                      photo = emp.photo))
        case Update(emp) =>
            updated(updateOne(emp.id)(_.copy(firstname = emp.firstname,
                                             lastname = emp.lastname,
                                             phone_number = emp.phone_number)))
        case Delete(id) =>
            updated(value.filterNot(_.id == id))
    }
}