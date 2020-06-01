package container

import component.*
import data.Author
import data.Book
import data.Genre
import data.State
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.RBuilder
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import redux.*

interface AnyListDispatchProps : RProps {
    var add: (Event) -> Unit
    var remove: (Int) -> Unit
}

interface AnyListStateProps<O> : RProps {
    var objs: Map<Int, O>
}

val bookListHOC =
        rConnect<
                State,
                RAction,
                WrapperAction,
                RProps,
                AnyListStateProps<Book>,
                AnyListDispatchProps,
                AnyListProps<Book>
                >(
                mapStateToProps = {state, _ ->
                    objs = state.books
                },
                mapDispatchToProps = { dispatch, _ ->
                    add = { dispatch(AddBook(Book("new book"))) }
                    remove = { dispatch(DeleteBook(it)) }
                }
        )

val bookAListRClass =
        withDisplayName(
                "BookAList",
                fAnyList("Book", "/booksA", RBuilder::book)
        )
                .unsafeCast<RClass<AnyListProps<Book>>>()

val bookAListContainer =
        bookListHOC(bookAListRClass)

val bookGListRClass =
        withDisplayName(
                "BookGList",
                fAnyList("Book", "/booksG", RBuilder::book)
        )
                .unsafeCast<RClass<AnyListProps<Book>>>()

val bookGListContainer =
        bookListHOC(bookGListRClass)


val authorListContainer =
        rConnect<
                State,
                RAction,
                WrapperAction,
                RProps,
                AnyListStateProps<Author>,
                AnyListDispatchProps,
                AnyListProps<Author>
                > (
                {state, _ ->
                    objs = state.authors
                },
                {dispatch, _ ->
                    add = {dispatch(AddAuthor(Author("new author")))}
                    remove = {dispatch(DeleteAuthor(it))}
                }
        )(
                withDisplayName(
                        "AuthorList",
                        fAnyList("Author", "/authors", RBuilder::author)
                )
                        .unsafeCast<RClass<AnyListProps<Author>>>()
        )

val genreListContainer =
        rConnect<
                State,
                RAction,
                WrapperAction,
                RProps,
                AnyListStateProps<Genre>,
                AnyListDispatchProps,
                AnyListProps<Genre>
                >(
                {state, _ ->
                    objs = state.genres
                },
                {dispatch, _ ->
                    add = {dispatch(AddGenre(Genre("new genre")))}
                    remove = {dispatch(DeleteGenre(it))}

                }
        )(
                withDisplayName(
                        "GenreList",
                        fAnyList("Genre", "/genres", RBuilder::genre)
                )
                        .unsafeCast<RClass<AnyListProps<Genre>>>()
        )
