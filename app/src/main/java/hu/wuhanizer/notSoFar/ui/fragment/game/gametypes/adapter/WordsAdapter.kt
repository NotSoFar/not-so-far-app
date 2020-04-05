package hu.wuhanizer.notSoFar.ui.fragment.game.gametypes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import hu.wuhanizer.notSoFar.R
import hu.wuhanizer.notSoFar.data.getUserId
import hu.wuhanizer.notSoFar.databinding.ListItemWordBinding
import hu.wuhanizer.notSoFar.model.Word

interface DataChangedListener
{
    fun onDataChanged()
}

class WordsAdapter(query: Query): FirestoreRecyclerAdapter<Word,WordsAdapter.WordViewHolder>(FirestoreRecyclerOptions.Builder<Word>().setQuery(query,Word::class.java).build())
{
    var changedListener:DataChangedListener?=null

    class WordViewHolder(val binding: ListItemWordBinding): RecyclerView.ViewHolder(binding.root)
    {

        fun setWord(w: Word,index:Int)
        {
            binding.word=w
            binding.userId=getUserId()
            binding.index=index+1
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_word, parent, false))
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int, model: Word)
    {
        holder.setWord(model,position)
    }

    override fun onDataChanged() {
        changedListener?.onDataChanged()
    }

}