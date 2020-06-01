package data

import enums.VisibilityFilter

typealias BookState = Map<Int, Book>

typealias AuthorState = Map<Int, Author>

typealias GenreState = Map<Int,Genre>

typealias Relation = Map<Int, Map<Int,Boolean>>

class State(
        val books:BookState,
        val authors:AuthorState,
        val genres: GenreState,
        val relationAB:Relation,
        val visibilityFilter: VisibilityFilter,
        val relationGB:Relation
)

fun <T> Map<Int, T>.newId() =
        (this.maxBy { it.key }?.key ?: 0) + 1

fun State.authorship(idBook:Int) =
        relationAB.map{
            it.key to (it.value [idBook] ?: false)
        }.toMap()

fun State.style(idBook: Int) =
        relationGB.map{
            it.key to (it.value [idBook] ?: false)
        }.toMap()

fun initialState() =
        State(
                bookList().mapIndexed { index, book ->
                    index to book
                }.toMap(),
                authorList().mapIndexed { index, author ->
                    index to author
                }.toMap(),
                genreList().mapIndexed{ index, genre ->
                    index to genre
                }.toMap(),
                authorList().mapIndexed { idAuthor, _ ->
                    idAuthor to bookList().mapIndexed { idBook, _ ->
                        idBook to false
                    }.toMap()
                }.toMap(),
                VisibilityFilter.SHOW_ALL,
                genreList().mapIndexed{idGenre, _ ->
                    idGenre to bookList().mapIndexed { idBook, _ ->
                        idBook to false
                    }.toMap()
                }.toMap()
        )