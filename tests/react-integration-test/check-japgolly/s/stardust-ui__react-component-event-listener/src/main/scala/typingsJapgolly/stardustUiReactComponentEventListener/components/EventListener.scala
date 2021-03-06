package typingsJapgolly.stardustUiReactComponentEventListener.components

import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.raw.React.RefHandle
import typingsJapgolly.StBuildingComponent
import typingsJapgolly.stardustUiReactComponentEventListener.typesMod.EventListenerOptions
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation._

object EventListener {
  @JSImport("@stardust-ui/react-component-event-listener/dist/es/EventListener", JSImport.Default)
  @js.native
  object component extends js.Object
  
  @scala.inline
  class Builder[/* <: typingsJapgolly.stardustUiReactComponentEventListener.typesMod.EventTypes */ T] (val args: js.Array[js.Any])
    extends AnyVal
       with StBuildingComponent[js.Object] {
    @scala.inline
    def capture(value: Boolean): this.type = set("capture", value.asInstanceOf[js.Any])
  }
  
  def withProps[/* <: typingsJapgolly.stardustUiReactComponentEventListener.typesMod.EventTypes */ T](p: EventListenerOptions[T]): Builder[T] = new Builder[T](js.Array(this.component, p.asInstanceOf[js.Any]))
  @scala.inline
  def apply[/* <: typingsJapgolly.stardustUiReactComponentEventListener.typesMod.EventTypes */ T](
    listener: /* import warning: importer.ImportType#apply Failed type conversion: / * import warning: transforms.QualifyReferences#resolveTypeRef many Couldn't qualify DocumentEventMap * / any[T] */ /* e */ js.Any => Callback,
    targetRef: RefHandle[
      /* import warning: transforms.QualifyReferences#resolveTypeRef many Couldn't qualify Node */ js.Any
    ],
    `type`: T
  ): Builder[T] = {
    val __props = js.Dynamic.literal(listener = js.Any.fromFunction1((t0: /* import warning: importer.ImportType#apply Failed type conversion: / * import warning: transforms.QualifyReferences#resolveTypeRef many Couldn't qualify DocumentEventMap * / any[T] */ /* e */ js.Any) => listener(t0).runNow()), targetRef = targetRef.asInstanceOf[js.Any])
    __props.updateDynamic("type")(`type`.asInstanceOf[js.Any])
    new Builder[T](js.Array(this.component, __props.asInstanceOf[EventListenerOptions[T]]))
  }
}

