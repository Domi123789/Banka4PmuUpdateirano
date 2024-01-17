package hr.milinko.banka4pmu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.milinko.banka4pmu.databinding.ActivityTransakcijaBinding
import java.lang.Exception

class Transakcija : AppCompatActivity() {

    lateinit var binding: ActivityTransakcijaBinding
    private val database: DatabaseReference = FirebaseDatabase.getInstance("https://firebaza-94a81-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Transakcije")
    private var tekst1=ArrayList<Transa>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTransakcijaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Prenesi.setOnClickListener{
            val tempIznosNovca=binding.iznos.text.toString()
            val tempDatTrans= binding.datum
            var tempId=0
            if (!tekst1.isEmpty()) tempId= tekst1[tekst1.size-1].id+1

            tekst1.add(Transa(tempId,tempIznosNovca,tempDatTrans))

            database.setValue(tekst1)
        }
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tekst1.clear()
                try{
                    val a:List<Transa> = snapshot.children.map { dataSnapshot ->  dataSnapshot.getValue(Transa::class.java)!!}
                    tekst1.addAll(a)

                }catch (e: Exception){}

                binding.transakcijee.apply{
                    layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = TransAdapter(tekst1, this@Transakcija)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}