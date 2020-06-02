package com.katerinah.dinnerdecider

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    // Set up local storage

    private val keyString = "foodList"
    private val defaultFoodList: MutableSet<String> = mutableSetOf("Chinese", "Mexican", "Indian", "Pizza", "Italian", "Japanese", "Burgers", "BBQ", "Chicken", "Salads")

    private fun saveFoodSet(foodSet: MutableSet<String>){
        val foodString: String = foodSet.joinToString(",")
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return

        with (sharedPref.edit()) {
            putString(keyString, foodString)
            commit()
        }
    }

    private fun getFoodSet(): MutableSet<String> {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val foodListString = sharedPref.getString(this.keyString, null)
        val foodList = foodListString?.split(",")?.toMutableSet()

        return if (foodList.isNullOrEmpty()){
            defaultFoodList
        } else {
            foodList
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myFoodList = getFoodSet()

        // Components
        val foodTextView: TextView = findViewById(R.id.FoodText)
        val decideBtn: Button = findViewById(R.id.decideBtn)
        val addFoodBtn: Button = findViewById(R.id.addFoodBtn)
        val addFoodText: EditText = findViewById(R.id.addFoodText)
        val clearBtn: Button = findViewById(R.id.ClearBtn)

        // Listeners
        decideBtn.setOnClickListener {
            val random = Random()
            val randomFoodId = random.nextInt(myFoodList.count())
            foodTextView.text = myFoodList.elementAt(randomFoodId)
        }

        addFoodBtn.setOnClickListener {
            val newFood: String = addFoodText.text.toString().capitalize()
            if (newFood.isNotEmpty()) {
                myFoodList.add(newFood)
                saveFoodSet(myFoodList)
                addFoodText.text = null
            }
        }

        clearBtn.setOnClickListener {
            saveFoodSet(defaultFoodList)
        }
    }
}
