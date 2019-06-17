package com.example.swim_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.swim_2.MainActivity.Companion.IMAGE_KEY
import com.example.swim_2.models.Image
import kotlinx.android.synthetic.main.add_image_activity.*

import java.util.*
import android.support.v4.content.ContextCompat
import org.xdty.preference.colorpicker.ColorPickerDialog

class AddImageActivity: AppCompatActivity(){
    private var selectedColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
       // url_text_edit.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
      //  name_text_edit.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_image_activity)

        selectedColor = ContextCompat.getColor(this, R.color.flamingo)

        color_button.setOnClickListener {
            changeColor()
        }

        add_button.setOnClickListener {
            if (validate()) {
                Toast.makeText(this, R.string.image_added, Toast.LENGTH_SHORT).show()
                passImages()
            } else {
                Toast.makeText(this, R.string.fill_gaps, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun manageDate(): Date {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }

    private fun validate() = !(url_text_edit.text.isBlank() || name_text_edit.text.isBlank())

    private fun passImages() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(IMAGE_KEY, Image(url_text_edit.text.toString(), name_text_edit.text.toString(), manageDate(), selectedColor))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun changeColor() {
        val colors = resources.getIntArray(R.array.default_rainbow)

        val dialog = ColorPickerDialog.newInstance(
            R.string.pick_color,
            colors,
            selectedColor,
            5, // Number of columns
            ColorPickerDialog.SIZE_SMALL,
            true // True or False to enable or disable the serpentine effect
            //0, // stroke width
            //Color.BLACK // stroke color
        )

        dialog.setOnColorSelectedListener { color ->
            selectedColor = color
            color_button.setBackgroundColor(color)
        }

        @Suppress("DEPRECATION")
        dialog.show(fragmentManager, "color_dialog_test")
    }
}