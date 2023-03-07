package top.iot.gateway.core.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TypeScriptUtilsTest {

    @Test
    public void test() {
        String script = TypeScriptUtils.loadDeclare("transparent-codec");

        System.out.println(Arrays.stream(script.split("\n"))
                        .map(str-> str.replace("\"","\\\""))
                .collect(Collectors.joining("\"\n,\"","[\"","\"]")));

    }
}