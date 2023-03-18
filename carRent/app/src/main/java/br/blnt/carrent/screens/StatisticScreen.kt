package br.blnt.carrent.screens

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import br.blnt.carrent.R
import br.blnt.carrent.cars
import br.blnt.carrent.getCarEarn
import br.blnt.carrent.stats
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlin.collections.ArrayList


class StatisticScreen : AppCompatActivity() {

  lateinit var pieEntriesListRent: ArrayList<PieEntry>
  lateinit var pieDataSetRent: PieDataSet

  lateinit var pieEntriesListEarn: ArrayList<PieEntry>
  lateinit var pieDataSetEarn: PieDataSet

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_statistic_screen)

    val profileIntent = Intent(this@StatisticScreen, ProfileScreen::class.java)

    val back: Button = findViewById(R.id.back)
    back.setOnClickListener{
      startActivity(profileIntent)
      finish()
    }

    val pieChartRent: PieChart = findViewById(R.id.piechart_rent)
    pieEntriesListRent = ArrayList<PieEntry>()
    for (stat in stats) {
      var carName = "na"
      val floatEntry = stat.rentsNumber
      for (car in cars) {
        if (car.id == stat.carId) {
          carName = car.carName
        }
      }
      pieEntriesListRent.add( PieEntry(floatEntry.toFloat(),carName))
    }
    pieDataSetRent = PieDataSet(pieEntriesListRent, getString(R.string.cars))
    pieDataSetRent.setColors(ColorTemplate.JOYFUL_COLORS, 150)
    pieDataSetRent.setDrawValues(true)

    pieChartRent.data = PieData(pieDataSetRent)

    pieChartRent.animateXY(5000, 5000)
    pieChartRent.description.text = getString(R.string.rent_stat)
    pieChartRent.description.textColor = Color.WHITE

    val pieChartEarn: PieChart = findViewById(R.id.piechart_earn)
    pieEntriesListEarn = ArrayList<PieEntry>()



    for (car in cars) {
      var carName = "na"
      val floatEntry = getCarEarn(car.id)
      carName = car.carName
      if (floatEntry > 10000)
        pieEntriesListEarn.add(PieEntry(floatEntry.toFloat(), carName))
    }



    pieDataSetEarn = PieDataSet(pieEntriesListEarn, getString(R.string.cars))
    pieDataSetEarn.setColors(ColorTemplate.JOYFUL_COLORS, 150)
    pieDataSetEarn.setDrawValues(true)

    pieChartEarn.data = PieData(pieDataSetEarn)

    pieChartEarn.animateXY(5000, 5000)
    pieChartEarn.description.text = getString(R.string.rent_earn)
    pieChartEarn.description.textColor = Color.WHITE
  }
}