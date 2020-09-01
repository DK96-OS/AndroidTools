

import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.a_main.*

/** Simple Activity with an AppBar and NavigationDrawer using the Navigation library 
  * Developed by DK96-OS : 2019 - 2020 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)
        
        window?.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        setContentView(R.layout.a_main)
        
        mainToolbar.setTitleTextColor(Color.rgb(255, 255, 255))
        setSupportActionBar(mainToolbar)
        
        val navController = findNavController(R.id.navHost)
        navController.setGraph(R.navigation.nav_graph)
        
        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfig)
        navigationView.setupWithNavController(navController)
        
        navController.navigate(R.id.welcomeFragment)
    }
    
}
