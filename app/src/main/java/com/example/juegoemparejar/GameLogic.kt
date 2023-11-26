package com.example.juegoemparejar
import android.widget.TextView
import kotlinx.coroutines.*

class GameLogic(private val cardAdapter: CardAdapter, private val onGameWinListener: OnGameWinListener) {

    private val flippedCards: MutableList<Carta> = mutableListOf()
    private val matchedCards: MutableList<Carta> = mutableListOf()
    private lateinit var textContador : TextView
    private var intentos : Int = 0
    private val maxIntentos : Int = 10


    fun onCardClick(clickedCard: Carta) {
        // Comprueba si la carta clickeada no está volteada y no es igual a la carta anteriormente volteada
        if (!clickedCard.volteada && flippedCards.none { it.id + 100 == clickedCard.id }
            || !clickedCard.volteada && flippedCards.none { it.id == clickedCard.id + 100 }) {
            clickedCard.flip()
            flippedCards.add(clickedCard)

            cardAdapter.notifyDataSetChanged()

            // Si hay dos cartas volteadas, se verifica si son iguales después de meterle un delay para observar.
            if (flippedCards.size == 2) {
                GlobalScope.launch {
                    delay(300) // Retraso de 300 milisegundos
                    checkForMatches()
                }
            }
        }
    }

    /*
    Método  que verifica si las dos cartas volteadas son iguales.
     */

    private suspend fun checkForMatches() {
        withContext(Dispatchers.Main) {
            // Si las dos cartas tienen ids consecutivos, se consideran iguales
            if (flippedCards[0].id == flippedCards[1].id + 100 || flippedCards[0].id + 100 == flippedCards[1].id) {
                flippedCards.forEach { it.matchOn = true }
                matchedCards.addAll(flippedCards)
            } else {
                // Si las cartas no son iguales, se vuelven a voltear y se incrementa el contador de intentos
                flippedCards.forEach { it.flip() }
                intentos++
            }

            flippedCards.clear()
            cardAdapter.notifyDataSetChanged()

            //Si el tamaño de las cartas que han hecho match es igual que el contador del adaptador, has ganado.
            if (matchedCards.size == cardAdapter.itemCount) {
                onGameWinListener.onGameWin()
            }

            //Si los intentos han igualado los maximos intentos, has perdido.
            if(intentos == maxIntentos){
                onGameWinListener.onGameLoose()
            }

        }
    }

    /*
    Metodo que resetea el juego.
     */
    fun resetGame() {
        for (card in cardAdapter.getCards()) { //Restablezco el estado de cada carta en el adaptador.
            card.volteada = false
            card.matchOn = false
        }

        intentos = 0
        flippedCards.clear()
        matchedCards.clear()
        com.example.juegoemparejar.cards.shuffle() //Barajo las cartas
        cardAdapter.notifyDataSetChanged() //Actualizo

    }

    fun getMaxIntentos() : Int{
        return maxIntentos
    }
    fun setTextContador (textView : TextView){
        textContador = textView
    }
    fun updateContador() {
        textContador?.text = (maxIntentos - intentos).toString()
    }

    interface OnGameWinListener {
            fun onGameWin()
            fun onGameLoose()
        }

}