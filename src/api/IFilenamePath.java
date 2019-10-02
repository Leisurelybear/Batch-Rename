package api;

import utlis.RenameUtils;

public interface IFilenamePath {
    public RenameUtils insert(int index, String content);
    public RenameUtils appendEnd(Object obj);
    public RenameUtils appendStart(Object obj);
    public RenameUtils replace(int start, int end, String content);
    public RenameUtils setCharAt(int index, char c);
    public RenameUtils setSuffix(String suffix);
    public RenameUtils delete(int start, int end);
    public RenameUtils deleteCharAt(int index);
    public RenameUtils deleteCharsStart(int length);
    public RenameUtils deleteCharsEnd(int length);
    public String getSuffix();
}
