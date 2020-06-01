package data

data class Genre(
    val style:String
){
    override fun toString(): String = style
}

fun genreList() =
    arrayOf(
        Genre("anti-utopia"),
        Genre("classic"),
        Genre("fantasy"),
        Genre("detective"),
        Genre("mysticism"),
        Genre("thriller")
    )
