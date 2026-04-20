package com.example.t3_mobile

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var tvErrorNama: TextView
    private lateinit var rgGender: RadioGroup
    private lateinit var cbMembaca: CheckBox
    private lateinit var cbCoding: CheckBox
    private lateinit var cbOlahraga: CheckBox
    private lateinit var btnTampilkan: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNama = findViewById(R.id.etNama)
        tvErrorNama = findViewById(R.id.tvErrorNama)
        rgGender = findViewById(R.id.rgGender)
        cbMembaca = findViewById(R.id.cbMembaca)
        cbCoding = findViewById(R.id.cbCoding)
        cbOlahraga = findViewById(R.id.cbOlahraga)
        btnTampilkan = findViewById(R.id.btnTampilkan)
        tvResult = findViewById(R.id.tvResult)

        btnTampilkan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            var isValid = true

            etNama.setBackgroundResource(R.drawable.bg_edittext) // Pakai background default
            etNama.error = null
            tvErrorNama.visibility = View.GONE

            if (nama.isEmpty()) {
                etNama.setBackgroundResource(R.drawable.bg_edittext_error)
                etNama.error = getString(R.string.error_nama_kosong)
                tvErrorNama.visibility = View.VISIBLE
                Toast.makeText(this, getString(R.string.error_nama_kosong_toast), Toast.LENGTH_SHORT).show()
                isValid = false
            }

            val selectedGenderId = rgGender.checkedRadioButtonId
            val gender = when (selectedGenderId) {
                R.id.rbLaki -> getString(R.string.radio_laki)
                R.id.rbPerempuan -> getString(R.string.radio_perempuan)
                else -> {
                    if (isValid) {
                        Toast.makeText(this, getString(R.string.error_gender_kosong_toast), Toast.LENGTH_SHORT).show()
                    }
                    isValid = false
                    ""
                }
            }

            if (!isValid) {
                tvResult.visibility = View.GONE
                return@setOnClickListener
            }

            tvResult.visibility = View.VISIBLE

            val hobiList = mutableListOf<String>()
            if (cbMembaca.isChecked) hobiList.add(getString(R.string.checkbox_membaca))
            if (cbCoding.isChecked) hobiList.add(getString(R.string.checkbox_coding))
            if (cbOlahraga.isChecked) hobiList.add(getString(R.string.checkbox_olahraga))
            val hobi = if (hobiList.isNotEmpty()) hobiList.joinToString(", ") else getString(R.string.hobi_kosong)
            tvResult.setBackgroundResource(R.drawable.bg_result_success)

            val darkBlue = "#1A237E"
            val hasilHtml = """
                <b><font color="$darkBlue">Nama</font></b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: $nama<br>
                <b><font color="$darkBlue">Kelamin</font></b> : $gender<br>
                <b><font color="$darkBlue">Hobi</font></b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: $hobi
            """.trimIndent()

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                tvResult.text = Html.fromHtml(hasilHtml, Html.FROM_HTML_MODE_LEGACY)
            } else {
                @Suppress("DEPRECATION")
                tvResult.text = Html.fromHtml(hasilHtml)
            }
        }
    }
}