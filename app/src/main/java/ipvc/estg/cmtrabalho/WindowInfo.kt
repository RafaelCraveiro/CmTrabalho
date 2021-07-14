package ipvc.estg.cmtrabalho

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import kotlin.math.log

class WindowInfo (context: Context) : GoogleMap.InfoWindowAdapter {

    var mContext = context
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.info, null)

    private fun rendowWindowText(marker: Marker, view: View){

        val tvTitle = view.findViewById<TextView>(R.id.title)
        val tvSnippet = view.findViewById<TextView>(R.id.snippet)
        val image = view.findViewById<ImageView>(R.id.imageView2)
        val btn1 = view.findViewById<Button>(R.id.button4)
        val btn2 = view.findViewById<Button>(R.id.button5)

        val strs= marker.snippet.split(";").toTypedArray()

        tvTitle.text = marker.title
        tvSnippet.text = strs[0]

        Log.d("TAG1", strs[3])
        Log.d("TAG2", strs[2])

        val decodedString: ByteArray = Base64.decode(strs[1], Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        image.setImageBitmap(decodedByte)

        image.getLayoutParams().height = 450; // ajudtar tamanho da iamgem
        image.getLayoutParams().width = 600;
        image.requestLayout();

        if( strs[2].equals(strs[3])){
            btn1.visibility = (View.VISIBLE)
            btn2.visibility = (View.VISIBLE)
        }else{
            btn1.visibility = (View.GONE)
            btn2.visibility = (View.GONE)
        }


    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}