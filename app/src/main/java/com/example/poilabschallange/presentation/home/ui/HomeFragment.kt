package com.example.poilabschallange.presentation.home.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.poilabschallange.R
import com.example.poilabschallange.databinding.CustomCarouselItemBinding
import com.example.poilabschallange.databinding.FragmentHomeBinding
import com.example.poilabschallange.presentation.detail.ui.DetailActivity
import com.example.poilabschallange.presentation.home.viewmodel.HomeViewModel
import com.example.poilabschallange.presentation.home.viewmodel.OrderType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var carouselData: MutableList<CarouselItem>
    private lateinit var viewModel: HomeViewModel
    private lateinit var filterByAdapter: ArrayAdapter<String>
    private lateinit var sortByAdapter: ArrayAdapter<String>
    lateinit var dropdownFilter: AutoCompleteTextView
    lateinit var dropdownSort: AutoCompleteTextView
    lateinit var filterButton: MaterialButton
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        carouselData = mutableListOf()
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        dropdownFilter = binding.root.findViewById(R.id.dropdown_filterByRegion)
        dropdownSort = binding.root.findViewById(R.id.dropdown_sortBy)
        filterButton = binding.root.findViewById(R.id.b_submit)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.root.findViewById(R.id.main_layout))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.carousel.registerLifecycle(viewLifecycleOwner)
        binding.carousel.setIndicator(binding.indicator)
        viewModel.getAllCountries()

        addListener()
        addObservers()
        setAdapters()
    }

    private fun setAdapters() {
        filterByAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.list_item) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                (view as? TextView)?.text = getItem(position)

                return view
            }
        }
        filterByAdapter.addAll(resources.getStringArray(R.array.filter_types).toList())
        dropdownFilter.setAdapter(filterByAdapter)

        sortByAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.list_item) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                (view as? TextView)?.text = getItem(position)

                return view
            }
        }
        sortByAdapter.addAll(resources.getStringArray(R.array.sort_types).toList())
        dropdownSort.setAdapter(sortByAdapter)
    }

    private fun addObservers() {

        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.lLoading.visibility = if(it) View.VISIBLE else View.GONE
        }

        viewModel.isError.observe(viewLifecycleOwner){
            binding.tError.visibility = if(it) View.VISIBLE else View.GONE
        }

        viewModel.countries.observe(viewLifecycleOwner){
            Log.d("COUNTRIES_HOME", it.toString())
            binding.carousel.setData(
                it.map { entity ->
                    CarouselItem(
                        imageUrl = entity.flags?.png,
                        headers = mapOf(
                            DetailActivity.COUNTRY_NAME to "${entity.name?.common}",
                            DetailActivity.CAPITAL_NAME to (entity.capital?.joinToString(",") ?: getString(R.string.notEnoughInfo)),
                            DetailActivity.CURRENCY to (entity.currencies?.keys?.toList()?.getOrNull(0) ?: getString(R.string.notEnoughInfo)),
                            DetailActivity.FLAG_DESC to "${entity.flags?.alt}",
                            DetailActivity.AREA to "${entity.area}",
                            DetailActivity.POPULATION to "${entity.population}",
                            DetailActivity.INDEPENDENT to "${entity.independent}",
                            DetailActivity.PHONE to "${entity.idd?.root}${entity.idd?.suffixes?.getOrNull(0)}",
                            DetailActivity.REGION to "${entity.region}",
                            DetailActivity.LANGUAGE to "${entity.languages?.values?.joinToString(", ")}",
                            DetailActivity.LAT to "${entity.capitalInfo?.latlng?.getOrNull(0)}",
                            DetailActivity.LON to "${entity.capitalInfo?.latlng?.getOrNull(1)}",
                            DetailActivity.BORDERS to "${entity.borders}",
                            DetailActivity.TIME_ZONE to "${entity.timezones?.getOrNull(0)}"
                        )
                    )
                }
            )
            // go to the first position
            // after collecting data, the sortType might be chosen by user, is YES, sort the data
            viewModel.sortType?.let {
                viewModel.sort()
                binding.carousel.currentPosition = 0
            }

            // clear filer and sort types
            dropdownFilter.setText("")
            dropdownSort.setText("")
            viewModel.filterType = null
            viewModel.sortType = null
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
                carouselBinding.tTitle.text = item.headers?.get(DetailActivity.COUNTRY_NAME)
                carouselBinding.tCapital.text = item.headers?.get(DetailActivity.CAPITAL_NAME)
                carouselBinding.tCurrency.text = item.headers?.get(DetailActivity.CURRENCY)
                carouselBinding.tPopulation.text = item.headers?.get(DetailActivity.POPULATION)
                carouselBinding.iIndependent.setImageResource(if(item.headers?.get(DetailActivity.INDEPENDENT) == "true") R.drawable.ic_check_circle else R.drawable.ic_cross)

                // intent to the detail activity
                carouselBinding.root.setOnClickListener {
                    val intent = Intent(requireContext(), DetailActivity::class.java)
                    intent.putExtra(DetailActivity.COUNTRY_NAME, item.headers?.get(DetailActivity.COUNTRY_NAME))
                    intent.putExtra(DetailActivity.CAPITAL_NAME, item.headers?.get(DetailActivity.CAPITAL_NAME))
                    intent.putExtra(DetailActivity.CURRENCY, item.headers?.get(DetailActivity.CURRENCY))
                    intent.putExtra(DetailActivity.POPULATION, item.headers?.get(DetailActivity.POPULATION))
                    intent.putExtra(DetailActivity.AREA, item.headers?.get(DetailActivity.AREA))
                    intent.putExtra(DetailActivity.FLAG_DESC, item.headers?.get(DetailActivity.FLAG_DESC))
                    intent.putExtra(DetailActivity.FLAG_URL, item.imageUrl)
                    intent.putExtra(DetailActivity.INDEPENDENT, item.headers?.get(DetailActivity.INDEPENDENT) == "true")
                    intent.putExtra(DetailActivity.PHONE, item.headers?.get(DetailActivity.PHONE))
                    intent.putExtra(DetailActivity.REGION, item.headers?.get(DetailActivity.REGION))
                    intent.putExtra(DetailActivity.LANGUAGE, item.headers?.get(DetailActivity.LANGUAGE))
                    intent.putExtra(DetailActivity.LAT, item.headers?.get(DetailActivity.LAT))
                    intent.putExtra(DetailActivity.LON, item.headers?.get(DetailActivity.LON))
                    intent.putExtra(DetailActivity.BORDERS, item.headers?.get(DetailActivity.BORDERS))
                    intent.putExtra(DetailActivity.TIME_ZONE, item.headers?.get(DetailActivity.TIME_ZONE))
                    startActivity(intent)
                }
            }
        }

        binding.tiSearch.addTextChangedListener {
            // search the countries with name
            viewModel.search(it.toString())
        }

        // filter drop down click
        dropdownFilter.setOnItemClickListener { adapterView, _, position, _ ->
            dropdownFilter.setText(adapterView.getItemAtPosition(position) as? String, false)
            (adapterView.getItemAtPosition(position) as? String)?.let {
                viewModel.filterType = it
            }
        }

        // sort drop down click
        dropdownSort.setOnItemClickListener { adapterView, _, position, _ ->
            dropdownSort.setText(adapterView.getItemAtPosition(position) as? String, false)
            viewModel.sortType = if(position == 0) OrderType.POPULATION else OrderType.ALPHABETICALLY
        }

        // filter button click listener, if region is chosen, get countries by region, otherwise check the sort type, if user chosen sort type,
        // then sort the current data
        filterButton.setOnClickListener{
            viewModel.filterType?.let {
                viewModel.getCountries()
            }
            if(viewModel.filterType.isNullOrEmpty()){
                viewModel.sortType?.let {
                    viewModel.sort()
                }
            }
            // collapse the bottomsheet
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}