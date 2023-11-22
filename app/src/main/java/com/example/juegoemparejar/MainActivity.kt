package com.example.juegoemparejar

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class MainActivity : AppCompatActivity(), GameLogic.OnGameWinListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private val flippedCards: MutableList<Carta> = mutableListOf()
    private lateinit var gameLogic: GameLogic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cards.shuffle()

        val filteredCards = cards

        recyclerView = findViewById(R.id.contenedor)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        cardAdapter = CardAdapter(filteredCards) { clickedCard ->
            gameLogic.onCardClick(clickedCard)
        }
        recyclerView.adapter = cardAdapter

        gameLogic = GameLogic(cardAdapter, this)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menucategorias, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onGameWin() {
        Toast.makeText(this, "¡Has ganado!", Toast.LENGTH_SHORT).show()
    }
}
