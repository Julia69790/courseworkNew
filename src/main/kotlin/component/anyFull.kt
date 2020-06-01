package component

import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.dom.h3
import react.dom.li
import react.dom.ul

interface AnyFullProps<O, S> : RProps {
    var obj: Pair<Int, O>
    var subobjs: Map<Int, S>
    var relation: Map<Int, Boolean>?
    var onClick: (Int) -> (Event) -> Unit
}

fun <O, S> fAnyFull(
        rComponent: RBuilder.(S, String, (Event) -> Unit) -> ReactElement
) =
        functionalComponent<AnyFullProps<O, S>> { props ->
            h3 {
                +props.obj.second.toString()
            }
            footer()
            ul{
                props.subobjs.map {
                    val filterValue = props.relation?.get(it.key)?: false
                    val cssClass = if(filterValue) "filter" else "not_filter"
                    li {
                        rComponent(it.value, cssClass, props.onClick(it.key) )
                    }
                }
            }
        }

fun <O, S> RBuilder.anyFull(
        rComponent: RBuilder.(S, String, (Event) -> Unit) -> ReactElement,
        obj: Pair<Int, O>,
        subobjs: Map<Int, S>,
        relation: Map<Int, Boolean>?,
        onClick:(Int) -> (Event) -> Unit
) = child(
        withDisplayName("Full", fAnyFull<O, S>(rComponent))
){
    attrs.obj = obj
    attrs.subobjs = subobjs
    attrs.relation = relation
    attrs.onClick = onClick
}