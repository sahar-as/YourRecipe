package ir.saharapps.yourreciepe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation

import android.view.animation.AnimationUtils
import ir.saharapps.yourreciepe.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashBinding: ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        val splashAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        splashBinding.txtSplashActivityAppName.animation = splashAnimation

        splashAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationEnd(p0: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                },1000)
            }

            override fun onAnimationStart(p0: Animation?) {
                //nothing here
            }

            override fun onAnimationRepeat(p0: Animation?) {
                //noting here
            }
        })

    }
}