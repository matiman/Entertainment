package com.bill.entertainment.utility

object ErrorMessages {
    const val BAD_REQUEST = "Bad Request"
    const val ACTOR_NOT_FOUND = "Actor Not Found"
    const val INVALID_INPUT = "Invalid Input"
    const val INVALID_JSON_FORMAT = "Invalid Json Format Received"
    const val MOVIE_NOT_FOUND = "Movie Not Found"
    const val CAN_NOT_DELETE_ACTOR = "Can't delete actor. Actor is already part of a movie."
    const val DUPLICATE_MOVIE_EXISTS = "Same movie with name and release date exists."
    const val DUPLICATE_ACTOR = "Duplicate Actor"
    const val SUCCESS = "SUCCESS"
    const val CAN_NOT_DELETE_ACTOR_IN_MOVIE = "Can't delete actor. Actor is already part of a movie."
    const val ONE_OR_MORE_ACTOR_NOT_IN_DB = "At least one or more actor not found in our db."
    const val ONE_ACTOR_AT_LEAST = "At least one actor is required"
    const val CAN_NOT_DELETE_MOVIE = "Failed to delete movie"
}
