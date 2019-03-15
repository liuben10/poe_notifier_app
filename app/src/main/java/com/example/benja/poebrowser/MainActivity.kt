package com.example.benja.poebrowser

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.example.benja.poebrowser.services.PoeFilterWebSocketListener
import com.example.benja.poebrowser.tasks.PoeModLoader
import com.example.benja.poebrowser.tasks.UpdateNotifierTask
import okhttp3.OkHttpClient
import okhttp3.Request

const val CHANNEL_ID: String = "POE_ITEMS_NOTIFICATION"

class MainActivity : AppCompatActivity() {

    private var drawerLayout: DrawerLayout? = null
    private var okHttpClient: OkHttpClient? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        okHttpClient = OkHttpClient()

        setContentView(R.layout.nav_drawer)
        bindDrawerLayout()
        bindToolBar()
        // -- Test Section -- //
        val loader = PoeModLoader(this)
        val loaded = loader.loadPoeMods()
        print(loaded)

        //////
        PoeAppContext.getPoeItemFilterDao(this)
    }

    private fun bindToolBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }

    override fun onStart() {
        super.onStart()
        val runnable = updateNotify()
        runnable.run()

        initializeWebSocketForTest()
        createNotificationChannel()
    }

    fun initializeWebSocketForTest() {
        val listener = PoeFilterWebSocketListener()
        val req = Request.Builder().url("ws://10.0.2.2:8080/gs-guide-websocket").build()
        val ws = okHttpClient!!.newWebSocket(req, listener)

        okHttpClient!!.dispatcher().executorService().shutdown()
    }

    fun updateNotify(): Runnable {
        return Runnable {
            val notifier = UpdateNotifierTask(this)
            try {
                notifier.execute()
            } finally {
                PoeAppContext.getHandler()!!.postDelayed(updateNotify(), 1000 * 240)
            }
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout!!.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun swapContentBody(menuItem: MenuItem) {
        val action = Router.route(menuItem.itemId)
        this.sendBroadcast(action)
    }

    private fun bindDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout!!.closeDrawers()
            swapContentBody(menuItem)
            true
        }
        drawerLayout!!.addDrawerListener(
                object : DrawerLayout.DrawerListener {
                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                        // Respond when the drawer's position changes
                        Log.i("MainActivity", "Drawer Sliding")
                    }

                    override fun onDrawerOpened(drawerView: View) {
                        // Respond when the drawer is opened
                        Log.i("MainActivity", "Drawer Opened")

                    }

                    override fun onDrawerClosed(drawerView: View) {
                        // Respond when the drawer is closed
                        Log.i("MainActivity", "Drawer Closed")
                    }

                    override fun onDrawerStateChanged(newState: Int) {
                        // Respond when the drawer motion state changes
                        Log.i("MainActivity", "Drawer State Changed")

                    }
                }
        )
    }
}
