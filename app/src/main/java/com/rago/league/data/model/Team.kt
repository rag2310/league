package com.rago.league.data.model

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idAPIfootball")
    val idAPIFootball: String,
    val idLeague: String,
    val idLeague2: String,
    val idLeague3: String,
    val idLeague4: String,
    val idLeague5: String,
    val idLeague6: Any,
    val idLeague7: Any,
    val idSoccerXML: String,
    val idTeam: String,
    val intFormedYear: String,
    val intLoved: String,
    val intStadiumCapacity: String,
    val strAlternate: String,
    val strCountry: String,
    val strDescriptionCN: String? = null,
    val strDescriptionDE: String? = null,
    val strDescriptionEN: String? = null,
    val strDescriptionES: String? = null,
    val strDescriptionFR: String? = null,
    val strDescriptionHU: String? = null,
    val strDescriptionIL: String? = null,
    val strDescriptionIT: String? = null,
    val strDescriptionJP: String? = null,
    val strDescriptionNL: String? = null,
    val strDescriptionNO: String? = null,
    val strDescriptionPL: String? = null,
    val strDescriptionPT: String? = null,
    val strDescriptionRU: String? = null,
    val strDescriptionSE: String? = null,
    val strDivision: Any,
    val strFacebook: String,
    val strGender: String,
    val strInstagram: String,
    val strKeywords: String,
    val strKitColour1: String,
    val strKitColour2: String,
    val strKitColour3: String,
    val strLeague: String,
    val strLeague2: String,
    val strLeague3: String,
    val strLeague4: String,
    val strLeague5: String,
    val strLeague6: String,
    val strLeague7: String,
    val strLocked: String,
    val strRSS: String,
    val strSport: String,
    val strStadium: String,
    val strStadiumDescription: String,
    val strStadiumLocation: String,
    val strStadiumThumb: String,
    val strTeam: String,
    val strTeamBadge: String,
    val strTeamBanner: String,
    @SerializedName("strTeamFanart1")
    val strTeamFanArt1: String,
    @SerializedName("strTeamFanart2")
    val strTeamFanArt2: String,
    @SerializedName("strTeamFanart3")
    val strTeamFanArt3: String,
    @SerializedName("strTeamFanart4")
    val strTeamFanArt4: String,
    val strTeamJersey: String?,
    val strTeamLogo: String,
    val strTeamShort: String,
    val strTwitter: String,
    val strWebsite: String,
    val strYoutube: String
) {
    override fun toString(): String {
        return Uri.encode(Gson().toJson(this))
    }
}


data class DescriptionRegion(
    val code: String,
    val description: String
)