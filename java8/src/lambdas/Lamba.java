package lambdas;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

public class Lamba {


    public static void main(String[] args) {
        runnableLambda();
        comparatorLambda();
        parameterLambda((String a) -> System.out.println(a));
        // can be replaced with method reference
        parameterLambda(System.out::println);
        // but following case cannot be replaced with method reference
        parameterLambda((String a) -> System.out.println(a + " ::: " + a));

        consumer();
    }

    private static void consumer() {
        final Consumer<Integer> printNumber = number -> {
            if (number > 4) System.out.println(number);
        };
        final List<Integer> numbers = asList(10, 1, 4, 5);

        // Note: forEach is new default method in Iterable interface
        // This new approach to iteration is essential for multi-core support to abstract way the implementation details
        // of iteration itself
        numbers.forEach(printNumber.andThen(number -> System.out.println("I can see ya! " + number)));
        // reference to my private method
        numbers.forEach(Lamba::add2AndPrint);
    }

    private static void add2AndPrint(int number) {
        System.out.print(number + 2);
    }

    private static void parameterLambda(Action action) {
        // if you want to use lambda as a parameter you have to define functional interface
        action.run("Random name");
    }

    @FunctionalInterface
    private static interface Action {
        void run(String executable);
    }

// Functional Interface with more than one abstract method is invalid
//    @FunctionalInterface
//    private static interface Action2 {
//        void start(String executable);
//        void stop(String executable);
//    }

    private static void comparatorLambda() {
        final Comparator<Time> hourComparator = (a, b) -> a.getHours() > b.getHours() ? 1 :
                (a.getHours() < b.getHours() ? -1 : 0);

        // "block" lambda must contain return but preferably should be replace with lambda expression as above
        final Comparator<Time> secondsComparator = (a, b) -> {
            return a.getSeconds() > b.getSeconds() ? 1 : (a.getSeconds() < b.getSeconds() ? -1 : 0);
        };

        final List<Time> times = asList(new Time(10, 10, 10), new Time(10, 1, 2), new Time(9, 10, 10), new
                Time(10, 0, 0), new Time(12, 0, 0), new Time(10, 59, 59));

        times.sort(hourComparator);
        System.out.println(times);

        times.sort(secondsComparator);
        System.out.println(times);

    }

    private static void runnableLambda() {
        final Runnable r = () -> System.out.println("Hello World from Runnable!");
        final ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        service.submit(r);
        service.shutdown();
    }


    private static class Time implements Comparable<Time> {
        int hours;
        int minutes;
        int seconds;

        public Time(int hours, int minutes, int seconds) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }

        public int getHours() {
            return hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public int getSeconds() {
            return seconds;
        }

        @Override
        public int compareTo(Time o) {
            if (o == null) {
                return 1;
            }

            if (getHours() == o.getHours()) {
                if (getMinutes() == o.getMinutes()) {
                    if (getSeconds() == o.getSeconds()) {
                        return 0;
                    }
                }
            } else if (getHours() > o.getHours()) {
                return 1;
            } else {
                return -1;
            }

            return 0;
        }

        @Override
        public String toString() {
            return format("[%02d:%02d:%02d]", getHours(), getMinutes(), getSeconds());
        }
    }


}
