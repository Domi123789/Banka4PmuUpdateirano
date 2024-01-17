package hr.milinko.banka4pmu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.milinko.banka4pmu.databinding.ActivityKorisnikBinding
import java.lang.Exception

class Korisnik : AppCompatActivity() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance("https://firebaza-94a81-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Korisnici")
    private var korisnik1=ArrayList<korisnici>()
    lateinit var binding: ActivityKorisnikBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityKorisnikBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.stvori.setOnClickListener{
            val tempIme=binding.ime.text.toString()
            val tempPrezime=binding.prezime.text.toString()
            val tempBrMob=binding.brMob.text.toString()
            val tempDatRodjY= binding.datum.year
            val tempDatRodjM= binding.datum.month + 1
            val tempDatRodjD= binding.datum.dayOfMonth
            val tempDatRodj="${tempDatRodjD}-${tempDatRodjM}-${tempDatRodjY}"
            var tempId=0
            if (!korisnik1.isEmpty()) tempId= korisnik1[korisnik1.size-1].id+1
            korisnik1.add(korisnici(tempIme,tempPrezime,tempDatRodj,tempBrMob,tempId))
            database.setValue(korisnik1)
        }

        val emptyAdapter=korisnikAdapter(korisnik1,this@Korisnik)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                korisnik1.clear()
                try{
                    val a:List<korisnici> = snapshot.children.map { dataSnapshot ->  dataSnapshot.getValue(korisnici::class.java)!!}

                    korisnik1.addAll(a)
                }catch (e: Exception){}
                binding.RV.apply{
                    layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = korisnikAdapter(korisnik1, this@Korisnik)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}