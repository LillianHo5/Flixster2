package com.example.flixster

/**
 * This interface is used by the [LatestMovieRecyclerViewAdapter] to ensure
 * it has an appropriate Listener.
 *
 * In this app, it's implemented by [LatestMovieRecyclerViewAdapter]
 */
interface OnListFragmentInteractionListener {
    fun onItemClick(item: LatestMovie)
}