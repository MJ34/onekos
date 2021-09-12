package muji.dev.onekost.dashboard.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class KosPutra(
    var bathroom: String? = "",
    var bed: String? = "",
    var breakfast: String? = "",
    var category: String? = "",
    var desc: String? = "",
    var gambar1: String? = "",
    var gambar2: String? = "",
    var gambar3: String? = "",
    var poster: String? = "",
    var price: Int? = null,
    var rating: String? = "",
    var tempat: String? = "",
    var wifi: String? = ""
):Parcelable