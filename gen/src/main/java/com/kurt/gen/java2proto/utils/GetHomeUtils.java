package com.kurt.gen.java2proto.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author yh
 * @create 2022/3/17 7:55 下午
 * @desc
 */
public class GetHomeUtils {

    public static String get(String shellName) {
        try {
            String bashCommand = System.getProperty("user.dir") + "/shell/" + shellName;
            System.out.println(bashCommand);
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(bashCommand, null, null);
            int status = process.waitFor();
            if (status != 0) {
                System.out.println("Failed to call shell's command ");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder strbr = new StringBuilder();
            String line;
            while ((line = br.readLine())!= null) {
                strbr.append(line);
            }
            String result = strbr.toString();
            System.out.println(shellName + ": " + result);
            return result;
        } catch (IOException | InterruptedException ec) {
            ec.printStackTrace();
        }
        return "";
    }

}
