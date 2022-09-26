package com.atguigu.smsservice.Controller;


import com.atguigu.commonutils.R;
import com.atguigu.smsservice.service.MsmService;
import com.atguigu.smsservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {


    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {

         if (!StringUtils.isEmpty(redisTemplate.opsForValue().get(phone))){
             return R.ok();
         }

        String code = RandomUtil.getFourBitRandom();

        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean isSend = msmService.send(param,phone);
        if (isSend){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();

        }else{
            return R.error().message("短信发送失败");
        }


    }


}
