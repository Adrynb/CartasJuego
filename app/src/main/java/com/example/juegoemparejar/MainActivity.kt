package com.example.juegoemparejar

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.constraintlayout.motion.widget.DesignTool
import androidx.core.content.ContextCompat
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
    private  var barajaCartas: NavBaraja = NavBaraja()
    private lateinit var toolbar: Toolbar
    private lateinit var starButton: Button
    private lateinit var textContador: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textContador = findViewById(R.id.textVidas)



        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        com.example.juegoemparejar.cards.shuffle()

        val filteredCards = com.example.juegoemparejar.cards

        recyclerView = findViewById(R.id.contenedor)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        starButton = findViewById(R.id.starrButton)
        starButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }


        cardAdapter = CardAdapter(filteredCards) { clickedCard ->
            gameLogic.onCardClick(clickedCard)
            gameLogic.updateContador()
        }
        recyclerView.adapter =cardAdapter

        gameLogic = GameLogic(cardAdapter, this)
        gameLogic.setTextContador(textContador)



        resetButton = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            resetGame()
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.nav_animals -> {
                    barajaCartas.changeCardList("Animals")
                    updateRecyclerView()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true

                }
                R.id.nav_fruits -> {
                    barajaCartas.changeCardList("Fruits")
                    updateRecyclerView()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_transports -> {
                    barajaCartas.changeCardList("OtherType")
                    updateRecyclerView()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> false
            }
        }


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
        com.example.juegoemparejar.cards.shuffle()
        val filteredCards = com.example.juegoemparejar.cards
        cardAdapter.getCards().forEach {
            it.volteada = true // Voltea todas las cartas en el adaptador para que estén boca arriba.
        }
        cardAdapter.notifyDataSetChanged()
        gameLogic.resetGame() // Reinicia la lógica del juego, limpiando las cartas volteadas y emparejadas, y barajando el mazo.
        gameLogic.updateContador() // Actualiza el contador
    }

    private fun updateRecyclerView() {
        val filteredCards = barajaCartas.currentCardList
        flippedCards.clear()
        cardAdapter.setItems(filteredCards) // Actualiza el adaptador con las cartas filtradas.
        cardAdapter.notifyDataSetChanged() // Notifica al adaptador que ha cambiado
        textContador.text = gameLogic.getMaxIntentos().toString() //Actualiza el contador igualando el texto con el numero de intentos obtenidos en la clase de gameLogic
    }



}
