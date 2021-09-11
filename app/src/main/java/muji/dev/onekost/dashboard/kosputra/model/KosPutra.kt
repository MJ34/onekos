package muji.dev.onekost.dashboard.kosputra.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class KosPutra(
    var bathroom: String? = "",
    var bed: String? = "",
    var breakfash: String? = "",
    var category: String? = "",
    var desc: String? = "",
    var gambar1: String? = "",
    var gambar2: String? = "",
    var gambar3: String? = "",
    var poster: String? = "",
    var price: String? = "",
    var rating: String? = "",
    var tempat: String? = "",
    var wifi: String? = ""
):Parcelable