package pt.amn.moveon.domain.models

class UseCaseResult<T>(
    var isError: Boolean,
    var data: T,
    var description: String = "")