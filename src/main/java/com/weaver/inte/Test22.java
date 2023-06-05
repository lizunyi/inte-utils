package com.weaver.inte;

import com.weaver.inte.utils.StringExtUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test22 {
    private final static Pattern regex_key = Pattern.compile("\"(\\w+)\"");
    private final static Pattern regex_remark = Pattern.compile("\\/\\*(.*?)\\*\\/");
    private final static Pattern regex_underline = Pattern.compile("[A-Z]");

    public static void main(String[] args) {

        String str = " \"Wins\":53,/*总胜场*/\n" +
                "            \"Rank\":1,/*排名*/\n" +
                "            \"LeagueCNAlias\":\"CBA\",\n" +
                "            \"Season\":2018,\n" +
                "            \"LeagueID\":401,\n" +
                "            \"TotalPoints\":6870,/*总得分*/\n" +
                "            \"LeagueCNName\":\"中国篮球职业联赛\",\n" +
                "            \"TeamENName\":\"GUANG DONG\",\n" +
                "            \"TeamID\":29124,\n" +
                "            \"Losses\":4,/*总负场*/\n" +
                "            \"TeamCNName\":\"广东宏远华南虎俱乐部东莞银行篮球队\",\n" +
                "            \"TeamENAlias\":\"GST\",\n" +
                "            \"MatchPlayed\":57,\n" +
                "            \"TotalPointsAgainst\":5940,/*总失分*/\n" +
                "            \"TeamCNAlias\":\"广东东莞银行\"";

        String[] lines = str.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            String key = match(regex_key, line);
            String remark = StringExtUtils.ifNull(match(regex_remark, line), key);
            String column = underline(key);
            System.out.println(String.format("`%s` varchar(50) null comment '%s',", column, remark));
        }
    }

    private static String match(Pattern pattern, String str) {
        Matcher mac2 = pattern.matcher(str);
        if (mac2.find()) {
            return mac2.group(1).trim();
        }
        return "";
    }

    private static String underline(String str) {
        return str.replaceAll("(.)([A-Z]+)", "$1_$2").toLowerCase();
    }
}
