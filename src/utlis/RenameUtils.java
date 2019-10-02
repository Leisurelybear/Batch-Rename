/**
 * FileName: FilePath
 * Author:   jason
 * Date:     2019/8/25 9:42
 * Description: 此类包含多个给文件改名方法，对文件名的追加，修改，删除等
 */
package utlis;

import api.IFilenamePath;

import java.nio.file.Path;
import java.nio.file.Paths;

public class RenameUtils implements IFilenamePath {
    private static Path filename;
    public RenameUtils(){}
    public RenameUtils(Path filename){
        this.filename = filename;
    }
    
    /**
     * @param index range(1~length) character's index of oldfilename,
     *              for example: test.txt -> if index = 3, the character of index(3) is 's',
     *              and "st.txt" move backward
     * @param content will insert into filename at index, eg. content is "open",
     *                after, filename is "teopenst.txt"
     * @return: com.zhangxujie.utlis.RenameUtils
     * @author: jason
     * @description: 在文件名指定位置插入字符串,index是插入的位置(从0到length)
     */
    public RenameUtils insert(int index, String content){
        if(index <= 0 || index > filename.toString().length()){
            return new RenameUtils(filename);//如果越界，则不改动，返回原值
        }
        index--;//范围变为从0~length-1
        StringBuffer sbName = new StringBuffer(filename.toString());
        sbName.insert(index, content);//在索引处加内容
        return new RenameUtils(Paths.get(sbName.toString()));
    }

    /**
     * @param obj elements to be appended
     * @return: com.zhangxujie.utlis.RenameUtils
     * @author: jason
     * @description: append elements to the end
     */
    public RenameUtils appendEnd(Object obj){
        if(obj == null){
            return new RenameUtils(filename);//如果越界，则不改动，返回原值
        }
        StringBuffer sbName = new StringBuffer(filename.toString());
        sbName.append(obj);
        return new RenameUtils(Paths.get(sbName.toString()));
    }

    /**
     * @param obj elements to be appended
     * @return: com.zhangxujie.utlis.RenameUtils
     * @author: jason
     * @description: append elements to the start
     */
    public RenameUtils appendStart(Object obj){
        if(obj == null){
            return new RenameUtils(filename);//如果越界，则不改动，返回原值
        }
        StringBuffer sbName = new StringBuffer(filename.toString());
        sbName.insert(0, obj);
        return new RenameUtils(Paths.get(sbName.toString()));
    }

    
    /**
     * @param start start index character to be replaced
     * @param end end index character to be replaced
     * @param content The new content will be replaced
     * @return: com.zhangxujie.utlis.RenameUtils
     * @author: jason
     * @description: 以1为文件名起始下标，然后start和end分别标识要被替换的开始和结束
     */
    public RenameUtils replace(int start, int end, String content){
        if(start <= 0 || end > filename.toString().length() || start > end){
            return new RenameUtils(filename);//如果越界，则不改动，返回原值
        }
        start--;//范围变为从0~length-1
        StringBuffer sbName = new StringBuffer(filename.toString());
        sbName.replace(start, end, content);
        return new RenameUtils(Paths.get(sbName.toString()));
    }

    /**
     * @param index Index of old char
     * @param c New char
     * @return: com.zhangxujie.utlis.RenameUtils
     * @author: jason
     * @description: replace char string[index] to new char c
     */
    public RenameUtils setCharAt(int index, char c){
        if(index <= 0 || index > filename.toString().length()){
            return new RenameUtils(filename);//如果越界，则不改动，返回原值
        }
        index--;
        StringBuffer sbName = new StringBuffer(filename.toString());
        sbName.setCharAt(index, c);
        return new RenameUtils(Paths.get(sbName.toString()));
    }
    
    /**
     * @param suffix new suffix to be modified
     * @return: com.zhangxujie.utlis.RenameUtils
     * @author: jason
     * @description: 把后缀修改成suffix,注意：只适合只有一种后缀的文件，不适合如.tar.gz类型多后缀文件
     * Modify file suffix to new suffix
     */
    public RenameUtils setSuffix(String suffix){
        if(suffix == "" || suffix == null){
            return new RenameUtils(filename);//如果越界，则不改动，返回原值
        }
        StringBuffer sbName = new StringBuffer(filename.toString());
        int dotPos = sbName.indexOf(".");
        sbName.replace(dotPos + 1, sbName.length(), suffix);
        return new RenameUtils(Paths.get(sbName.toString()));
    }

    //get file's suffix, and return it
    public String getSuffix(){
        StringBuffer sbName = new StringBuffer(filename.toString());
        int dotPos = sbName.indexOf(".");
        String suffix = sbName.substring(dotPos + 1, sbName.length());
        return suffix;
    }

    /**
     * @param start Start of delete 删除字符的起始位置
     * @param end  End of delete删除字符的终止位置
     * @return: com.zhangxujie.utlis.RenameUtils
     * @author: jason
     * @description: delete all of characters between start and end(include start and and)
     * Range(1~length)  给出开始位置和终止位置，然后把中间的字符全部移除，包括首尾
     */
    public RenameUtils delete(int start, int end){
        if(start <= 0 || end > filename.toString().length() || start > end){
            return new RenameUtils(filename);//如果越界，则不改动，返回原值
        }
        start--;
        StringBuffer sbName = new StringBuffer(filename.toString());
        sbName.delete(start, end);
        return new RenameUtils(Paths.get(sbName.toString()));
    }

    /**
     * @param index 要删除字符的下标，从1开始
     * @return: com.zhangxujie.utlis.RenameUtils
     * @author: jason
     * @description: 删除指定位置的单个字符
     */
    public RenameUtils deleteCharAt(int index){
        if(index <= 0 || index > filename.toString().length()){
            return new RenameUtils(filename);//如果越界，则不改动，返回原值
        }
        index--;
        StringBuffer sbName = new StringBuffer(filename.toString());
        sbName.deleteCharAt(index);
        return new RenameUtils(Paths.get(sbName.toString()));
    }

    /**
     * @param length 要删除字符串的长度
     * @return: com.zhangxujie.utlis.RenameUtils
     * @author: jason
     * @description: 从文件头开始删除length长的字符
     */
    public RenameUtils deleteCharsStart(int length){
        if(length <= 0 || length >= filename.toString().length()){
            return new RenameUtils(filename);//如果越界，则不改动，返回原值
        }
        StringBuffer sbName = new StringBuffer(filename.toString());
        for(int i = 0; i < length; i++){
            sbName.deleteCharAt(0);
        }
        return new RenameUtils(Paths.get(sbName.toString()));
    }

    /**
     * @param length 要删除字符串的长度
     * @return: com.zhangxujie.utlis.RenameUtils
     * @author: jason
     * @description: 从文件名末尾开始删除length长的字符
     */
    public RenameUtils deleteCharsEnd(int length){
        if(length <= 0 || length >= filename.toString().length()){
            return new RenameUtils(filename);//如果越界，则不改动，返回原值
        }
        StringBuffer sbName = new StringBuffer(filename.toString());
        sbName.delete(filename.toString().length() - length, filename.toString().length());
        return new RenameUtils(Paths.get(sbName.toString()));
    }


    @Override
    public String toString() {
        return filename.getFileName().toString();
    }

    public void setFilename(Path filename){
        this.filename = filename;
    }

}
