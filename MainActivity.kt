package com.example.knowyourleaders

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowyourleaders.R


class MainActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Leaders>
    private lateinit var searchView: SearchView
    private lateinit var adapter: MyAdapter
    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    lateinit var leaders : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageId = arrayOf(
            R.drawable.modi,
            R.drawable.mamata,
            R.drawable.amit,
            R.drawable.rajnath,
            R.drawable.nitin,
            R.drawable.rahul,
            R.drawable.piyush,
            R.drawable.ashwini,
            R.drawable.stalin,
            R.drawable.yogi,
        )

        heading = arrayOf(
            "Narendra Damodardas Modi is the 14th Prime Minister of India since May 2014",
            "Mamata Banerjee is the Chief Minister of the state of West Bengal.",
            "Amit Anil Chandra Shah is the 31st Minister of Home Affairs since 2019.",
            "Rajnath Singh is the 29th Defence Minister of India since 2019.",
            "Nitin Jairam Gadkari is the current Minister for Road Transport & Highways.",
            "Rahul Gandhi is an Indian politician and a member of the Indian Parliament",
            "Piyush Goyal is the Cabinet Minister in the Government of India",
            "Ashwini Vaishnaw is the Minister of Railways, Communications, Electronics & Information Technology",
            "MK Stalin is the Chief Minister of Tamil Nadu since 2021.",
            "Yogi Adityanath is an Indian Hindu monk and Chief Minister of UttarPradesh"
        )

        leaders = arrayOf(
            getString(R.string.leader_modi),
            getString(R.string.leader_mamata),
            getString(R.string.leader_amit),
            getString(R.string.leader_rajnath),
            getString(R.string.leader_nitin),
            getString(R.string.leader_rahul),
            getString(R.string.leader_piyush),
            getString(R.string.leader_ashwini),
            getString(R.string.leader_stalin),
            getString(R.string.leader_yogi)

        )

        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)


        newArrayList = arrayListOf()
        for (i in imageId.indices) {
            val leaders = Leaders(imageId[i], heading[i])
            newArrayList.add(leaders)
        }


        adapter = MyAdapter(newArrayList)
        newRecyclerView.adapter = adapter

        val swipegesture = object : SwipeGesture(this) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        adapter.deleteItem(viewHolder.adapterPosition)
                    }

                    ItemTouchHelper.RIGHT -> {
                        val archiveItem = newArrayList[viewHolder.adapterPosition]
                        adapter.deleteItem(viewHolder.adapterPosition)
                        adapter.addItem(newArrayList.size,archiveItem)
                    }
                }
            }
        }


        val touchHelper = ItemTouchHelper(swipegesture)
        touchHelper.attachToRecyclerView(newRecyclerView)

        setSupportActionBar(findViewById(R.id.toolbar))


        adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(
                    this@MainActivity,
                    "You clicked on leader no. $position",
                    Toast.LENGTH_SHORT
                ).show()


                val intent = Intent(this@MainActivity, LeadersActivity::class.java)
                intent.putExtra("heading",heading[position])
                intent.putExtra("imageId",imageId[position])
                intent.putExtra("leaders", leaders[position])
                startActivity(intent)
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)

        val searchItem = menu?.findItem(R.id.search_action)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filter(newText)
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_action -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
