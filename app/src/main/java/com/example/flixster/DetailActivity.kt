package com.example.flixster

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import org.w3c.dom.Text


class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var overviewTextView: TextView
    private lateinit var releaseDateTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Find the views for the screen
        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        ratingTextView = findViewById(R.id.mediaRating)
        overviewTextView = findViewById(R.id.mediaOverview)
        releaseDateTextView = findViewById(R.id.mediaRelease)

        // Get the extra from the Intent
        val movieVotes = intent.getSerializableExtra(MOVIE_VOTE_AVERAGE).toString()
        val movieTitle = intent.getSerializableExtra(MOVIE_TITLE).toString()
        val movieOverview = intent.getSerializableExtra(MOVIE_OVERVIEW).toString()
        val moviePosterPath = intent.getSerializableExtra(MOVIE_POSTER_PATH).toString()
        val movieReleaseDate = intent.getSerializableExtra(MOVIE_RELEASE_DATE)

        // Set the title, byline, and abstract information from the article
        titleTextView.text = movieTitle
        ratingTextView.text = "Rating: " + movieVotes + "/10.0"
        releaseDateTextView.text = "Released: " + movieReleaseDate
        overviewTextView.text = movieOverview

        // Load the media image
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" + moviePosterPath)
            .centerInside()
            .into(mediaImageView)
    }
}