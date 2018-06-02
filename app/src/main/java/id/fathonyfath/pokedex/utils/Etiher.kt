package id.fathonyfath.pokedex.utils

sealed class Either<out L, out R> {
    data class Left<out L>(val left: L) : Either<L, Nothing>()
    data class Right<out R>(val right: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(value: L) = Either.Left(value)
    fun <R> right(value: R) = Either.Right(value)

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
            when (this) {
                is Left -> fnL.invoke(left)
                is Right -> fnR.invoke(right)
            }
}