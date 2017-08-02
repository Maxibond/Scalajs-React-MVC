package diodeERP

import diode.react.ModelProxy
import diode.Action
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.ext.KeyCode
import java.util.UUID


object EmployeeList {

    case class Props(proxy: ModelProxy[Employees], router: RouterCtl[Pages])

    case class State(editing: Option[EmployeeId])

    class Backend($ : BackendScope[Props, State]) {
        def mounted(props: Props) = Callback {}

        def editingDone(): Callback =
            $.modState(_.copy(editing = None))

        val startEditing: EmployeeId => Callback =
            id => $.modState(_.copy(editing = Some(id)))

        def render(p: Props, s: State) = {
            val proxy = p.proxy()
            val dispatch: Action => Callback = p.proxy.dispatchCB
            val employees = proxy.employeeList
            
            <.div(
                <.h1("Employees"),
                <.header(
                    p.router.link(EmployeeCreateP)("Create", ^.className := "btn btn-success")
                ),
                employeeList(dispatch, employees, p.router).when(employees.nonEmpty)
            )
        }

        def employeeList(dispatch: Action => Callback ,employees: Seq[Employee], router: RouterCtl[Pages]) =
            <.div(
                <.ul(
                    employees.toTagMod(
                        emp =>
                            EmployeeView(EmployeeView.Props(
                              onSave = e => dispatch(Update(e)),
                              onDelete = dispatch(Delete(emp.id)),
                              employee = emp,
                              router.link(EmployeeEditP(emp.id.toString))("Edit", ^.className := "btn btn-primary")
                            )
                        )
                    )
                )
            )
        
    }

    private val component = ScalaComponent.
        builder[Props]("employeeList")
        .initialStateFromProps(p => State(None))
        .renderBackend[Backend]
        .componentDidMount(scope => scope.backend.mounted(scope.props))
        .build

    def apply(proxy: ModelProxy[Employees], router: RouterCtl[Pages]) =
        component(Props(proxy, router))
}