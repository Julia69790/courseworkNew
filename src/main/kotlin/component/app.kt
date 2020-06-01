package component

import container.*
import data.*
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.*
import react.functionalComponent
import react.router.dom.RouteResultProps
import react.router.dom.navLink
import react.router.dom.route
import react.router.dom.switch

interface AppProps : RProps {
    var authors: Map<Int, Author>
    var books: Map<Int, Book>
    var genres: Map <Int, Genre>
}

interface RouteNumberResult : RProps {
    var number: String
}

fun fApp() =
        functionalComponent<AppProps> { props ->
            header {
                h1 { +"App" }
                nav {
                    ul {
                        li { navLink("/booksA") { +"Books by authors" } }
                        li{ navLink ("/booksG"){+"Books by genres"}}
                        li { navLink("/authors") { +"Authors" } }
                        li { navLink("/genres"){+"Genres"} }
                    }
                }
            }

            switch{
               route("/booksA",
                       exact = true,
                       render ={ bookAListContainer{}}
               )
                route("/booksG",
                        exact = true,
                        render ={ bookGListContainer{}}
                )
                route("/authors",
                        exact = true,
                        render ={ authorListContainer{}}
                )
                route("/genres",
                        exact = true,
                        render = { genreListContainer{}}

                )
                route(
                        "/authors/:number",
                        exact = true,
                        render = renderObject(
                                {props.authors[it]},
                                {index, author ->
                                    authorFullContainer{
                                        attrs.obj = index to author
                                    }
                                }
                        )
                )
                route(
                        "/booksA/:number",
                        exact = true,
                        render = renderObject(
                                {props.books[it]},
                                {index, book ->
                                    bookFullContainer{
                                      attrs.obj = index to book
                                    }
                                }
                        )
                )
                route(
                        "/booksG/:number",
                        exact = true,
                        render = renderObject(
                                {props.books[it]},
                                {index, book ->
                                    bookByGenreFullContainer{
                                        attrs.obj = index to book
                                    }
                                }
                        )
                )
                route(
                        "/genres/:number",
                        exact = true,
                        render = renderObject(
                                {props.genres[it]},
                                {index, genre ->
                                    genreFullContainer{
                                        attrs.obj = index to genre
                                    }
                                }
                        )
                )
                route(
                        "/authors/:number/edit",
                        render = renderObject(
                                {props.authors[it]},
                                {index, author ->
                                    authorEditContainer{
                                        attrs.author = index to author
                                    }
                                }
                        )
                )
                route(
                        "/booksA/:number/edit",
                        render = renderObject(
                                {props.books[it]},
                                {index, book ->
                                    bookEditContainer{
                                        attrs.book = index to book
                                    }
                                }
                        )
                )
                route(
                        "/booksG/:number/edit",
                        render = renderObject(
                                {props.books[it]},
                                {index, book ->
                                    bookEditContainer{
                                        attrs.book = index to book
                                    }
                                }
                        )
                )
                route(
                        "/genres/:number/edit",
                        render = renderObject(
                                {props.genres[it]},
                                {index, genre ->
                                    genreEditContainer{
                                        attrs.genre = index to genre
                                    }
                                }
                        )
                )
            }
        }

fun <O> RBuilder.renderObject(
        selector: (Int) -> O?,
        rElement: (Int, O) -> ReactElement
) =
        { route_props: RouteResultProps<RouteNumberResult> ->
            val num = route_props.match.params.number.toIntOrNull() ?: -1
            val obj = selector(num)
            if (obj != null)
                rElement(num, obj)
            else
                p { +"Object not found" }
        }