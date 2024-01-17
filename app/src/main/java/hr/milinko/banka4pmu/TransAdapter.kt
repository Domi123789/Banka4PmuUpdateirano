package hr.milinko.banka4pmu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.milinko.banka4pmu.databinding.ItemTransBinding
import hr.milinko.banka4pmu.databinding.TekstiItemBinding

class TransAdapter(
    private val textList:ArrayList<Transa>,
    private val th:Context

): RecyclerView.Adapter<TransAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransAdapter.ViewHolder {
        val v = ItemTransBinding.inflate(LayoutInflater.from(th), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: TransAdapter.ViewHolder, position: Int) {
        holder.bindItem(textList[position], th)
    }

    override fun getItemCount(): Int {
        return textList.size
    }


    class ViewHolder(private var itemBinding: ItemTransBinding) :
            RecyclerView.ViewHolder(itemBinding.root){
                fun bindItem(transa: Transa, th: Context){
                    itemBinding.id.text=transa.id.toString()
                    itemBinding.id.text=transa.iznosNovca.toString()
                    itemBinding.id.text=transa.datTrans.toString()
                }
            }
}