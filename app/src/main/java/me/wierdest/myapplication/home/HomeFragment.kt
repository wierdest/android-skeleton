package me.wierdest.myapplication.home

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import androidx.core.math.MathUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import me.wierdest.myapplication.R
import me.wierdest.myapplication.database.MyViewModel
import me.wierdest.myapplication.databinding.FragmentHomeBinding
import me.wierdest.myapplication.utilities.hideSupportBar
import me.wierdest.myapplication.utilities.linearConversion

const val MAX_SCROLL_TIME_IN_MILLI = 100000
const val MIN_SCROLL_TIME_IN_MILLI = 15000

class HomeFragment : Fragment() {

    val TAG = javaClass.simpleName

    private lateinit var b: FragmentHomeBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var preferences: SharedPreferences

    @Suppress("InlinedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        b = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        b.lifecycleOwner = this

        /**
         * Now, instead of having a viewModel created from scratch,
         * the same old instance created by TitleFragment is requested
         */
        viewModel =
            ViewModelProvider(this.requireParentFragment()).get(MyViewModel::class.java)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)

        hideSupportBar(true)


        b.tabScroll.viewTreeObserver.addOnGlobalLayoutListener( object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                b.tabScroll.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val anim = ObjectAnimator.ofInt(b.tabScroll, "scrollY", b.tabScroll.getChildAt(0).height - b.tabScroll.height)
                anim.duration = MathUtils.clamp(preferences.getLong("time", 10000L), 0L, MAX_SCROLL_TIME_IN_MILLI.toLong())
                anim.interpolator = LinearInterpolator()
                anim.start()
                anim.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        animation.duration = preferences.getLong("time", 10000L)

                    }
                    override fun onAnimationEnd(animation: Animator?) = Unit
                    override fun onAnimationCancel(animation: Animator?) = Unit
                    override fun onAnimationRepeat(animation: Animator?) = Unit
                })

                b.tabText.setOnClickListener {
                    if (anim.isRunning) anim.cancel() else anim.start()
                    Log.i(TAG, "clicked! ${anim.duration}")

                }
            }
        })

        /**
         * TAB TO READ OBSERVER
         */

        viewModel.tabToRead.observe(viewLifecycleOwner, Observer {
            it?.also {
                b.tabText.text = it.raw
            }
        })

        return b.root

    }

}

