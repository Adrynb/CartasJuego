package com.example.juegoemparejar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(private var cards: List<Carta>, private val onCardClickListener: (Carta) -> Unit) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    //Listas de cartas volteadas en el juego
    private val cartasVolteadas: MutableList<Carta> = mutableListOf()

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImageView: ImageView = itemView.findViewById(R.id.imageView) //Enseña la carta en el juego.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemcard, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]

        if(card.matchOn){
            holder.cardImageView.setImageResource(card.imageId) //Si la carta ha hecho match, se enseñan las imagen
        }
        else{
            holder.cardImageView.setImageResource(if (card.volteada) card.imageId else R.drawable.poker_svgrepo_com) //Si no, vuelve a su estado original.
        }


        holder.itemView.setOnClickListener {
            onCardClickListener.invoke(card)
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun setItems(newCards: MutableList<Carta>) {
        cards = newCards
        notifyDataSetChanged()
    }

    fun getCards(): List<Carta> {
        return cards
    }


}