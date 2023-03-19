package com.example.proyecto_ironbloodimages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var user:String
    lateinit var email:String
    lateinit var admin: String
    lateinit var headerName: TextView
    lateinit var headerEmail: TextView
    lateinit var menuHome: MenuItem
    lateinit var menuLogin: MenuItem
    lateinit var menuLogout: MenuItem
    lateinit var menuRegister: MenuItem
    lateinit var menuAgregar: MenuItem
    lateinit var header: View
    lateinit var menu: Menu
    lateinit var navView: NavigationView
    lateinit var mBundle:Bundle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        user = intent.getStringExtra("username").toString()
        email = intent.getStringExtra("email").toString()
        admin = intent.getStringExtra("admin").toString()
        mBundle = Bundle()
        //////
        FragmentoEnFrameLayout(Inicio())
        //////
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.nav_view)
        header = navView.getHeaderView(0)
        menu = navView.menu
        headerName = header.findViewById(R.id.tv_nombre)
        headerEmail = header.findViewById(R.id.tv_correo)
        menuHome = menu.findItem(R.id.menu_home)
        menuLogin = menu.findItem(R.id.menu_login)
        menuLogout = menu.findItem(R.id.menu_logout)
        menuRegister = menu.findItem(R.id.menu_register)
        menuAgregar = menu.findItem(R.id.menu_agregar)
        userLogged()
        userDetail()

        toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> {
                    FragmentoEnFrameLayout(Inicio())
                    drawerLayout.closeDrawers()
                }
                R.id.menu_login ->{
                    FragmentoEnFrameLayout(Login())
                    drawerLayout.closeDrawers()
                }
                R.id.menu_register ->{
                    FragmentoEnFrameLayout(Registrarse())
                    drawerLayout.closeDrawers()
                }
                R.id.menu_logout ->{
                    logout()
                }
                R.id.menu_buscar ->{
                    FragmentoEnFrameLayout(Buscar())
                    drawerLayout.closeDrawers()
                }
                R.id.menu_agregar ->{
                    FragmentoEnFrameLayout(Agregar())
                    drawerLayout.closeDrawers()
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun FragmentoEnFrameLayout(fragment: Fragment?){
        if (fragment == null) return
        val fm = supportFragmentManager
        val tr = fm.beginTransaction()
        mBundle.putString("user", user)
        mBundle.putString("admin", admin)
        fragment.arguments = mBundle
        tr.replace(R.id.framelayout, fragment)
        tr.commit()
    }

    private fun userLogged(){
        if(user === "null" && email === "null"){
            if(admin == "No" || admin == "null"){
                menuAgregar.setVisible(false)
            }
            else{
                menuAgregar.setVisible(true)
            }
            headerName.setText("Puede iniciar sesion en la parte de Login")
            headerEmail.setText("")
            menuLogout.setVisible(false)
            menuRegister.setVisible(true)
            menuLogin.setVisible(true)
            header.isGone = true
        }
        else{
            if(admin == "No"){
                menuAgregar.setVisible(false)
            }
            else{
                menuAgregar.setVisible(true)
            }
            headerName.setText(user)
            headerEmail.setText(email)
            menuLogin.setVisible(false)
            menuRegister.setVisible(false)
            menuLogout.setVisible(true)
            header.isVisible = true
        }
    }

    fun logout(){
        finish()
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun userDetail(){
        header.setOnClickListener {
            FragmentoEnFrameLayout(UsuarioF())
            drawerLayout.closeDrawers()
        }
    }
}