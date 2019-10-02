/**
 * FileName: RenameService
 * Author:   jason
 * Date:     2019/8/25 9:05
 * Description: 遍历访问文件
 */
package service;

import api.IFilenamePath;
import utlis.RenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class RenameService {
    public RenameService() {

    }

    /**
     * @param dir 文件夹目录
     * @author: jason
     * @description: 将要修改的文件放在一个文件夹中，然后传入文件夹的地址，之后批量修改
     */
    public static void rename(String dir) throws IOException {
        Path path = Paths.get(dir);
        System.out.println(path);
        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                IFilenamePath filenamePath = new RenameUtils(file.getFileName());
                File oldFile = new File(file.toUri());//得到旧文件
                try{
                    //[VCB-Studio] Toaru Kagaku no Railgun [01][Hi10p_1080p][x264_2flac].ass
                    //filenamePath.setSuffix("ass");
                    filenamePath.delete(1, 10);
                    //System.out.println(file.getParent().resolve(filenamePath.toString()));//拼接目录和文件名
                    File newFile = new File(file.getParent().resolve(filenamePath.toString()).toUri());//拼接并创新新文件名
                    oldFile.renameTo(newFile);//更新文件名
                } catch(Exception e){
                    System.err.println("您修改后的文件名可能为空！");
                    e.printStackTrace();
                } finally{

                }
                return FileVisitResult.CONTINUE;//继续读取下一个文件
            }
        });
    }


}
