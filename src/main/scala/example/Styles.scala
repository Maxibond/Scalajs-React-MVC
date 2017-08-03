package diodeERP

import CssSettings._

import scalacss.internal.Attrs.textDecorationLine

object Styles extends StyleSheet.Inline {
  import dsl._

  val mainFont = fontFace("mainfont")(
    _.src("local(Tahoma)")
      .fontStretch.ultraCondensed
      .fontWeight._200
  )

  val appHeader = style(
    backgroundColor.rgb(34, 34, 34),
    height(150 px),
    padding(20 px),
    color.white
  )

  val body = style(
    margin(0 px),
    padding(0 px),
    fontFamily(mainFont)
  )

  val header = style(
    textAlign.center
  )

  private val button = style(
    addClassName("btn"),
    textAlign.center,
    marginRight(10 px)
  )

  val buttonDefault = style(
    addClassName("btn-default"),
    button
  )

  val buttonSuccess = style(
    addClassName("btn-success"),
    button
  )

  val buttonPrimary = style(
    addClassName("btn-primary"),
    button
  )

  val buttonDanger = style(
    addClassName("btn-danger"),
    button
  )

  val mainCreateButton = style(
    marginBottom(15 px),
    buttonSuccess
  )

}
