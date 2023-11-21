package com.example.juegoemparejar

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private val flippedCards: MutableList<Carta> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Duplicate the cards to ensure pairs
        cards.shuffle()

        // Exclude the default poker card
        val filteredCards = cards

        recyclerView = findViewById(R.id.contenedor)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        cardAdapter = CardAdapter(filteredCards) { clickedCard ->
            // Handle card click event
            onCardClick(clickedCard)
        }
        recyclerView.adapter = cardAdapter
    }

    private fun onCardClick(clickedCard: Carta) {
        // Check if the card is already flipped or matched
        if (!clickedCard.volteada && flippedCards.none { it.id+100 == clickedCard.id }
            || !clickedCard.volteada && flippedCards.none { it.id == clickedCard.id+100}) {
            // Flip the clicked card
            clickedCard.flip()
            // Add the flipped card to the list
            flippedCards.add(clickedCard)

            // Update the UI
            cardAdapter.notifyDataSetChanged()

            // Check for matches after a short delay
            if (flippedCards.size == 2) {
                Handler(Looper.getMainLooper()).postDelayed({
                    checkForMatches()
                }, 300) // Adjust the delay as needed
            }
        }
    }

    private fun checkForMatches() {
        if (flippedCards[0].id == flippedCards[1].id + 100 || flippedCards[0].id + 100 == flippedCards[1].id) {
            flippedCards.forEach{it.matchOn = true}


        } else {
            flippedCards.forEach { it.flip() }

        }

        flippedCards.clear()
        cardAdapter.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menucategorias, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
