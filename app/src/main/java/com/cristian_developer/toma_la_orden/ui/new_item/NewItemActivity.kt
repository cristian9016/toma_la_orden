package com.cristian_developer.toma_la_orden.ui.new_item

import android.annotation.SuppressLint
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.Plato
import com.cristian_developer.toma_la_orden.data.preferences.UserSession
import com.cristian_developer.toma_la_orden.databinding.ActivityNewItemBinding
import com.cristian_developer.toma_la_orden.util.*
import com.jakewharton.rxbinding3.view.clicks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_new_item.*
import org.jetbrains.anko.longToast

class NewItemActivity : AppCompatActivity() {

    private val dis = LifeDisposable(this)
    private val viewModel: NewItemViewModel by lazy { buildViewModel<NewItemViewModel>() }
    private var plato: Plato? = null
    private var image = ""
    lateinit var binding: ActivityNewItemBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_item)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        plato = intent.getParcelableExtra("plato")
        if (plato != null) {
            plateName.setText(plato!!.nombre)
            platePrice.setText(plato!!.precio)
            title = getString(R.string.edit_plate)
            image = plato!!.foto
//            dis add permission().subscribe {
//                if(it) PhotoUtil.loadImage(this, plato!!)
//                else toast("Permisos Denegados")
//            }
        } else title = getString(R.string.new_plate)

        binding.image = image

        itemImage.setOnClickListener {
            PhotoUtil.selectImage(this)
                .subscribe()
        }

        btnSavePlate.setOnClickListener {
            dis add validateForm(R.string.empty_fields, plateName.toText(), platePrice.toText())
                .flatMap {
                    if (plato != null) viewModel.updatePlate(
                        UserSession.token,
                        Plato(null, plato!!.idUsuario, it[0], image, it[1]),
                        plato!!._id!!
                    )
                    else viewModel.addPlate(
                        UserSession.token,
                        Plato(null, null, it[0], image, it[1])
                    )
                }
                .subscribe(
                    {
                        if (plato != null) longToast(R.string.plate_edited)
                        else longToast(R.string.plate_added)
                        finish()
                    },
                    {
                        longToast(it.message!!)
                    }
                )
        }
    }

    override fun onResume() {
        super.onResume()
        dis add PhotoUtil.processedImg
            .subscribe {
                Picasso.get().load(it.first).into(plateImage)
                image = it.second
            }
    }

//    fun permission() = RxPermissions(this)
//        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        PhotoUtil.processImage(this, requestCode, resultCode, 200, 200, data)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()

    }
}
