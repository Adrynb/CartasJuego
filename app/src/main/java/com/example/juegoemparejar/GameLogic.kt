package com.example.juegoemparejar
import kotlinx.coroutines.*

class GameLogic(private val cardAdapter: CardAdapter, private val onGameWinListener: OnGameWinListener) {

    private val flippedCards: MutableList<Carta> = mutableListOf()
    private val matchedCards: MutableList<Carta> = mutableListOf()

    fun onCardClick(clickedCard: Carta) {
        if (!clickedCard.volteada && flippedCards.none { it.id + 100 == clickedCard.id }
            || !clickedCard.volteada && flippedCards.none { it.id == clickedCard.id + 100 }) {
            clickedCard.flip()
            flippedCards.add(clickedCard)

            cardAdapter.notifyDataSetChanged()

            if (flippedCards.size == 2) {
                GlobalScope.launch {
                    delay(300) // Retraso de 300 milisegundos
                    checkForMatches()
                }
            }
        }
    }

    private suspend fun checkForMatches() {
        withContext(Dispatchers.Main) {
            if (flippedCards[0].id == flippedCards[1].id + 100 || flippedCards[0].id + 100 == flippedCards[1].id) {
                flippedCards.forEach { it.matchOn = true }
                matchedCards.addAll(flippedCards)
            } else {
                flippedCards.forEach { it.flip() }
            }

            flippedCards.clear()
            cardAdapter.notifyDataSetChanged()

            if (matchedCards.size == cardAdapter.itemCount) {
                onGameWinListener.onGameWin()
            }
        }
    }
        interface OnGameWinListener {
            fun onGameWin()
        }

}