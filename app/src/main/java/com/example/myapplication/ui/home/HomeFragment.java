package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentWeatherBinding;
import com.google.gson.Gson;
import com.qweather.sdk.bean.air.AirNowBean;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentWeatherBinding binding;

    private static final String TAG = "HomeFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        HeConfig.init("HE2307032236511272", "7b1ed93f96b240898df25befd2c77780");
        HeConfig.switchToDevService();

        //////
      View bg;
      bg=root.findViewById(R.id.back);
      bg.setBackgroundResource(R.drawable.night_cloud_long);
        /////

        TextView Location = root.findViewById(R.id.location);
        TextView Temperature = root.findViewById(R.id.temperature_now);
        TextView WeatherText = root.findViewById(R.id.weather);
        TextView MaxMinTemp = root.findViewById(R.id.max_min_temp);
        TextView AirQuality = root.findViewById(R.id.air_quality);
     /*  QWeather.getGeoCityLookup(this.getContext(), "101010300", new QWeather.OnResultGeoListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }

            @Override
            public void onSuccess(GeoBean geoBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(geoBean));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因

                if (Code.OK == geoBean.getCode()) {
                    List<GeoBean.LocationBean> now= geoBean.getLocationBean();
                    Location.setText(now.get(0).getName());
                } else {
                    //在此查看返回数据失败的原因
                    Code code = geoBean.getCode();
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });
        QWeather.getWeatherNow(this.getContext(), "101010300", new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherNowBean));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因

                if (Code.OK == weatherNowBean.getCode()) {
                    WeatherNowBean.NowBaseBean now = weatherNowBean.getNow();
                    Temperature.setText(now.getTemp());
                    WeatherText.setText(now.getText());
                } else {
                    //在此查看返回数据失败的原因
                    Code code = weatherNowBean.getCode();
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });
        QWeather.getWeather3D(this.getContext(), "101010300", new QWeather.OnResultWeatherDailyListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }

            @Override
            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherDailyBean));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因

                if (Code.OK == weatherDailyBean.getCode()) {
                    List<WeatherDailyBean.DailyBean> now = weatherDailyBean.getDaily();
                    String text_max_min_temp = now.get(0).getTempMax() + "°C/" + now.get(0).getTempMin() + "°C";
                    MaxMinTemp.setText(text_max_min_temp);

                } else {
                    //在此查看返回数据失败的原因
                    Code code = weatherDailyBean.getCode();
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });
        QWeather.getAirNow(this.getContext(), "101010300", Lang.ZH_HANS, new QWeather.OnResultAirNowListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }

            @Override
            public void onSuccess(AirNowBean airNowBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(airNowBean));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因

                if (Code.OK == airNowBean.getCode()) {
                    AirNowBean.NowBean now = airNowBean.getNow();
                    String text_air_quality = now.getAqi() + "  " + now.getCategory();
                    AirQuality.setText(text_air_quality);

                } else {
                    //在此查看返回数据失败的原因
                    Code code = airNowBean.getCode();
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });
*/

//        final TextView textView = binding.textWeather;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}