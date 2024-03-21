package com.griddynamics.jsondatabase.client.input;

import com.beust.jcommander.Parameter;
import lombok.Getter;

@Getter
public class ClientArgs {
  @Parameter(
      names = {"--input", "-in"},
      description = "input file to read Request from")
  private String input;

  @Parameter(
      names = {"--type", "-t"},
      description = "Type of request (set, get, delete, exit)")
  private String type;

  @Parameter(
      names = {"--key", "-k"},
      description = "Key of the record in JSON database")
  private String key;

  @Parameter(
      names = {"--value", "-v"},
      description = "Value to set in the cell")
  private String value;
}
