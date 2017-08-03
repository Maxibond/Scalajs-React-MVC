package diodeERP

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object EmployeeView {

  case class Props(employee: Employee,
                   edit: VdomTag)

  case class State(data: Employee)

  class Backend($ : BackendScope[Props, State]) {

    def render(p: Props, s: State): VdomElement = {
      <.div(
        ^.className := "thumbnail",
        <.table(
          ^.className := "table",
          <.tr(
            <.td("Id"),
            <.td(p.employee.id.toString)
          ),
          <.tr(
            <.td("Firstname"),
            <.td(p.employee.firstname)
          ),
          <.tr(
            <.td("Lastname"),
            <.td(p.employee.lastname)
          ),
          <.tr(
            <.td("Phone number"),
            <.td(p.employee.phone_number)
          )
        ),
        p.edit
      )
    }

  }

  val component = ScalaComponent
    .builder[Props]("EmployeeView")
    .initialStateFromProps(p => State(p.employee))
    .renderBackend[Backend]
    .build

  def apply(P: Props) =
    component.withKey(P.employee.id.toString)(P)
}