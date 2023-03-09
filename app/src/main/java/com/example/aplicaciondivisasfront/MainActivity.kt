package com.example.aplicaciondivisasfront

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader

class MainActivity : AppCompatActivity() {

    lateinit var spnCurrency1: Spinner
    lateinit var spnCurrency2: Spinner

    lateinit var txtCountry1: TextView
    lateinit var txtCountry2: TextView

    val mLoaderCallbacks = object : LoaderManager.LoaderCallbacks<Cursor> {

        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            /*return CursorLoader(
                applicationContext,
                Uri.parse("content://com.example.aplicaciondivisas/cambios"),
                arrayOf<String>("_ID", "codeMonedaCambio", "cambio"),
                null, null, null
            )*/

            //var leastestExchangeRates = getContext().

            /*var leatestER = CursorLoader(
                applicationContext,
                Uri.parse("content://com.example.aplicaciondivisas/leatestER"),
                arrayOf<String>("_ID", "rates","fecha"),
                null, null, null)*/

            return CursorLoader(
                applicationContext,
                Uri.parse("content://com.example.aplicaciondivisas/monedas"),
                arrayOf<String>("_ID", "codeMoneda","nombreMoneda"),
                null, null, null)
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {}

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            val adapter = SimpleCursorAdapter(
                applicationContext,
                android.R.layout.simple_list_item_2, data,
                arrayOf<String>("_ID", "codeMoneda", "nombreMoneda"),
                IntArray(3).apply {
                    set(1, android.R.id.text1)
                    set(2, android.R.id.text2)
                },
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE
            )
            spnCurrency1.adapter = adapter
            spnCurrency2.adapter = adapter
        }

    }

    val spinnerListener = object : AdapterView.OnItemSelectedListener {
        @SuppressLint("Range")
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedCursor = parent?.getItemAtPosition(position) as Cursor
            val pais = selectedCursor.getString(selectedCursor.getColumnIndex("nombreMoneda"))
            val viewId = parent?.id
            when (viewId) {
                R.id.currency1 -> {
                    txtCountry1.text = pais
                }
                R.id.currency2 -> {
                    txtCountry2.text = pais
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // No se seleccionó nada en el Spinner
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spnCurrency1 = findViewById(R.id.currency1)
        spnCurrency2 = findViewById(R.id.currency2)

        txtCountry1 = findViewById(R.id.text1)
        txtCountry2 = findViewById(R.id.text2)

        LoaderManager.getInstance(this)
            .initLoader<Cursor>(1001, null, mLoaderCallbacks)

        spnCurrency1.onItemSelectedListener = spinnerListener
        spnCurrency2.onItemSelectedListener = spinnerListener
    }
}