package com.merapar.graphql;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

import java.time.Duration;
import java.time.Instant;

public class Scalars {

    private Scalars() {
        throw new IllegalAccessError("Scalars class");
    }

    public final static GraphQLScalarType GraphQlInstant = new GraphQLScalarType("Instant", "Built-in java.time.Instant", new Coercing() {

        @Override
        public Instant serialize(Object input) {
            return getInstant(input);
        }

        @Override
        public Instant parseValue(Object input) {
            return getInstant(input);
        }

        @Override
        public Instant parseLiteral(Object input) {
            return getInstant(input);
        }

        private Instant getInstant(Object input) {
            if (input instanceof Instant) {
                return (Instant) input;
            } else if (input instanceof String) {
                return Instant.parse((String) input);
            } else if (input instanceof StringValue) {
                return Instant.parse(((StringValue) input).getValue());
            }

            return null;
        }
    });

    public final static GraphQLScalarType GraphQlDuration = new GraphQLScalarType("Duration", "Built-in java.time.Duration", new Coercing() {
        @Override
        public Duration serialize(Object input) {
            return getDuration(input);
        }

        @Override
        public Duration parseValue(Object input) {
            return getDuration(input);
        }

        @Override
        public Duration parseLiteral(Object input) {
            return getDuration(input);
        }

        private Duration getDuration(Object input) {
            if (input instanceof Duration) {
                return (Duration) input;
            } else if (input instanceof String) {
                return Duration.parse((String) input);
            } else if (input instanceof StringValue) {
                return Duration.parse(((StringValue) input).getValue());
            }
            return null;
        }
    });
}
