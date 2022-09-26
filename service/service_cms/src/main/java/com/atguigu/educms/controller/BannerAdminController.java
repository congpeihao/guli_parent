package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  后台
 * </p>
 *
 * @author cph
 * @since 2022-09-19
 */
@RestController
@RequestMapping("/educms/bannerAdmin")
@CrossOrigin
public class BannerAdminController {


    @Autowired
    private CrmBannerService bannerService ;

    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable Long page, @PathVariable Long limit){
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        bannerService.page(bannerPage, null);
        return  R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }


    //2 添加banner
    @PostMapping("addBanner")

    public R addBanner(@RequestBody CrmBanner banner){

        bannerService.save(banner);
        return R.ok();
    }

    @GetMapping("get/{id}")

    public R get(@PathVariable String id){

        CrmBanner byId = bannerService.getById(id);
        return R.ok().data("item",byId);
    }


    @PutMapping("update")
    public R update( @RequestBody CrmBanner banner){

        bannerService.updateById(banner);
        return R.ok();
    }


    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id){

        bannerService.removeById(id);
        return R.ok();
    }

}

