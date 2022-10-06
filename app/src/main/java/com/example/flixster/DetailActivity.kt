package com.example.flixster

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var overviewTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Find the views for the screen
        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        ratingTextView = findViewById(R.id.mediaRating)
        overviewTextView = findViewById(R.id.mediaOverview)

        // Get the extra from the Intent
        val movieVotes = intent.getSerializableExtra(MOVIE_VOTE_AVERAGE).toString()
        val movieTitle = intent.getSerializableExtra(MOVIE_TITLE).toString()
        val movieOverview = intent.getSerializableExtra(MOVIE_OVERVIEW).toString()
        val moviePosterPath = intent.getSerializableExtra(MOVIE_POSTER_PATH).toString()

        // Set the title, byline, and abstract information from the article
        titleTextView.text = movieTitle
        "Rating: $movieVotes/10.0".also { ratingTextView.text = it }
        overviewTextView.text = movieOverview

        // Load the media image
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" + moviePosterPath)
            .centerInside()
            .into(mediaImageView)
    }
}