package com.vincent.teng.projectaliyunoss.controller;

import com.vincent.teng.projectaliyunoss.dto.FileOSSDownLoadDTO;
import com.vincent.teng.projectaliyunoss.dto.FileOSSDownLoadResultDTO;
import com.vincent.teng.projectaliyunoss.dto.FileOSSUploadDTO;
import com.vincent.teng.projectaliyunoss.dto.FileOSSUploadResultDTO;
import com.vincent.teng.projectaliyunoss.util.AliyunFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.vincent.teng.projectaliyunoss.service.aliyun.*;

import java.io.*;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/4/2517:02
 */
@RestController
@RequestMapping("/fileManager")
public class UpdateController {

    @Autowired
    private AliyunFileOSSClientService clientService;

    @PostMapping(value = "/updateFile")
    public void updateFile(@RequestParam("file") MultipartFile[] multipartFile){
        FileOSSUploadDTO fileOSSUploadDTO=new FileOSSUploadDTO();
        try {
            fileOSSUploadDTO.setFileBytes(multipartFile[0].getBytes());
            fileOSSUploadDTO.setFileType(AliyunFileUtil.getFileType(multipartFile[0].getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOSSUploadResultDTO fileOSSUploadResultDTO=clientService.upload(fileOSSUploadDTO);

        System.out.println(fileOSSUploadResultDTO.toString());
    }

    @GetMapping(value = "/downloadFile")
    public void downloadFile(@RequestBody FileOSSDownLoadDTO fileOSSDownLoadDTO){
        FileOSSDownLoadResultDTO  fileOSSDownLoadResultDTO=clientService.download(fileOSSDownLoadDTO);
        File file= new File("D:/source" + File.separator + fileOSSDownLoadDTO.getFileKey());
        FileOutputStream fos = null;
        BufferedOutputStream bos =null;
        try {
            fos = new FileOutputStream(file);
            bos =new BufferedOutputStream(fos);
            bos.write(fileOSSDownLoadResultDTO.getFileBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
}
