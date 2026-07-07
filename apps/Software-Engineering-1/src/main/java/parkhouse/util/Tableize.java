package parkhouse.util;

public class Tableize {

    /*
    Author: jstueh2s
     */

    private Tableize() {}

    public static String table(String[] headers, String[][] rows) {
        StringBuilder sb = new StringBuilder("<table><tr>");
        for (String h : headers) {
            sb.append("<th>").append(h).append("</th>");
        }
        sb.append("</tr>");
        for (String[] row : rows) {
            sb.append("<tr>");
            for (String r : row) {
                sb.append("<td>").append(r).append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }
}
