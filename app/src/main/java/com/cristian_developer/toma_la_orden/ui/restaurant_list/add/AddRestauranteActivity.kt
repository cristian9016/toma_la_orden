package com.cristian_developer.toma_la_orden.ui.restaurant_list.add

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.Restaurante
import com.cristian_developer.toma_la_orden.data.preferences.UserSession
import com.cristian_developer.toma_la_orden.databinding.ActivityAddRestauranteBinding
import com.cristian_developer.toma_la_orden.ui.restaurant_list.RestaurantViewModel
import com.cristian_developer.toma_la_orden.util.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_restaurante.*
import org.jetbrains.anko.longToast

class AddRestauranteActivity : AppCompatActivity() {
    private val dis = LifeDisposable(this)
    private val viewModel: RestaurantViewModel by lazy { buildViewModel<RestaurantViewModel>() }
    private var rest: Restaurante? = null
    private var image = ""
    lateinit var binding: ActivityAddRestauranteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_restaurante)

        rest = intent.getParcelableExtra("rest")
        if (rest != null) {
            restaurantName.setText(rest!!.nombre)
            title = getString(R.string.edit_rest)
            image = rest!!.foto
        } else title = getString(R.string.new_rest)

        binding.image = image

        itemImage.setOnClickListener {
            PhotoUtil.selectImage(this)
                .subscribe()
        }

        btnSaveRestaurant.setOnClickListener {
            dis add validateForm(R.string.empty_fields, restaurantName.toText())
                .flatMap {
                    if (rest != null) viewModel.updateRestaurant(
                        UserSession.token,
                        Restaurante(rest!!._id, rest!!.idDueno, image, it[0])
                    )
                    else viewModel.addRestaurant(
                        UserSession.token,
                        Restaurante(null, null, image, it[0])
                    )
                }
                .subscribe(
                    {
                        if (rest != null) longToast(R.string.restaurante_edited)
                        else longToast(R.string.restaurante_added)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        PhotoUtil.processImage(this, requestCode, resultCode, 200, 200, data)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()

    }
}
