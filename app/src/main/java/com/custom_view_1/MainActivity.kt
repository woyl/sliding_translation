package com.custom_view_1

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.custom_view_1.adpter.BaseRecyclerAdapter
import com.custom_view_1.adpter.SuperHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var downX = 0f
        var downY = 0f
        var moveX = 0f
        var moveY = 0f
        var isStart = true
        /**属性动画*/
        val objTranslateAnimation = ObjectAnimator.ofFloat(img_right, "translationX", 0f, 200f)
        objTranslateAnimation.duration = 1000

        val objTranslateAnimation2 = ObjectAnimator.ofFloat(img_right, "translationX", 200f, 0f)
        objTranslateAnimation2.duration = 1000

        val lists = mutableListOf<String>()
        for (it in 0..500) {
            lists.add("$it")
        }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = object : BaseRecyclerAdapter<String>(this, R.layout.item_recyc, lists) {
            override fun bindData(holder: SuperHolder, position: Int, bean: String?) {
                val name = holder.getView<TextView>(R.id.tv_name)
                name.text = bean
            }
        }
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy != 0){
                    if (isStart) {
                        isStart = false
                        objTranslateAnimation.start()
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isStart = true
                    if (isStart) {
                        objTranslateAnimation2.start()
                    }
                }
            }
        })
        recycler.setOnTouchListener { p0, p1 ->

            when (p1?.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = p1.x
                    downY = p1.y
                }
                MotionEvent.ACTION_MOVE -> {
                    moveX = p1.x
                    moveY = p1.y
                    if (downY - moveY > 50 && Math.abs(downY - moveY) > 50) {//向下
                        if (isStart) {
                            isStart = false
                            objTranslateAnimation.start()
                        }
                    } else if (downY - moveY < 0 && Math.abs(downY - moveY) > 50) {//向上
                        if (isStart) {
                            isStart = false
                            objTranslateAnimation.start()
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (downY - moveY > 50 && Math.abs(downY - moveY) > 50) {//向下
                        isStart = true
                        if (isStart) {
                            objTranslateAnimation2.start()
                        }
                    } else if (downY - moveY < 0 && Math.abs(downY - moveY) > 50) {//向上
                        isStart = true
                        if (isStart) {
                            objTranslateAnimation2.start()
                        }
                    }
                }
            }
            false
        }
    }

}
