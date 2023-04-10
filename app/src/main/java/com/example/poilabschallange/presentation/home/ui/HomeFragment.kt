package com.example.poilabschallange.presentation.home.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.poilabschallange.R
import com.example.poilabschallange.data.remote.api.HomeService
import com.example.poilabschallange.data.repository.HomeRepository
import com.example.poilabschallange.data.repository.impl.HomeRepositoryImpl
import com.example.poilabschallange.databinding.CustomCarouselItemBinding
import com.example.poilabschallange.databinding.FragmentHomeBinding
import com.example.poilabschallange.domain.usecase.HomeUseCase
import com.example.poilabschallange.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var carouselData: MutableList<CarouselItem>
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        carouselData = mutableListOf()

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.carousel.registerLifecycle(viewLifecycleOwner)
        viewModel.getCountries()

        addListener()
        addObservers()
    }

    private fun addObservers() {
        viewModel.countries.observe(viewLifecycleOwner){
            Log.d("COUNTRIES_HOME", it.toString())
            carouselData.addAll(it.map { entity ->
                CarouselItem(
                    imageUrl = entity.flags?.png,
                    headers = mapOf(
                        "title" to "${entity.name?.official}",
                        "capital" to "${entity.capital?.joinToString(",")}",
                        "currency" to "${entity.currencies?.keys?.toList()?.getOrNull(0)}"
                    )
                )
            })
            binding.carousel.setData(carouselData)
        }
    }

    private fun addListener() {
        // Carousel listener
        binding.carousel.carouselListener = object : CarouselListener {

            override fun onCreateViewHolder(
                layoutInflater: LayoutInflater,
                parent: ViewGroup
            ): ViewBinding? {
                // Here, our XML layout file name is custom_item_layout.xml. So our view binding generated class name is CustomItemLayoutBinding.
                return CustomCarouselItemBinding.inflate(layoutInflater, parent, false)
            }

            override fun onBindViewHolder(
                binding: ViewBinding,
                item: CarouselItem,
                position: Int
            ) {
                // Cast the binding to the returned view binding class of the onCreateViewHolder() method.
                val carouselBinding = binding as CustomCarouselItemBinding
                Glide.with(requireContext()).load(item.imageUrl).into(carouselBinding.iLogo)
                carouselBinding.tTitle.text = item.headers?.get("title")
                carouselBinding.tCapital.text = item.headers?.get("capital")
                carouselBinding.tCurrency.text = item.headers?.get("currency")
            }

            override fun onClick(position: Int, carouselItem: CarouselItem) {
                // ...
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                // ...
            }
        }
    }
}