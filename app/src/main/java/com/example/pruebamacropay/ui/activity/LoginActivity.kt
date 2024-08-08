package com.example.pruebamacropay.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebamacropay.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

/**
 * Activity usado para mostrar una pagina de inicio de sesion simple con credenciales hardcodeadas.
 */
class LoginActivity: AppCompatActivity() {

    // Credenciales hardcodeadas para inicio de sesion manual.
    private val staticUsername = "user"
    private val staticPassword = "password"

    // Instancia de FirebaseAuth para gestionar la autenticación.
    private lateinit var auth: FirebaseAuth

    // Instancia de progress bar que sera mostrado como indicador mientras se realiza la autenticacion a Firebase
    private lateinit var circleProgressIndicator: CircularProgressIndicator

    /**
     * Metodo usado para crear la actividad y mostrarla.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se carga el XML que representa la pantalla a mostrar.
        setContentView(R.layout.activity_login)

        // INICIALIZACION DEL LOGIN CON FIREBASE
        // Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Inicializamos los elementos de la interfaz que contiene el XML layout relacionados al inicio de sesion usando Firebase.
        val mailFirebaseAccount = findViewById<TextInputEditText>(R.id.firebase_email_edit_text)
        val passwordFirebaseAccount = findViewById<TextInputEditText>(R.id.firebase_password_edit_text)
        val buttonFirebaseLogin = findViewById<MaterialButton>(R.id.firebase_login_button)
        circleProgressIndicator = findViewById(R.id.progress_indicator_firebase_login)

        // Agregamos un listener para escuchar cuando el boton de "Login por Firebase" sea presionado.
        buttonFirebaseLogin.setOnClickListener {

            // Obtenemos los valores ingresados por el usuario (o en este caso, hardcodeados directamente en el layout).
            val email = mailFirebaseAccount.text.toString()
            val password = passwordFirebaseAccount.text.toString()

            // Mostrar el progress indicator mientras se realiza la autenticación con Firebase.
            circleProgressIndicator.visibility = CircularProgressIndicator.VISIBLE

            // Autenticación con Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    // Ocultar el progress bar al finalizar el proceso de autenticación.
                    circleProgressIndicator.visibility = CircularProgressIndicator.GONE

                    if (task.isSuccessful) {

                        // Inicio de sesión exitoso, navega a la actividad de catálogo de películas
                        val intent = Intent(this, MovieCatalogActivity::class.java)
                        startActivity(intent)

                    } else {

                        // Muestra un mensaje de error en caso de fallo.
                        Toast.makeText(this, getString(R.string.firebase_login_error_message), Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // INICIALIZACION DE LOGIN MANUAL
        // Inicializamos los elementos de la interfaz que contiene el XML layout.
        val usernameEditText = findViewById<TextInputEditText>(R.id.username_edit_text)
        val passwordEditText = findViewById<TextInputEditText>(R.id.password_edit_text)
        val loginButton = findViewById<MaterialButton>(R.id.login_button)

        // Agregarmos un listener para escuchar cuando el boton de "Login" sea presionado.
        loginButton.setOnClickListener {

            // Obtiene los valores tecleados por el usuario dentro de los campos usuario y contrasena.
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Verificamos si las credenciales ingresadas corresponden a las hardcodeadas, de lo contrario se muestra un mensaje simple de error.
            if (username == staticUsername && password == staticPassword) {
                val intent = Intent(this, MovieCatalogActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.wrong_credentials), Toast.LENGTH_SHORT).show()
            }
        }
    }

}