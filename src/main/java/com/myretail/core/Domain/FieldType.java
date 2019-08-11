package com.myretail.core.Domain;

public enum FieldType {
  INT("int"),
  STRING("java.lang.String"),
  MAP("java.util.Map"),
  URL("java.net.URL"),
  LOCAL_DATE("java.time.LocalDate"),
  OBJECT("Java.lang.Object");

  private final String name;

  FieldType(String name) {
    this.name = name;
  }

  public static FieldType fromName(String n) {
    for(FieldType ft : FieldType.values()) {
      if(ft.name.equalsIgnoreCase(n)) {
        return ft;
      }
    }
    return FieldType.OBJECT;
  }
}
