package com.mwrobel.postcodesio.models

import spray.json.DefaultJsonProtocol

case class Postcode( postcode: String,
                     quality: Int,
                     eastings: Int,
                     northings: Int,
                     country: String,
                     nhs_ha: String,
                     longitude: Double,
                     latitude: Double,
                     parliamentary_constituency: String,
                     european_electoral_region: String,
                     primary_care_trust: String,
                     region: String,
                     lsoa: String,
                     msoa: String,
                     incode: String,
                     outcode: String,
                     admin_district: String,
                     parish: String,
                     admin_county: Option[String],
                     admin_ward: String,
                     ccg: String,
                     nuts: String
                     )

object Postcode extends DefaultJsonProtocol {
  implicit val format = jsonFormat22(Postcode.apply)
}
