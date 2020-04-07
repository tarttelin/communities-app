package com.pyruby.communities.thing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.pyruby.communities.R
import com.pyruby.communities.databinding.AddThingFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class AddThingFragment : Fragment() {

    private val vmModel: AddThingViewModel by viewModel()
    private lateinit var binding: AddThingFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_thing_fragment, container, false)
        binding.viewmodel = vmModel
        binding.lifecycleOwner = viewLifecycleOwner
        closeOnSuccessfulSave()

        return binding.root
    }

    private fun closeOnSuccessfulSave() {
        vmModel.thingAdded.observe(viewLifecycleOwner, Observer {
            if (it) {
                val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(activity!!.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                findNavController().popBackStack()
            }
        })
    }


}