package com.pyruby.communities.thing

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.pyruby.communities.api.CommunityRepository
import com.pyruby.communities.api.QueryState
import com.pyruby.fragment.Member
import com.pyruby.type.Category

class AddThingViewModel(private val repo: CommunityRepository) : ViewModel() {

    val name = MutableLiveData<String>()
    val quantity = MutableLiveData<String>()
    val error = MutableLiveData<String>()
    val thingAdded = MutableLiveData(false)
    val categories = Category.values().map { it.rawValue }.toTypedArray()
    var category = Category.UNKNOWN__
    private var observer: Observer<QueryState<Member>>? = null

    fun selectedCategory(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        category = Category.values()[pos]
    }

    fun createThing(view: View) {
        val nameArg = name.value
        val quantityArg = quantity.value
        if (nameArg != null && quantityArg != null && category != Category.UNKNOWN__) {
            val addThing = repo.addThing(nameArg, quantityArg, category)
            observer = Observer<QueryState<Member>> { t ->
                when (t) {
                    is QueryState.Success -> {
                        name.value = null
                        quantity.value = null
                        category = Category.UNKNOWN__
                        error.value = null
                        addThing.removeObserver(observer!!)
                        thingAdded.value = true
                    }
                    is QueryState.Failure -> {
                        error.value = t.cause
                        addThing.removeObserver(observer!!)
                    }
                }
            }
            addThing.observeForever(observer!!)


        }
    }
}