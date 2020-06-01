package container

import component.*
import data.Author
import data.Book
import data.Genre
import hoc.withDisplayName
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import redux.*

interface AuthorEditOwnProps : RProps {
    var author:Pair<Int, Author>
}

val authorEditContainer =
        rConnect<
                RAction,
                WrapperAction,
                AuthorEditOwnProps,
                AuthorEditProps
        >(
                { dispatch, ownProps ->
                    onClick = {
                        dispatch(EditAuthor(ownProps.author.first, it))
                    }
                }
        )(
                withDisplayName(
                        "AuthorEdit",
                        fAuthorEdit
                ).unsafeCast<RClass<AuthorEditProps>>()
        )

interface BookEditOwnProps: RProps{
    var book:Pair<Int, Book>
}

val bookEditContainer =
        rConnect<
                RAction,
                WrapperAction,
                BookEditOwnProps,
                BookEditProps
                >(
                {dispatch, ownProps ->
                    onClick ={
                        dispatch(EditBook(ownProps.book.first,it))
                    }
                }
        )(
                withDisplayName(
                        "BookEdit",
                        fBookEdit
                ).unsafeCast<RClass<BookEditProps>>()
        )

interface GenreEditOwnProps : RProps{
    var genre: Pair<Int, Genre>
}

val genreEditContainer =
        rConnect<
                RAction,
                WrapperAction,
                GenreEditOwnProps,
                GenreEditProps
                >(
                {dispatch,ownProps ->
                    onClick ={
                        dispatch(EditGenre(ownProps.genre.first,it))
                    }
                }
        )(
                withDisplayName(
                        "GenreEdit",
                        fGenreEdit
                ).unsafeCast<RClass<GenreEditProps>>()
        )