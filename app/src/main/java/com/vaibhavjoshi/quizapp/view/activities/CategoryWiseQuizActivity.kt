package com.vaibhavjoshi.quizapp.view.activities

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vaibhavjoshi.quizapp.R
import com.vaibhavjoshi.quizapp.databinding.ActivityCategoryWiseQuizBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryWiseQuizActivity : AppCompatActivity() {

    private var _binding: ActivityCategoryWiseQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCategoryWiseQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        init()
    }

    private fun init() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_host_fragments) as NavHostFragment
        navController = navHostFragment.navController
//        checkPermissionRequired()

    }
/*

    companion object {

        var permission_s = arrayOf(

            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET
        )

        var permission_r = arrayOf(

            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
        )

    }

    // Method to Check the Permission Required
    private fun checkPermissionRequired() {
        this.let {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!hasPermission(it as Context, permission_s)) {
                    permReqLauncher.launch(permission_s)
                }
            }

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
                if (!hasPermission(it as Context, permission_r)) {
                    permReqLauncher.launch(permission_r)
                }
            }
        }
    }

    // Variable to provide Response That Permission is Granted or Not
    private val permReqLauncher =  registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val granted = permissions.entries.all { it.value } // Checks the permission is granted or not

        if (!granted) {
            // navigate to respective screen
            //startFragment()
            showPermissionDialog(this@CategoryWiseQuizActivity)
        } else {
            // show custom alert
            //Previously Permission Request was cancelled with 'Dont Ask Again',
            // Redirect to Settings after showing Information about why you need the permission
        }
    }

    // Method Checks That Permission is Provided or not
    private fun hasPermission(context: Context, permissions: Array<String>): Boolean = permissions.all { ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
*/

    fun initActionbar(
        appbarTitle: String,
        leftButton: Int = R.drawable.icon_back,
        rightImageButton : Int = 0,
        rightButton: Int = 0,
        leftButtonClick: () -> Unit = {},
        rightButtonClick: () -> Unit = {},
        secondRightButtonClick: () -> Unit = {},
        isVisible: Boolean = true
    ) {

        if (isVisible) binding.customActionBar.root.visibility = View.VISIBLE else binding.customActionBar.root.visibility = View.GONE

        val toolbar = binding.customActionBar.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        toolbar.removeAllViews()

        //left side button
        val leftImageButton = AppCompatImageButton(this)
        val leftImageButtonLayoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,Toolbar.LayoutParams.WRAP_CONTENT)
        leftImageButtonLayoutParams.gravity = Gravity.START
        leftImageButton.layoutParams = leftImageButtonLayoutParams
        leftImageButton.setBackgroundColor(0)
        leftImageButton.setImageResource(leftButton)
        leftImageButton.setOnClickListener {
            leftButtonClick()
        }
        toolbar.addView(leftImageButton)

        //center Textview
        val textToolbarLabel = TextView(this)
        textToolbarLabel.text = appbarTitle
        textToolbarLabel.textSize = 24F
        textToolbarLabel.typeface = Typeface.DEFAULT_BOLD
        textToolbarLabel.setTextColor(ContextCompat.getColorStateList(this, R.color.green))
        val textToolbarLayoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,Toolbar.LayoutParams.WRAP_CONTENT)
        textToolbarLayoutParams.gravity = Gravity.CENTER
        textToolbarLabel.layoutParams = textToolbarLayoutParams
        toolbar.addView(textToolbarLabel)

        //Right side button
        val rightSideImageButton = AppCompatImageButton(this)
        val rightImageButtonLayoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,Toolbar.LayoutParams.WRAP_CONTENT)
        rightImageButtonLayoutParams.gravity = Gravity.END
        rightImageButtonLayoutParams.rightMargin = resources.getDimensionPixelSize(R.dimen.dp_8)
        rightSideImageButton.layoutParams = rightImageButtonLayoutParams
        rightSideImageButton.setBackgroundColor(0)
        rightSideImageButton.setImageResource(rightButton)
        rightSideImageButton.setOnClickListener {
            rightButtonClick()
        }
        toolbar.addView(rightSideImageButton)

        //Right side second button
        val secondRightImageButton = AppCompatImageButton(this)
        val secondRightImageButtonLayoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,Toolbar.LayoutParams.WRAP_CONTENT)
        secondRightImageButtonLayoutParams.gravity = Gravity.END
        secondRightImageButtonLayoutParams.rightMargin = resources.getDimensionPixelSize(R.dimen.dp_8)
        secondRightImageButtonLayoutParams.leftMargin = resources.getDimensionPixelSize(R.dimen.dp_8)
        secondRightImageButtonLayoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.dp_8)
        secondRightImageButtonLayoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.dp_8)
        secondRightImageButton.layoutParams = secondRightImageButtonLayoutParams
        secondRightImageButton.setBackgroundColor(0)
        secondRightImageButton.setImageResource(rightImageButton)
        secondRightImageButton.setOnClickListener {
            secondRightButtonClick()
        }
        toolbar.addView(secondRightImageButton)
    }

    fun initScoreActionbar(
        leftButtonText: String,
        rightButtonText: String,
        appbarTitle: String,
        isVisible: Boolean = true
    ) {
        if (isVisible) binding.customActionBar.root.visibility = View.VISIBLE
        else binding.customActionBar.root.visibility = View.GONE

        val toolbar = binding.customActionBar.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        toolbar.removeAllViews()

        // Create a container layout to hold all elements inside the toolbar
        val toolbarLayout = LinearLayout(this).apply {
            layoutParams = Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        // Left Button TextView
        val textToolbarLeftLabel = TextView(this).apply {
            text = leftButtonText
            textSize = 18F
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(ContextCompat.getColorStateList(this@CategoryWiseQuizActivity, R.color.blue))
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1F
            )
        }

        // Center Title TextView
        val textToolbarLabel = TextView(this).apply {
            text = appbarTitle
            textSize = 24F
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(ContextCompat.getColorStateList(this@CategoryWiseQuizActivity, R.color.green))
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 2F
            )
        }

        // Right Button TextView
        val textToolbarRightLabel = TextView(this).apply {
            text = rightButtonText
            textSize = 18F
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(ContextCompat.getColorStateList(this@CategoryWiseQuizActivity, R.color.orange))
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1F
            )
        }

        // Add views to LinearLayout container
        toolbarLayout.addView(textToolbarLeftLabel)
        toolbarLayout.addView(textToolbarLabel)
        toolbarLayout.addView(textToolbarRightLabel)

        // Add container layout to toolbar
        toolbar.addView(toolbarLayout)
    }

}