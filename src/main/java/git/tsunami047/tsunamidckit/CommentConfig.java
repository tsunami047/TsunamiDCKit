package git.tsunami047.tsunamidckit;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 喵呜、natsumi
 * @CreateTime: 2023-05-29  20:15
 * @Description: 喵呜在MCBBS的代码，有修改与新增，带注释配置
 */
public class CommentConfig extends YamlConfiguration {


    protected static String commentPrefixSymbol = "'注释 ";
    protected static String commentSuffixSymbol = "': 注释";
    protected static String fromRegex = "( *)(#.*)";
    protected static Pattern fromPattern = Pattern.compile(fromRegex);
    protected static String toRegex = "( *)(- )*" + "(" + commentPrefixSymbol + ")" + "(#.*)" + "(" + commentSuffixSymbol + ")";
    protected static Pattern toPattern = Pattern.compile(toRegex);
    protected static Pattern countSpacePattern = Pattern.compile("( *)(- )*(.*)");
    protected static int commentSplitWidth = 90;


    private static String[] split(String string, int partLength) {
        String[] array = new String[string.length() / partLength + 1];
        for (int i = 0; i < array.length; i++) {
            int beginIndex = i * partLength;
            int endIndex = beginIndex + partLength;
            if (endIndex > string.length()) {
                endIndex = string.length();
            }
            array[i] = string.substring(beginIndex, endIndex);
        }
        return array;
    }

    private final String newLine = "\n";

    @Override
    public void save(File file) throws IOException {
        Validate.notNull(file, "File cannot be null");
        Files.createParentDirs(file);
        String data = this.saveToString();
        try (Writer writer = new OutputStreamWriter(java.nio.file.Files.newOutputStream(file.toPath()), Charsets.UTF_8)) {
            writer.write(data);
        }
    }

    private static String readFileToString(File file) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(java.nio.file.Files.newInputStream(file.toPath()), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static CommentConfig loadCommentConfig(File file){
        String s = readFileToString(file);
        CommentConfig commentConfig = new CommentConfig();
        try {
            commentConfig.loadFromString(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return commentConfig;
    }

    @Override
    public void loadFromString(String contents) throws InvalidConfigurationException {
        String[] parts = contents.split(newLine);
        List<String> lastComments = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            Matcher matcher = fromPattern.matcher(part);
            if (matcher.find()) {
                String originComment = matcher.group(2);
                String[] splitComments = split(originComment, commentSplitWidth);
                for (int i = 0; i < splitComments.length; i++) {
                    String comment = splitComments[i];
                    if (i == 0) {
                        comment = comment.substring(1);
                    }
                    comment = COMMENT_PREFIX + comment;
                    lastComments.add(comment.replaceAll("\\.", "．").replaceAll("'", "＇").replaceAll(":", "："));
                }
            } else {
                matcher = countSpacePattern.matcher(part);
                if (matcher.find() && !lastComments.isEmpty()) {
                    for (String comment : lastComments) {
                        builder.append(matcher.group(1));
                        builder.append(this.checkNull(matcher.group(2)));
                        builder.append(commentPrefixSymbol);
                        builder.append(comment);
                        builder.append(commentSuffixSymbol);
                        builder.append(newLine);
                    }
                    lastComments.clear();
                }
                builder.append(part);
                builder.append(newLine);
            }
        }
        super.loadFromString(builder.toString());
    }


    @Override
    public String saveToString() {
        String contents = super.saveToString();
        StringBuilder savcontent = new StringBuilder();
        String[] parts = contents.split(newLine);
        for (String part : parts) {
            Matcher matcher = toPattern.matcher(part);
            if (matcher.find() && matcher.groupCount() == 5) {
                part = this.checkNull(matcher.group(1)) + matcher.group(4);
            }
            if(part.contains("#")){
                part=part.replace("# ","#");
            }
            savcontent.append(part.replaceAll("．", ".").replaceAll("＇", "'").replaceAll("：", ":"));
            savcontent.append(newLine);
        }
        return savcontent.toString();

    }


    /**
     * 检查字符串
     *
     * @param string 检查字符串
     * @return 返回非null字符串
     */

    private String checkNull(String string) {
        return string == null ? "" : string;
    }

}
