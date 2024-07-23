package com.example.bibliomayaya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bibliomayaya.config.config
import com.example.bibliomayaya.models.libro
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [detalleLibroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class detalleLibroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    //definir las variables
    private lateinit var lbltitulo_libro: TextView
    private lateinit var lblautor_libro: TextView
    private lateinit var lblisbn_libro: TextView
    private lateinit var lblgenero_libro: TextView
    private lateinit var lblnumero_ejemplares_disponibles: TextView
    private lateinit var lblnumero_ejemplares_ocupados: TextView
    //se asigna un id temporal
    private var id:String="6fa64aa8-333e-11ef-a079-a4bb6d503080"
    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: Button

    /*
    Request es petición que hace a la API
    StringRequest=responde un String
    JsonRequest=responde un json
    JsonArrayRequest=responde un arreglo de json
     */
//método encargado de traer la información del libro
    fun consultarLibro(){
        /*
        solo se debe consultar, si el ID
        es diferente a vacío
         */
        if(id!=""){

            var request= JsonObjectRequest(
                Request.Method.GET,//método de la petición
                config.urlLibro+id, //url
                null,//parametros
                { response->
                    //variable response contiene la respuesta de la API
                    //se convierte de json a un objeto tipo libro
                    //se genera un objeto de la librería Gson
                    val gson= Gson()
                    //se convierte
                    val libro: libro =gson.fromJson(response.toString(), libro::class.java)
                    //se modifica el atributo text de los campos con el valor del objeto
                    lbltitulo_libro .setText(response.getString( "titulo_libro" ))
                    lblautor_libro .setText(response.getString( "autor_libro" ))
                    lblisbn_libro .setText(response.getString( "isbn_libro" ))
                    lblgenero_libro .setText(response.getString( "genero_libro" ))
                    lblnumero_ejemplares_disponibles .setText(response.getInt( "numero_ejemplares_disponibles" ).toString())
                    lblnumero_ejemplares_ocupados.setText(response.getInt( "numero_ejemplares_ocupados" ).toString())
                },//respuesta correcta
                {error->
                    Toast.makeText(
                        context,
                        "Error al consultar",
                        Toast.LENGTH_LONG
                    ).show()
                } //error en la petición
            )
            //se añade la petición
            var queue= Volley.newRequestQueue(context)
            queue.add(request)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val  view=inflater.inflate(R.layout.fragment_detalle_libro, container, false)
        lblautor_libro = view.findViewById(R.id.lblAutor)
        lbltitulo_libro= view.findViewById(R.id.lblTitulo)
        lblgenero_libro = view.findViewById(R.id.lblGenero)
        lblisbn_libro= view.findViewById(R.id.lblISBN)
        lblnumero_ejemplares_disponibles= view.findViewById(R.id.lblDisponibles)
        lblnumero_ejemplares_ocupados = view.findViewById(R.id.lblOcupados)
        btnEditar= view.findViewById(R.id.btnGuardar)
        btnEditar.setOnClickListener{editarlibro()}
        btnEliminar= view.findViewById(R.id.btnEliminar)
        btnEliminar.setOnClickListener{eliminarlibro()}
        return view
    }

    // se crea el metodo editar libro
    fun editarlibro(){

    }
    // se crea el metodo eliminar libro
    fun eliminarlibro(){

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment detalleLibroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            detalleLibroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}