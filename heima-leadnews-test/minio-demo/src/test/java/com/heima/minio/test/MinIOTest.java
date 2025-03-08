package com.heima.minio.test;

import com.heima.file.service.FileStorageService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest(classes = MinIOApplication.class)
@RunWith(SpringRunner.class)
//@RequiredArgsConstructor
public class MinIOTest {

    @Autowired
    private FileStorageService fileStorageService;

//    private final FileStorageService fileStorageService;

    @Test
    public void testUploadHtmlFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\list.html");
            String filePath = fileStorageService.uploadHtmlFile("", "list.html", fileInputStream);
            System.out.println(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateImgFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\vx头像.jpg");
            String filePath = fileStorageService.uploadImgFile("", "vx头像.jpg", fileInputStream);
            System.out.println(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//
//        FileInputStream fileInputStream = null;
//        try {
//
//            fileInputStream =  new FileInputStream("D:\\list.html");;
//
//            //1.创建minio链接客户端
//            MinioClient minioClient = MinioClient.builder().credentials("minio", "minio123").endpoint("http://192.168.200.130:9000").build();
//            //2.上传
//            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
//                    .object("list.html")//文件名
//                    .contentType("text/html")//文件类型
//                    .bucket("leadnews")//桶名词  与minio创建的名词一致
//                    .stream(fileInputStream, fileInputStream.available(), -1) //文件流
//                    .build();
//            minioClient.putObject(putObjectArgs);
//
//            System.out.println("http://192.168.200.130:9000/leadnews/list.html");
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }



}