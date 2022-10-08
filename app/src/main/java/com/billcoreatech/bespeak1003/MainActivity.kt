package com.billcoreatech.bespeak1003

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.afollestad.materialdialogs.MaterialDialog
import com.billcoreatech.bespeak1003.ui.composables.BespeakScaffold
import com.billcoreatech.bespeak1003.ui.composables.BottomBar
import com.billcoreatech.bespeak1003.ui.theme.Bespeak1003Theme
import com.billcoreatech.bespeak1003.widget.NavGraphs
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.appset.AppSet
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class MainActivity : ComponentActivity() {

    lateinit var sp : SharedPreferences
    var appUpdateManager: AppUpdateManager? = null
    var appUpdateInfoTask: Task<AppUpdateInfo>? = null

    val MY_REQUEST_CODE = 1000
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sp = getSharedPreferences(packageName, MODE_PRIVATE)

        installSplashScreen().apply {
            // Animation part
            setOnExitAnimationListener { sp ->
                // Create your custom animation.
                sp.iconView.animate().rotation(180F).duration = 3000L
                val slideUp = ObjectAnimator.ofFloat(
                    sp.iconView,
                    View.TRANSLATION_Y,
                    0f,
                    -sp.iconView.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 3000L

                // Call SplashScreenView.remove at the end of your custom animation.
                slideUp.doOnEnd { sp.remove() }

                // Run your animation.
                slideUp.start()
            }
        }

        appUpdateManager = AppUpdateManagerFactory.create(this@MainActivity)
        // Returns an intent object that you use to check for an update.
        appUpdateInfoTask = appUpdateManager!!.appUpdateInfo
        doUpdateCheck()
        getIdAndLat()
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onStart() {
        super.onStart()

        setContent {

            val engine = rememberAnimatedNavHostEngine()
            val navController = engine.rememberNavController()
            val startRoute = NavGraphs.root.startRoute

            Bespeak1003Theme(darkTheme = isSystemInDarkTheme()) {
                BespeakScaffold(
                    navController = navController,
                    startRoute = startRoute,
                    bottomBar = {
                        BottomBar(navController)
                    }
                ) {
                    DestinationsNavHost(
                        engine = engine,
                        navController = navController,
                        navGraph = NavGraphs.root,
                        modifier = Modifier.padding(it),
                        startRoute = startRoute
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        MaterialDialog(this@MainActivity).show {
            icon(R.drawable.ic_bespeak_foreground)
            title(R.string.titleFinish)
            message(R.string.mesgFinish)
            positiveButton(R.string.OK) { doFinish() }
            negativeButton(R.string.Cancel) { this.dismiss() }
        }
    }

    private fun doUpdateCheck() {
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask!!.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request the update.
                try {
                    doRequestUpdate(appUpdateInfo)
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @Throws(IntentSender.SendIntentException::class)
    private fun doRequestUpdate(appUpdateInfo: AppUpdateInfo) {
        Log.e(TAG, "do Update Start")
        appUpdateManager!!.startUpdateFlowForResult( // Pass the intent that is returned by 'getAppUpdateInfo()'.
            appUpdateInfo,  // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
            AppUpdateType.IMMEDIATE,  // The current activity making the update request.
            this,  // Include a request code to later monitor this update request.
            MY_REQUEST_CODE
        )
    }

    fun getIdAndLat() {
        var adInfo: AdvertisingIdClient.Info? = null;
        try {
            adInfo = AdvertisingIdClient.getAdvertisingIdInfo(this@MainActivity)
        } catch (e : java.lang.Exception) {

        }
        var GAID = adInfo?.id
        var limitTracking = adInfo?.isLimitAdTrackingEnabled

        var client = AppSet.getClient(this@MainActivity)
        var task = client.appSetIdInfo
        task.addOnSuccessListener { info ->
            var scope = info.scope
            var id = info.id

            Log.e("getIdAndLat=", "${GAID} ${limitTracking} ${scope} ${id}")
        }

        Log.e("getIdAndLat", "----------------- END")
    }

    fun doFinish() {
        finish()
    }
    
}


