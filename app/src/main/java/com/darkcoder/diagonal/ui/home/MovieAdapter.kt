package com.codinginflow.imagesearchapp.ui.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darkcoder.diagonal.R
import com.darkcoder.diagonal.data.MovieModel.Page.ContentItems.Content
import com.darkcoder.diagonal.databinding.RowMovieBinding

class MovieAdapter(val context: Context) :
    PagingDataAdapter<Content, MovieAdapter.PhotoViewHolder>(DIFFUTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PhotoViewHolder(private val binding: RowMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun assetImg(imgName: String) = context.resources.getIdentifier(
            imgName.split(".")[0],
            "drawable",
            context.packageName
        )


        fun bind(movie: Content) {
            binding.apply {
                Glide.with(itemView)
                    .load(assetImg(movie.posterImage))
                    .error(R.drawable.placeholder_for_missing_posters)
                    .placeholder(R.drawable.placeholder_for_missing_posters)
                    .into(ivPoster)
                tvTitle.text = movie.name
            }

        }
    }


//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(charSequence: CharSequence): FilterResults {
//                val filterResults = FilterResults()
//                if (charSequence.isEmpty()) {
//                    filterResults.count = currentList.size
//                    filterResults.values = currentList
//                } else {
//                    val searchChr = charSequence.toString().toLowerCase()
//                    val resultData: MutableList<Content> = ArrayList()
//                    for (userModel in currentList) {
//                        if (userModel.name.toLowerCase().contains(searchChr)) {
//                            resultData.add(userModel)
//                        }
//                    }
//                    filterResults.count = resultData.size
//                    filterResults.values = resultData
//                }
//                return filterResults
//            }
//
//            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
//                 = filterResults.values as List<Content?>
//                notifyDataSetChanged()
//            }
//        }
//    }

    companion object {
        private val DIFFUTIL = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content) =
                oldItem.posterImage == newItem.posterImage

            override fun areContentsTheSame(oldItem: Content, newItem: Content) =
                oldItem == newItem
        }
    }
}