package com.yasykur.timesheet.util;

public class EmailUtil {
    public static String generateEmailText(String employeeName, String body) {
        return ("<body style=\"margin:0px; background: #f8f8f8; \">\n" + //
                "<div width=\"100%\" style=\"background: #f8f8f8; padding: 0px 0px; font-family:arial; line-height:28px; height:100%;  width: 100%; color: #514d6a;\">\r\n" + //
                "  <div style=\"max-width: 700px; padding:50px 0;  margin: 0px auto; font-size: 14px\">\n" + //
                "    <div style=\"padding: 40px; background: #fff;\">\r\n" + //
                "      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%;\">\n" + //
                "        <tbody>\n" + //
                "          <tr>\n" + //
                "            <td><b>Dear " + //
                employeeName + //
                " </b>\n" + //
                "              <p> " + //
                body + //
                "</p>\n" + //
                "          </tr>\n" + //
                "        </tbody>\n" + //
                "      </table>\n" + //
                "    </div>\n" + //
                "    <div style=\"text-align: center; font-size: 12px; color: #b2b2b5; margin-top: 20px\">\n" + //
                "    </div>\n" + //
                "  </div>\n" + //
                "</div>\n" + //
                "</body>");
    }
}
