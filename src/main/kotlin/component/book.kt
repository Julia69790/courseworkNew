package component

import data.Book
import hoc.withDisplayName
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.button
import react.dom.defaultValue
import react.dom.input
import react.dom.span
import react.functionalComponent
import kotlin.browser.document
import kotlin.browser.window

interface BookProps :RProps{
    var book : Book
    var cssClass: String
    var onClick: (Event) -> Unit
}

val fBook =
        functionalComponent<BookProps> {
            span(
                    it.cssClass
            ) {
                +"${it.book.title}"
                attrs.onClickFunction = it.onClick
            }
        }

fun RBuilder.book(
        book:Book,
        cssClass: String,
        onClick: (Event) -> Unit
)=child(
        withDisplayName("Book", fBook)
){
    attrs.book = book
    attrs.cssClass = cssClass
    attrs.onClick = onClick
}

interface BookEditProps : RProps{
    var book : Pair<Int,Book>
    var onClick: (Book) -> Unit
}

val fBookEdit =
        functionalComponent<BookEditProps>{ props ->
            span{
                input {
                    attrs.id = "bookEdit${props.book.first}"
                    attrs.defaultValue = props.book.second.title
                }
                button{
                    +"Save"
                    attrs.onClickFunction = {
                        val inputElement = document
                                .getElementById("bookEdit${props.book.first}")
                                as HTMLInputElement
                        props.onClick(Book(inputElement.value))
                        window.history.back()
                    }
                }
            }
        }
