package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {



    @Override
    public String uploadFileAvatar(MultipartFile file) {


        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId =  ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret =  ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName =  ConstantPropertiesUtils.BUCKET_NAME;

        String originalFilename = file.getOriginalFilename();
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        originalFilename= s+originalFilename;
        String datePath = new DateTime().toString("yyyy/MM/dd");
        originalFilename=datePath+"/"+originalFilename;

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 创建OSSClient实例。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName, originalFilename, inputStream);
            //https://edu-201010.oss-cn-qingdao.aliyuncs.com/1.jpg
            String Url="https://"+bucketName+"."+endpoint+"/"+originalFilename;
            return Url;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

    }
}
