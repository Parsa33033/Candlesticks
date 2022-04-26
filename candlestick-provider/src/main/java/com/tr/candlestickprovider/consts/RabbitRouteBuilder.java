package com.tr.candlestickprovider.consts;

public final class RabbitRouteBuilder {

    public static Destination from(String from) {
        return new Destination(from);
    }

    public static final class Destination {
        private StringBuilder route = new StringBuilder();
        private static final String DOT = ".";
        private static final String ALL = "*";

        public Destination(String from) {
            route.append(from);
        }

        public Destination to(String to) {
            route.append(DOT + to);
            return this;
        }

        public Destination toAnywhere() {
            route.append(DOT + ALL);
            return this;
        }

        public String build() {
            return route.toString();
        }
    }

}

