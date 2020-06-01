package redux

import data.*
import enums.VisibilityFilter

fun authorshipReducer (state: Relation, action: RAction, id: Int = -1) =
    when(action){
        is ChangeAuthorship ->
            state.toMutableMap().apply {
                this[action.authorID]?.let{
                    val old = it[action.bookID]?:false
                    (it as MutableMap)[action.bookID]=!old
                }
            }
        is DeleteAuthor ->
            state.minus(action.id)
        is AddAuthor ->
            state.plus(id to state.values.first().keys.associateWith { false })

        is DeleteBook ->
            HashMap<Int, Map<Int, Boolean>>().toMutableMap().apply {
                state.map {
                    put(it.key, it.value.minus(action.id))
                }
            }

        is AddBook ->
            HashMap<Int, Map<Int, Boolean>>().toMutableMap().apply {
                state.map {
                    put(it.key, it.value.plus(id to false))
                }
            }

        else -> state
    }

fun genreReducer (state:GenreState,action: RAction, newId: Int = -1 ) =
        when(action){
            is DeleteGenre -> state.minus(action.id)
            is EditGenre ->
                state.toMutableMap()
                        .apply{
                            this[action.id] = action.newGenre
                        }
            is AddGenre -> state + (newId to action.genre)
            else -> state
        }

fun styleReducer(state: Relation, action: RAction, id: Int = -1 ) =
        when(action){
            is ChangeStyle ->
                state.toMutableMap().apply {
                    this[action.genreID]?.let{
                        val old = it[action.bookID]?:false
                        (it as MutableMap)[action.bookID]=!old
                    }
                }

            is DeleteBook ->
                HashMap<Int, Map<Int, Boolean>>().toMutableMap().apply {
                    state.map {
                        put(it.key, it.value.minus(action.id))
                    }
                }

            is AddBook ->
                HashMap<Int, Map<Int, Boolean>>().toMutableMap().apply {
                    state.map {
                        put(it.key, it.value.plus(id to false))
                    }
                }

            is DeleteGenre -> state.minus(action.id)

            is AddGenre ->  state.plus(id to state.values.first().keys.associateWith { false })

            else -> state
        }

fun authorReducer (state:AuthorState, action:RAction,newId: Int = -1 ) =
        when(action){
            is DeleteAuthor -> state.minus(action.id)
            is EditAuthor ->
                state.toMutableMap()
                        .apply{
                            this[action.id] = action.newAuthor
                        }
            is AddAuthor -> state + (newId to action.author)
            else -> state
        }

fun bookReducer (state: BookState, action: RAction, newId: Int = -1 ) =
        when(action){
            is EditBook ->
                state.toMutableMap()
                        .apply{
                            this[action.id] = action.newBook
                        }
            is DeleteBook -> state.minus(action.id)
            is AddBook -> state + (newId to action.book)
            else -> state
        }

fun visibilityReducer(
        state: VisibilityFilter,
        action: RAction
) = when (action) {
    is SetVisibilityFilter -> action.filter
    else -> state
}

fun rootReducer(state:State, action: RAction) =
        when (action) {
            is AddAuthor -> {
                val id = state.authors.newId()
                State(
                        bookReducer(state.books, action),
                        authorReducer(state.authors, action, id),
                        genreReducer(state.genres,action),
                        authorshipReducer(state.relationAB,action,id),
                        visibilityReducer(state.visibilityFilter,action),
                        styleReducer(state.relationGB,action)
                )
            }
            is AddBook ->{
                val id = state.books.newId()
                State(
                        bookReducer(state.books, action, id),
                        authorReducer(state.authors, action),
                        genreReducer(state.genres,action),
                        authorshipReducer(state.relationAB,action,id),
                        visibilityReducer(state.visibilityFilter,action),
                        styleReducer(state.relationGB,action,id)

                )
            }

            is AddGenre ->{
                val id = state.genres.newId()
                State(
                        bookReducer(state.books, action),
                        authorReducer(state.authors, action),
                        genreReducer(state.genres,action, id),
                        authorshipReducer(state.relationAB,action),
                        visibilityReducer(state.visibilityFilter,action),
                        styleReducer(state.relationGB,action,id)
                )
            }

            else ->
                State(
                        bookReducer(state.books, action),
                        authorReducer(state.authors, action),
                        genreReducer(state.genres,action),
                        authorshipReducer(state.relationAB,action),
                        visibilityReducer(state.visibilityFilter,action),
                        styleReducer(state.relationGB,action)
                )
        }
