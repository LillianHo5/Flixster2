package com.example.flixster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import com.example.flixster.DetailActivity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONObject

// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
const val MOVIE_VOTE_AVERAGE = "MOVIE_VOTE_AVERAGE"
const val MOVIE_TITLE = "MOVIE_TITLE"
const val MOVIE_OVERVIEW = "MOVIE_OVERVIEW"
const val MOVIE_POSTER_PATH = "MOVIE_POSTER_PATH"
const val MOVIE_RELEASE_DATE = "MOVIE_RELEASE_DATE"

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class LatestMovieFragment : Fragment(), OnListFragmentInteractionListener {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_latest_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY

        // Using the client, perform the HTTP request

         client["https://api.themoviedb.org/3/movie/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", params, object :
            JsonHttpResponseHandler() {
            /*
             * The onSuccess function gets called when
             * HTTP response status is "200 OK"
             */
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                // The wait for a response is over
                progressBar.hide()

                //TODO - Parse JSON into Models
                val resultsJSON : String = json.jsonObject.get("results").toString()
                val gson = Gson()
                val arrayMovieType = object : TypeToken<List<LatestMovie>>() {}.type

                val models : List<LatestMovie> = gson.fromJson(resultsJSON, arrayMovieType)
                recyclerView.adapter = LatestMovieRecyclerViewAdapter(models, this@LatestMovieFragment)

                // Look for this in Logcat:
                Log.d("LatestMovieFragment", "response successful")
            }

            /*
             * The onFailure function gets called when
             * HTTP response status is "4XX" (eg. 401, 403, 404)
             */
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // The wait for a response is over
                progressBar.hide()

                // If the error is not null, log it!
                t?.message?.let {
                    Log.e("LatestMovieFragment", errorResponse)
                }
            }
        }]
    }

    /*
     * What happens when a particular movie is clicked.
     */
    override fun onItemClick(item: LatestMovie) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(MOVIE_VOTE_AVERAGE, item.vote_average)
        intent.putExtra(MOVIE_TITLE, item.title)
        intent.putExtra(MOVIE_OVERVIEW, item.overview)
        intent.putExtra(MOVIE_POSTER_PATH, item.poster_path)
        intent.putExtra(MOVIE_RELEASE_DATE, item.release_date)
        context?.startActivity(intent)
    }

}