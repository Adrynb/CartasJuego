package com.example.juegoemparejar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(private val cards: List<Carta>, private val onCardClickListener: (Carta) -> Unit) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private val flippedCards: MutableList<Carta> = mutableListOf()

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemcard, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]

        if(card.matchOn){
            holder.cardImageView.setImageResource(card.imageId)
        }
        else{
            holder.cardImageView.setImageResource(if (card.volteada) card.imageId else R.drawable.poker_svgrepo_com)
        }

        holder.itemView.setOnClickListener {
            onCardClickListener.invoke(card)
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}