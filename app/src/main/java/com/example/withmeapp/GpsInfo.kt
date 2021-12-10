package com.example.withmeapp

import android.Manifest
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import java.lang.Exception


/**
 * Created by yong on 15. 2. 12.
 */
class GpsInfo(private val mContext: Context) : Service(),
    LocationListener {
    // GPS 사용여부
    var isGPSEnabled = false

    // 네트워크 사용여부
    var isNetWorkEnabled = false

    // end of isGetLocation
    // GPS 상태값
    var isGetLocation = false
    var location = null
    var lat // 위도
            = 0.0
    var lon // 경도
            = 0.0
    private var startTime: Long = -1
    private var beforeLocation: Location? = null
    private var curLocation: Location? = null
    protected var locationManager: LocationManager? = null


    // 현재 위치를 다른 함수로부터 받아옴
    fun getLocation(): Location? {
        try {
            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_FINE // 정확도
            criteria.powerRequirement = Criteria.POWER_LOW // 전원소비량
            criteria.isAltitudeRequired = true // 고도
            criteria.isBearingRequired = false // 기본 정보, 방향, 방위
            criteria.isSpeedRequired = false // 속도
            criteria.isCostAllowed = true // 위치정보 비용
            locationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager
            val provider = locationManager!!.getBestProvider(criteria, true)

            // GPS 정보 가져오기
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

            // 현재 네트워크 상태 값 알아오기
            isNetWorkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGPSEnabled && !isNetWorkEnabled) {
            } else {
                if (isNetWorkEnabled) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                        // return
                    }
                    locationManager!!.requestLocationUpdates(
                        provider!!,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATE.toFloat(),
                        this
                    )
                    if (locationManager != null) {
                        curLocation = locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (curLocation != null) {
                            // 위도 경도 저장
                            lat = curLocation!!.latitude
                            lat = curLocation!!.longitude
                        }
                    }
                }
                isGetLocation = true
                if (isGPSEnabled) {
                    if (curLocation == null) {                  // LocationManager.GPS_PROVIDER
                        locationManager!!.requestLocationUpdates(
                            provider!!,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATE.toFloat(),
                            this
                        )
                        if (locationManager != null) {
                            curLocation =
                                locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (curLocation != null) {
                                lat = curLocation!!.latitude
                                lon = curLocation!!.longitude
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } // end of try~catch
        return curLocation
    } // end of getLocation

    // GPS OFF
    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this@GpsInfo)
        } // end of if
    } // end of stopUsingGPS

    // end of if
    // end of getLatitude
    val latitude: Double
        get() {
            if (curLocation != null) {
                lat = curLocation!!.latitude
            } // end of if
            return lat
        }

    // end of if
    // end of getLatitude
    val longitude: Double
        get() {
            if (curLocation != null) {
                lon = curLocation!!.longitude
            } // end of if
            return lon
        }

    fun showSettingAlert() {
        val alertDialog = AlertDialog.Builder(mContext)
        alertDialog.setTitle("GPS 사용유무")
        alertDialog.setMessage("GPS 사용해야됨, 설정 창 ㄱ?")
        alertDialog.setPositiveButton(
            "Settings"
        ) { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            mContext.startActivity(intent)
        } // end of onClick
        // end of setPositiveButton
        alertDialog.setNegativeButton(
            "Cancle"
        ) { dialog, which -> dialog.cancel() } // end of onClick
        // end of setNegativeButton
        alertDialog.show()
    } // end of showSettingAlert

    override fun onLocationChanged(location: Location) {
        Log.d("----Start location----", location.toString())
        if (startTime == -1L) {
            startTime = location.time
        } // end of if
        Log.i("time", location.time.toString())
        beforeLocation = getLocation()
        val distance = FloatArray(1)
        Log.i(
            "** Before Location",
            beforeLocation!!.latitude.toString() + "!!!!" + beforeLocation!!.longitude.toString()
        )
        Log.i(
            "&& Current Location",
            location.latitude.toString() + "!!!!" + location.longitude.toString()
        )
        Location.distanceBetween(
            beforeLocation!!.latitude,
            beforeLocation!!.longitude,
            location.latitude,
            location.longitude,
            distance
        ) // distance -> meter m/s
        val dis = distance[0]
        //        Log.i("distance", distance.toString());
        Log.i("*** distance ", dis.toString())
        location.speed
        Log.i("Speed", location.speed.toString())
        val delay = location.time - startTime
        val speed = (distance[0] / delay).toDouble()
        val speedKMH = speed * 3600 // m/s
        beforeLocation = location
        Log.i("speed", speedKMH.toString())
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATE: Long = 1
        private const val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong()
    }

    init {
        getLocation()
    }
}

