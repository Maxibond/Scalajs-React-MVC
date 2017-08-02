package diodeERP

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import java.util.UUID

object EmployeeEdit {

  case class Props(employeeId: UUID, router: RouterCtl[Pages])

  case class State(emp: Option[Employee])

  class Backend($ : BackendScope[Props, State]) {

    def editFieldChangedFirstname(e: ReactEventFromInput) = {
      val value = e.target.value.trim
      CallbackTo { $.modState(state => state.copy(emp = Some(state.emp.get.copy(firstname = value)))) }.flatten
    }

    def editFieldChangedLastname(e: ReactEventFromInput) = {
      val value = e.target.value.trim
      CallbackTo { $.modState(state => state.copy(emp = Some(state.emp.get.copy(lastname = value)))) }.flatten
    }

    def editFieldChangedPhoneNumber(e: ReactEventFromInput) = {
      val value = e.target.value.trim
      CallbackTo { $.modState(state => state.copy(emp = Some(state.emp.get.copy(phone_number = value)))) }.flatten
    }

    def render(p: Props, s: State): VdomElement = {
      if (s.emp.isEmpty) {
        <.div(
          <.span("Not Found"),
          p.router.link(HomeP)("Back to Home")
        )
      } else {
        <.div(
          <.p(p.employeeId.toString),
          <.p(s.emp.get.toString),
          <.form(
            <.div(
              ^.className := "form-group",
              <.label("First name"),
              <.input(
                ^.className := "form-control",
                ^.onChange ==> editFieldChangedFirstname,
                ^.value := s.emp.get.firstname
              )
            ),
            <.div(
              ^.className := "form-group",
              <.label("Last name"),
              <.input(
                ^.className := "form-control",
                ^.onChange ==> editFieldChangedLastname,
                ^.value := s.emp.get.lastname
              )
            ),
            <.div(
              ^.className := "form-group",
              <.label("Phone number"),
              <.input(
                ^.className := "form-control",
                ^.onChange ==> editFieldChangedPhoneNumber,
                ^.value := s.emp.get.phone_number
              )
            ),
            <.button("Save",
              ^.className := "btn btn-success",
              ^.onClick --> { AppCircuit.dispatch(Update(s.emp.get)); p.router.set(HomeP) }
            )
          ),
          <.button("Delete",
            ^.className := "btn btn-danger",
            ^.onClick --> { AppCircuit.dispatch(Delete(s.emp.get.id)); p.router.set(HomeP) }
          ),
          p.router.link(HomeP)("Back to Home", ^.className := "btn btn-default")
        )
      }
    }

  }

  val component = ScalaComponent
    .builder[Props]("EmployeeEdit")
    .initialStateFromProps(p => {
      val employees = AppCircuit.zoom(_.employees).value.employeeList.filter(_.id.toString == p.employeeId.toString)
      if (!employees.isEmpty) State(Some(employees.head)) else State(None)
    })
    .renderBackend[Backend]
    .build

  def apply(employeeId: UUID, router: RouterCtl[Pages]) =
    component(EmployeeEdit.Props(employeeId, router))
}