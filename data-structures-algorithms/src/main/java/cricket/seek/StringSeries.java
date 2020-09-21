package cricket.seek;

/**
 * 关于字符串的求解
 */
public class StringSeries {

    @Override
    public String toString() {
        return "整理关于字符串的求解";
    }

    class StringAddition {

        /**
         * 两个大字符串数字相加
         *
         * @param num1 字符串1
         * @param num2 字符串2
         * @return 字符串
         */
        public String Addition(String num1, String num2) {
            int idx1 = num1.length() - 1;//num1的末尾位置
            int idx2 = num2.length() - 1;//num2的末尾位置
            int next = 0;//进位
            StringBuilder rtn = new StringBuilder();//保存结果
            //最长串完了才算完
            while (idx1 >= 0 || idx2 >= 0) {
                int ele1 = 0;
                int ele2 = 0;
                //从尾->头取串1数字
                if (idx1 >= 0) {
                    ele1 = num1.charAt(idx1) - '0';//取到最后一个字符对应的数字,根据ASCII码表，数字字符正好比0字符大相应大小位
                    idx1--;
                }
                if (idx2 >= 0) {
                    ele2 = num2.charAt(idx2) - '0';
                    idx2--;
                }
                //当前值=当前对应位+上一次进位
                int addret = ele1 + ele2 + next;
                if (addret > 9) {
                    addret -= 10;
                    next = 1;
                } else {
                    next = 0;
                }
                rtn.append(addret);
            }
            //如果最后一次有进位
            if (next == 1) {
                rtn.append(1);
            }
            return rtn.reverse().toString();
        }
    }

    /**
     * Levenshtein距离
     */
    class Levenshtein {

        /**
         * 计算Levenshtein距离
         * @param s1 字符串1
         * @param s2 字符串2
         * @return 距离
         */
        public int getDistance(String s1, String s2) {
            int[][] distance;//距离表
            int n = s1.length();
            int m = s2.length();
            if (n == 0) {
                return m;
            }
            if (m == 0) {
                return n;
            }
            distance = new int[n + 1][m + 1];
            //初始化第一行和第一列
            for (int i = 0; i <= n; i++) {
                distance[i][0] = i;
            }
            for (int j = 0; j <= m; j++) {
                distance[0][j] = j;
            }
            int cost;
            for (int i = 1; i <= n; i++) {
                char c1 = s1.charAt(i - 1);
                for (int j = 1; j <= m; j++) {
                    char c2 = s2.charAt(j - 1);
                    if (c1 == c2) {
                        cost = 0;
                    } else {
                        cost = 1;
                    }
                    //取三者最小
                    distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1), distance[i - 1][j - 1] + cost);
                }
            }
            return distance[n][m];
        }

        /**
         * 两字符串相似度，可以使用的地方：DNA分析 　　拼字检查 　　语音辨识 　　抄袭侦测
         * @param s1 字符串1
         * @param s2 字符串2
         * @return 相似度
         */
        public float getSimilarityRatio(String s1, String s2) {
            int dif = getDistance(s1, s2);
            float similarity = 1 - (float) dif / Math.max(s1.length(), s2.length());
            return similarity;
        }
    }
}
