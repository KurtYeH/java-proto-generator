package com.kurt.gen.java2proto.service;

import com.kurt.gen.java2proto.utils.GetHomeUtils;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * @author yh
 * @create 2022/3/17 5:00 下午
 * @desc
 */
public class ExecCommand {

    public static void main(String[] args) throws IOException {
        /*Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("mvn -v");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            System.out.println(br.readLine());
        }*/
        InvocationRequest request = new DefaultInvocationRequest();
        // 设置java home
        request.setJavaHome(new File(GetHomeUtils.get("javahome.sh")));
        // pom文件的位置
        request.setPomFile(new File(System.getProperty("user.dir") + "/pom.xml"));
        // maven命令
        request.setGoals(Collections.singletonList("protobuf:compile"));

        Invoker invoker = new DefaultInvoker();
        // 设置maven_home
        // "/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3"
        // 此处可以让用户自己输入
        invoker.setMavenHome(new File(GetHomeUtils.get("mavenhome.sh")));

        invoker.setLogger(new PrintStreamLogger(System.err, InvokerLogger.DEBUG) {
        });
        // 控制台打印日志
        invoker.setOutputHandler(System.out::println);
        try {
            invoker.execute(request);
        } catch (
            MavenInvocationException e) {
            e.printStackTrace();
        }
    }

}
