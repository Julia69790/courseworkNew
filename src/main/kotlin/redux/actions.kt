package redux

import data.*
import enums.VisibilityFilter

class ChangeAuthorship(val authorID: Int, val bookID: Int) : RAction

class AddAuthor(val author: Author) : RAction

class DeleteAuthor (val id: Int) : RAction

class EditAuthor (val id:Int, val newAuthor: Author) : RAction

class  AddBook (val book:Book) : RAction

class DeleteBook(val id: Int) : RAction

class EditBook(val id: Int, val newBook: Book) : RAction

class SetVisibilityFilter(val filter: VisibilityFilter) : RAction

class ChangeStyle(val genreID: Int, val bookID: Int) : RAction

class AddGenre(val genre:Genre) : RAction

class DeleteGenre(val id:Int) : RAction

class EditGenre(val id:Int, val newGenre: Genre) : RAction


