package component

import data.Book
import data.Genre
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

interface GenreProps : RProps {
    var genre: Genre
    var cssClass: String
    var onClick: (Event) -> Unit
}

val fGenre =
        functionalComponent<GenreProps> {
            span (
                    it.cssClass
            ){
                +"${it.genre.style}"
                attrs.onClickFunction = it.onClick
            }
        }

fun RBuilder.genre(
        genre: Genre,
        cssClass: String,
        onClick: (Event) -> Unit
)= child(
        withDisplayName("Genre", fGenre)
){
    attrs.genre = genre
    attrs.cssClass = cssClass
    attrs.onClick = onClick
}

interface GenreEditProps :RProps{
    var genre: Pair<Int, Genre>
    var onClick: (Genre) -> Unit
}

val fGenreEdit =
        functionalComponent<GenreEditProps> {  props ->
            span{
                input {
                    attrs.id = "genreEdit${props.genre.first}"
                    attrs.defaultValue = props.genre.second.style
                }
                button{
                    +"Save"
                    attrs.onClickFunction = {
                        val inputElement = document
                                .getElementById("genreEdit${props.genre.first}")
                                as HTMLInputElement
                        props.onClick(Genre(inputElement.value))
                        window.history.back()
                    }
                }
            }

        }