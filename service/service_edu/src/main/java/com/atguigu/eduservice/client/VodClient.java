package com.atguigu.eduservice.client;


import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = "service-vod", fallback=VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/video/removeALYVideo/{id}")
    public R removeALYVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/removeALYVideoAll")
    public R removeALYVideoAll(@RequestParam("VideoList") List VideoList);
}
