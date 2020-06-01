package data

data class Book (
    val title: String
){
    override fun toString(): String = title
}


fun bookList() =
    arrayOf(
        Book( "1984"),
        Book( "The Master and Margarita"),
        Book("Harry Potter and the Philosopher's Stone"),
        Book("The Adventures Of Sherlock Holmes"),
        Book("The Picture of Dorian Gray"),
        Book("The Hobbit, or There and Back Again"),
        Book("Pet Sematary")
    )
