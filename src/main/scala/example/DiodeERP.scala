package diodeERP

import boopickle.Default._
import diode.dev.{Hooks, PersistStateIDB}
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.Implicits._
import java.util.UUID

import diode.react.ReactConnectProxy

import scala.scalajs.js.typedarray.TypedArrayBufferOps._
import scala.scalajs.js.typedarray._

import CssSettings._
import scalacss.ScalaCssReact._

@JSExportTopLevel("DiodeERP")
object DiodeERP extends JSApp {

    val baseUrl = BaseUrl(dom.window.location.href.takeWhile(_ != '#'))

    val AppConnection = AppCircuit.connect(_.employees)

    val routerConfig: RouterConfig[Pages] = RouterConfigDsl[Pages].buildConfig { dsl =>
        import dsl._
        (emptyRule
        | staticRoute(root, HomeP) ~> renderR(router => AppConnection(p => EmployeeList(p, router)))
        | staticRoute("#employee/create", EmployeeCreateP) ~> renderR(router => AppConnection(p => EmployeeCreate(p, router)))
        | dynamicRouteCT("#employee" / string(".+").caseClass[EmployeeEditP]) ~> dynRenderR(
          (employeeId: EmployeeEditP, router) => EmployeeEdit(UUID.fromString(employeeId.id), router)
        )
        ).notFound(redirectToPage(HomeP)(Redirect.Replace))
    }
    @JSExport
    override def main(): Unit = {
        val router = Router(baseUrl, routerConfig)
        AppCircuit.dispatch(InitEmployees)
        Styles.addToDocument()
        router().renderIntoDOM(dom.document.getElementsByClassName("app")(0).domAsHtml)
    }
}