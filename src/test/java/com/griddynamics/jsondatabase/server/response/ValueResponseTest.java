package com.griddynamics.jsondatabase.server.response;

import com.google.gson.JsonPrimitive;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueResponseTest {

  private static final JsonPrimitive value1 = new JsonPrimitive("val1");
  private static final JsonPrimitive value2 = new JsonPrimitive("val2");

  @Test
  void testEqualsReflexivity() {
    // given
    ValueResponse valueResponse = new ValueResponse(OutputMessages.OK, value1);
    // then
    assertEquals(valueResponse, valueResponse);
  }

  @Test
  void testEqualsSymmetry() {
    // given
    ValueResponse valueResponse1 = new ValueResponse(OutputMessages.OK, value1);
    ValueResponse valueResponse2 = new ValueResponse(OutputMessages.OK, value1);
    // then
    assertEquals(valueResponse1, valueResponse2);
    assertEquals(valueResponse1, valueResponse2);
  }

  @Test
  void testEqualsTransitivity() {
    // given
    ValueResponse valueResponse1 = new ValueResponse(OutputMessages.OK, value1);
    ValueResponse valueResponse2 = new ValueResponse(OutputMessages.OK, value1);
    ValueResponse valueResponse3 = new ValueResponse(OutputMessages.OK, value1);
    // then
    assertEquals(valueResponse1, valueResponse2);
    assertEquals(valueResponse2, valueResponse3);
    assertEquals(valueResponse1, valueResponse3);
  }

  @Test
  void testEqualsConsistency() {
    // given
    ValueResponse valueResponse1 = new ValueResponse(OutputMessages.OK, value1);
    ValueResponse valueResponse2 = new ValueResponse(OutputMessages.OK, value1);
    // then
    assertEquals(valueResponse1, valueResponse2);
    assertEquals(valueResponse1, valueResponse2);
  }

  @Test
  void testEqualsNonNullity() {
    // given
    ValueResponse valueResponse2 = new ValueResponse(OutputMessages.OK, value1);
    // then
    assertNotEquals(null, valueResponse2);
  }

  @Test
  void testEqualsDifferentReasons() {
    // given
    ValueResponse valueResponse1 = new ValueResponse(OutputMessages.OK, value1);
    ValueResponse valueResponse2 = new ValueResponse(OutputMessages.OK, value2);
    // then
    assertNotEquals(valueResponse1, valueResponse2);
  }

  @Test
  void testEqualsDifferentClasses() {
    // given
    ValueResponse valueResponse1 = new ValueResponse(OutputMessages.OK, value1);
    Object otherObject = new Object();
    // then
    assertNotEquals((valueResponse1), otherObject);
  }

  @Test
  void testHashCodeConsistency() {
    // given
    ValueResponse valueResponse1 = new ValueResponse(OutputMessages.OK, value1);
    int initialHashCode = valueResponse1.hashCode();
    // then
    assertEquals(initialHashCode, valueResponse1.hashCode());
    assertEquals(initialHashCode, valueResponse1.hashCode());
  }

  @Test
  void testEqualObjectsHaveEqualHashCodes() {
    // given
    ValueResponse valueResponse1 = new ValueResponse(OutputMessages.OK, value1);
    ValueResponse valueResponse2 = new ValueResponse(OutputMessages.OK, value1);
    // then
    assertEquals(valueResponse1, valueResponse2);
    assertEquals(valueResponse1.hashCode(), valueResponse2.hashCode());
  }

  @Test
  void testUnequalObjectsCanHaveDifferentHashCodes() {
    // given
    ValueResponse valueResponse1 = new ValueResponse(OutputMessages.OK, value1);
    ValueResponse valueResponse2 = new ValueResponse(OutputMessages.OK, value2);
    // then
    assertNotEquals(valueResponse1, valueResponse2);
    assertNotEquals(valueResponse1.hashCode(), valueResponse2.hashCode());
  }
}
