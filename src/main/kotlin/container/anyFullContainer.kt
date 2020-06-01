package container

import component.*
import data.*
import enums.VisibilityFilter
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.RBuilder
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import redux.ChangeAuthorship
import redux.ChangeStyle
import redux.RAction
import redux.WrapperAction

interface AnyFullDispatchProps : RProps {
    var onClick: (Int) -> (Event) -> Unit
}

interface AnyFullStateProps<O, S> : RProps {
    var subobjs: Map<Int, S>
    var relation: Map<Int, Boolean>?
}

interface AnyFullOwnProps<O> : RProps {
    var obj: Pair<Int, O>
}

val authorFullContainer =
        rConnect<
                State,
                RAction,
                WrapperAction,
                AnyFullOwnProps<Author>,
                AnyFullStateProps<Author,Book>,
                AnyFullDispatchProps,
                AnyFullProps<Author,Book>>(
                {state, ownProps ->
                    relation =state.relationAB[ownProps.obj.first]
                    subobjs = getVisibleObjects(state.books,relation,state.visibilityFilter)
                },
                {dispatch, ownProps ->
                    onClick =
                            {index ->
                                {
                                    dispatch(ChangeAuthorship(ownProps.obj.first, index))
                                }
                            }
                }
        )(
                withDisplayName(
                        "AuthorFull",
                        fAnyFull<Author,Book>(RBuilder::book)
                ).unsafeCast<RClass<AnyFullProps<Author,Book>>>()
        )


val bookFullContainer =
        rConnect<
                State,
                RAction,
                WrapperAction,
                AnyFullOwnProps<Book>,
                AnyFullStateProps<Book,Author>,
                AnyFullDispatchProps,
                AnyFullProps<Book,Author>>(
                {state, ownProps ->
                    subobjs = getVisibleObjects(state.authors,state.authorship(ownProps.obj.first),state.visibilityFilter)
                    relation =state.authorship(ownProps.obj.first)
                    },
                {dispatch, ownProps ->
                    onClick = { index ->
                        {
                            dispatch(ChangeAuthorship(index, ownProps.obj.first))
                        }
                    }
                }
        )(
                withDisplayName(
                        "BookFull",
                        fAnyFull<Book,Author>(RBuilder::author)
                )
                        .unsafeCast<RClass<AnyFullProps<Book, Author>>>()
        )

val genreFullContainer =
        rConnect<
                State,
                RAction,
                WrapperAction,
                AnyFullOwnProps<Genre>,
                AnyFullStateProps<Genre,Book>,
                AnyFullDispatchProps,
                AnyFullProps<Genre,Book>>(
                {state, ownProps ->
                    relation = state.relationGB[ownProps.obj.first]
                    subobjs = getVisibleObjects(state.books,relation,state.visibilityFilter)
                },
                {dispatch, ownProps ->
                    onClick ={index ->
                        {
                            dispatch(ChangeStyle(ownProps.obj.first, index))
                        }
                    }

                }
        )(
                withDisplayName(
                        "GenreFull",
                        fAnyFull<Genre, Book>(RBuilder::book)
                )
                        .unsafeCast<RClass<AnyFullProps<Genre,Book>>>()
        )

val bookByGenreFullContainer =
        rConnect<
                State,
                RAction,
                WrapperAction,
                AnyFullOwnProps<Book>,
                AnyFullStateProps<Book,Genre>,
                AnyFullDispatchProps,
                AnyFullProps<Book,Genre>>(
                {state, ownProps ->
                    subobjs = getVisibleObjects(state.genres,state.style(ownProps.obj.first),state.visibilityFilter)
                    relation =state.style(ownProps.obj.first)
                },
                {dispatch, ownProps ->
                    onClick = { index ->
                        {
                            dispatch(ChangeStyle(index, ownProps.obj.first))
                        }
                    }

                }
        )(
                withDisplayName(
                        "BookByGenreFull",
                        fAnyFull<Book,Genre>(RBuilder::genre)
                )
                        .unsafeCast<RClass<AnyFullProps<Book, Genre>>>()
        )

fun <O> getVisibleObjects(objects:Map <Int,O>, relation:Map<Int,Boolean>?,filter: VisibilityFilter): Map<Int, O>
        = when (filter) {
    VisibilityFilter.SHOW_ALL -> objects
    VisibilityFilter.SHOW_FILTER ->{
        val authorshipObj = objects.toMutableMap()
        authorshipObj.clear()
        if (relation != null) {
            relation.filter { it.value }.map{
                authorshipObj[it.key] = objects.getValue(it.key)
            }
        }
        authorshipObj
    }
        }