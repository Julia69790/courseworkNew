package data

data class Author(
    val name:String
){
    override fun toString(): String = name
}

fun authorList() =
    arrayOf(
        Author("George Orwell"),
        Author("Mikhail Bulgakov"),
        Author("Joanne Rowling"),
        Author("Arthur Conan Doyle"),
        Author("Oscar Wilde"),
        Author("John Tolkien"),
        Author("Stephen King")
    )




