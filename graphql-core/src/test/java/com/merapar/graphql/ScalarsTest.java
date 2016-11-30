package com.merapar.graphql;

import graphql.language.StringValue;
import lombok.val;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ScalarsTest {

    @Test
    public void instant_ParseValue_String() {
        // Given
        val input = "2016-11-21T06:00:00Z";

        // When
        val parseResult = Scalars.GraphQlInstant.getCoercing().parseValue(input);

        // Then
        assertThat(parseResult).isEqualTo(LocalDateTime.of(2016, 11, 21, 6, 0).toInstant(ZoneOffset.UTC));
    }

    @Test
    public void instant_ParseValue_Instant() {
        // Given
        val input = LocalDateTime.of(2016, 11, 21, 6, 0).toInstant(ZoneOffset.UTC);

        // When
        val parseResult = Scalars.GraphQlInstant.getCoercing().parseValue(input);

        // Then
        assertThat(parseResult).isEqualTo(LocalDateTime.of(2016, 11, 21, 6, 0).toInstant(ZoneOffset.UTC));
    }

    @Test
    public void instant_ParseLiteral_OtherType_ShouldReturnNull() {
        // Given
        Integer input = 4;

        // When
        val parseResult = Scalars.GraphQlInstant.getCoercing().parseLiteral(input);

        // Then
        assertThat(parseResult).isNull();
    }

    @Test
    public void instant_Serialize_StringValue() {
        // Given
        val input = new StringValue("2016-11-21T06:00:00Z");

        // When
        val parseResult = Scalars.GraphQlInstant.getCoercing().serialize(input);

        // Then
        assertThat(parseResult).isEqualTo(LocalDateTime.of(2016, 11, 21, 6, 0).toInstant(ZoneOffset.UTC));
    }

    @Test
    public void instant_ParseLiteral_WrongStringInput_ShouldThrowDateTimeParseException() {
        // Given
        val input = new StringValue("2016-11-21T06:00:");

        // When
        val parseExcpetion = assertThatThrownBy(() -> Scalars.GraphQlInstant.getCoercing().parseLiteral(input));

        // Then
        parseExcpetion.isExactlyInstanceOf(DateTimeParseException.class).hasMessage("Text '2016-11-21T06:00:' could not be parsed at index 17");
    }

    @Test
    public void duration_ParseValue_String() {
        // Given
        val input = "PT48H";

        // When
        val parseResult = Scalars.GraphQlDuration.getCoercing().parseValue(input);

        // Then
        assertThat(parseResult).isEqualTo(Duration.ofHours(48));
    }

    @Test
    public void duration_ParseValue_Duration() {
        // Given
        val input = Duration.ofHours(48);

        // When
        val parseResult = Scalars.GraphQlDuration.getCoercing().parseValue(input);

        // Then
        assertThat(parseResult).isEqualTo(Duration.ofHours(48));
    }

    @Test
    public void duration_ParseLiteral_OtherType_ShouldReturnNull() {
        // Given
        Integer input = 4;

        // When
        val parseResult = Scalars.GraphQlDuration.getCoercing().parseLiteral(input);

        // Then
        assertThat(parseResult).isNull();
    }

    @Test
    public void duration_Serialize_StringValue() {
        // Given
        val input = new StringValue("PT48H");

        // When
        val parseResult = Scalars.GraphQlDuration.getCoercing().serialize(input);

        // Then
        assertThat(parseResult).isEqualTo(Duration.ofHours(48));
    }

    @Test
    public void duration_ParseLiteral_WrongStringInput_ShouldThrowDateTimeException() {
        // Given
        val input = new StringValue("P48H");

        // When
        val parseExcpetion = assertThatThrownBy(() -> Scalars.GraphQlDuration.getCoercing().parseLiteral(input));

        // Then
        parseExcpetion.isExactlyInstanceOf(DateTimeParseException.class).hasMessage("Text cannot be parsed to a Duration");
    }
}