package com.example.apmc

import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.WriterException

class MainActivity : AppCompatActivity() {
    //vaiables for imageview,edittext,button, bitmap and qrencoder.
    private lateinit var qrCodeIV: ImageView
    private lateinit var dataEdt: EditText
    private lateinit var generateQrBtn: Button
    var bitmap: Bitmap? = null
    private lateinit var qrgEncoder: QRGEncoder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initializing all variables.
        qrCodeIV = findViewById<ImageView>(R.id.idIVQrcode)
        dataEdt = findViewById<EditText>(R.id.idEdt)
        generateQrBtn = findViewById<Button>(R.id.idBtnGenerateQR)

        //intializing onclick listner for button.
        generateQrBtn.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(dataEdt.getText().toString())) {
                //if the edittext inputs are empty then execute this method showing a toast message.
                Toast.makeText(
                    this@MainActivity,
                    "Enter some text to generate QR Code",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                //below line is for getting the windowmanager service.
                val manager = getSystemService(WINDOW_SERVICE) as WindowManager
                //initializing a variable for default display.
                val display = manager.defaultDisplay
                //creating a variable for point which is to be displayed in QR Code.
                val point = Point()
                display.getSize(point)
                //getting width and height of a point
                val width = point.x
                val height = point.y
                //generating dimension from width and height.
                var dimen = if (width < height) width else height
                dimen = dimen * 3 / 4
                //setting this dimensions inside our qr code encoder to generate our qr code.
                qrgEncoder =
                    QRGEncoder(dataEdt.getText().toString(), null, QRGContents.Type.TEXT, dimen)
                try {
                    //getting our qrcode in the form of bitmap.
                    bitmap = qrgEncoder.bitmap
                    // the bitmap is set inside our image view using .setimagebitmap method.
                    qrCodeIV.setImageBitmap(bitmap)
                } catch (e: WriterException) {
                    //this method is called for exception handling.
                    Log.e("Tag", e.toString())
                }
            }
        })
    }
}