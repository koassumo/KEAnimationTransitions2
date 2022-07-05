package com.example.keanimationtransitions2.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.keanimationtransitions2.R
import com.example.keanimationtransitions2.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var toRightAnimation = false
    var x = 0f
    var y = 0f

    private lateinit var viewModel: MainViewModel
    // 1. Сделать запись в gradle
    // 2. Создаем вспомогательный объект
    private var _binding: MainFragmentBinding? = null
    // 3. Создаем объект
    private val binding get() = _binding!!
    // 4. занулить вспомогательный binding здесь
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    // 5. см. ниже добавить строки в onCreateView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 5. изменение для binding здесь:
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.bind(view)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.myContainerAnim3.setOnTouchListener { v, event ->
            x = event?.x ?: 0f
            y = event?.y ?: 0f
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
//                    Toast.makeText(requireContext(), "x: $x, y: $y", Toast.LENGTH_SHORT).show()
                    mClickListener()
                }
            }
            v?.onTouchEvent(event) ?: true
        }
    }

    private fun mClickListener () {
        val changeBounds = ChangeBounds()
        changeBounds.setPathMotion (ArcMotion())
        changeBounds.duration = 1500

        TransitionManager.beginDelayedTransition(
            binding.myContainerAnim3,
            changeBounds
        )

        val params : FrameLayout.LayoutParams = binding.btn333.layoutParams as FrameLayout.LayoutParams
        params.marginStart = x.toInt() -90
        params.topMargin = y.toInt() -45
        binding.btn333.layoutParams = params

    //  example below not possible (no animation) because of attributions belong view, not viewGroup/layout
    //        binding.myContainerAnimBtn.translationX = 600F
    //        binding.myContainerAnimBtn.setX = 700f
    }
}