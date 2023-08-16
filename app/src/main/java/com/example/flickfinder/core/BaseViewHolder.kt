package com.example.flickfinder.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * este BaseViewHolder.lo hacemos abstracto para poder reutilizarlo
 * donde querramos y facilitar la creacion de los adaptadores,
 * agregamos un método Abstract fun bind (item: T), se va a encargar
 * de "bindiar" las vistas.
 */

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T)
}