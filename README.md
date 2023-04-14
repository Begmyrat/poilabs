# Country Information apk using MVVM and Clean Code Architecture

## REST countries sample app that loads information from REST countries API V3 to show an approach to using some of the best practices in Android Development.

On the very first launch app send the request to the API and fetch the data (all countries), after successfully fetch set the data to UI

## Used
- Kotlin
- MVVM
- Clean Code Architecture
- Dependency Injection - Dagger Hilt
- Retrofit
- Kotlin Coroutines
- Jetpack Components
- 3rd Party Libraries (Carousel, Glide)
- LiveData
- Lifecycle
- Observers
- Navigation Graph
- Use Cases


## API Response
Countries API return alot of data but currently I am using this data. Sample JSON.
```
[
  {
    "name": {
      "common": "Malaysia",
      "official": "Malaysia"
    },
    "unMember": true,
    "currencies": {
      "MYR": {
        "name": "Malaysian ringgit",
        "symbol": "RM"
      }
    },
    "capital": [
      "Kuala Lumpur"
    ],
    "region": "Asia",
    "subregion": "South-Eastern Asia",
    "languages": {
      "eng": "English",
      "msa": "Malay"
    },
    "population": 32365998,
    "timezones": [
      "UTC+08:00"
    ],
    "continents": [
      "Asia"
    ],
    "flags": {
      "png": "https://flagcdn.com/w320/my.png"
    },
    "startOfWeek": "sunday"
  }
]
```
