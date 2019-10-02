/**
 * FileName: MatchCopyFilename
 * Author:   jason
 * Date:     2019/8/26 11:18
 * Description: 设置原文件名独有的一段字符串，匹配后按顺序拷贝文件名 （为了给视频和字幕匹配文件名）
 */
package utlis;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayDeque;
import java.util.Queue;

public class MatchCopyFilename {

    private static String sourceDir;
    private static String targetDir;
    private static Queue<Path> sourceNames = new ArrayDeque<>();

    public MatchCopyFilename(String sourceDir, String targetDir) {
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
        if(sourceDir != null && targetDir != null){
            getSourceNames();//把源目录下的文件名都获取过来
        }
    }

    private static void getSourceNames() {
        Path path = Paths.get(sourceDir);
        System.out.println(path);
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println(file.getFileName());
                    sourceNames.offer(file.getFileName());//把源文件的名字添加到队列中
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StringBuffer renameToTargetNames() throws IOException {
        StringBuffer log = new StringBuffer("参照文件所在文件夹： "+sourceDir + "\n待修改文件所在文件夹："+targetDir+"\n");
        Path path = Paths.get(targetDir);
        System.out.println(path);
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                RenameUtils sourceFile = new RenameUtils(file);
                String suffix = sourceFile.getSuffix();//得到target文件需要的后缀(原后缀)
                File oldTargetFile = new File(file.toUri());//得到没改名的文件
                File targetFile = new File(file.getParent().resolve(new RenameUtils(sourceNames.poll()).setSuffix(suffix).toString()).toUri());//拼接并创新新文件名
                log.append(targetFile.toString()+"\n");
                oldTargetFile.renameTo(targetFile);//更新文件名
                return FileVisitResult.CONTINUE;
            }
        });
        log.append("Done!");
        return log;
    }



    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }
}
