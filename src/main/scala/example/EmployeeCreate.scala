package diodeERP

import java.util.UUID

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._

import scalacss.ScalaCssReact._

object EmployeeCreate {

  case class Props(proxy: ModelProxy[Employees], router: RouterCtl[Pages])

  case class State(emp: EmployeeData)

  class Backend($ : BackendScope[Props, State]) {

    def editFieldChangedFirstname(e: ReactEventFromInput) = {
      val value = e.target.value.trim
      CallbackTo { $.modState(state => state.copy(emp = state.emp.copy(firstname = value))) }.flatten
    }

    def editFieldChangedLastname(e: ReactEventFromInput) = {
      val value = e.target.value.trim
      CallbackTo { $.modState(state => state.copy(emp = state.emp.copy(lastname = value))) }.flatten
    }

    def editFieldChangedPhoneNumber(e: ReactEventFromInput) = {
      val value = e.target.value.trim
      CallbackTo { $.modState(state => state.copy(emp = state.emp.copy(phone_number = value))) }.flatten
    }

    def render(p: Props, s: State): VdomElement = {
      <.div(
        <.div(
          Styles.appHeader,
          <.p(s.emp.toString),
          <.p("Creating a new employee!")
        ),
        <.form(
          <.div(
            ^.className := "form-group",
            <.label("First name"),
            <.input(
              ^.className := "form-control",
              ^.onChange ==> editFieldChangedFirstname,
              ^.value := s.emp.firstname
            )
          ),
          <.div(
            ^.className := "form-group",
            <.label("Last name"),
            <.input(
              ^.className := "form-control",
              ^.onChange ==> editFieldChangedLastname,
              ^.value := s.emp.lastname
            )
        ),
          <.div(
            ^.className := "form-group",
            <.label("Phone number"),
            <.input(
              ^.className := "form-control",
              ^.onChange ==> editFieldChangedPhoneNumber,
              ^.value := s.emp.phone_number
            )
          )
        ),
        <.button("Create",
            Styles.buttonSuccess,
            ^.onClick --> {  AppCircuit.dispatch(AddEmployee(s.emp)); p.router.set(HomeP) }
        ),
        p.router.link(HomeP)("Back to Home", Styles.buttonDefault)
      )
    }

  }

  val component = ScalaComponent
    .builder[Props]("EmployeeEdit")
    .initialState(State(EmployeeData(
        firstname = "",
        lastname="",
        phone_number="",
        photo = ""))
    )
    .renderBackend[Backend]
    .build

  def apply(proxy: ModelProxy[Employees], router: RouterCtl[Pages]) =
    component(EmployeeCreate.Props(proxy, router))
}