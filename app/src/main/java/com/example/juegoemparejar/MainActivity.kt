package com.example.juegoemparejar

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), GameLogic.OnGameWinListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private val flippedCards: MutableList<Carta> = mutableListOf()
    private lateinit var gameLogic: GameLogic
    private lateinit var resetButton: Button
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        cards.shuffle()

        val filteredCards = cards

        recyclerView = findViewById(R.id.contenedor)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        drawerLayout.openDrawer(GravityCompat.START)

        cardAdapter = CardAdapter(filteredCards) { clickedCard ->
            gameLogic.onCardClick(clickedCard)
        }
        recyclerView.adapter = cardAdapter

        resetButton = findViewById(R.id.resetButton)
        resetButton.setOnClickListener{
            resetGame()
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_animals -> {

                    true
                }
                R.id.nav_fruits -> {

                    true
                }

                R.id.nav_transports -> {

                    true
                }

                else -> false
            }
        }
        gameLogic = GameLogic(cardAdapter, this)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menucategorias, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onGameWin() {
        Toast.makeText(this, "¡Has ganado!", Toast.LENGTH_SHORT).show()
        resetGame()
    }

    override fun onGameLoose() {
        Toast.makeText(this, "Has perdido :c", Toast.LENGTH_SHORT).show()
        resetGame()
    }

    private fun resetGame() {
        cards.shuffle()
        val filteredCards = cards
        flippedCards.clear()
        cardAdapter.setItems(filteredCards)
        cardAdapter.notifyDataSetChanged()
        gameLogic.resetGame()
    }
}
