package top.iot.gateway.core.benchmark;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class BenchmarkRunner {

    public static void main(String[] args) throws RunnerException {
        run("benchmark.*");
    }

    public static void run(String include) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(include)
                .threads(4)
                .warmupIterations(2)
                .warmupTime(TimeValue.seconds(2))
                .measurementIterations(3)
                .measurementTime(TimeValue.seconds(2))
                .result("./target/benchmark-result.txt")
//                .addProfiler(GCProfiler.class)
                .resultFormat(ResultFormatType.TEXT)
                .jvmArgs("-Xms1g", "-Xmx1g")
                .build();
        new Runner(opt).run();
    }

    public static void run(Class<?> clazz) throws RunnerException {
         run(clazz.getSimpleName());
    }

}
