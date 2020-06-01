package component

import data.Author
import hoc.withDisplayName
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.RBuilder
import react.RProps
import react.child
import react.dom.button
import react.dom.defaultValue
import react.dom.input
import react.dom.span
import react.functionalComponent
import kotlin.browser.document
import kotlin.browser.window


interface AuthorProps : RProps {
    var author: Author
    var cssClass:String
    var onClick: (Event) -> Unit
}

val fAuthor =
        functionalComponent<AuthorProps>{
            span(
                    it.cssClass
            ) {
                +it.author.name
                attrs.onClickFunction = it.onClick
            }
        }

fun RBuilder.author(
        author: Author,
        cssClass:String,
        onClick: (Event) -> Unit
) = child(
        withDisplayName("Author",fAuthor)
){
    attrs.author = author
    attrs.cssClass = cssClass
    attrs.onClick = onClick
}

interface AuthorEditProps : RProps{
    var author: Pair<Int, Author>
    var onClick: (Author) -> Unit
}

val fAuthorEdit =
        functionalComponent<AuthorEditProps>{props ->
            span{
                input {
                    attrs.id = "authorEdit${props.author.first}"
                    attrs.defaultValue = props.author.second.name
                }
                button{
                    +"Save"
                    attrs.onClickFunction = {
                        val inputElement = document
                                .getElementById("authorEdit${props.author.first}")
                                    as HTMLInputElement
                        props.onClick(Author(inputElement.value))
                        window.history.back()
                    }
                }
            }

        }
