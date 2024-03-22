package com.griddynamics.jsondatabase.server.response;

import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

  @Test
  void testEqualsReflexivity() {
    // given
    ErrorResponse errorResponse =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    // then
    assertEquals(errorResponse, errorResponse);
  }

  @Test
  void testEqualsSymmetry() {
    // given
    ErrorResponse errorResponse1 =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    ErrorResponse errorResponse2 =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    // then
    assertEquals(errorResponse1, errorResponse2);
    assertEquals(errorResponse2, errorResponse1);
  }

  @Test
  void testEqualsTransitivity() {
    // given
    ErrorResponse errorResponse1 =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    ErrorResponse errorResponse2 =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    ErrorResponse errorResponse3 =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    // then
    assertEquals(errorResponse1, errorResponse2);
    assertEquals(errorResponse2, errorResponse3);
    assertEquals(errorResponse1, errorResponse3);
  }

  @Test
  void testEqualsConsistency() {
    // given
    ErrorResponse errorResponse1 =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    ErrorResponse errorResponse2 =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    // then
    assertEquals(errorResponse1, errorResponse2);
    assertEquals(errorResponse1, errorResponse2);
  }

  @Test
  void testEqualsNonNullity() {
    // given
    ErrorResponse errorResponse =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    // then
    assertNotEquals(null, errorResponse);
  }

  @Test
  void testEqualsDifferentReasons() {
    // given
    ErrorResponse errorResponse1 =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    ErrorResponse errorResponse2 = new ErrorResponse(OutputMessages.ERROR, "Invalid");
    // then
    assertNotEquals(errorResponse1, errorResponse2);
  }

  @Test
  void testEqualsDifferentClasses() {
    // given
    ErrorResponse errorResponse =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    Object otherObject = new Object();
    // then
    assertNotEquals(errorResponse, otherObject);
  }

  @Test
  void testHashCodeConsistency() {
    // given
    ErrorResponse errorResponse =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    int initialHashCode = errorResponse.hashCode();
    // then
    assertEquals(initialHashCode, errorResponse.hashCode());
    assertEquals(initialHashCode, errorResponse.hashCode());
  }

  @Test
  void testEqualObjectsHaveEqualHashCodes() {
    // given
    ErrorResponse errorResponse1 =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    ErrorResponse errorResponse2 =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    // then
    assertEquals(errorResponse1, errorResponse2);
    assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());
  }

  @Test
  void testUnequalObjectsCanHaveDifferentHashCodes() {
    // given
    ErrorResponse errorResponse1 =
        new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    ErrorResponse errorResponse2 = new ErrorResponse(OutputMessages.ERROR, "Some reason");
    // then
    assertNotEquals(errorResponse1, errorResponse2);
    assertNotEquals(errorResponse1.hashCode(), errorResponse2.hashCode());
  }
}
